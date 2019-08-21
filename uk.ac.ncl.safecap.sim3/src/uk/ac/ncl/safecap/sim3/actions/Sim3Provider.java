package uk.ac.ncl.safecap.sim3.actions;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import safecap.Project;
import safecap.model.Line;
import servicepattern.Pattern;
import traintable.Train;
import uk.ac.ncl.safecap.capacity.actions.SimulationProvider;
import uk.ac.ncl.safecap.capacity.experiment.ExperimentRegistry;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.capacity.heatmap.HeatmapRegister;
import uk.ac.ncl.safecap.capacity.views.TrainRunReport;
import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.gui.trainconfig.ExtendedTrainDescriptor;
import uk.ac.ncl.safecap.gui.trainconfig.TrainConfig;
import uk.ac.ncl.safecap.misc.core.LineUtil;
import uk.ac.ncl.safecap.scitus.base.IWorld;
import uk.ac.ncl.safecap.scitus.common.SimulationException;
import uk.ac.ncl.safecap.sim3.core.IGlobalState;
import uk.ac.ncl.safecap.sim3.core.S3Interlocking;
import uk.ac.ncl.safecap.sim3.core.S3TrainActor;
import uk.ac.ncl.safecap.sim3.core.S3TrainActorTableBased;
import uk.ac.ncl.safecap.sim3.core.S3World;
import uk.ac.ncl.safecap.sim3.routebased.FixedAdvanceRouteSetter;
import uk.ac.ncl.safecap.sim3.routebased.RBInterlockingState;
import uk.ac.ncl.safecap.sim3.routebased.S3RouteBasedInterlocking;

public class Sim3Provider {

	private static S3World buildSim3World(Project project) {
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

		final S3World world = new S3World(project, 0.2);
		final IGlobalState state = new RBInterlockingState(world, new FixedAdvanceRouteSetter());
		final S3Interlocking interlocking = new S3RouteBasedInterlocking(world, state);
		world.setInterlocking(interlocking);

		placeAllTrains(project, trains, rules, world);

		return world;
	}

	public static void simulation(final Project project, IProgressMonitor monitor) throws SimulationException {
		final SimulationExperiment experiment = ExperimentRegistry.getInstance().getExperiment(project, "sim3");

		if (experiment == null) {
			_simulation(project, monitor);
		} else {
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					HeatmapRegister.addHeatmap(project, experiment);
					TrainRunReport.open(experiment);
				}
			});
		}
	}

	private static void _simulation(Project project, IProgressMonitor monitor) {
		final S3World world = buildSim3World(project);
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

	public static void reportResults(IWorld world) {
		final SimulationExperiment experiment = new SimulationExperiment("sim3", world);
		HeatmapRegister.addHeatmap(world.getProject(), experiment);
		ExperimentRegistry.getInstance().putExperiment(world.getProject(), experiment);
		TrainRunReport.open(experiment);
	}

	private static boolean placeAllTrains(Project project, List<Train> trains, List<Pattern> rules, S3World world) {
		for (final Pattern pattern : rules) {
			final String class_name = pattern.getTrainClass();
			final Train train_class = TrainConfig.resolveTrain(trains, class_name);
			if (train_class == null) {
				showError("Simulation aborted", "Train class '" + class_name + "' is undefined.");
				return false;
			}

			final int start = pattern.getStart();
			if (start < 0) {
				showError("Simulation aborted", "Start time cannot be less than zero.");
				return false;
			}

			if (pattern.getTrainName() == null || pattern.getTrainName().length() == 0) {
				showError("Simulation aborted", "Train must be labeled.");
				return false;
			}

			final Line line = LineUtil.getByLabel(project, pattern.getLine());
			if (line == null) {
				showError("Simulation aborted", "Line '" + pattern.getLine() + "' is undefined.");
				return false;
			}

			final TrainDescriptor descr = SimulationProvider.getDescriptor(pattern.getTrainName(), train_class, pattern);
			S3TrainActor train;
			if (descr instanceof ExtendedTrainDescriptor) {
				train = new S3TrainActorTableBased(world, world.getTrainLine(line), (ExtendedTrainDescriptor) descr);
			} else {
				train = new S3TrainActor(world, world.getTrainLine(line), descr);
			}
			final RBInterlockingState state = (RBInterlockingState) world.getState();
			state.addPendingTrain(train, start);
		}

		return true;
	}

	private static void showError(final String title, final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openError(null, title, message);
			}
		});
	}
}
