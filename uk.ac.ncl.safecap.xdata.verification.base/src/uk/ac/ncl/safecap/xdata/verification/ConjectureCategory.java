package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

public interface ConjectureCategory extends Element, ICommented, IConjectureCategory, INamed {

	ElementType TYPE = new ElementType(ConjectureCategory.class);

	@Type(base = Conjecture.class)
	@Label(standard = "property")
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "conjecture", type = Conjecture.class) })
	ListProperty PROP_CONJECTURES = new ListProperty(TYPE, "Conjectures");

	ElementList<Conjecture> getConjectures();
}
