package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ImpliedElementProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface TransitionDefinition extends Element, ICommented, IVerificationSource, ITagSet, INamed {
	ElementType TYPE = new ElementType(TransitionDefinition.class);

	@XmlBinding(path = "pre")
	@Type(base = IFormulaSource.class)
	@LongString
	ImpliedElementProperty PROP_PRE = new ImpliedElementProperty(TYPE, "Pre");

	IFormulaSource getPre();

	@XmlBinding(path = "post")
	@Type(base = IFormulaSource.class)
	@LongString
	@Required
	ImpliedElementProperty PROP_POST = new ImpliedElementProperty(TYPE, "Post");

	IFormulaSource getPost();
}
