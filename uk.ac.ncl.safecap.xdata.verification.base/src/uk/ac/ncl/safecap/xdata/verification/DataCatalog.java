package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

@XmlBinding(path = "data")

public interface DataCatalog extends Element {
	ElementType TYPE = new ElementType(DataCatalog.class);

	@Type(base = DataReference.class)
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "references", type = DataReference.class) })
	ListProperty PROP_REFERENCES = new ListProperty(TYPE, "References");

	ElementList<DataReference> getReferences();
}
