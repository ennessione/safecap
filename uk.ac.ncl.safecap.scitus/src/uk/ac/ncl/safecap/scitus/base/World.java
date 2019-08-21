package uk.ac.ncl.safecap.scitus.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.RGB;

import safecap.Project;
import safecap.model.Line;
import safecap.model.Point;
import safecap.model.Route;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.misc.core.PointSequence;
import uk.ac.ncl.safecap.misc.core.PointUtil;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.adaptive.AdaptiveControlState;
import uk.ac.ncl.safecap.scitus.adaptive.ScenarioPoint;
import uk.ac.ncl.safecap.scitus.common.ImminentCollisionException;
import uk.ac.ncl.safecap.scitus.common.SimulationException;
import uk.ac.ncl.safecap.scitus.common.TrainOverlapException;
import uk.ac.ncl.safecap.scitus.routeinterlck.MBInterlocking;
import uk.ac.ncl.safecap.scitus.routeinterlck.OptimalDriver;
import uk.ac.ncl.safecap.scitus.stat.SimHistoryRecord;

public class World implements Serializable, IWorld {
	private static final long serialVersionUID = -317289717155713775L;

	private static final RGB extraTrain = new RGB(255, 120, 120);

	public enum KIND {
		EXTENSIVE, INTENSIVE
	};

	protected List<IActor> actors;
	protected List<IActor> suspended;
	protected List<IActor> removed;
	protected Project project;
	protected transient Map<Line, TrainLine> lines;
	protected List<Evolution> history;
	protected TrainController interlocking;

	protected List<IWorldEvent> events;
	protected List<IWorldEvent> gray_events; // used to temporarily hold new events while events are processed

	private InstanceProgression instance_progression_at_start;
	private InstanceProgression instance_progression_at_end;

	protected double time; // current time

	// adaptive control extra state
	protected AdaptiveControlState adaptiveControlState;

	private double timeWindow = Double.POSITIVE_INFINITY;

	// adaptive control point sequences
	private Map<Point, PointSequence> sequences;

	private KIND kind;

	private boolean allowDisruption = true;

	public World(Project project) {
		this.project = project;
		actors = new ArrayList<>();
		suspended = new ArrayList<>();
		removed = new ArrayList<>();
		lines = new HashMap<>(project.getLines().size());

		events = new ArrayList<>(20);
		gray_events = new ArrayList<>(20);

		kind = KIND.EXTENSIVE;

		time = 0d;

		adaptiveControlState = new AdaptiveControlState();

		history = new ArrayList<>(100);

		for (final Line l : project.getLines()) {
			lines.put(l, new TrainLine(l));
		}

		final Evolution initial = new Evolution();

		instance_progression_at_start = new InstanceProgression(0d);
		initial.addProgression(instance_progression_at_start);
		history.add(initial);

	}

	@Override
	public Set<ITrain> getTrains() {
		final Set<ITrain> trains = new HashSet<>();
		for (final Evolution evol : getHistory()) {
			for (final Progression pg : evol.getProgressions()) {
				if (pg instanceof TrainProgression) {
					final TrainProgression tpg = (TrainProgression) pg;
					trains.add((ITrain) tpg.getActor());
				}
			}
		}

		return trains;
	}

	public void insertTrainShift(Line line, String trainName, double trainLength, Point point, double offset, double speed, double delay) {
		final Segment segment = PointUtil.getAnySegment(point);
		final TrainLine tline = lines.get(line);
		final Route route = tline.getSegmentRoute(segment);
		Route prev = null;
		for (final Route r : line.getRoutes()) {
			if (r == route) {
				break;
			}
			prev = r;
		}

		if (prev == null) {
			return;
		}

		final double length = RouteUtil.getLength(prev) - trainLength - 10;
		double offset_route = length - offset;
		if (offset_route < 0) {
			offset_route = 0;
		}

		offset_route += 10;

		// final double position = tline.getSegmentOffset(prev.getSegments().get(0)) + offset_route;

	}

	public KIND getKind() {
		return kind;
	}

	public void setKind(KIND kind) {
		this.kind = kind;
	}

	public boolean isDeadlocked() {
		for (final IActor actor : actors) {
			if (actor instanceof S1TrainActor) {
				return true;
			}
		}

		for (final IActor actor : suspended) {
			if (actor instanceof S1TrainActor) {
				return true;
			}
		}

		return false;
	}

	public void setSequences(Map<Point, PointSequence> sequences) {
		this.sequences = sequences;
	}

