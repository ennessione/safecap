package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import safecap.Labeled;
import safecap.Orientation;
import safecap.Project;
import safecap.derived.DerivedFactory;
import safecap.derived.MergedAmbit;
import safecap.derived.MergedJunction;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.ModelFactory;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.mcommon.conf.IConfigError;

public class AmbitUtil {

	public static String getInterlocking(Ambit ambit) {
		final String interlocking = ExtensionUtil.getString(ambit, SafecapConstants.EXT_LDL_GEN, "interlocking");
		if (interlocking == null) {
			return "main";
		}
		return interlocking;
	}

	@SuppressWarnings("unchecked")
	public static Map<Ambit, Collection<MergedAmbit>> getAmbitToComposedMap(Project root) {
		Map<Ambit, Collection<MergedAmbit>> result = (Map<Ambit, Collection<MergedAmbit>>) ProjectTransient.getValue(root,
				ProjectTransient.AMBIT_COMPOSED_MAP);
		if (result == null) {
			result = _buildAmbitToComposedMap(root);

			ProjectTransient.setValue(root, ProjectTransient.AMBIT_COMPOSED_MAP, result);
		}
		return result;
	}

	private static Map<Ambit, Collection<MergedAmbit>> _buildAmbitToComposedMap(Project root) {
		final Map<Ambit, Collection<MergedAmbit>> result = new HashMap<>();

		for (final Ambit ambit : root.getAmbits()) {
			if (isComposed(ambit)) {
				final MergedAmbit ma = (MergedAmbit) ambit;
				for (final Ambit ca : ma.getAmbits()) {
					Collection<MergedAmbit> set = result.get(ca);
					if (set == null) {
						set = new ArrayList<>();
						result.put(ca, set);
					}
					set.add(ma);
				}
			}
		}

		return result;
	}

