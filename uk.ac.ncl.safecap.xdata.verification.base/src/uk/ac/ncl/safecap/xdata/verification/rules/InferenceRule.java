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

public interface InferenceRule extends Element, ICommented, ITagSet, INamed {
	ElementType TYPE = new ElementType(InferenceRule.class);

	@XmlBinding(path = "premise")
	@Type(base = IFormulaSource.class)
	@LongString
	ImpliedElementProperty PROP_PREMISE = new ImpliedElementProperty(TYPE, "Premise");

	IFormulaSource getPremise();

	@XmlBinding(path = "condition")
	@Type(base = IFormulaSource.class)
	@LongString
	@Required
	ImpliedElementProperty PROP_CONDITION = new ImpliedElementProperty(TYPE, "Condition");

	IFormulaSource getCondition();

	@XmlBinding(path = "conclusion")
	@Type(base = IFormulaSource.class)
	@LongString
	@Required
	ImpliedElementProperty PROP_CONCLUSION = new ImpliedElementProperty(TYPE, "Conclusion");

	IFormulaSource getConclusion();
}
