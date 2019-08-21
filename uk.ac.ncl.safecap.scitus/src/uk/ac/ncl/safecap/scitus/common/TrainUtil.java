package uk.ac.ncl.safecap.scitus.common;

import java.util.List;

import org.eclipse.swt.graphics.RGB;

import safecap.Project;
import traintable.Train;
import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.gui.trainconfig.TrainConfig;

public class TrainUtil {
	public static TrainDescriptor getDescriptor(Project project, String type, String name, RGB colour) {
		final List<Train> trains = TrainConfig.getTrainTypes();
		if (trains == null) {
			return null;
		}

		final Train train = TrainConfig.resolveTrain(trains, type);
		if (train == null) {
			return null;
		}

		return new TrainDescriptor(train.getName(), name, train.getSpeed(), train.getAcceleration(), train.getDeceleration(),
				train.getLength(), colour);
	}
}
