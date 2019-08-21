package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

public interface DataReference extends Element, ICommented, INamed {
	ElementType TYPE = new ElementType(DataReference.class);

	@XmlBinding(path = "path")
	@Type(base = org.eclipse.sapphire.modeling.Path.class)
	// @Service( impl = DataReferenceValidationService.class )
	@Required
	ValueProperty PROP_PATH = new ValueProperty(TYPE, "Path");

	Value<org.eclipse.sapphire.modeling.Path> getPath();

	void setPath(org.eclipse.sapphire.modeling.Path value);

	@Type(base = Object.class)
	TransientProperty PROP_OBJECT = new TransientProperty(TYPE, "Object");

	Transient<Object> getObject();

	void setObject(Object value);
}
