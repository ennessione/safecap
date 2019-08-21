package uk.ac.ncl.safecap.gui.trainconfig;

import org.eclipse.swt.graphics.RGB;

import traintable.TDAttribute;
import traintable.TDTable;
import traintable.Train;
import traintable.util.AttributeUtil;
import uk.ac.ncl.safecap.common.TrainDescriptor;

public class ExtendedTrainDescriptor extends TrainDescriptor {

	private Train train;
	private double trainMass = Double.NaN;
	private double inertialCoefficient = Double.NaN;
	private double constantA = Double.NaN;
	private double constantB = Double.NaN;
	private double constantC = Double.NaN;
	private double jerkLimit = Double.NaN;
	private double brakingForce = Double.NaN;
	private final DenseLinearApproxFunction tractiveEffort;
	private DenseLinearApproxFunction performance;
	private final IDataSurface energy;

	public ExtendedTrainDescriptor(Train train, String trainName, RGB rgb) throws Exception {
		super(trainName, rgb);

		System.out.println("Table list:");
		for (final TDAttribute ta : train.getAttributes()) {
			if (ta instanceof TDTable) {
				final TDTable value = (TDTable) ta;
				System.out.println("\t" + value.getLabel());
			}
		}

		super.length = AttributeUtil.getAttributeReal(train, AttributeUtil.LENGTH);
		super.maxSpeed = AttributeUtil.getAttributeReal(train, AttributeUtil.MAXPSEED);
		super.trainClass = AttributeUtil.getAttributeString(train, AttributeUtil.CLASS);
		trainMass = AttributeUtil.getAttributeReal(train, AttributeUtil.TRAINMASS);
		inertialCoefficient = AttributeUtil.getAttributeReal(train, AttributeUtil.INERTIALC);
		constantA = AttributeUtil.getAttributeReal(train, AttributeUtil.CONSTA);
		constantB = AttributeUtil.getAttributeReal(train, AttributeUtil.CONSTB);
		constantC = AttributeUtil.getAttributeReal(train, AttributeUtil.CONSTC);
		jerkLimit = AttributeUtil.getAttributeReal(train, AttributeUtil.JERKLIMIT);
		brakingForce = AttributeUtil.getAttributeReal(train, AttributeUtil.BRAKINGFORCE);
		super.maxDeceleration = brakingForce / (trainMass * inertialCoefficient);

		tractiveEffort = new DenseLinearApproxFunction(AttributeUtil.getTable(train, AttributeUtil.TRACTIVEEFFORT), 1000, Double.NaN);

		if (AttributeUtil.getTable(train, AttributeUtil.PERFORMANCE) != null) {
			performance = new DenseLinearApproxFunction(AttributeUtil.getTable(train, AttributeUtil.PERFORMANCE), 1000, 1.0);
		}

		energy = new SparseApproxSurface(AttributeUtil.getTable(train, AttributeUtil.POWERDRAW));
		super.maxAcceleration = tractiveEffort.value(tractiveEffort.getMin()) / (trainMass * inertialCoefficient);

		System.out.println("ac=" + maxAcceleration + "; dc=" + maxDeceleration);

		System.out.println("energy=\n" + energy.toString());
	}

	public static String checkDefinition(Train train) {
		final StringBuilder report = new StringBuilder();

		for (final String s : AttributeUtil.VALUE_ATTRIBUTES) {
			final double v = AttributeUtil.getAttributeReal(train, s);
			if (Double.isNaN(v) || v < 0) {
				report.append("Attribute \"" + s + "\" is missing or invalid\n");
			}
		}

		for (final String s : AttributeUtil.STRING_ATTRIBUTES) {
			final String v = AttributeUtil.getAttributeString(train, s);
			if (v == null) {
				report.append("Attribute \"" + s + "\" is missing or invalid\n");
			}
		}

		for (final String s : AttributeUtil.TABLE_ATTRIBUTES_1D) {
			final TDTable t = AttributeUtil.getTable(train, s);
			if (t == null) {
				report.append("Table \"" + s + "\" is missing\n");
			}

			try {
				new DenseLinearApproxFunction(t, 1000, Double.NaN);
			} catch (final Throwable e) {
				report.append("Table \"" + s + "\" is invalid: " + e.getMessage() + "\n");
			}
		}

		for (final String s : AttributeUtil.TABLE_ATTRIBUTES_2D) {
			final TDTable t = AttributeUtil.getTable(train, s);
			if (t == null) {
				report.append("Table \"" + s + "\" is missing\n");
			}

			try {
				new SparseApproxSurface(t);
			} catch (final Throwable e) {
				report.append("Table \"" + s + "\" is invalid: " + e.getMessage() + "\n");
			}
		}

		return report.toString();
	}

	public Train getTrain() {
		return train;
	}

	public double getTrainMass() {
		return trainMass;
	}

	public double getInertialCoefficient() {
		return inertialCoefficient;
	}

	public double getConstantA() {
		return constantA;
	}

	public double getConstantB() {
		return constantB;
	}

	public double getConstantC() {
		return constantC;
	}

	public double getJerkLimit() {
		return jerkLimit;
	}

	public double getBrakingForce() {
		return brakingForce;
	}

	public IDataFunction getTractiveEffort() {
		return tractiveEffort;
	}

	public IDataSurface getEnergy() {
		return energy;
	}

	public DenseLinearApproxFunction getPerformance() {
		return performance;
	}

}
