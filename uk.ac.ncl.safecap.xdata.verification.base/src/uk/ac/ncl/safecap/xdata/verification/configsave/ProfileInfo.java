package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import uk.ac.ncl.safecap.xmldata.FileUtil;

public class ProfileInfo {
	public static final String CONJECTURES_FILE = "conjectures.xml";
	public static final String SCHEMA_CONTRACT_FILE = "schema.csv";
	public static final String DERIVED_CONTRACT_FILE = "derived.csv";
	public static final String IXL_CONTRACT_FILE = "ixl.csv";
	public static final String AUTHOR_FILE = "author";
	public static final String COMMENT_FILE = "comment";
	public static final String PROPERTIES_FILE = "properties";

	private final String name;
	private String author;
	private String comment;
	private Map<String, String> properties;
	private final File schemaContract;
	private final File signallingContract;
	private final File conjectures;
	private final File derivedContract;

	public static ProfileInfo make(File folder) {
		try {
			final String s_name = folder.getName();
			final File schema_contract = getFileIn(folder, SCHEMA_CONTRACT_FILE);
			File derived_contract = getFileIn(folder, DERIVED_CONTRACT_FILE);
			final File ixl_contract = getFileIn(folder, IXL_CONTRACT_FILE);
			final File author = getFileIn(folder, AUTHOR_FILE);
			final File comment = getFileIn(folder, COMMENT_FILE);
			final File properties = getFileIn(folder, PROPERTIES_FILE);
			final File conjectures = getFileIn(folder, CONJECTURES_FILE);

			if (schema_contract.exists() && schema_contract.canRead() && ixl_contract.exists() && ixl_contract.canRead()
					&& conjectures.exists() && conjectures.canRead()) {
				String s_author = null;
				if (author != null && author.exists() && author.canRead()) {
					s_author = FileUtil.getFileContents(author).trim();
				}
				String s_comment = null;
				if (comment != null && comment.exists() && comment.canRead()) {
					s_comment = FileUtil.getFileContents(comment).trim();
				}
				String s_properties = null;
				if (properties != null && properties.exists() && properties.canRead()) {
					s_properties = FileUtil.getFileContents(properties);
				}

				if (derived_contract != null && (!derived_contract.exists() || !derived_contract.canRead())) {
					derived_contract = null;
				}

				final ProfileInfo profile = new ProfileInfo(s_name, schema_contract, ixl_contract, conjectures, derived_contract);
				profile.setAuthor(s_author);
				profile.setComment(s_comment);

				if (s_properties != null) {
					profile.setProperties(parseProperties(s_properties));
				}

				return profile;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Map<String, String> parseProperties(String s_properties) {
		final Map<String, String> result = new HashMap<>();
		final String[] parts = s_properties.split("\n");
		for (final String p : parts) {
			final String[] sb = p.split("=");
			if (sb.length == 2) {
				result.put(sb[0].trim(), sb[1].trim());
			} else {
				return null;
			}
		}
		return result;
	}

	private static File getFileIn(File folder, String name) {
		final File file = new File(folder.getAbsolutePath() + File.separator + name);
		if (file.exists() && file.isFile() && file.canRead()) {
			return file;
		}

		return null;
	}

	public ProfileInfo(String name, File schemaContract, File signallingContract, File conj, File derivedContract) {
		this.name = name;
		this.schemaContract = schemaContract;
		this.signallingContract = signallingContract;
		conjectures = conj;
		this.derivedContract = derivedContract;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getComment() {
		return comment;
	}

	public File getSchemaContract() {
		return schemaContract;
	}

	public File getSignallingContract() {
		return signallingContract;
	}

	public File getDerivedContract() {
		return derivedContract;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public File getConjectures() {
		return conjectures;
	}

	@Override
	public String toString() {
		try {
			final StringBuilder sb = new StringBuilder();
			final BasicFileAttributes attr = Files.readAttributes(schemaContract.toPath(), BasicFileAttributes.class);
			final FileTime date = attr.lastModifiedTime();
			final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			final String dateCreated = df.format(date.toMillis());
			sb.append("Created on " + dateCreated);
			if (author != null) {
				sb.append(" by " + author);
			}
			return sb.toString();
		} catch (final Throwable e) {
			return "error reading profile";
		}
	}
}
