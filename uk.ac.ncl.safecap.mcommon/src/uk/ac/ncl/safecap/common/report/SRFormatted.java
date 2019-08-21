package uk.ac.ncl.safecap.common.report;

public abstract class SRFormatted extends SRPart {
	private StringBuilder content;

	public SRFormatted() {

	}

	public SRFormatted(String text) {
		print(text);
	}

	public String getContent() {
		if (content == null) {
			return "";
		} else {
			return content.toString();
		}
	}

	public SRFormatted print(String text) {
		if (content == null) {
			content = new StringBuilder();
		}

		content.append(text);

		return this;
	}

	public StringBuilder getBuilder() {
		if (content == null) {
			content = new StringBuilder();
		}

		return content;
	}

}
