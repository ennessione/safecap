package uk.ac.ncl.safecap.capacity.experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import safecap.trackside.Signal;
import servicepattern.Pattern;
import uk.ac.ncl.safecap.gui.trainconfig.ServicePatternUtil;
import uk.ac.ncl.safecap.gui.trainconfig.TrainConfig;
import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.IWorld;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.World;
import uk.ac.ncl.safecap.scitus.stat.ITrainRun;
import uk.ac.ncl.safecap.scitus.stat.ITrainWithRun;

public class SimulationExperiment extends Experiment {
	private final HashMap<ITrain, ITrainRun> data;
	private final HashMap<Signal, SignalRun> signalData = new HashMap<>();
	private final HashMap<ITrain, RGB> colour;
	private HashMap<ITrain, Color> deviceColors = new HashMap<>();

	private final IWorld world;
	private final Set<ITrain> trains;
	private double totalTime;
	private double[] times;

	/**
	 * Merge two experiments into a single one (for overlayed simulation replay)
	 */
	public void overlay(SimulationExperiment zz) {
		data.putAll(zz.data);

		for (final ITrain ta : zz.trains) {
			final ITrain xta = resolveTrainbyName(ta.getDescriptor().getTrainName());
			final RGB c = colour.get(xta);
			setTrainColour(ta, new RGB(c.red, c.green, c.blue));
		}

	}

	public int getVOffset(ITrain train) {
		if (trains.contains(train)) {
			return 0;
		} else {
			return -12;
		}
	}

	public double getTotalTime() {
		return totalTime;
	}

	public List<Evolution> getHistory() {
		return ((World) world).getHistory();
	}

	public SignalRun getSignalData(Signal signal) {
		if (signalData.containsKey(signal)) {
			return signalData.get(signal);
		} else {
			final SignalRun data = new SignalRun(signal, (World) world, this);
			signalData.put(signal, data);
			return data;
		}
	}

	public SimulationExperiment(String kind, IWorld world) {
		super(kind, world.getProject(), System.currentTimeMillis());
		this.world = world;

		trains = world.getTrains();

		totalTime = 0;
		for (final ITrain train : trains) {
			if (train.getTimeExtent() > totalTime) {
				totalTime = train.getTimeExtent();
			}
		}

		data = new HashMap<>(trains.size());
		colour = new HashMap<>(trains.size());

		for (final ITrain train : trains) {
			if (train instanceof ITrainWithRun) {
				data.put(train, ((ITrainWithRun) train).getTrainRun().truncate());
			}
		}

		populateColours();
	}

	public SimulationExperiment(String kind, World world, int points) {
		super(kind, world.getProject(), System.currentTimeMillis());
		this.world = world;

		times = new double[world.getHistory().size()];
		totalTime = 0;
		for (int i = 0; i < world.getHistory().size(); i++) {
			final Evolution ev = world.getHistory().get(i);
			times[i] = totalTime;
			totalTime += ev.getExtent();
		}

		trains = world.getTrains();

		data = new HashMap<>(trains.size());
		colour = new HashMap<>(trains.size());

		for (final ITrain actor : trains) {
			if (actor instanceof S1TrainActor) {
				final S1TrainActor train = (S1TrainActor) actor;
				data.put(train, new TrainRun(train, world, points));
			} else if (actor instanceof ITrainWithRun) {
				final ITrainWithRun train = (ITrainWithRun) actor;
				data.put(train, train.getTrainRun());
			}
		}

		populateColours();
	}

	public Evolution getEvolution(double time) {
		int index = Arrays.binarySearch(times, time);
		if (index < 0) {
			index = -(index + 1) - 1;
		}
		return ((World) world).getHistory().get(index);
	}

	private void populateColours() {
		final List<Pattern> rules = TrainConfig.getServicePatterns(project);
		if (rules == null || rules.size() == 0) {
			return;
		}

		for (final Pattern pattern : rules) {
			final String name = pattern.getTrainName();
			if (name != null) {
				final ITrain actor = resolveTrainbyName(name);
				if (actor != null) {
					final RGB rgb = ServicePatternUtil.getColor(pattern);
					if (rgb != null) {
						setTrainColour(actor, rgb);
					}
				}
			}
		}

	}

	public ITrain resolveTrainbyName(String name) {
		for (final ITrain train : trains) {
			if (train.getName() != null && train.getDescriptor().getTrainName().equals(name)) {
				return train;
			}
		}
		return null;
	}

	public RGB getTrainColour(ITrain train) {
		return colour.get(train);
	}

	public void setTrainColour(ITrain train, RGB rgb) {
		colour.put(train, rgb);
		final Device device = Display.getCurrent();
		final Color color = new Color(device, rgb);
		deviceColors.put(train, color);
	}

	public Color getTrainDeviceColor(ITrain train) {
		return deviceColors.get(train);
	}

	public double getIntegralTime(double start_position, double end_position) {
		double r = 0d;
		for (final ITrain train : trains) {
			final ITrainRun run = data.get(train);
			r += run.getIntegralTime(start_position, end_position);
		}

		return r;
	}

	/**
	 * Note! - the world is dead at this point.
	 * 
	 * @return
	 */
	public IWorld getWorld() {
		return world;
	}

	public Set<ITrain> getTrains() {
		return data.keySet();
	}

	public ITrainRun getRunData(ITrain train) {
		return data.get(train);
	}

	@Override
	protected void finalize() throws Throwable {
		if (deviceColors != null) {
			for (final Color color : deviceColors.values()) {
				color.dispose();
			}
			deviceColors = null;
		}
		super.finalize();
	}

}
