package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.DefaultValue;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface IdentifierDefinition extends Element, ICommented, IFormulaSource, ITagSet, INamed {
	ElementType TYPE = new ElementType(IdentifierDefinition.class);

	@XmlBinding(path = "kind")
	@Type(base = IdentifierKind.class)
	@DefaultValue(text = "MODEL")
	@Required
	ValueProperty PROP_KIND = new ValueProperty(TYPE, "Kind");

	Value<IdentifierKind> getKind();

	void setKind(IdentifierKind value);

}
