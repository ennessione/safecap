package uk.ac.ncl.safecap.xmldata.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.StringPair;
import uk.ac.ncl.safecap.xmldata.TypeDecomposition;
import uk.ac.ncl.safecap.xmldata.TypeTransformation;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XPowType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XSeqType;
import uk.ac.ncl.safecap.xmldata.types.XType;

class FunctionTreeContentProvider implements ITreeContentProvider {
	public static final String SCHEMA_PATH = "schema path: ";

	private static final Object[] EMPTY_ARRAY = new Object[] {};
	private final XmlDataEditor editor;
	private DataContext dataContext;

	public FunctionTreeContentProvider(XmlDataEditor editor) {
		this.editor = editor;
	}

	@Override
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	@Override
	public void dispose() {
	}

	static class FunctionBundle {
		String name;
		Collection<IXFunction> functions;
		Collection<FunctionBundle> children;
		int typed;
		int mapped;

		private FunctionBundle(String name, Collection<IXFunction> functions) {
			super();
			this.name = name;
			this.functions = functions;
			children = new ArrayList<>();
		}
	}

	private Collection<FunctionBundle> buildBundles() {
		final Collection<FunctionBundle> result = new ArrayList<>();

		for (final IXFunction f : filter(dataContext.getFunctions())) {
			final String prefix = detectPrefix(f);
			FunctionBundle b = locateBundle(result, prefix);
			final String child = detectChild(f);
			if (child != null) {
				b = locateBundle(b.children, child);
			}
			b.functions.add(f);
			if (f.getFunctionType() != null) {
				b.typed++;
			}
			if (dataContext != null && dataContext.getConceptMap() != null) {
				final String path = dataContext.getConceptMap().getConceptProvenance(f.getCanonicalName());
				if (path != null) {
					b.mapped++;
				}
			}

		}

		return result;
	}

	private String detectChild(IXFunction f) {
		final String name = f.getName();
		if (name.indexOf(".Raw") > 0) {
			return "Raw";
		}

		return null;
	}

	private String detectPrefix(IXFunction f) {
		final String name = f.getName();
		if (name.endsWith("Valid")) {
			return "Flags";
		} else if (name.startsWith("/")) {
			return "Misc";
		}

		final int dot = Math.max(name.indexOf('.'), name.indexOf(':'));
		if (dot > 0) {
			final String prefix = name.substring(0, dot);
			return prefix;
		}
		return "Other";
	}

	private FunctionBundle locateBundle(Collection<FunctionBundle> result, String prefix) {
		for (final FunctionBundle b : result) {
			if (b.name.equals(prefix)) {
				return b;
			}
		}

		final FunctionBundle b = new FunctionBundle(prefix, new ArrayList<IXFunction>(10));
		result.add(b);
		return b;
	}

	@Override
	public Object[] getElements(Object input) {
		if (input instanceof DataContext) {
			dataContext = (DataContext) input;
			final List<Object> result = new ArrayList<>();
			result.add("Total objects: " + dataContext.getFunctions().size() + ", typed " + getTypedNumber(dataContext.getFunctions())
					+ ", functions " + getFunctionsNumber(dataContext.getFunctions()) + ", types " + dataContext.getEnums().size());

			if (!dataContext.getEnums().isEmpty()) {
				result.add(new DataTypes(dataContext));
			}

			if (dataContext.getElementExternalMap() != null && !dataContext.getElementExternalMap().isEmpty()) {
				result.add(new ExternalElementMappings(dataContext));
			}

			result.addAll(buildBundles());
			// result.addAll(filter(dataContext.getFunctions()));
			return result.toArray();
		} else {
			return EMPTY_ARRAY;
		}
	}

	private Collection<IXFunction> filter(Collection<IXFunction> functions) {
		if (!editor.isHideEmpty()) {
			return functions;
		} else {
			final Collection<IXFunction> r = new ArrayList<>(functions.size());
			for (final IXFunction f : functions) {
				if (!f.domain().isEmpty()) {
					r.add(f);
				}
			}

			return r;
		}
	}

	@Override
	public Object[] getChildren(Object parent) {
		if (parent instanceof FunctionBundle) {
			final FunctionBundle b = (FunctionBundle) parent;
			if (b.children.isEmpty()) {
				return b.functions.toArray();
			} else {
				final List<Object> result = new ArrayList<>();
				result.addAll(b.children);
				result.addAll(b.functions);
				return result.toArray();
			}
		} else if (parent instanceof IXFunction) {
			final IXFunction f = (IXFunction) parent;
			return FDomain.getDomain(dataContext, f);
		} else if (parent instanceof CarrierType) {
			final CarrierType carriertype = (CarrierType) parent;
			final List<Object> result = new ArrayList<>();
			result.add(new TypeDecompositionTemplate(carriertype.context, carriertype.type));
			// result.add(new TypeTransformationTemplate(carriertype.context,
			// carriertype.type));
			result.addAll(carriertype.context.getTypeDecompositionsFor(carriertype.type));
			result.add(new CarrierTypeElements(carriertype.context, carriertype.type));
			return result.toArray();
		} else if (parent instanceof TypeDecomposition) {
			final TypeDecomposition td = (TypeDecomposition) parent;
			if (td.getFunction() != null) {
				return new Object[] { td.getPattern() == null ? "<unknown pattern>" : td.getPattern(), td.getFunction() };
			} else {
				return null;
			}
		} else if (parent instanceof TypeTransformation) {
			final TypeTransformation td = (TypeTransformation) parent;
			return td.getMapping().entrySet().toArray();
		} else if (parent instanceof CarrierTypeElements) {
			final CarrierTypeElements carriertype = (CarrierTypeElements) parent;
			return carriertype.context.getEnumMembers(carriertype.type).toArray();
		} else if (parent instanceof DataTypes) {
			final DataTypes datatypes = (DataTypes) parent;
			final List<CarrierType> ctype = new ArrayList<>();
			for (final String t : datatypes.context.getEnums()) {
				ctype.add(new CarrierType(datatypes.context, t));
			}
			return ctype.toArray();
		} else if (parent instanceof ExternalElementMappings) {
			final ExternalElementMappings mappings = (ExternalElementMappings) parent;
			final List<ExternalElementMap> maps = new ArrayList<>();
			final Collection<String> domain = new HashSet<>();
			for (final StringPair p : mappings.context.getElementExternalMap()) {
				domain.add(p.getKey());
			}

			for (final String t : domain) {
				maps.add(new ExternalElementMap(mappings.context, t));
			}
			return maps.toArray();
		} else if (parent instanceof FTypeInfo) {
			final FTypeInfo typeinfo = (FTypeInfo) parent;
			final XRelationType type = (XRelationType) typeinfo.function.getFunctionType();
			return new Object[] { type };
		} else if (parent instanceof XProductType) {
			final XProductType pt = (XProductType) parent;
			return pt.getMembers().toArray();
		} else if (parent instanceof XRelationType) {
			final XRelationType pt = (XRelationType) parent;
			return new XType[] { pt.getDomain(), pt.getRange() };
		} else if (parent instanceof XPowType) {
			final XPowType pt = (XPowType) parent;
			return new XType[] { pt.getBase() };
		} else if (parent instanceof XSeqType) {
			final XSeqType pt = (XSeqType) parent;
			return new XType[] { pt.getBase() };
		} else {
			return EMPTY_ARRAY;
		}
	}

