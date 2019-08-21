package uk.ac.ncl.safecap.xmldata.editors;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.TypeDecomposition;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.CarrierType;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.CarrierTypeElements;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.DataTypes;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.ExternalElementMap;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.ExternalElementMappings;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FConceptMap;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FDomain;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FDomainType;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FInfo;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FRangeType;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FTypeInfo;
import uk.ac.ncl.safecap.xmldata.editors.FunctionTreeContentProvider.FunctionBundle;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XIntegerType;
import uk.ac.ncl.safecap.xmldata.types.XPowType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRealType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XSeqType;
import uk.ac.ncl.safecap.xmldata.types.XType;

class FunctionTreeLabelProvider extends StyledCellLabelProvider {

	@Override
	public void update(ViewerCell cell) {
		final Object element = cell.getElement();
		final StyledString text = new StyledString();

		try {
			if (element instanceof FunctionBundle) {
				final FunctionBundle b = (FunctionBundle) element;
				text.append(b.name, StyleColours.commentStyler);
				text.append(" " + b.mapped + "/" + b.typed + "/" + b.functions.size(), StyleColours.typeStyler);
			} else if (element instanceof IXFunction) {
				final IXFunction f = (IXFunction) element;
				final XType type = f.getFunctionType();
				final XFunctionBasic ff = (XFunctionBasic) f;
				final DataContext dataContext = ff.getDataContext();
				if (type != null) {
					text.append(f.getName(), StyledString.COUNTER_STYLER);
					if (dataContext != null && dataContext.getConceptMap() != null) {
						final String path = dataContext.getConceptMap().getConceptProvenance(f.getCanonicalName());
						if (path != null) {
							text.append("+ ");
						}
					}
					text.append(" ");
					text.append(type.toString(), StyleColours.typeStyler);
				} else {
					text.append(f.getName(), StyleColours.errorStyler);
				}

				if (f.domain().isEmpty()) {
					text.append(" ");
					text.append("(empty)", StyleColours.commentStyler);
				}
				if (f.getTag() != null) {
					text.append(" [" + f.getTag() + "]", StyleColours.commentStyler);
				}

				text.append(" card:" + f.domain().size(), StyleColours.commentStyler);
			} else if (element instanceof CarrierType) {
				final CarrierType carriertype = (CarrierType) element;
				final Set<String> members = carriertype.context.getEnumMembers(carriertype.type);
				text.append(carriertype.type);
				if (members != null) {
					text.append(" (" + members.size() + ")");
				}
			} else if (element instanceof CarrierTypeElements) {
				text.append("elements");
			} else if (element instanceof DataTypes) {
				text.append("Types");
			} else if (element instanceof ExternalElementMappings) {
				text.append("External mappings");
			} else if (element instanceof ExternalElementMap) {
				final ExternalElementMap map = (ExternalElementMap) element;
				text.append(map.element);
				text.append(" \u21A6 ");
				text.append("{");
				int count = 0;
				for (final String z : map.context.getExternalMap(map.element)) {
					if (count > 0) {
						text.append(", ");
					}

					text.append(z);
					count++;
				}
				text.append("}");
			} else if (element instanceof TypeDecompositionTemplate) {
				text.append("*decomposition template: enter here", StyleColours.typeStyler);
			} else if (element instanceof TypeDecomposition) {
				final TypeDecomposition td = (TypeDecomposition) element;
				text.append("extractor " + td.getName(), StyleColours.typeStyler);
			} else if (element instanceof FDomain) {
				final FDomain d = (FDomain) element;
				// text.append(d.obj.toString());
				format(text, d.obj, d.function, true);
				// text.append(" |-> ");
				text.append(" \u21A6 ");
				final Object v = d.function.get(d.obj);
				if (d.function.isFunction()) {
					final Object t = d.function.getAny(d.obj);
					format(text, t, d.function, true);
				} else {
					final Collection<?> coll = (Collection<?>) v;

					text.append("{");
					int count = 0;
					for (final Object z : coll) {
						if (count > 0) {
							text.append(", ");
						}

						format(text, z, d.function, true);
						count++;
					}
					text.append("}");
				}
			} else if (element instanceof FDomainType) {
				final FDomainType fd = (FDomainType) element;
				final XType type = ((XFunctionBasic) fd.function).getDomainType();
				if (type == null) {
					text.append("*domain: ", StyledString.COUNTER_STYLER);
					text.append("unknown", StyleColours.errorStyler);
				}
			} else if (element instanceof FRangeType) {
				final FRangeType fd = (FRangeType) element;
				final XType type = ((XFunctionBasic) fd.function).getRangeType();
				if (type == null) {
					text.append("*range: ", StyledString.COUNTER_STYLER);
					text.append("unknown", StyleColours.errorStyler);
				}
			} else if (element instanceof FTypeInfo) {
				final FTypeInfo fd = (FTypeInfo) element;
				final XType type = fd.function.getFunctionType();
				if (type instanceof XRelationType) {
					final XRelationType rl = (XRelationType) type;
					text.append(rl.getKind().getLongName(), StyledString.COUNTER_STYLER);
				} else {
					text.append(type.toString(), StyledString.COUNTER_STYLER);
				}
			} else if (element instanceof XIntegerType || element instanceof XRealType) {
				text.append(element.toString(), StyleColours.typeStyler);
			} else if (element instanceof FInfo) {
				final FInfo finfo = (FInfo) element;
				if (finfo.function.getDescription() != null) {
					text.append(finfo.function.getDescription(), StyleColours.commentStyler);
				} else {
					text.append("*description: enter here", StyleColours.warningStyler);
				}
			} else if (element instanceof FConceptMap) {
				final FConceptMap fc = (FConceptMap) element;
				text.append(fc.map, StyleColours.typeStyler);
			} else {
				text.append(element.toString());
			}
			cell.setText(text.toString());
			cell.setStyleRanges(text.getStyleRanges());
		} catch (final Exception e) {
			cell.setText("?error?");
			e.printStackTrace();
		}

		super.update(cell);

	}

