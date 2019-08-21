package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ImpliedElementProperty;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;

public interface VerificationProperty extends Verifiable {
	ElementType TYPE = new ElementType(VerificationProperty.class);

	@XmlBinding(path = "hyp")
	@Type(base = IFormulaSource.class)
	ImpliedElementProperty PROP_HYPOTHESES = new ImpliedElementProperty(TYPE, "Hypotheses");

	IFormulaSource getHypotheses();

	@Type(base = ManagedProofObligation.class)
	TransientProperty PROP_PROOF_SCRIPT = new TransientProperty(TYPE, "ProofScript");

	Transient<ManagedProofObligation> getProofScript();

	void setProofScript(ManagedProofObligation value);
}
