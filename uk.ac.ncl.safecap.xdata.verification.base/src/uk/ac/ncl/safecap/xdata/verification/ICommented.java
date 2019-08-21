package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface ICommented extends Element {
	ElementType TYPE = new ElementType(ICommented.class);

	@XmlBinding(path = "comment")
	@LongString
	ValueProperty PROP_COMMENT = new ValueProperty(TYPE, "Comment");

	Value<String> getComment();

	void setComment(String value);
}
