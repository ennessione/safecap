package uk.ac.ncl.safecap.capacity.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import safecap.Project;
import safecap.model.Line;
import safecap.model.Point;
import servicepattern.Pattern;
import traintable.Train;
import uk.ac.ncl.safecap.capacity.experiment.ExperimentRegistry;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.capacity.heatmap.HeatmapRegister;
import uk.ac.ncl.safecap.capacity.views.TrainRunReport;
import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.gui.trainconfig.ExtendedTrainDescriptor;
import uk.ac.ncl.safecap.gui.trainconfig.ServicePatternUtil;
import uk.ac.ncl.safecap.gui.trainconfig.TrainConfig;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.misc.core.LineUtil;
import uk.ac.ncl.safecap.scitus.adaptive.PointLockingStep;
import uk.ac.ncl.safecap.scitus.adaptive.ScenarioPoint;
import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.IActor;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.World;
import uk.ac.ncl.safecap.scitus.common.SimulationException;
import uk.ac.ncl.safecap.scitus.routeinterlck.MBInterlocking;

public class SimulationProvider {

	public static World buildSimulationWorld(Project project, boolean shifts) throws SimulationException {
		final List<Train> trains = TrainConfig.getTrainTypes();
		if (trains == null) {
			showError("Simulation aborted", "No train classes are defined");
			return null;
		}

		final List<Pattern> rules = TrainConfig.getServicePatterns(project);
		if (rules == null || rules.size() == 0) {
			showError("Simulation aborted", "No service pattern has been defined for this project");
			return null;
		}

		final World world = new World(project);
		world.setAllowDisruption(shifts);
		new MBInterlocking(world);

		for (final Pattern pattern : rules) {
			final String class_name = pattern.getTrainClass();
			final Train train_class = TrainConfig.resolveTrain(trains, class_name);
			if (train_class == null) {
				showError("Simulation aborted", "Train class '" + class_name + "' is undefined.");
				return null;
			}

			final int start = pattern.getStart();
			if (start < 0) {
				showError("Simulation aborted", "Start time cannot be less than zero.");
				return null;
			}

			if (pattern.getTrainName() == null || pattern.getTrainName().length() == 0) {
				showError("Simulation aborted", "Train must be labeled.");
				return null;
			}

			final Line line = LineUtil.getByLabel(project, pattern.getLine());
			if (line == null) {
				showError("Simulation aborted", "Line '" + pattern.getLine() + "' is undefined.");
				return null;
			}

			final TrainDescriptor descr = getDescriptor(pattern.getTrainName(), train_class, pattern);

			if (start == 0) {
				world.addTrain(line, descr);
			} else {
				world.addTrainAtTime(line, descr, start);
			}
		}

		return world;
	}

	public static TrainDescriptor getDescriptor(String name, Train train, Pattern pattern) {
		if (train.getAttributes().isEmpty()) {
			return new TrainDescriptor(train.getName(), name, train.getSpeed(), train.getAcceleration(), train.getDeceleration(),
					train.getLength(), ServicePatternUtil.getColor(pattern));
		} else {
			try {
				return new ExtendedTrainDescriptor(train, name, ServicePatternUtil.getColor(pattern));
			} catch (final Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static void simulation(Project project, IProgressMonitor monitor) throws SimulationException {
		final World world = buildSimulationWorld(project, true);
		if (world == null) {
			return;
		}

		world.simulation(monitor);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				reportResults(world);
			}
		});
	}

	private static void showError(final String title, final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openError(null, title, message);
			}
		});
	}

	public static void reportResults(World world) {
		final List<S1TrainActor> deadlocked = new ArrayList<>();

		for (final IActor actor : world.getActors()) {
			if (actor instanceof S1TrainActor) {
				deadlocked.add((S1TrainActor) actor);
			}
		}

		if (deadlocked.size() > 0) {
			showError("Simulation deadlock", "Trains " + deadlocked.toString() + " deadlocked.");
			return;
		}

		final SimulationExperiment experiment = new SimulationExperiment("scitus", world, 50000);
		HeatmapRegister.addHeatmap(world.getProject(), experiment);
		ExperimentRegistry.getInstance().putExperiment(world.getProject(), experiment);

		// MBInterlocking intrl = (MBInterlocking) world.getInterlocking();
		// intrl.getState().printState();

		// System.out.println("World end time:" + world.getTime());

		// ppAdaptivePointsInfo(world);

		// ppHistory(world);

		TrainRunReport.open(experiment);

		// DistanceGraph graph = new DistanceGraph(experiment);
		// graph.create();

		// ScenarioPointReport.open(world);
	}

	public static void reportOverlayedResults(World world, World overlay) {
		final List<S1TrainActor> deadlocked = new ArrayList<>();

		for (final IActor actor : world.getActors()) {
			if (actor instanceof S1TrainActor) {
				deadlocked.add((S1TrainActor) actor);
			}
		}

		if (deadlocked.size() > 0) {
			showError("Simulation deadlock", "Trains " + deadlocked.toString() + " deadlocked.");
			return;
		}

		final SimulationExperiment experiment = new SimulationExperiment("scitus", world, 50000);
		experiment.overlay(new SimulationExperiment("scitus", overlay, 50000));

		HeatmapRegister.addHeatmap(world.getProject(), experiment);
		ExperimentRegistry.getInstance().putExperiment(world.getProject(), experiment);

		// MBInterlocking intrl = (MBInterlocking) world.getInterlocking();
		// intrl.getState().printState();

		// System.out.println("World end time:" + world.getTime());

		// ppAdaptivePointsInfo(world);

		// ppHistory(world);

		// DistanceGraph graph = new DistanceGraph(experiment);
		// graph.create();

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("uk.ac.ncl.safecap.replay.ReplayView");
		} catch (final PartInitException e) {
			e.printStackTrace();
		}
	}

	private static void ppAdaptivePointsInfo(World world) {
		final Map<Point, ScenarioPoint> scen_point = world.getAdaptiveControlState().getScenarioPoints();
		for (final Point p : scen_point.keySet()) {
			ppAdaptivePointInfo(scen_point.get(p));
		}
	}

	private static void ppAdaptivePointInfo(ScenarioPoint sp) {
		System.out.println("===== History of point " + sp.getPoint().getNode().getLabel() + " =====");
		System.out.println("===== Locking:");
		for (final PointLockingStep lp : sp.getLockingSteps()) {
			System.out.println("\t" + lp.toString());
		}

		System.out.println("\n===== Occupation:");
		for (final PointLockingStep lp : sp.getOccupationSteps()) {
			System.out.println("\t" + lp.toString());
		}

	}

	public static void ppHistory(World world) {
		double tt = 0d;
		for (final Evolution evolution : world.getHistory()) {
			ppEvolution(evolution, tt);
			tt += evolution.getExtent();
		}
	}

	private static void ppEvolution(Evolution evolution, double time) {
		System.out.println("Extent:" + Delta.round2(evolution.getExtent()) + "; time: " + Delta.round2(time));
		for (final Progression pg : evolution.getProgressions()) {
			System.out.println("\t" + pg.toString());
		}
	}

}
