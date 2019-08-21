package traintable.util;

import traintable.TDAttribute;
import traintable.TDTable;
import traintable.TDValue;
import traintable.Train;

public class AttributeUtil {
	public static final String CLASS = "train class name";
	public static final String LENGTH = "length";
	public static final String MAXPSEED = "speed limit";
	public static final String TRAINMASS = "train mass";
	public static final String INERTIALC = "inertial coefficient";
	public static final String CONSTA = "constant A";
	public static final String CONSTB = "constant B";
	public static final String CONSTC = "constant C";
	public static final String JERKLIMIT = "jerk limit";
	public static final String BRAKINGFORCE = "braking force";
	public static final String TRACTIVEEFFORT = "maximum tractive effort";
	public static final String POWERDRAW = "power draw";
	public static final String PERFORMANCE = "performance function";

	public static final String[] VALUE_ATTRIBUTES = { LENGTH, MAXPSEED, TRAINMASS, INERTIALC, CONSTA, CONSTB, CONSTC, JERKLIMIT,
			BRAKINGFORCE };
	public static final String[] STRING_ATTRIBUTES = { CLASS };
	public static final String[] TABLE_ATTRIBUTES_1D = { TRACTIVEEFFORT };
	public static final String[] TABLE_ATTRIBUTES_2D = { POWERDRAW };

	public static double getAttributeReal(Train train, String name) {
		for (final TDAttribute ta : train.getAttributes()) {
			if (ta instanceof TDValue) {
				final TDValue value = (TDValue) ta;
				if (name.equals(value.getLabel())) {
					try {
						return Double.parseDouble(value.getValue());
					} catch (final NumberFormatException e) {
						return Double.NaN;
					}
				}

			}
		}
		return Double.NaN;
	}

	public static String getAttributeString(Train train, String name) {
		for (final TDAttribute ta : train.getAttributes()) {
			if (ta instanceof TDValue) {
				final TDValue value = (TDValue) ta;
				if (name.equals(value.getLabel())) {
					return value.getValue();
				}

			}
		}
		return "?";
	}

	public static TDTable getTable(Train train, String name) {
		for (final TDAttribute ta : train.getAttributes()) {
			if (ta instanceof TDTable) {
				final TDTable value = (TDTable) ta;
				if (name.equals(value.getLabel())) {
					return value;
				}
			}
		}

		return null;
	}
}
