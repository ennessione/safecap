package uk.ac.ncl.safecap.common.report;

public interface ISRDocumentRenderer {
	String render(SRDocument document);
	String renderPart(SRPart document);
}
