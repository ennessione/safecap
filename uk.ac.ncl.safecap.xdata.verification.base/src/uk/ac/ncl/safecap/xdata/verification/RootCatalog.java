package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.ListProperty;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;
import org.eclipse.sapphire.modeling.xml.annotations.XmlListBinding;

import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.dictionary.TermDictionary;
import uk.ac.ncl.safecap.xdata.verification.profile.Profiles;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;
import uk.ac.ncl.safecap.xdata.verification.safetycase.SafetyCase;

@XmlBinding(path = "catalog")
public interface RootCatalog extends TermDictionary, SafetyCase, Profiles, IIdentifierCategory, IPredicateCategory, ITransitionCategory,
		IConjectureCategory, IFragmentCategory, IProverFragments, ITagSet, ICommented, INamed {
	ElementType TYPE = new ElementType(RootCatalog.class);

	@Type(base = DataReference.class)
	// @Service( impl = CatalogReferencesValidationService.class )
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "references", type = DataReference.class) })
	ListProperty PROP_REFERENCES = new ListProperty(TYPE, "References");
	ElementList<DataReference> getReferences();

	@Type(base = SourceReference.class)
	@XmlListBinding(mappings = { @XmlListBinding.Mapping(element = "sources", type = SourceReference.class) })
	ListProperty PROP_SOURCES = new ListProperty(TYPE, "Sources");
	ElementList<SourceReference> getSources();

	// Run-time data context
	@Type(base = SDAContext.class)
	TransientProperty PROP_CONTEXT = new TransientProperty(TYPE, "Context");
	Transient<SDAContext> getContext();
	void setContext(SDAContext value);
}
