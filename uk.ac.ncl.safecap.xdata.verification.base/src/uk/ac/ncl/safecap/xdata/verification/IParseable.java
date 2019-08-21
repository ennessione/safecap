package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.LongString;
import org.eclipse.sapphire.modeling.annotations.Service;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.safecap.xdata.verification.services.FormulaValidationService;

public interface IParseable extends Element {
	ElementType TYPE = new ElementType(IParseable.class);

	@Type(base = FormulaSource.class)
	TransientProperty PROP_FORMULASOURCE = new TransientProperty(TYPE, "FormulaSource");

	Transient<FormulaSource> getFormulaSource();

	void setFormulaSource(FormulaSource value);

	@XmlBinding(path = "formula")
	@Service(impl = FormulaValidationService.class)
	@LongString
	// @Required
	ValueProperty PROP_FORMULA = new ValueProperty(TYPE, "Formula");

	Value<String> getFormula();

	void setFormula(String value);
}
