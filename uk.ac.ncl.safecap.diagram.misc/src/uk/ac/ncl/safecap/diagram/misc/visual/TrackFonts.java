package uk.ac.ncl.safecap.diagram.misc.visual;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class TrackFonts {

	public static final Font FONT_NORMAL;
	public static final Font FONT_SMALL;
	public static final Font FONT_SMALLER;
	public static final Font FONT_TINY;
	public static final Font FONT_BOLD;
	public static final Font FONT_LARGE_BOLD;
	public static final Font FONT_BOLD_ITALIC;
	public static final Font FONT_SMALL_ITALIC;
	public static final Font FONT_SMALL_BOLD;
	public static final Font FONT_HELV9;
	public static final Font FONT_MONO;

	static {
		final Font f = JFaceResources.getFont("Helvetica");

		FONT_NORMAL = makeFont(f, 10, SWT.NORMAL);
		FONT_TINY = makeFont(f, 6, SWT.NORMAL);
		FONT_SMALL = makeFont(f, 8, SWT.NORMAL);
		FONT_SMALLER = makeFont(f, 8, SWT.NORMAL);
		FONT_BOLD = makeFont(f, 10, SWT.BOLD);
		FONT_LARGE_BOLD = makeFont(f, 14, SWT.BOLD);
		FONT_BOLD_ITALIC = makeFont(f, 10, SWT.BOLD | SWT.ITALIC);
		FONT_SMALL_ITALIC = makeFont(f, 8, SWT.ITALIC);
		FONT_SMALL_BOLD = makeFont(f, 8, SWT.BOLD);
		FONT_HELV9 = makeFont("Helvetica", 8, SWT.BOLD);
		FONT_MONO = makeFont("Monospaced", 12, SWT.NORMAL);
	}

	public static Font makeFont(final Font font, final int size, final int style) {
		final FontData[] fontData = font.getFontData();
		for (int i = 0; i < fontData.length; ++i) {
			fontData[i].setHeight(size);
			fontData[i].setStyle(style);
		}

		return new Font(TrackColours.getDisplay(), fontData);
	}

	public static Font makeFont(final String font, final int size, final int style) {
		return new Font(TrackColours.getDisplay(), font, size, style);
	}

}