	public void setZeroStateSequences(Map<Point, PointSequence> seq) {
		if (sequences != null) {
			sequences.clear();
		} else {
			sequences = new HashMap<>(seq.size());
		}

		for (final Point p : seq.keySet()) {
			final PointSequence ps = seq.get(p).copy();
			ps.setState(0);
			sequences.put(p, ps);
		}
	}

	public void setStatefulSequences(Map<Point, PointSequence> seq) {
		if (sequences != null) {
			sequences.clear();
		} else {
			sequences = new HashMap<>(seq.size());
		}

		for (final Point p : seq.keySet()) {
			final PointSequence ps = seq.get(p).copy();
			ps.setState(getSequenceState(p));
			sequences.put(p, ps);
		}
	}

	public int getSequenceState(Point point) {
		// if (sequences != null && sequences.containsKey(point)) {
		// return sequences.get(point).getState();
		// } else {
		final ScenarioPoint sp = getAdaptiveControlState().getScenarioPoints().get(point);
		if (sp != null) {
			return sp.getLockingSteps().size();
		} else {
			return 0;
			// }
		}
	}

	public PointSequence getSequence(Point point) {
		if (sequences == null) {
			return null;
		}

		return sequences.get(point);
	}

	public Map<Point, PointSequence> getSequences() {
		return sequences;
	}

	/**
	 * Extent of time for which simulation is run
	 */
	@Override
	public void setTimeWindow(double timeWindow) {
		this.timeWindow = timeWindow;
	}

	public AdaptiveControlState getAdaptiveControlState() {
		return adaptiveControlState;
	}

	public void addTrain(Line line, TrainDescriptor descr) {
		assert line != null;
		final TrainLine tl = getTrainLine(line);
		final S1TrainActor tr = new S1TrainActor(tl, descr, interlocking, new OptimalDriver());
		addActor(tr);
	}

	public void addEvent(IWorldEvent event) {
		events.add(event);
	}

	public void addGrayEvent(IWorldEvent event) {
		gray_events.add(event);
	}

	public void addTrainAtTime(Line line, TrainDescriptor descr, double when) {
		final WorldTimeEvent event = new AddActorEvent(line, descr, this, when);
		events.add(event);
	}

	public TrainController getInterlocking() {
		return interlocking;
	}

	public void setInterlocking(TrainController interlocking) {
		this.interlocking = interlocking;
	}

	public List<Evolution> getHistory() {
		return history;
	}

	public void addActor(IActor actor) {
		actors.add(actor);
	}

	public List<IActor> getActors() {
		return actors;
	}

	public List<IActor> getSuspendedActors() {
		return suspended;
	}

	public boolean hasActors() {
		return actors.size() != 0;
	}

	@Override
	public Project getProject() {
		return project;
	}

	public Collection<TrainLine> getLines() {
		return lines.values();
	}

	public TrainLine getTrainLine(Line line) {
		return lines.get(line);
	}

	public void removeActor(IActor actor) {
		actors.remove(actor);
	}

	@Override
	public void simulation(IProgressMonitor monitor) throws SimulationException {
		while (!step(monitor)) {
			;
		}
	}

	/**
	 * An iterated simulation step
	 * 
	 * @return true if simulation is complete and false otherwise
	 * @throws TrainOverlapException
	 */
	public boolean simulation(int steps, IProgressMonitor monitor) throws SimulationException {
		while (steps > 1 && !step(monitor)) {
			steps--;
		}
		if (steps == 1) {
			return step(monitor);
		} else {
			return true;
		}
	}

	/**
	 * Get current world time
	 * 
	 * @return time, in seconds since start
	 */
	@Override
	public double getTime() {
		return time;
	}

	public InstanceProgression getInstanceProgressionAtStart() {
		return instance_progression_at_start;
	}

	public InstanceProgression getInstanceProgressionAtEnd() {
		return instance_progression_at_end;
	}

