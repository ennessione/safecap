package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.ClearOnDisable;
import org.eclipse.sapphire.modeling.annotations.LongString;

public interface IValued extends Element {
	ElementType TYPE = new ElementType(IValued.class);

	@Type(base = String.class)
	@LongString
	@ClearOnDisable
	ValueProperty PROP_VALUE = new ValueProperty(TYPE, "Value");

	Value<String> getValue();

	void setValue(String value);
}
