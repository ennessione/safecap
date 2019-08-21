package uk.ac.ncl.safecap.sim3.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

import safecap.Project;
import safecap.model.Line;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.IWorld;
import uk.ac.ncl.safecap.scitus.stat.Run2DRecord;
import uk.ac.ncl.safecap.scitus.stat.SimHistoryRecord;
import uk.ac.ncl.safecap.sim3.routebased.RBInterlockingState;

public class S3World implements IWorld {
	private static final int IDLE_MAX = 10000;

	protected transient Map<Line, TrainLine> lines;

	private final Project project;
	private S3Interlocking interlocking;
	private final Set<ITrain> trains;
	private final double timeDelta;
	private double timeWindow = Double.POSITIVE_INFINITY;
	private long steps = 0;

	private final Set<ITrain> newTrains, removedTrains;
	private final SimHistoryRecord auxRecord;
	private final Run2DRecord timeRecord;

	public S3World(Project project, double timeDelta) {
		this.project = project;
		this.timeDelta = timeDelta;
		trains = new HashSet<>();
		newTrains = new HashSet<>();
		removedTrains = new HashSet<>();
		lines = new HashMap<>(project.getLines().size());
		auxRecord = new SimHistoryRecord();
		timeRecord = new Run2DRecord("", "time");

		for (final Line l : project.getLines()) {
			lines.put(l, new TrainLine(l));
		}

	}

	public Run2DRecord getTimeRecord() {
		return timeRecord;
	}

	public double getTimeDelta() {
		return timeDelta;
	}

	public void setInterlocking(S3Interlocking interlocking) {
		this.interlocking = interlocking;
	}

	public S3Interlocking getInterlocking() {
		return interlocking;
	}

	public void addTrain(IS3Train train) {
		newTrains.add(train);
		train.start(this);
	}

	public void removeTrain(IS3Train train) {
		removedTrains.add(train);
	}

	public Object getState() {
		return interlocking.getState();
	}

	@Override
	public void simulation(IProgressMonitor monitor) {
		final Set<ITrain> activeTrains = new HashSet<>();

		interlocking.start();
		final long time_start = System.currentTimeMillis();
		int idleSteps = 0;
		boolean allDone = false;
		while (!allDone && !monitor.isCanceled() && getTime() < timeWindow) {
			trains.addAll(newTrains);
			trains.removeAll(removedTrains);
			newTrains.clear();
			removedTrains.clear();

			allDone = true;
			activeTrains.clear();
			double maxSpeed = 0;
			for (final ITrain _train : trains) {
				final IS3Train train = (IS3Train) _train;
				if (!train.isCompleted()) {
					final boolean moved = train.move(interlocking);
					if (moved) {
						activeTrains.add(train);
						idleSteps = 0;
					}
				}
				maxSpeed = Double.max(maxSpeed, train.getSpeed());
				allDone = allDone && train.isCompleted();
			}
			steps++;
			idleSteps++;
			allDone = allDone && interlocking.isCompleted();

			// do state update
			interlocking.step(activeTrains);

			if (allDone) {
				System.out.println("All done at step " + steps);
			}

			if (idleSteps > IDLE_MAX) {
				System.out.println("Deadlock detected at step " + steps);
				break;
			}

			// do delta update
			// timeDelta = maxSpeed > 1.0 ? (0.2 / Math.sqrt(maxSpeed)) : 0.2;
		}

		interlocking.end();

		final long time_end = System.currentTimeMillis();
		System.out.println("Simulation time " + (time_end - time_start) / 1000.0);

		((RBInterlockingState) getState()).printState();
	}

	@Override
	public Set<ITrain> getTrains() {
		return trains;
	}

	@Override
	public double getTime() {
		return steps * timeDelta;
	}

	@Override
	public void setTimeWindow(double timeW) {
		timeWindow = timeW;
	}

	@Override
	public Project getProject() {
		return project;
	}

	public TrainLine getTrainLine(Line line) {
		return lines.get(line);
	}

	@Override
	public SimHistoryRecord getWorldAuxRecord() {
		return auxRecord;
	}
}
