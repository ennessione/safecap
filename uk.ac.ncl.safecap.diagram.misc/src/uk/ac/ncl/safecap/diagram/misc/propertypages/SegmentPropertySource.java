package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import safecap.Orientation;
import safecap.model.Ambit;
import safecap.schema.NodeKind;
import safecap.schema.PointRole;
import safecap.schema.Segment;
import safecap.schema.SegmentRole;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SubOverlap;
import uk.ac.ncl.safecap.misc.core.SubOverlapUtil;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class SegmentPropertySource extends LabeledPropertySource {
	protected static final String[] pointroles = new String[] { "None", "Normal", "Reverse", "Unknown" };
	protected static final String[] crossroles = new String[] { "Direction A", "Direction B", "Unknown" };
	protected static final String[] orientation = new String[] { "Visual", "Left", "Right" };
	protected Segment segment;
	protected Ambit ambit;
	private final Map<String, SubRoute> subroutes;
	private final Map<String, SubOverlap> suboverlaps;

	public SegmentPropertySource(final Segment segment) {
		super(segment);
		this.segment = segment;
		ambit = SegmentUtil.getSegmentAmbit(segment);
		subroutes = new HashMap<>();
		suboverlaps = new HashMap<>();
	}

	@Override
	public void getDescriptors(List<IPropertyDescriptor> list) {
		if (ambit != null) {
			list.add(new TextPropertyDescriptor("ambitname", "Ambit name"));
		}
		list.add(new TextPropertyDescriptor("length", "Length"));

		// TODO: enable via options
//		list.add(new TextPropertyDescriptor("gradient", "Gradient"));
//		list.add(new TextPropertyDescriptor("speed1", "Limit (mps)"));
//		list.add(new TextPropertyDescriptor("speed2", "Limit (kph)"));
//		list.add(new TextPropertyDescriptor("speed3", "Limit (mph)"));

		list.add(new ComboBoxPropertyDescriptor("orientation", "Orientation", orientation));

		if (SegmentUtil.isJunctionPart(segment)) {
			if (segment.getFrom().getKind() == NodeKind.POINT || segment.getTo().getKind() == NodeKind.POINT) {
				list.add(new ComboBoxPropertyDescriptor("pointrole", "Point role", pointroles));
			}

			if (segment.getFrom().getKind() == NodeKind.CROSSOVER || segment.getTo().getKind() == NodeKind.CROSSOVER
					|| segment.getFrom().getKind() == NodeKind.SWITCHED_CROSSOVER
					|| segment.getTo().getKind() == NodeKind.SWITCHED_CROSSOVER) {
				list.add(new ComboBoxPropertyDescriptor("crossrole", "Diamond crossing role", crossroles));
			}
		}

		if (ambit != null) {
			int sri = 0;
			for (final SubRoute sr : SubRouteUtil.getSubRoutes(ambit)) {
				subroutes.put("sr" + sri, sr);
				list.add(new TextPropertyDescriptor("sr" + sri, "SubRoute"));

				int sro = 0;
				for (final SubOverlap so : SubOverlapUtil.getSubOverlap(sr)) {
					if (so != null) {
						list.add(new TextPropertyDescriptor("so" + sri + "_" + sro, "SubOverlap"));
						suboverlaps.put("so" + sri + "_" + sro, so);
						sro++;
					}
				}
				sri++;
			}
//			Collection<String> used = AmbitUtil.getUsedSubRouteDirections(ambit);
//			Collection<String> all = AmbitUtil.getAllSubRouteDirections(ambit);
			final Collection<String> forced = AmbitUtil.getForcedSubRouteDirections(ambit);

			if (!forced.isEmpty()) {
				list.add(new TextPropertyDescriptor("subroute.forced", "Forced sub routes"));
			}

		}

		super.getDescriptors(list);
	}

	private Double getLimit() {
		try {
			if (segment.getSpeedlimit() == null || segment.getSpeedlimit().length() == 0) {
				return null;
			}
			final Double v = Double.parseDouble(segment.getSpeedlimit());
			if (v.isNaN() || v.isInfinite() || v < 0) {
				return null;
			} else {
				return v;
			}
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private void setLimit(String value, double scale) {
		try {
			final String s = value.toString();
			final Double v = Double.parseDouble(s);
			if (v.isNaN() || v.isInfinite() || v < 0 || v > 1000) {
				return;
			}
			segment.setSpeedlimit("" + v * scale);
		} catch (final NumberFormatException e) {
			// ignore
		}
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.toString().equals("ambitname")) {
			if (ambit != null) {
				return ambit.getLabel() != null ? ambit.getLabel() : "";
			} else {
				return "(no ambit)";
			}
		} else if (id.toString().startsWith("sr")) {
			return subroutes.get(id).toString();
		} else if (id.toString().startsWith("so")) {
			return suboverlaps.get(id).toString();
		} else if (id.toString().equals("subroute.forced")) {
			final Collection<String> forced = AmbitUtil.getForcedSubRouteDirections(ambit);
			return forced.toString();
		} else if (id.toString().equals("length")) {
			return "" + segment.getLength();
		} else if (id.toString().equals("speed1")) {
			return getLimit() != null ? "" + getLimit() : "";
		} else if (id.toString().equals("speed2")) {
			return getLimit() != null ? "" + getLimit() * 3.6 : "";
		} else if (id.toString().equals("speed3")) {
			return getLimit() != null ? "" + getLimit() / 0.44704 : "";
		} else if (id.toString().equals("gradient")) {
			return "" + segment.getGradient();
		} else if (id.toString().equals("orientation")) {
			switch (segment.getOrientation().getValue()) {
			case Orientation.ANY_VALUE:
				return 0;
			case Orientation.LEFT_VALUE:
				return 1;
			case Orientation.RIGHT_VALUE:
				return 2;
			}
			return 3;
		} else if (id.toString().equals("pointrole")) {
			switch (segment.getPointrole().getValue()) {
			case PointRole.NONE_VALUE:
				return 0;
			case PointRole.NORMAL_VALUE:
				return 1;
			case PointRole.REVERSE_VALUE:
				return 2;
			}
			return 3;
		} else if (id.toString().equals("crossrole")) {
			if ((segment.getRole() & SegmentRole.CROSS_A_VALUE) == SegmentRole.CROSS_A_VALUE) {
				return 0;
			} else if ((segment.getRole() & SegmentRole.CROSS_B_VALUE) == SegmentRole.CROSS_B_VALUE) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return super.getPropertyValue(id);
		}
	}

	@Override
	public boolean setValueCommand(Object id, Object value) {
		if (value == null || value.toString().length() == 0) {
			return true;
		}

		if (id.toString().startsWith("sr") || id.toString().startsWith("so")) {
			return true;
		}

		if (id.toString().equals("ambitname")) { // align with ambit name
			if (ambit != null) {
				ambit.setLabel(value.toString());
			}
			return true;
		} else if (id.toString().equals("speed1")) {
			setLimit(value.toString(), 1.0);
			return true;
		} else if (id.toString().equals("speed2")) {
			setLimit(value.toString(), 1.0 / 3.6);
			return true;
		} else if (id.toString().equals("speed3")) {
			setLimit(value.toString(), 0.44704);
			return true;
		} else if (id.toString().equals("length")) {
			try {
				final String s = value.toString();
				final int length = Integer.parseInt(s);
				segment.setLength(length);
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else if (id.toString().equals("gradient")) {
			try {
				final String s = value.toString();
				final double gradient = Double.parseDouble(s);
				if (gradient < 20 && gradient > -20) {
					segment.setGradient(s);
				}
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else if (id.toString().equals("orientation")) {
			try {
				final String s = value.toString();
				final int index = Integer.parseInt(s);
				switch (index) {
				case 0:
					segment.setOrientation(Orientation.ANY);
					break;
				case 1:
					segment.setOrientation(Orientation.LEFT);
					break;
				case 2:
					segment.setOrientation(Orientation.RIGHT);
					break;
				}
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else if (id.toString().equals("pointrole")) {
			try {
				final String s = value.toString();
				final int index = Integer.parseInt(s);
				switch (index) {
				case 0:
					segment.setPointrole(PointRole.NONE);
					break;
				case 1:
					segment.setPointrole(PointRole.NORMAL);
					break;
				case 2:
					segment.setPointrole(PointRole.REVERSE);
					break;
				}
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else if (id.toString().equals("crossrole")) {
			try {
				final String s = value.toString();
				final int index = Integer.parseInt(s);
				final int role = segment.getRole() & ~(SegmentRole.CROSS_A_VALUE | SegmentRole.CROSS_B_VALUE);
				switch (index) {
				case 0:
					segment.setRole(role | SegmentRole.CROSS_A_VALUE);
					break;
				case 1:
					segment.setRole(role | SegmentRole.CROSS_B_VALUE);
					break;
				}
			} catch (final NumberFormatException e) {
				// ignore
			}
			return true;
		} else {
			return super.setValueCommand(id, value);
		}
	}

}
