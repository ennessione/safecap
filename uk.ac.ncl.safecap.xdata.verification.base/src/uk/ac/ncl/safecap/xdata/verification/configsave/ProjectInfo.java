package uk.ac.ncl.safecap.xdata.verification.configsave;

class ProjectInfo implements java.io.Serializable {
	private static final long serialVersionUID = 2447559685809718852L;
	private final String profile;
	private final String name;
	private final String author;
	private final String descripton;

	public ProjectInfo(String name, String profile, String author, String descripton) {
		super();
		this.name = name;
		this.profile = profile;
		this.author = author;
		this.descripton = descripton;
	}

	public String getProfile() {
		return profile;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescripton() {
		return descripton;
	}

}