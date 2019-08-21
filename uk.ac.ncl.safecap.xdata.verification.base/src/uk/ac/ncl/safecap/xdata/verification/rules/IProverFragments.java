
package uk.ac.ncl.safecap.xdata.verification.rules;

import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

import uk.ac.ncl.safecap.prover.core.BuiltProveFragments;
import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.INamed;

public interface IProverFragments extends ICommented, INamed {
	ElementType TYPE = new ElementType(IProverFragments.class);

	@Type(base = RewriteRule.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "rewrite", type = RewriteRule.class))
	ListProperty PROP_REWRITE_RULES = new ListProperty(TYPE, "RewriteRules");

	ElementList<RewriteRule> getRewriteRules();

	@Type(base = InferenceRule.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "inference", type = InferenceRule.class))
	ListProperty PROP_INFERENCE_RULES = new ListProperty(TYPE, "InferenceRules");

	ElementList<InferenceRule> getInferenceRules();

	@Type(base = TacticRule.class)
	@XmlListBinding(mappings = @XmlListBinding.Mapping(element = "tactic", type = TacticRule.class))
	ListProperty PROP_TACTIC_RULES = new ListProperty(TYPE, "TacticRules");

	ElementList<InferenceRule> getTacticRules();

	@Type(base = BuiltProveFragments.class)
	TransientProperty PROP_BUILT_PROVE_FRAGMENTS = new TransientProperty(TYPE, "BuiltProveFragments");

	Transient<BuiltProveFragments> getBuiltProveFragments();

	void setBuiltProveFragments(BuiltProveFragments value);
}
