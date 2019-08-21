package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;

import uk.ac.ncl.safecap.xdata.provers.VerificationResult;

public interface Verifiable extends Element, ICommented, ITagSet, INamed, IFormulaSource, IValued {
	ElementType TYPE = new ElementType(Verifiable.class);

	@Type(base = Boolean.class)
	ValueProperty PROP_VALID = new ValueProperty(TYPE, "Valid");

	Value<Boolean> isValid();

	void setValid(String value);

	void setValid(Boolean value);

	@Type(base = VerificationResult.class)
	TransientProperty PROP_VRESULT = new TransientProperty(TYPE, "VResult");

	Transient<VerificationResult> getVResult();

	void setVResult(VerificationResult value);

}
