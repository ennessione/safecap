package uk.ac.ncl.safecap.xdata.verification.rules;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ImpliedElementProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.IFormulaSource;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.ITagSet;

public interface RewriteRule extends Element, ICommented, ITagSet, INamed {
	ElementType TYPE = new ElementType(RewriteRule.class);

	@XmlBinding(path = "template")
	@Type(base = IFormulaSource.class)
	@LongString
	ImpliedElementProperty PROP_TEMPLATE = new ImpliedElementProperty(TYPE, "Template");

	IFormulaSource getTemplate();

	@XmlBinding(path = "target")
	@Type(base = IFormulaSource.class)
	@LongString
	@Required
	ImpliedElementProperty PROP_TARGET = new ImpliedElementProperty(TYPE, "Target");

	IFormulaSource getTarget();

}