	public static Collection<String> getUsedSubRouteDirections(Ambit ambit) {
		return ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE);
	}

	public static Collection<String> getAllAmbitSubRouteDirections(Ambit ambit) {
		
		if (AmbitUtil.isMerged(ambit)) {
			final Collection<String> result = new HashSet<>();
			MergedAmbit ma = (MergedAmbit) ambit;
			for (Ambit a : ma.getAmbits())
				result.addAll(getAllBasicAmbitSubRouteDirections(a));
			return result;
		} else {
			return getAllBasicAmbitSubRouteDirections(ambit);
		}
		
	}

	private static Collection<String> getAllBasicAmbitSubRouteDirections(Ambit ambit) {

		final Collection<String> original = ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE_PATH);
		final Collection<String> result = new HashSet<>(original);
		for (final String o : original) {
			result.add(new StringBuilder(o).reverse().toString());
		}
		return result;
	}

	public static Collection<String> getForcedSubRouteDirections(Ambit ambit) {
		return ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE_FORCED);
	}

	public static List<Ambit> normaliseAmbits(Collection<Ambit> ambits, Orientation orientation) {
		final List<Ambit> result = new ArrayList<>(ambits.size());

		if (orientation == Orientation.LEFT) {
			for (final Ambit a : ambits) {
				placeLeft(a, result);
			}
		} else {
			for (final Ambit a : ambits) {
				placeRight(a, result);
			}
		}

		return result;
	}

	private static void placeLeft(Ambit a, List<Ambit> result) {
		for (int i = 0; i < result.size(); i++) {
			final Ambit z = result.get(i);
			final Collection<Ambit> c = AmbitUtil.getNextAmbitsExclusive(a);
			if (c.size() == 1 && c.contains(z)) {
				result.add(i, a);
				return;
			}
		}
		result.add(a);
	}

	private static void placeRight(Ambit a, List<Ambit> result) {
		for (int i = 0; i < result.size(); i++) {
			final Ambit z = result.get(i);
			final Collection<Ambit> c = AmbitUtil.getPrevAmbitsExclusive(a);
			if (c.size() == 1 && c.contains(z)) {
				result.add(i, a);
				return;
			}
		}
		result.add(a);
	}

	public static boolean isAxleCounter(Ambit ambit) {
		return ExtensionUtil.getBool(ambit, SafecapConstants.EXT_AMBIT, SafecapConstants.TRACK_AXLE_COUNTER);
	}

	public static List<Ambit> normaliseAmbitsLeft(List<Ambit> source) {
		final List<Ambit> result = new ArrayList<>(source.size());

		for (final Ambit a : source) {
			placeLeft(a, result);
		}

		return result;
	}

	public static Collection<Ambit> getNextAmbitsExclusive(Ambit ambit) {
		final List<Ambit> result = new ArrayList<>();

		for (final Node node : getAmbitRightBoundaryNodes(ambit)) {
			result.addAll(NodeUtil.getLeftBoundaryAmbits(node));
		}

		result.removeAll(getPrevAmbits(ambit));

		return result;
	}

	public static Collection<Ambit> getPrevAmbitsExclusive(Ambit ambit) {
		final List<Ambit> result = new ArrayList<>();

		for (final Node node : getAmbitLeftBoundaryNodes(ambit)) {
			result.addAll(NodeUtil.getRightBoundaryAmbits(node));
		}

		result.removeAll(getNextAmbits(ambit));

		return result;
	}

	public static Collection<Ambit> getNextAmbits(Ambit ambit) {
		final List<Ambit> result = new ArrayList<>();

		for (final Node node : getAmbitRightBoundaryNodes(ambit)) {
			result.addAll(NodeUtil.getLeftBoundaryAmbits(node));
		}

		return result;
	}

	public static Collection<Ambit> getPrevAmbits(Ambit ambit) {
		final List<Ambit> result = new ArrayList<>();

		for (final Node node : getAmbitLeftBoundaryNodes(ambit)) {
			result.addAll(NodeUtil.getRightBoundaryAmbits(node));
		}

		return result;
	}

	public static Collection<Node> getAmbitNodes(Ambit ambit) {
		final List<Node> result = new ArrayList<>(ambit.getSegments().size() * 2);

		for (final Segment s : ambit.getSegments()) {
			final Node a = s.getFrom();
			final Node b = s.getTo();
			if (!result.contains(a)) {
				result.add(a);
			}

			if (!result.contains(b)) {
				result.add(b);
			}
		}

		return result;
	}

	public static Collection<Node> getSharedNodes(Ambit a, Ambit b) {
		final Collection<Node> anodes = getAmbitNodes(a);
		final Collection<Node> bnodes = getAmbitNodes(b);
		anodes.retainAll(bnodes);
		return anodes;
	}

	public static Set<Node> getAmbitLeftBoundaryNodes(Ambit ambit) {
		final Set<Node> result = new HashSet<>(ambit.getSegments().size());

		for (final Segment s : ambit.getSegments()) {
			result.add(s.getFrom());
		}

		for (final Segment s : ambit.getSegments()) {
			result.remove(s.getTo());
		}

		return result;
	}

	public static Set<Node> getAmbitRightBoundaryNodes(Ambit ambit) {
		final Set<Node> result = new HashSet<>(ambit.getSegments().size());

		for (final Segment s : ambit.getSegments()) {
			result.add(s.getTo());
		}

		for (final Segment s : ambit.getSegments()) {
			result.remove(s.getFrom());
		}

		return result;
	}

	public static List<Route> getAmbitRoutes(Ambit ambit) {
		final Project root = EmfUtil.getProject(ambit);
		final List<Route> result = new ArrayList<>();

		if (root == null) {
			return result;
		}

		for (final Route r : root.getRoutes()) {
			if (r.getAmbits().contains(ambit)) {
				result.add(r);
			}
		}

		return result;
	}

	public static Ambit getByLabel(Project root, String label) {
		if (label == null) {
			return null;
		}

		for (final Ambit a : root.getAmbits()) {
			if (label.equals(a.getLabel())) {
				return a;
			}
		}

		for (final Labeled a : root.getDarkmatter()) {
			if (a instanceof Ambit && label.equals(a.getLabel())) {
				return (Ambit) a;
			}
		}

		return null;
	}

	public static Collection<MergedAmbit> getMergedAmbits(Project root) {
		final Collection<MergedAmbit> result = new HashSet<>();

		for (final Ambit a : root.getAmbits()) {
			if (a instanceof MergedAmbit) {
				result.add((MergedAmbit) a);
			}
		}

		return result;
	}

	public static MergedAmbit getMergedByAmbit(Project root, Ambit ambit) {
		for (final Ambit a : root.getAmbits()) {
			if (a instanceof MergedAmbit) {
				final MergedAmbit ma = (MergedAmbit) a;
				if (ma.getAmbits().contains(ambit)) {
					return ma;
				}
			}
		}

		return null;
	}

	public static Ambit getByLabelPrefix(Project root, String label) {
		if (label == null) {
			return null;
		}

		for (final Ambit a : root.getAmbits()) {
			if (a.getLabel().equals(label)) {
				return a;
			}
			final int bracket = a.getLabel().indexOf('(');
			if (bracket > 0 && a.getLabel().substring(0, bracket).equals(label)) {
				return a;
			}
		}

		return null;
	}

	public static void renameAmbits(Project root, String patternString, String template) {
		final Pattern pattern = Pattern.compile(patternString);

		for (final Ambit ambit : root.getAmbits()) {
			if (ambit.getLabel() != null) {
				final Matcher matcher = pattern.matcher(ambit.getLabel());
				if (matcher.matches()) {
					String newAmbitName = template;
					for (int i = 0; i < matcher.groupCount() + 1; i++) {
						final String value = matcher.group(i);
						newAmbitName = newAmbitName.replaceAll("\\$" + i, value);
					}
					if (AmbitUtil.getByLabel(root, newAmbitName) == null) {
						ambit.setLabel(newAmbitName);
						for (final Segment s : ambit.getSegments()) {
							s.setLabel(newAmbitName);
						}
					}
				}
			}
		}

		ProjectTransient.resetProjectLevel(root);

	}

	public static void declareVirtualAmbits(Project root, String patternString, String template) {
		final Pattern pattern = Pattern.compile(patternString);

		final Collection<Ambit> toAdd = new ArrayList<>();

		for (final Ambit ambit : root.getAmbits()) {
			buildVirtualAmbits(root, template, pattern, toAdd, ambit);
		}

		for (final Labeled l : root.getDarkmatter()) {
			if (l instanceof Ambit) {
				buildVirtualAmbits(root, template, pattern, toAdd, (Ambit) l);
			}
		}

		root.getDarkmatter().addAll(toAdd);

		ProjectTransient.resetProjectLevel(root);

	}

	private static void buildVirtualAmbits(Project root, String template, Pattern pattern, Collection<Ambit> toAdd, Ambit ambit) {
		if (ambit.getLabel() != null) {
			final Matcher matcher = pattern.matcher(ambit.getLabel());
			if (matcher.matches()) {
				String newAmbitName = template;
				for (int i = 0; i < matcher.groupCount() + 1; i++) {
					final String value = matcher.group(i);
					newAmbitName = newAmbitName.replaceAll("\\$" + i, value);
				}

				if (AmbitUtil.getByLabel(root, newAmbitName) == null) {
					final Ambit newAmbit = ModelFactory.eINSTANCE.createSection();
					newAmbit.setLabel(newAmbitName);
					newAmbit.getSegments().addAll(ambit.getSegments());
					ExtensionUtil.setFlag(newAmbit, SafecapConstants.EXT_VIRTUAL, true);
					toAdd.add(newAmbit);
					// System.out.println("Adding virtual ambit " + newAmbitName);
				}
			}
		}
	}

	public static void buildMergedAmbits(Project project, String part1, String delimeter, String part2, boolean composed,
			IConfigError error) {

//		Collection<MergedAmbit> allMerged = getMergedAmbits(project);
//		for (MergedAmbit merged : allMerged)
//			if (!merged.isComposed())
//				project.getAmbits().addAll(merged.getAmbits());
//		project.getAmbits().removeAll(allMerged);

		final Collection<Ambit> processed = new HashSet<>();
		final Collection<MergedAmbit> added = new HashSet<>();
		final String pstring = "(" + part1 + ")" + delimeter + part2;
		final Pattern pattern = Pattern.compile(pstring);

		for (final Ambit ambit : project.getAmbits()) {
			if (!processed.contains(ambit)) {
				final Matcher matcher = pattern.matcher(ambit.getLabel());
				if (matcher.matches()) {
					processed.add(ambit);
					final String name = matcher.group(1);
					if (AmbitUtil.getByLabel(project, name) == null) {
						buildAmbitGroup(project, name, processed, added, delimeter, part2, composed);
					} else {
						error.error("detectmergedambits: ambit " + name + " already defined");
					}
				}
			}
		}

		for (final MergedAmbit merged : added) {
//			System.out.println("Adding merged ambit " + merged.getLabel() + " : " + merged.getSegments() + " //A: "
//					+ merged.getAmbits());
			project.getAmbits().add(merged);

			if (!composed) {
				for (final Ambit a : merged.getAmbits()) {
					ExtensionUtil.setVirtual(a);
				}
				project.getAmbits().removeAll(merged.getAmbits());
				project.getDarkmatter().addAll(merged.getAmbits());
			}
		}

		ProjectTransient.resetProjectLevel(project);
	}

	private static void buildAmbitGroup(Project project, String name, Collection<Ambit> processed, Collection<MergedAmbit> added,
			String delimeter, String part2, boolean composed) {
		Pattern pattern = Pattern.compile(name, Pattern.LITERAL);
		final String pstring = pattern.pattern() + delimeter + "(" + part2 + ")";
		pattern = Pattern.compile(pstring);

		final Collection<Ambit> ambits = new ArrayList<>();
		final Collection<String> codes = new ArrayList<>();
		boolean isJunction = false;

		for (final Ambit ambit : project.getAmbits()) {
			if (!ambits.contains(ambit)) {
				final Matcher matcher = pattern.matcher(ambit.getLabel());
				if (matcher.matches() && getMergedByAmbit(project, ambit) == null) {
					isJunction = isJunction || ambit instanceof Junction;
					final String code = matcher.group(1);
					codes.add(code);
					ambits.add(ambit);
					processed.add(ambit);
				}
			}
		}

		if (ambits.size() > 0) {
			final MergedAmbit merged = isJunction ? DerivedFactory.eINSTANCE.createMergedJunction()
					: DerivedFactory.eINSTANCE.createMergedSection();
			merged.setComposed(composed);
			merged.setLabel(name);
			merged.getAmbits().addAll(ambits);
			merged.getCodes().addAll(codes);

			for (final Ambit ambit : merged.getAmbits()) {
				merged.getSegments().addAll(ambit.getSegments());
				if (isJunction && ambit instanceof Junction) {
					final Junction j = (Junction) ambit;
					final MergedJunction mj = (MergedJunction) merged;
					mj.getPoints().addAll(j.getPoints());
				}
			}

			added.add(merged);
//			System.out.println("Added merged ambit " + merged.getLabel() + " with " + merged.getSegments() + " / "
//					+ merged.getAmbits());
		} else {
			// System.out.println("Rejected merged ambit " + name);
		}
	}

	public static boolean isMerged(Ambit a) {
		return a instanceof MergedAmbit;
	}

	public static boolean isComposed(Ambit a) {
		if (a instanceof MergedAmbit) {
			final MergedAmbit ma = (MergedAmbit) a;
			return ma.isComposed();
		}
		return false;
	}

	public static boolean isDisjoint(Ambit a) {
		if (a instanceof MergedAmbit) {
			final MergedAmbit ma = (MergedAmbit) a;
			return ma.isDisjoint();
		}
		return false;
	}

	public static boolean hasManualSubRoutePathAssignment(Ambit ambit) {
		for (final Node n : AmbitUtil.getAmbitNodes(ambit)) {
			if (ExtensionUtil.hasKey(n, SafecapConstants.EXT_NODE_TAG_PROTECT)) {
				return true;
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public static synchronized Map<Segment, Ambit> getSegmentAmbitMap(Project root) {
		Map<Segment, Ambit> result;
		result = (Map<Segment, Ambit>) ProjectTransient.getValue(root, ProjectTransient.SEGMENT_AMBIT_MAP);
		if (result == null) {
			result = new HashMap<>();
//			System.out.println("Build segment ambit map cache");
			for (final Ambit a : root.getAmbits()) {
				if (!isComposed(a) && !ExtensionUtil.isVirtual(a)) {
					for (final Segment s : a.getSegments()) {
						assert !result.containsKey(s);
						assert a != null;
						result.put(s, a);
					}
				}
			}

			ProjectTransient.setValue(root, ProjectTransient.SEGMENT_AMBIT_MAP, result);
		}
		return result;
	}

	public static void disbandMergedAmbit(Project root, MergedAmbit merged) {
		root.getAmbits().remove(merged);

		// for composed just remove the merged part
		if (merged.isComposed()) {
			return;
		}

		// for normal merged section recover sub sections and remove virtual sections
		for (final Ambit a : merged.getAmbits()) {
			root.getAmbits().add(a);
			root.getDarkmatter().remove(a);
		}

	}

}