	/**
	 * A single simulation step
	 * 
	 * @return true if simulation is complete and false otherwise
	 * @throws TrainOverlapException
	 */
	public boolean step(IProgressMonitor monitor) throws SimulationException {
		if (monitor.isCanceled()) {
			monitor.done();
			return false;
		}

		final double eh = eventHorizon();
		// System.out.println("Time horizon:" + Delta.round2(eh) + "; acc: " +
		// Delta.round2(time));

		if (eh == Double.POSITIVE_INFINITY || eh <= 0) { // nothing to do
			return true;
		}

		final Evolution evolution = new Evolution(eh);

		instance_progression_at_start = new InstanceProgression(0d);
		instance_progression_at_end = new InstanceProgression(eh);

		for (final IActor actor : actors) {
			// System.out.println("Moving actor " + actor);
			final Progression pg = actor.progress(eh);
			if (pg != null) {
				evolution.addProgression(pg);
			}
		}

		time += eh;

		// process internal events
		attendEvents(eh);

		if (instance_progression_at_start.getInstances().size() > 0) {
			evolution.addProgression(instance_progression_at_start);
		}

		if (instance_progression_at_end.getInstances().size() > 0) {
			evolution.addProgression(instance_progression_at_end);
		}

		history.add(evolution);

		final boolean done = actors.size() == 0 && internalHorizon() == Double.POSITIVE_INFINITY; // suspended do not become unsuspended
																									// when there are no active actors

		return done;
	}

	public double eventHorizon() throws SimulationException {
		// double min_eh = internalHorizon();
		if (time >= timeWindow) {
			return Double.POSITIVE_INFINITY;
		}

		double min_eh = Double.POSITIVE_INFINITY;

		actors.addAll(suspended);
		suspended.clear();

		for (final IActor actor : actors) {

			double eh;
			try {
				eh = actor.eventHorizon();
			} catch (final ImminentCollisionException e) {
				if (getInterlocking() instanceof MBInterlocking) {
					final MBInterlocking ilck = (MBInterlocking) getInterlocking();
					ilck.getState().printState();
				}
				e.printStackTrace();

				throw new ImminentCollisionException(e.getMessage());
			}

			// System.out.println("Time horizon: " + actor.toString() + " -> " + eh);

			if (Double.isNaN(eh)) {
				removed.add(actor);
				// System.out.println("Removed " + actor);
			} else if (eh == 0 || eh == Double.NEGATIVE_INFINITY) {
				suspended.add(actor);
				// System.out.println("Suspended " + actor);
			} else if (eh < min_eh) {
				min_eh = eh;
			}
		}

		final double eh = internalHorizon();
		if (eh < min_eh) {
			min_eh = eh;
		}

		if (!Double.isInfinite(min_eh) && min_eh > timeWindow - time) {
			min_eh = timeWindow - time;
		}

		// if (removed.size() > 0) System.out.println("Removed: " + removed);
		// if (suspended.size() > 0) System.out.println("Suspended: " + suspended);

		actors.removeAll(removed);
		actors.removeAll(suspended);
		removed.clear();

		return min_eh;
	}

	/**
	 * event horizon determined by the registered WorldEvents
	 * 
	 * @return
	 */
	private double internalHorizon() {
		double horizon = Double.POSITIVE_INFINITY;
		for (final IWorldEvent _we : events) {
			if (_we instanceof WorldTimeEvent) {
				final WorldTimeEvent we = (WorldTimeEvent) _we;
				final double t = we.when - time;
				assert t > 0;
				if (t < horizon) {
					horizon = t;
				}
			}
		}
		return horizon;
	}

	// private void attendEvents(double horizon)
	// {
	// List<WorldTimeEvent> fired = new ArrayList<WorldTimeEvent>();
	//
	// for(WorldTimeEvent we: events) {
	// if (we.when >= time && we.when <= time + horizon) {
	// assert we.when == time + horizon;
	// we.fire();
	// fired.add(we);
	// }
	// }
	//
	// events.removeAll(fired);
	// }

	private void attendEvents(double horizon) {
		final List<IWorldEvent> fired = new ArrayList<>();

		for (final IWorldEvent we : events) {
			final Object arg = we.happens();
			if (arg != null) {
				if (we.fire(arg)) {
					fired.add(we);
				}
			}
		}

		events.removeAll(fired);
		events.addAll(gray_events);
		gray_events.clear();
	}

	// private void notifyListeners()
	// {
	// for(IWorldListener listener: listeners) {
	// listener.worldChanged(this);
	// }
	// }

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		for (final IActor actor : actors) {
			sb.append(actor.toString());
			sb.append('\n');
		}

		return sb.toString();
	}

	public boolean isAllowDisruption() {
		return allowDisruption;
	}

	public void setAllowDisruption(boolean allowDisruption) {
		this.allowDisruption = allowDisruption;
	}

	@Override
	public SimHistoryRecord getWorldAuxRecord() {
		// TODO
		return null;
	}
}
