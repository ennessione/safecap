package uk.ac.ncl.safecap.xdata.verification.rules;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.INamed;

public interface TacticRule extends Element, ICommented, INamed {
	ElementType TYPE = new ElementType(TacticRule.class);

	@XmlBinding(path = "tacticorder")
	@Required
	@LongString
	ValueProperty PROP_TACTIC_ORDER = new ValueProperty(TYPE, "TacticOrder");

	Value<String> getTacticOrder();

	void setTacticOrder(String value);
}
