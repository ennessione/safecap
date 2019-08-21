package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.ClearOnDisable;
import org.eclipse.sapphire.modeling.annotations.LongString;

public interface FragmentDefinition extends ICommented, IFormulaSource, INamed {
	ElementType TYPE = new ElementType(FragmentDefinition.class);

	@Type(base = String.class)
	@LongString
	@ClearOnDisable
	ValueProperty PROP_SEMANTICS = new ValueProperty(TYPE, "Semantics");

	Value<String> getSemantics();

	void setSemantics(String value);
}