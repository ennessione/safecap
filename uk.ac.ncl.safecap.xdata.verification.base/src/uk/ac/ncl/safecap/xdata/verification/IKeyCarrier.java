package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Unique;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.annotations.Service;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.xdata.verification.services.KeyValidationService;

public interface IKeyCarrier extends Element {
	ElementType TYPE = new ElementType(IKeyCarrier.class);

	@XmlBinding(path = "key")
	@Required
	@Unique
	@Service(impl = KeyValidationService.class)
	ValueProperty PROP_KEY = new ValueProperty(TYPE, "Key");

	Value<String> getKey();

	void setKey(String value);

}
