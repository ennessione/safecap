package uk.ac.ncl.safecap.common.report;

public class SRDocument extends SRContainer {
	public static final String AUTHOR = "author";
	public static final String DATE = "date";
	public static final String SUB_TITLE = "subtitle";
	public static final String HTML_STYLE = "html_style";

	public SRDocument(String title) {
		super.set(TITLE, title);
	}
}
