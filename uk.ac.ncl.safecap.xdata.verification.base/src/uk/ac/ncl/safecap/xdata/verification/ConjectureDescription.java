package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.DefaultValue;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface ConjectureDescription extends INamed {
	ElementType TYPE = new ElementType(ConjectureDescription.class);

	@XmlBinding(path = "kind")
	@Type(base = ConjectureDescriptionKind.class)
	@DefaultValue(text = "NONE")
	@Required
	ValueProperty PROP_KIND = new ValueProperty(TYPE, "Kind");

	Value<ConjectureDescriptionKind> getKind();

	void setKind(ConjectureDescriptionKind value);

	@XmlBinding(path = "description")
	@LongString
	ValueProperty PROP_DESCRIPTION = new ValueProperty(TYPE, "Description");

	Value<String> getDescription();

	void setDescription(String value);
}
