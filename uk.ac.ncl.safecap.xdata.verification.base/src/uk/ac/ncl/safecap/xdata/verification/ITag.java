package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface ITag extends Element {
	ElementType TYPE = new ElementType(ITag.class);

	@XmlBinding(path = "name")
	@Required
	ValueProperty PROP_NAME = new ValueProperty(TYPE, "Name");

	Value<String> getName();

	void setName(String value);

	@XmlBinding(path = "value")
	@Required
	ValueProperty PROP_VALUE = new ValueProperty(TYPE, "Value");

	Value<String> getValue();

	void setValue(String value);
}
