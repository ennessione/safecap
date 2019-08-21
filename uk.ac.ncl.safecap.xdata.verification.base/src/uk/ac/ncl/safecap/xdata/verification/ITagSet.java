package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface ITagSet extends Element {
	ElementType TYPE = new ElementType(ITagSet.class);

	@Type(base = ITag.class)
	@Label(standard = "tag")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "tag", type = ITag.class) })
	ListProperty PROP_TAGS = new ListProperty(TYPE, "Tags");

	ElementList<ITag> getTags();

}
