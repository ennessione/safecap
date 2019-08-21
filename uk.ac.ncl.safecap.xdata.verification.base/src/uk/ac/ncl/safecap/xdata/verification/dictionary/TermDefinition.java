package uk.ac.ncl.safecap.xdata.verification.dictionary;

import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.DefaultValue;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.IFormulaSource;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.ITagSet;
import uk.ac.ncl.safecap.xdata.verification.IValued;

public interface TermDefinition extends ICommented, IFormulaSource, INamed, IValued, ITagSet {
	ElementType TYPE = new ElementType(TermDefinition.class);

	@XmlBinding(path = "kind")
	@Type(base = TermKind.class)
	@DefaultValue(text = "LITERAL")
	@Required
	ValueProperty PROP_KIND = new ValueProperty(TYPE, "Kind");

	Value<TermKind> getKind();

	void setKind(TermKind value);

}
