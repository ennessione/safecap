package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Unique;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.annotations.Service;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.xdata.verification.services.IdValidationService;

public interface INamed extends Element {
	ElementType TYPE = new ElementType(INamed.class);

	@XmlBinding(path = "id")
	@Required
	@Unique
	@Service(impl = IdValidationService.class)
	ValueProperty PROP_ID = new ValueProperty(TYPE, "Id");
	Value<String> getId();
	void setId(String value);

}
