package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Unique;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.prime.sim.parser.FormulaSource;

public interface SourceReference extends Element, ICommented, INamed {
	ElementType TYPE = new ElementType(SourceReference.class);

	@XmlBinding(path = "path")
	@Required
	@Unique
	ValueProperty PROP_PATH = new ValueProperty(TYPE, "Path");

	Value<String> getPath();

	void setPath(String value);

	@Type(base = String.class)
	TransientProperty PROP_CONTENT = new TransientProperty(TYPE, "Content");

	Transient<String> getContent();

	void setContent(String value);

	@Type(base = FormulaSource.class)
	TransientProperty PROP_FORMULASOURCE = new TransientProperty(TYPE, "FormulaSource");

	Transient<FormulaSource> getFormulaSource();

	void setFormulaSource(FormulaSource value);

}
