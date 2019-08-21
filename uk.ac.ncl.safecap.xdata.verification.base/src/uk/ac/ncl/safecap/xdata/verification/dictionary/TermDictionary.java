package uk.ac.ncl.safecap.xdata.verification.dictionary;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

@XmlBinding(path = "termdictionary")
public interface TermDictionary extends Element {
	ElementType TYPE = new ElementType(TermDictionary.class);

	@Type(base = DictionarySection.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "section", type = DictionarySection.class))
	ListProperty PROP_SECTIONS = new ListProperty(TYPE, "Sections");

	ElementList<DictionarySection> getSections();

}
