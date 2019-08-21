package uk.ac.ncl.safecap.common.report;

public class SRFigure extends SRContent {
	public static final String CAPTION = "caption";
	public static final String IMAGE_TYPE = "imageType";

	public SRFigure(String caption) {
		super();
		super.set(CAPTION, caption);
	}

	@Override
	public SRFigure add(String text) {
		final SFPlain plain = new SFPlain(text);
		plain.set(SRPart.NOESCAPE, true);
		return (SRFigure) add(plain);
	}

	public SRFigure(String caption, String type) {
		super();
		super.set(CAPTION, caption);
		super.set(IMAGE_TYPE, type);
	}

}