	private void format(StyledString text, Object value, IXFunction f, boolean topLevel) {
		if (value instanceof String) {
			if (f.getFunctionType() == null) {
				// text.append(value.toString(), StyleColours.errorStyler);
				text.append(value.toString());
				if (f instanceof XFunctionBasic) {
					final XFunctionBasic ff = (XFunctionBasic) f;
					final XType type = XType.resolve(ff.getDataContext().getTypeRegistry(), value);
					if (type != null) {
						text.append("\u2219");
						text.append(type.toString(), StyleColours.subTypeStyler);
					}
				}
			} else {
				text.append(value.toString());
			}
		} else if (value instanceof Boolean || value instanceof Integer || value instanceof Double) {
			text.append(value.toString(), StyleColours.literalStyler);
		} else if (value instanceof LocatedValue) {
			final LocatedValue locvalue = (LocatedValue) value;
			format(text, locvalue.getValue(), f, topLevel);
			// text.append("@" + ((LocatedValue) value).getOffset(),
			// StyleColours.commentStyler);
		} else if (value instanceof ValueList) {
			final ValueList vlist = (ValueList) value;
			if (vlist.getType() instanceof XSeqType) {
				text.append("[");
				for (int i = 0; i < vlist.size(); i++) {
					if (i > 0) {
						text.append(", ");
					}

					format(text, vlist.get(i), f, false);
				}
				text.append("]");
				if (f.getFunctionType() == null && vlist.getType() != null) {
					text.append("\u2219");
					text.append(vlist.getType().toString(), StyleColours.subTypeStyler);
				}
			} else if (vlist.getType() instanceof XPowType) {
				text.append("{");
				for (int i = 0; i < vlist.size(); i++) {
					if (i > 0) {
						text.append(", ");
					}

					format(text, vlist.get(i), f, false);
				}
				text.append("}");
				if (f.getFunctionType() == null && vlist.getType() != null) {
					text.append("\u2219");
					text.append(vlist.getType().toString(), StyleColours.subTypeStyler);
				}
			} else if (vlist.getType() instanceof XProductType) {
				if (!topLevel) {
					text.append("(");
				}
				for (int i = 0; i < vlist.size(); i++) {
					if (i > 0) {
						text.append(" \u21A6 ");
					}
					format(text, vlist.get(i), f, false);
				}
				if (!topLevel) {
					text.append(")");
				}
				if (f.getFunctionType() == null && vlist.getType() != null) {
					text.append("\u2219");
					text.append(vlist.getType().toString(), StyleColours.subTypeStyler);
				}
			} else {
				text.append("(");
				for (int i = 0; i < vlist.size(); i++) {
					if (i > 0) {
						text.append(", ");
					}

					format(text, vlist.get(i), f, false);
				}
				text.append(")");
				if (f.getFunctionType() == null && vlist.getType() != null) {
					text.append("\u2219");
					text.append(vlist.getType().toString(), StyleColours.subTypeStyler);
				}
			}
		} else {
			text.append(value.toString(), StyleColours.errorStyler);
		}
	}
}