	private static int getTypedNumber(Collection<IXFunction> ff) {
		int typed = 0;
		for (final IXFunction f : ff) {
			if (f.getFunctionType() != null) {
				typed++;
			}
		}

		return typed;
	}

	private static int getFunctionsNumber(Collection<IXFunction> ff) {
		int function = 0;
		for (final IXFunction f : ff) {
			if (f.isFunction()) {
				function++;
			}
		}

		return function;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof DataContext) {
			final DataContext dataContext = (DataContext) element;
			return !dataContext.getFunctions().isEmpty();
		} else if (element instanceof CarrierType) {
			return true;
		} else if (element instanceof TypeDecomposition) {
			final TypeDecomposition td = (TypeDecomposition) element;
			return td.getFunction() != null;
		} else if (element instanceof CarrierTypeElements) {
			return true;
		} else if (element instanceof DataTypes) {
			return true;
		} else if (element instanceof ExternalElementMappings) {
			return true;
		} else if (element instanceof IXFunction) {
			return true;
		} else if (element instanceof FunctionBundle) {
			return true;
		} else if (element instanceof FTypeInfo) {
			return true;
		} else if (element instanceof XProductType) {
			return true;
		} else if (element instanceof XRelationType) {
			return true;
		} else if (element instanceof XPowType) {
			return true;
		} else if (element instanceof XSeqType) {
			return true;
		} else {
			return false;
		}
	}

	static class FInfo {
		IXFunction function;

		public FInfo(IXFunction function) {
			this.function = function;
		}
	}

	static class FConceptMap {
		String map;

		public FConceptMap(String map) {
			this.map = map;
		}
	}

	static class FDomain {
		IXFunction function;
		Object obj;

		FDomain(IXFunction function, Object obj) {
			this.function = function;
			this.obj = obj;
		}

		static Object[] getDomain(DataContext dataContext, IXFunction f) {
			final List<Object> fdom = new ArrayList<>();

			if (!f.isDerived()) {
				fdom.add(f.getCanonicalName());
			} else {
				fdom.add("derived entity");
			}

			if (dataContext != null && dataContext.getConceptMap() != null) {
				final String path = dataContext.getConceptMap().getConceptProvenance(f.getCanonicalName());
				if (path != null) {
					fdom.add(new FConceptMap(path));
				}
			}

			if (f instanceof XFunctionBasic) {
				final XFunctionBasic fb = (XFunctionBasic) f;
				if (fb.getDomainType() == null) {
					fdom.add(new FDomainType(f));
				}

				if (fb.getRangeType() == null) {
					fdom.add(new FRangeType(f));
				}

				if (fb.getDomainType() != null && fb.getRangeType() != null) {
					fdom.add(new FTypeInfo(f));
				}

				fdom.add(new FInfo(f));
			}

			for (final Object o : f.domain()) {
				fdom.add(new FDomain(f, o));
			}

			return fdom.toArray();
		}
	}

	static class FDomainType {
		IXFunction function;

		FDomainType(IXFunction function) {
			this.function = function;
		}
	}

	static class FRangeType {
		IXFunction function;

		FRangeType(IXFunction function) {
			this.function = function;
		}
	}

	static class FTypeInfo {
		IXFunction function;

		FTypeInfo(IXFunction function) {
			this.function = function;
		}
	}

	static class DataTypes {
		DataContext context;

		public DataTypes(DataContext context) {
			this.context = context;
		}
	}

	static class ExternalElementMappings {
		DataContext context;

		public ExternalElementMappings(DataContext context) {
			this.context = context;
		}
	}

	static class ExternalElementMap {
		DataContext context;
		String element;

		public ExternalElementMap(DataContext context, String element) {
			this.context = context;
			this.element = element;
		}
	}

	static class CarrierType {
		DataContext context;
		String type;

		public CarrierType(DataContext context, String type) {
			this.context = context;
			this.type = type;
		}
	}

	static class CarrierTypeElements {
		DataContext context;
		String type;

		public CarrierTypeElements(DataContext context, String type) {
			this.context = context;
			this.type = type;
		}
	}

}