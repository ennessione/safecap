package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ImpliedElementProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface BaseReport extends ICommented, INamed, ITagSet {
	ElementType TYPE = new ElementType(BaseReport.class);

	@XmlBinding(path = "location")
	@Type(base = IParseable.class)
	@LongString
	ImpliedElementProperty PROP_LOCATION = new ImpliedElementProperty(TYPE, "Location");

	IParseable getLocation();

	@XmlBinding(path = "description")
	@Type(base = IParseable.class)
	@LongString
	ImpliedElementProperty PROP_DESCRIPTION = new ImpliedElementProperty(TYPE, "Description");

	IParseable getDescription();

	@XmlBinding(path = "solution")
	@Type(base = IParseable.class)
	@LongString
	ImpliedElementProperty PROP_SOLUTION = new ImpliedElementProperty(TYPE, "Solution");

	IParseable getSolution();

	@XmlBinding(path = "keyelements")
	@LongString
	ValueProperty PROP_KEY_ELEMENTS = new ValueProperty(TYPE, "KeyElements");

	Value<String> getKeyElements();

	void setKeyElements(String value);

}
