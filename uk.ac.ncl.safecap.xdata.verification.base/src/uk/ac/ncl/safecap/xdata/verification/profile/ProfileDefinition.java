package uk.ac.ncl.safecap.xdata.verification.profile;

import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.Path;
import org.eclipse.sapphire.modeling.annotations.AbsolutePath;
import org.eclipse.sapphire.modeling.annotations.FileExtensions;
import org.eclipse.sapphire.modeling.annotations.FileSystemResourceType;
import org.eclipse.sapphire.modeling.annotations.MustExist;
import org.eclipse.sapphire.modeling.annotations.Required;
import org.eclipse.sapphire.modeling.annotations.ValidFileSystemResourceType;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

import uk.ac.ncl.safecap.xdata.verification.ICommented;
import uk.ac.ncl.safecap.xdata.verification.INamed;
import uk.ac.ncl.safecap.xdata.verification.ITagSet;

public interface ProfileDefinition extends ICommented, INamed, ITagSet {
	ElementType TYPE = new ElementType(ProfileDefinition.class);

	@XmlBinding(path = "profiles")
	@Required
	ValueProperty PROP_PROFILES = new ValueProperty(TYPE, "Profiles");

	Value<String> getProfiles();

	void setProfiles(String value);

	@XmlBinding(path = "author")
	@Required
	ValueProperty PROP_AUTHOR = new ValueProperty(TYPE, "Author");

	Value<String> getAuthor();

	void setAuthor(String value);

	@XmlBinding(path = "schemacontr")
	@Type(base = Path.class)
	@AbsolutePath
	@ValidFileSystemResourceType(FileSystemResourceType.FILE)
	@FileExtensions(expr = "csv,CSV")
	@MustExist
	@Required
	ValueProperty PROP_SCHEMA_CONTRACT = new ValueProperty(TYPE, "SchemaContract");

	Value<Path> getSchemaContract();

	void setSchemaContract(Path value);

	void setSchemaContract(String value);

	@XmlBinding(path = "ixlcontr")
	@Type(base = Path.class)
	@AbsolutePath
	@ValidFileSystemResourceType(FileSystemResourceType.FILE)
	@FileExtensions(expr = "csv,CSV")
	@MustExist
	@Required
	ValueProperty PROP_IXL_CONTRACT = new ValueProperty(TYPE, "IxlContract");

	Value<Path> getIxlContract();

	void setIxlContract(Path value);

	void setIxlContract(String value);

	@Type(base = String.class)
	TransientProperty PROP_DESCRIPTION = new TransientProperty(TYPE, "Description");

	Transient<String> getDescription();

	void setDescription(String value);
}
