package uk.ac.ncl.safecap.xdata.verification.core;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLGivenType;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeReal;
import uk.ac.ncl.prime.sim.lang.typing.CLUserType;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.safecap.common.MD5Checksum;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.ConjectureCategory;
import uk.ac.ncl.safecap.xdata.verification.IFormulaSource;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.ITag;
import uk.ac.ncl.safecap.xdata.verification.ITagSet;
import uk.ac.ncl.safecap.xdata.verification.IdentifierCategory;
import uk.ac.ncl.safecap.xdata.verification.IdentifierDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;
import uk.ac.ncl.safecap.xdata.verification.PredicateKind;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.SourceReference;
import uk.ac.ncl.safecap.xdata.verification.TransitionCategory;
import uk.ac.ncl.safecap.xdata.verification.TransitionDefinition;
import uk.ac.ncl.safecap.xdata.verification.Verifiable;
import uk.ac.ncl.safecap.xdata.verification.VerificationProperty;
import uk.ac.ncl.safecap.xmldata.FileUtil;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.base.SDAImportException;
import uk.ac.ncl.safecap.xmldata.types.XBoolType;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XIntegerType;
import uk.ac.ncl.safecap.xmldata.types.XPowType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRealType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XSeqType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class SDAUtils {
	public static final String IMPORT_ORIGIN = "import-origin";
	public static final String START_POSITION = "source-start";
	public static final String SOURCE = "source-file";
	public static final String END_POSITION = "source-end";

	public static File resolveFileReference(ISDADataProvider context, String name) {
		for (final String folder : context.getExternalFileLocations()) {
			final File f = resolveFileReference(new File(folder), name, 5);
			if (f != null) {
				return f;
			}
		}

		return null;
	}

	public static File resolveFileReference(File dir, String name, int depth) {
		if (dir.isDirectory() && dir.exists() && dir.canRead()) {
			for (final File f : dir.listFiles()) {
				if (f.isFile() && f.getName().equals(name)) {
					return f;
				} else if (depth > 0 && f.isDirectory()) {
					final File x = resolveFileReference(f, name, depth - 1);
					if (x != null) {
						return x;
					}
				}

			}
		}
		return null;
	}

	public static boolean isBalanced(SDAContext sdaContext, Verifiable verifiable) {
		if (verifiable.getParsed().empty()) {
			return false;
		}

		final CLExpression expression = verifiable.getParsed().content();
		final Set<String> sources = new HashSet<>(4);
		for (final String id : expression.getFreeIdentifiers()) {
			final ISDADataProvider origin = sdaContext.getFunctionOrigin(id);
			if (origin != null) {
				sources.add(origin.getName());
			}
		}

		if (sources.size() > 1 && hasSignalling(sources)) {
			// System.out.println("balanced: " + verifiable.getId());
			// for (String id : expression.getFreeIdentifiers()) {
			// ISDADataProvider origin = sdaContext.getFunctionOrigin(id);
			// if (origin != null)
			// System.out.println("\t" + id + " -> " + origin.getName());
			// }
			return true;
		}

		return false;
	}

	public static boolean hasSchemaDepedency(SDAContext sdaContext, Verifiable verifiable) {
		if (verifiable.getParsed().empty()) {
			return false;
		}

		final CLExpression expression = verifiable.getParsed().content();
		for (final String id : expression.getFreeIdentifiers()) {
			final ISDADataProvider origin = sdaContext.getFunctionOrigin(id);
			if (origin != null && origin.getName().startsWith("schema")) {
				return true;
			}
		}

		return false;
	}

	private static boolean hasSignalling(Set<String> sources) {
		for (final String s : sources) {
			if (s.startsWith("signalling")) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasSchema(Set<String> sources) {
		for (final String s : sources) {
			if (s.startsWith("schema")) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasDerived(Set<String> sources) {
		for (final String s : sources) {
			if (s.startsWith("derived")) {
				return true;
			}
		}
		return false;
	}

	public static File resolveResource(File file) {
		final IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = myWorkspaceRoot.findMember(file.getPath(), false);
		if (resource instanceof IFile) {
			final IFile f = (IFile) resource;
			return new File(f.getLocationURI().getPath());
		}
		return null;
	}

	public static File resolveResource(IFile file) {
		final IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = myWorkspaceRoot.findMember(file.getFullPath(), false);
		if (resource instanceof IFile) {
			final IFile f = (IFile) resource;
			return new File(f.getLocationURI().getPath());
		}
		return null;
	}

	public static File resolveResource(String path) {
		final IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = myWorkspaceRoot.findMember(path, false);
		if (resource instanceof IFile) {
			final IFile f = (IFile) resource;
			return new File(f.getLocationURI().getPath());
		}
		return null;
	}

	public static CLExpression getParsedNoType(SDARuntimeExecutionContext model, IFormulaSource pd) {
		if (!pd.getParsed().empty()) {
			return pd.getParsed().content();
		}

		try {
			final CLFormulaParser parser = VerificationTool.getParser(model);
			pd.getParsed().clear();
			final FormulaSource source = getSource(pd);
			if (source == null) {
				return null;
			}
			final CLExpression parsed = parser.parseOnly(source);
			pd.setParsed(parsed);
			source.mark();
			return pd.getParsed().content();
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static CLExpression parseString(SDARuntimeExecutionContext model, String text) {
		try {
			final FormulaSource source = new FormulaSource(text);
			final CLFormulaParser parser = new CLFormulaParser(model);
			return parser.parseOnly(source);
		} catch (final Throwable e) {
			return null;
		}
	}

	public static FormulaSource getSource(IFormulaSource id) {
		if (id.getFormulaSource().empty()) {
			if (id.getFormula().empty()) {
				return null;
			}
			final FormulaSource fs = new FormulaSource(id.getFormula().content());
			id.setFormulaSource(fs);
			return fs;
		} else {
			final FormulaSource fs = id.getFormulaSource().content();
			if (fs.getText() == null) {
				if (id.getFormula().empty()) {
					return null;
				}

				fs.setText(id.getFormula().content());
			}
			return fs;

		}
	}

//	public static FormulaSource getSourceFileMarker(IFormulaSource fs) {
//		ITagSet tagset = getTagSet(fs);
//		if (tagset == null)
//			return null;
//
//		String file = getHiddenTagValue(tagset, SOURCE);
//		if (file == null)
//			return null;
//		RootCatalog root = (RootCatalog) fs.root();
//		for (SourceReference sr : root.getSources()) {
//			if (!file.equals(sr.getId().content())) {
//				FormulaSource fors = sr.getFormulaSource().content();
//				if (fors == null) {
//					fors = new FormulaSource(getSourceReferenceContent(sr));
//					sr.setFormulaSource(fors);
//				}
//				return fors;
//			}
//		}
//
//		return null;
//	}

//	public static String getSourceFileName(IFormulaSource fs) {
//		ITagSet tagset = getTagSet(fs);
//
//		return getHiddenTagValue(tagset, SOURCE);
//	}

	private static ITagSet getTagSet(IFormulaSource fs) {
		ITagSet tagset;
		if (fs instanceof ITagSet) {
			tagset = (ITagSet) fs;
		} else if (fs.parent().element() instanceof ITagSet) {
			tagset = (ITagSet) fs.parent().element();
		} else {
			return null;
		}
		return tagset;
	}

//	public static int[] getSourceFilePosition(IFormulaSource fs) {
//		ITagSet tagset = getTagSet(fs);
//		if (tagset == null)
//			return null;
//
//		int start = asInt(getHiddenTagValue(tagset, START_POSITION));
//		int end = asInt(getHiddenTagValue(tagset, END_POSITION));
//
//		if (start == -1 || end == -1)
//			return null;
//		else
//			return new int[] { start, end };
//	}

	public static String getSourceReferenceContent(SourceReference source) {
		try {
			if (!source.getContent().empty()) {
				return source.getContent().content();
			}
			if (source.getPath().empty() || !source.getContent().empty()) {
				return null;
			}
			final File f = new File(source.getPath().content());
			if (f.exists() && f.isFile()) {
				source.setContent(FileUtil.getFileContents(f));
			}
			return source.getContent().content();
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getModelName(String path) {
		if (path != null) {
			final int dot = path.indexOf('.');
			if (dot != -1) {
				return path.substring(0, dot);
			} else {
				return path;
			}
		} else {
			return null;

		}
	}

	private static int asInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (final NumberFormatException e) {
			return -1;
		}
	}

	public static INamed fromFullName(RootCatalog root, String name) {
		final String[] parts = name.split("/");
		if (parts.length < 3) {
			return null;
		}
		if (parts[0].equals("Identifiers")) {
			for (final IdentifierCategory prc : root.getIdentifierCategories()) {
				if (parts[1].equals(prc.getId().content())) {
					return walkIdentifiers(prc, parts, 2);
				}
			}
		} else if (parts[0].equals("Predicates")) {
			for (final PredicateCategory prc : root.getPredicateCategories()) {
				if (parts[1].equals(prc.getId().content())) {
					return walkPredicates(prc, parts, 2);
				}
			}
		} else if (parts[0].equals("Transitions")) {
			for (final TransitionCategory prc : root.getTransitionCategories()) {
				if (parts[1].equals(prc.getId().content())) {
					return walkTransitions(prc, parts, 2);
				}
			}
		}

		return null;
	}

	private static INamed walkPredicates(PredicateCategory parent, String[] parts, int i) {
		if (parts.length == i + 1) {
			for (final PredicateDefinition pd : parent.getPredicates()) {
				if (parts[i].equals(pd.getId().content())) {
					return pd;
				}
			}
		} else {
			for (final PredicateCategory prc : parent.getPredicateCategories()) {
				if (parts[i].equals(prc.getId().content())) {
					return walkPredicates(prc, parts, i + 1);
				}
			}
		}
		return null;
	}

	private static INamed walkIdentifiers(IdentifierCategory parent, String[] parts, int i) {
		if (parts.length == i + 1) {
			for (final IdentifierDefinition pd : parent.getIdentifiers()) {
				if (parts[i].equals(pd.getId().content())) {
					return pd;
				}
			}
		} else {
			for (final IdentifierCategory prc : parent.getIdentifierCategories()) {
				if (parts[i].equals(prc.getId().content())) {
					return walkIdentifiers(prc, parts, i + 1);
				}
			}
		}
		return null;
	}

	private static INamed walkTransitions(TransitionCategory parent, String[] parts, int i) {
		if (parts.length == i + 1) {
			for (final TransitionDefinition pd : parent.getTransitions()) {
				if (parts[i].equals(pd.getId().content())) {
					return pd;
				}
			}
		} else {
			for (final TransitionCategory prc : parent.getTransitionCategories()) {
				if (parts[i].equals(prc.getId().content())) {
					return walkTransitions(prc, parts, i + 1);
				}
			}
		}
		return null;
	}

	public static String fullElementName(INamed named) {
		if (named instanceof PredicateDefinition) {
			return "Predicates" + _fullElementName(named);
		} else if (named instanceof IdentifierDefinition) {
			return "Identifiers" + _fullElementName(named);
		} else if (named instanceof TransitionDefinition) {
			return "Transitions" + _fullElementName(named);
		} else if (named instanceof VerificationProperty) {
			final VerificationProperty vp = (VerificationProperty) named;
			if (vp.parent().element() instanceof INamed) {
				final String name = _fullElementName((INamed) vp.parent().element());
				return name + "/" + vp.getId().content();
			}
		}
		return null;
	}

	public static String _fullElementName(INamed named) {
		String prefix = "";
		if (named.parent() != null) {
			if (named.parent().element() instanceof INamed) {
				prefix = _fullElementName((INamed) named.parent().element());
			}
		}

		return prefix + "/" + named.getId().content();
	}

	public static String _fullElementNamePoint(INamed named) {
		String prefix = "";
		if (named.parent() != null) {
			if (named.parent().element() instanceof INamed) {
				prefix = _fullElementNamePoint((INamed) named.parent().element());
			}
		}

		if (prefix != null && prefix.length() > 0) {
			return prefix + "." + named.getId().content();
		} else {
			return named.getId().content();
		}
	}

	public static String _fullElementPrefixPoint(INamed named) {
		String prefix = "";
		if (named.parent() != null) {
			if (named.parent().element() instanceof INamed) {
				prefix = _fullElementNamePoint((INamed) named.parent().element());
			}
		}

		return prefix;
	}

//	public static String getHiddenTagValue(ITagSet element, String name) {
//		for (ITag tag : element.getHiddenTags()) {
//			if (!tag.empty() && !tag.getName().empty() && !tag.getValue().empty() && tag.getName().content().equals(name))
//				return tag.getValue().content();
//		}
//
//		return null;
//	}

	public static String getVisibleTagValue(ITagSet element, String name) {
		for (final ITag tag : element.getTags()) {
			if (!tag.empty() && !tag.getName().empty() && !tag.getValue().empty() && tag.getName().content().equals(name)) {
				return tag.getValue().content();
			}
		}

		return null;
	}

	public static String getTagValue(ITagSet element, String name) {
		return getVisibleTagValue(element, name);
	}

	public static SourceReference getSourceReference(RootCatalog root, String name) {
		for (final SourceReference ref : root.getSources()) {
			if (!ref.getId().empty() && ref.getId().content().equals(name)) {
				return ref;
			}
		}

		return null;
	}

	public static synchronized SDAContext getDataContext(RootCatalog catalog) {
		try {
			if (catalog.disposed()) {
				return null;
			}

			if (catalog.getContext().content() == null) {
				catalog.setContext(new SDAContext(catalog));
			}

			return catalog.getContext().content();
		} catch (final SDAImportException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SDAContext getDataContextSoft(RootCatalog catalog) {
		if (catalog.disposed()) {
			return null;
		}

		return catalog.getContext().content();
	}

	/**
	 * Returns untyped query formula of the form a a->b (a->b)->c and so on
	 */
	public static CLExpression listToMap(List<String> query) {
		if (query.size() == 0) {
			throw new CLException("Invalid counter-example expression");
		} else if (query.size() == 1) {
			return new CLIdentifier(query.get(0));
		} else {
			// build left-associate map
			CLExpression map = new CLBinaryExpression(alphabet.OP_MAP, new CLIdentifier(query.get(0)), new CLIdentifier(query.get(1)));
			for (int i = 2; i < query.size(); i++) {
				map = new CLBinaryExpression(alphabet.OP_MAP, map, new CLIdentifier(query.get(i)));
			}
			return map;
		}
	}

	public static CLType mapType(String type, RuntimeExecutionContext context) {
		final CLType r = context.getRootContext().resolveType(type);
		if (r != null) {
			return r;
		} else {
			return new CLGivenType(type);
		}
	}

	public static CLType mapType(XType type, RuntimeExecutionContext context) {
		if (type instanceof XBoolType) {
			return CLTypeBool.INSTANCE;
		} else if (type instanceof XIntegerType) {
			final XIntegerType itype = (XIntegerType) type;
			if (itype.getPhysicalType() == null) {
				return CLTypeInteger.INSTANCE;
			} else {
				return new CLTypeInteger(itype.getPhysicalType());
			}
		} else if (type instanceof XRealType) {
			final XRealType itype = (XRealType) type;
			if (itype.getPhysicalType() == null) {
				return CLTypeReal.INSTANCE;
			} else {
				return new CLTypeReal(itype.getPhysicalType());
			}
		} else if (type instanceof XEnumType) {
			final XEnumType gt = (XEnumType) type;
			final CLUserType rt = context.getRootContext().resolveType(gt.getName());
			if (rt != null) {
				return rt;
			} else {
				return new CLGivenType(gt.getName());
			}
		} else if (type instanceof XRelationType) {
			final XRelationType rt = (XRelationType) type;
			return new CLPowerType(new CLProductType(mapType(rt.getDomain(), context), mapType(rt.getRange(), context)));
		} else if (type instanceof XSeqType) {
			final XSeqType st = (XSeqType) type;
			return new CLSeqType(mapType(st.getBase(), context));
		} else if (type instanceof XPowType) {
			final XPowType st = (XPowType) type;
			final CLType base = mapType(st.getBase(), context);
			return new CLPowerType(base);
		} else if (type instanceof XProductType) {
			final XProductType pt = (XProductType) type;
			CLType result = mapType(pt.get(0), context);
			for (int i = 1; i < pt.getSize(); i++) {
				result = new CLProductType(result, mapType(pt.get(i), context));
			}
			return result;
		} else {
			// assert (false);
			return null;
		}
	}

	public static String formatTime(double timeToWork) {
		final int hours = (int) (timeToWork / (60 * 60 * 1000));
		double remainder = timeToWork - hours * 60 * 60 * 1000;
		final int minutes = (int) (remainder / (60 * 1000));
		remainder = remainder - minutes * 60 * 1000;
		final int seconds = (int) (remainder / 1000);

		final StringBuilder sb = new StringBuilder();

		if (hours == 1) {
			sb.append(hours);
			sb.append(" hour ");
		} else if (hours > 1) {
			sb.append(hours);
			sb.append(" hours ");
		}

		if (minutes == 1) {
			sb.append(minutes);
			sb.append(" minute ");
		} else if (minutes > 1) {
			sb.append(minutes);
			sb.append(" minutes ");
		}

		if (seconds == 1) {
			sb.append(seconds);
			sb.append(" second ");
		} else if (seconds > 1) {
			sb.append(seconds);
			sb.append(" seconds ");
		}
		return sb.toString();
	}

	public static IXFunction resolveFromXPath(SDAContext context, String path) {
		for (final String fn : context.getFunctionIds()) {
			if (context.getFunction(fn).getCanonicalName().equals(path)) {
				return context.getFunction(fn);
			}
		}

		return null;
	}

	public static int[] getCategoryStatistics(SDAContext sdaContext, ConjectureCategory category) {
		class Z {
			int total = 0;
			int wellformed = 0;
			int invalid = 0;
			int unknown = 0;
			int valid = 0;
		}

		final Z z = new Z();

		VisitorUtils.visitAllPropertiesInConjectures(category, new ElementVisitor<Verifiable>() {
			@Override
			public Object visit(Verifiable element, Object userData) {
				z.total++;
				if (element.validation().ok()) {
					z.wellformed++;
				}
				if (element.getVResult().empty()) {
					z.unknown++;
				} else {
					if (element.getVResult().content().isDefinite()) {
						if (element.getVResult().content().isValid()) {
							z.valid++;
						} else {
							z.invalid++;
						}
					} else {
						z.unknown++;
					}

				}
				return null;
			}

		}, null);

		return new int[] { z.total, z.wellformed, z.invalid, z.unknown, z.valid };
	}

	public static int[] getCategoryStatistics(SDAContext sdaContext, PredicateCategory category) {
		class Z {
			int total = 0;
			int wellformed = 0;
			int axioms = 0;
			int lemmas = 0;
			int invariants = 0;
		}

		final Z z = new Z();

		VisitorUtils.visitAllPredicates(category, new ElementVisitor<PredicateDefinition>() {
			@Override
			public Object visit(PredicateDefinition element, Object userData) {
				z.total++;
				if (element.validation().ok()) {
					z.wellformed++;
				}
				if (element.getKind().content() == PredicateKind.AXIOM) {
					z.axioms++;
				}
				if (element.getKind().content() == PredicateKind.LEMMA) {
					z.lemmas++;
				}
				if (element.getKind().content() == PredicateKind.INVARIANT) {
					z.invariants++;
				}
				return null;
			}

		}, null);

		return new int[] { z.total, z.wellformed, z.axioms, z.lemmas, z.invariants };
	}

	public static String generateHash(Collection<Object> values) {
		final StringBuilder sb = new StringBuilder();
		for (final Object v : values) {
			sb.append(v);
		}
		return MD5Checksum.getMD5TextChecksum(sb.toString());
	}

}
