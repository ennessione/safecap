package uk.ac.ncl.safecap.xdata.provers;

import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLExceptionNotImplemented;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLQuantifiedExpression;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.typing.CLEnumType;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XPowType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XSeqType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class SDADependencies implements ICLExpressionVisitor {
	private final Set<XEnumType> types;
	private final Set<IXFunction> functions;
	private final ISDADataProvider context;

	public SDADependencies(ICondition property) {
		types = new HashSet<>();
		functions = new HashSet<>();
		context = property.getContext();
	}

	public void analyzeExpression(CLExpression expression) {
		expression.visit(this, null);
	}

	@Override
	public boolean visit(CLExpression element, Object userobject) throws CLExceptionNotImplemented {
		if (element instanceof CLQuantifiedExpression) {
			final CLQuantifiedExpression exp = (CLQuantifiedExpression) element;
			for (final CLParameter x : exp.getBoundParameters()) {
				addCLTypeDependency(x.getType());
			}
		} else if (element instanceof CLIdentifier) {
			final CLIdentifier iden = (CLIdentifier) element;
			addCLTypeDependency(iden.getType());

			final XEnumType enumt = context.getEnum(iden.getName());
			if (enumt != null) {
				addDependency(enumt);
			} else {
				final IXFunction func = context.getFunction(iden.getName());
				if (func != null) {
					addDependency(func);
				}
			}
		}

		return true;
	}

	private void addDependency(Object o) {
		if (o instanceof XEnumType) {
			addTypeDependency((XEnumType) o);
		} else if (o instanceof IXFunction) {
			addFunctionDependency((IXFunction) o);
//		} else if (o instanceof TypedId) {
//			String typeName = ((TypedId) o).getType();
//			if (typeName != null) {
//				XEnumType et = context.getEnum(typeName);
//				if (et != null)
//					addTypeDependency(et);
//			}
		} else if (o instanceof ValueList) {
			addTypedIdDependency(((ValueList) o).getType());
		} else {
			// do nothing
		}

	}

	private void addTypedIdDependency(XType type) {
		if (type instanceof XEnumType) {
			addTypeDependency((XEnumType) type);
		} else if (type instanceof XPowType) {
			addTypedIdDependency(((XPowType) type).getBase());
		} else if (type instanceof XSeqType) {
			addTypedIdDependency(((XSeqType) type).getBase());
		} else if (type instanceof XProductType) {
			final XProductType pt = (XProductType) type;
			for (int i = 0; i < pt.getSize(); i++) {
				addTypedIdDependency(pt.get(i));
			}
		} else if (type instanceof XRelationType) {
			final XRelationType rt = (XRelationType) type;
			addTypedIdDependency(rt.getDomain());
			addTypedIdDependency(rt.getRange());
		}
	}

	private void addCLTypeDependency(CLType type) {
		if (type instanceof CLEnumType) {
			final XEnumType et = context.getEnum(((CLEnumType) type).getName());
			if (et != null) {
				addTypeDependency(et);
			}
		} else if (type instanceof CLSeqType) {
			addCLTypeDependency(((CLSeqType) type).getBase());
		} else if (type instanceof CLPowerType) {
			addCLTypeDependency(((CLPowerType) type).getBase());
		} else if (type instanceof CLProductType) {
			final CLProductType pt = (CLProductType) type;
			addCLTypeDependency(pt.getLeft());
			addCLTypeDependency(pt.getRight());
		}
	}

	private void addFunctionDependency(IXFunction o) {
		if (!functions.contains(o)) {
			final XType _type = o.getFunctionType();
			if (_type instanceof XRelationType) {
				final XRelationType type = (XRelationType) _type;
				addTypedIdDependency(type.getDomain());
				addTypedIdDependency(type.getRange());
				// System.out.println("#### +dep " + o.getName());
				functions.add(o);
			}
		}
	}

	private void addTypeDependency(XEnumType o) {
		if (!types.contains(o)) {
			// System.out.println("#### +dep " + o.getName());
			types.add(o);
		}
	}

	public Set<XEnumType> getTypes() {
		return types;
	}

	public Set<IXFunction> getFunctions() {
		return functions;
	}
}
