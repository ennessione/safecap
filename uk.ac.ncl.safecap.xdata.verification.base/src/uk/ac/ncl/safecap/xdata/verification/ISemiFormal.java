package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.annotations.Service;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.xdata.verification.services.SemiFormalValidationService;

public interface ISemiFormal extends Element {
	ElementType TYPE = new ElementType(ISemiFormal.class);

	@XmlBinding(path = "semiformal")
	@LongString
	@Service(impl = SemiFormalValidationService.class)
	ValueProperty PROP_SEMIFORMAL = new ValueProperty(TYPE, "SemiFormal");

	Value<String> getSemiFormal();

	void setSemiFormal(String value);
}
