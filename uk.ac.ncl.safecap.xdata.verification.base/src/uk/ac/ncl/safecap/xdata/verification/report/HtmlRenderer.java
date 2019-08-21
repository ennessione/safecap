package uk.ac.ncl.safecap.xdata.verification.report;

import uk.ac.ncl.safecap.common.report.BaseRenderer;
import uk.ac.ncl.safecap.common.report.SFBold;
import uk.ac.ncl.safecap.common.report.SFElement;
import uk.ac.ncl.safecap.common.report.SFEmph;
import uk.ac.ncl.safecap.common.report.SFExternalImage;
import uk.ac.ncl.safecap.common.report.SFLink;
import uk.ac.ncl.safecap.common.report.SFPath;
import uk.ac.ncl.safecap.common.report.SFPlain;
import uk.ac.ncl.safecap.common.report.SFSource;
import uk.ac.ncl.safecap.common.report.SFType;
import uk.ac.ncl.safecap.common.report.SMError;
import uk.ac.ncl.safecap.common.report.SMNotice;
import uk.ac.ncl.safecap.common.report.SMWarning;
import uk.ac.ncl.safecap.common.report.SRBlock;
import uk.ac.ncl.safecap.common.report.SRCode;
import uk.ac.ncl.safecap.common.report.SRComment;
import uk.ac.ncl.safecap.common.report.SRDocument;
import uk.ac.ncl.safecap.common.report.SRFigure;
import uk.ac.ncl.safecap.common.report.SRFoldable;
import uk.ac.ncl.safecap.common.report.SRFormatted;
import uk.ac.ncl.safecap.common.report.SRFormula;
import uk.ac.ncl.safecap.common.report.SRGrid;
import uk.ac.ncl.safecap.common.report.SRList;
import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.common.report.SRRelated;
import uk.ac.ncl.safecap.common.report.SRSection;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;

public class HtmlRenderer extends BaseRenderer {
	public static final String CLASS = "class";

	private String style_file;

	public HtmlRenderer() {
	}

	public void setStyleFile(String style_file) {
		this.style_file = style_file;
	}

	@Override
	protected void renderFormatted(SFElement child) {
		print("<span" + getFullClass("element", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</span>");
	}

	@Override
	protected void renderFormatted(SFType child) {
		print("<span" + getFullClass("type", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</span>");
	}

	@Override
	protected void renderFormatted(SFPath child) {
		print("<span" + getFullClass("path", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</span>");
	}
	
	@Override
	protected void renderFormatted(SFLink child) {
		print("<a " + getFullClass("path", child) + getElementStyle(child) + " href=\"" + child.get(SRPart.LINK, "#")+ "\">");
		print(getEscapedFormatted(child));
		print("</a>");
	}	

	@Override
	protected void renderFormatted(SFBold child) {
		print("<span" + getFullClass("bold", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</span>");
	}

	@Override
	protected void renderFormatted(SFEmph child) {
		print("<span" + getFullClass("emph", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</span>");
	}

	@Override
	protected void renderFormatted(SFPlain child) {
		print(getEscapedFormatted(child));
	}

	@Override
	protected void renderFormatted(SFSource child) {
		print("<span" + getFullClass("source", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</span>");
	}

	@Override
	protected void renderMarker(SMError child) {
		print("<div" + getFullClass("infomessage error", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</div>");
	}

	@Override
	protected void renderMarker(SMWarning child) {
		print("<div" + getFullClass("infomessage warning", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</div>");
	}

	@Override
	protected void renderMarker(SMNotice child) {
		print("<div" + getFullClass("infomessage warning", child) + getElementStyle(child) + ">");
		print(getEscapedFormatted(child));
		print("</div>");
	}

	@Override
	protected void renderContentStart(SRBlock child) {
		print("<div" + getFullClass("block", child) + getElementStyle(child));
		if (child.getTag() != null) {
			print(" id=\"" + child.getTag() + "\"");
		}
		print(">");
	}

	private String getElementStyleRaw(SRPart child) {
		final StringBuilder sb = new StringBuilder();
		if (child.hasKey(SRPart.BACKGROUND)) {
			sb.append("background-color: " + child.get(SRPart.BACKGROUND) + "; ");
		}
		if (child.hasKey(SRPart.COLOR)) {
			sb.append("color: " + child.get(SRPart.COLOR) + "; ");
		}		
		if (child.hasKey(SRPart.BORDER)) {
			sb.append("border-width: " + child.get(SRPart.BORDER) + "; ");
			sb.append("border-style: solid;");
		}
		if (child.hasKey(SRPart.PADDING)) {
			sb.append("padding: " + child.get(SRPart.PADDING) + "; ");
		}
		if (child.hasKey(SRPart.MARGIN)) {
			sb.append("margin: " + child.get(SRPart.MARGIN) + "; ");
		}		
		if (child.get(SRPart.NOWRAP, false)) {
			sb.append("white-space: nowrap; ");
		}
		if (child.hasKey(SRPart.ALIGN)) {
			sb.append("text-align: " + child.get(SRPart.ALIGN) + "; ");
		}
		if (child.hasKey(SRPart.WIDTH)) {
			sb.append("width: " + child.get(SRPart.WIDTH) + "; ");
		}
		if (child.hasKey(SRPart.HEIGHT)) {
			sb.append("height: " + child.get(SRPart.HEIGHT) + "; ");
		}
		if (child.hasKey(SRPart.MINHEIGHT)) {
			sb.append("min-height: " + child.get(SRPart.MINHEIGHT) + "; ");
		}
		if (child.hasKey(SRPart.MAXHEIGHT)) {
			sb.append("max-height: " + child.get(SRPart.MAXHEIGHT) + "; ");
		}		
		
		if (child.hasKey(SRPart.RAWSTYLE)) {
			sb.append(child.get(SRPart.RAWSTYLE) + "; ");
		}		
		
		sb.append("border-collapse: collapse; ");
		 
		
//		if (child.hasKey(SRPart.HEIGHT) || child.hasKey(SRPart.WIDTH)) {
//			sb.append("display: block; ");
//		}

		return sb.toString();

	}

	private String getElementStyle(SRPart child) {
		return _getElementStyle(child) + _getExtraTags(child);
	}	
	
	private String _getElementStyle(SRPart child) {
		final String s = getElementStyleRaw(child);
		if (s.length() > 0) {
			return " style = \"" + s.toString() + "\" ";
		} else {
			return "";
		}
	}
	
	private String _getExtraTags(SRPart part) {
		StringBuilder sb = new StringBuilder();
		if (part.hasKey(SRPart.ID)) {
			sb.append(" id=\"" + part.get(SRPart.ID) + "\"");
		}	
		if (part.hasKey(SRPart.INDEX)) {
			sb.append(" data-col-index=\"" + part.get(SRPart.INDEX) + "\"");
		}			
		if (part.hasKey(SRPart.ONINPUT)) {
			sb.append(" oninput=\"" + part.get(SRPart.ONINPUT) + "\"");
		}
		if (part.hasKey(SRPart.ONFOCUS)) {
			sb.append(" onfocus=\"" + part.get(SRPart.ONFOCUS) + "\"");
		}
		if (part.hasKey(SRPart.ONBLUR)) {
			sb.append(" onblur=\"" + part.get(SRPart.ONBLUR) + "\"");
		}
		
		if (part.hasKey(SRPart.EDITABLE)) {
			sb.append(" contenteditable=\"" + part.get(SRPart.EDITABLE, false) + "\"");
			sb.append(" spellcheck=\"" + part.get(SRPart.SPELLCHECK, false) + "\"");
		}		
		
		if (part.hasKey(SRPart.TOOLTIP)) {
			sb.append(" tooltip=\"" + part.get(SRPart.TOOLTIP) + "\"");
		}		
		
		return sb.toString();
	}	

	@Override
	protected void renderContentEnd(SRBlock child) {
		print("</div>");
	}

	@Override
	protected void renderContentStart(SRComment child) {
		print("<div" + getFullClass("comment", child));
		if (child.getTag() != null) {
			print(" id=\"" + child.getTag() + "\"");
		}
		print(">");
	}

	@Override
	protected void renderContentEnd(SRComment child) {
		print("</div>");
	}

	@Override
	protected void renderContentStart(SRCode child) {
		print("<div" + getFullClass("code", child) + getElementStyle(child));
		if (child.getTag() != null) {
			print(" id=\"" + child.getTag() + "\"");
		}
		print(">");
	}

	@Override
	protected void renderContentEnd(SRCode child) {
		print("</div>");
	}

	@Override
	protected void renderContentStart(SRFigure child) {
		print("<div" + getFullClass("figure", child) + getElementStyle(child));
		if (child.getTag() != null) {
			print(" id=\"" + child.getTag() + "\"");
		}
		print(">");
	}

	@Override
	protected void renderContentEnd(SRFigure child) {
		print("</div>");
	}

	@Override
	protected void renderContentStart(SRFormula child) {
		print("<div" + getFullClass("formula", child) + getElementStyle(child));
		if (child.getTag() != null) {
			print(" id=\"" + child.getTag() + "\"");
		}
		print(">");
	}

	@Override
	protected void renderContentEnd(SRFormula child) {
		print("</div>");
	}

	@Override
	protected void renderContentStart(SRRelated child) {
		print("<div" + getFullClass("related", child) + getElementStyle(child));
		if (child.getTag() != null) {
			print(" id=\"" + child.getTag() + "\"");
		}
		print(">");
	}

	@Override
	protected void renderContentEnd(SRRelated child) {
		print("</div>");
	}

	@Override
	protected void renderListStart(SRList section, String title) {
		print(title);
		print("<ul" + getFullClass("list", section) + ">");
	}

	@Override
	protected void renderListItemStart(SRList list, SRPart item) {
		print("<li" + getFullClass("listitem", item) + ">");
	}

	@Override
	protected void renderListItemEnd(SRList list, SRPart item) {
		print("</li>");
	}

	@Override
	protected void renderListEnd(SRList section) {
		print("</ul>");
	}

	@Override
	protected void renderSectionStart(SRSection section, String index) {
		print("<div" + getFullClass("section" + getSectionDepth() + getElementStyle(section), section));
		if (section.getTag() != null) {
			print(" id=\"" + section.getTag() + "\"");
		}
		print(">");

		if (section.get(SRPart.TITLE) != null) {
			print("<div" + getFullClass("sectiontitle" + getSectionDepth(), section) + ">");
			if (index != null) {
				print(index);
				print(". ");
			}
			print(section.get(SRPart.TITLE).toString());
			print("</div>");
		}
	}

	@Override
	protected void renderSectionEnd(SRSection section) {
		print("</div>");
	}

	@Override
	public String render(SRDocument document) {
		if (style_file == null) {
			style_file = document.get(SRDocument.HTML_STYLE, "htmlstyle.css");
		}
		return super.render(document);
	}

	@Override
	protected void documentStart(String title) {
		sb.append("<html>");
		sb.append("<header>");
		sb.append("<title>");
		sb.append(title);
		sb.append("</title>");
		embeddedStyles();
		sb.append("</header>");
		sb.append("<body>");
	}

	@Override
	protected void documentEnd() {
		sb.append("</body>");
		sb.append("</html>");
	}

	private String getEscapedFormatted(SRFormatted child) {
		if (child.get(SRPart.NOESCAPE, false)) {
			return child.getContent();
		} else {
			final String escaped = escapeHTML(child.getContent());
			return escaped;
		}
	}

	private static String escapeHTML0(String s) {
		final StringBuilder out = new StringBuilder(Math.max(16, s.length()));
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
				out.append("&#");
				out.append((int) c);
				out.append(';');
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	public static String escapeHTML(String s) {
		final StringBuilder builder = new StringBuilder();
		boolean previousWasASpace = false;
		for (final char c : s.toCharArray()) {
			if (c == ' ') {
				if (previousWasASpace) {
					builder.append("&nbsp;");
					previousWasASpace = false;
					continue;
				}
				previousWasASpace = true;
			} else {
				previousWasASpace = false;
			}
			switch (c) {
			case '<':
				builder.append("&lt;");
				break;
			case '>':
				builder.append("&gt;");
				break;
			case '&':
				builder.append("&amp;");
				break;
			case '"':
				builder.append("&quot;");
				break;
			case '\n':
				builder.append("<br>");
				break;
			// We need Tab support here, because we print StackTraces as HTML
			case '\t':
				builder.append("&nbsp; &nbsp; &nbsp;");
				break;
			default:
				if (c < 128) {
					builder.append(c);
				} else {
					builder.append("&#").append((int) c).append(";");
				}
			}
		}
		return builder.toString();
	}

	private String getFullClass(String baseClass, SRPart part) {
		String userClass = null;
		if (part.get(CLASS) instanceof String) {
			userClass = part.get(CLASS).toString();
		}

		if (baseClass != null) {
			if (userClass != null) {
				return " class=\"" + baseClass + " " + userClass + "\"";
			} else {
				return " class=\"" + baseClass + "\"";
			}
		} else {
			if (userClass != null) {
				return " class=\"" + userClass + "\"";
			} else {
				return "";
			}
		}

	}

	@Override
	protected void embeddedStyles() {
		try {
			print(VerificationBasePlugin.getLibFileContents(style_file));
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void renderFoldableStart(SRFoldable child) {
		print("<details" + getFullClass("foldable", child));
		if (child.getTag() != null) {
			print(" id=\"" + child.getTag() + "\"");
		}
		print(">");
		if (child.get(SRPart.TITLE, null) != null) {
			print("<summary>" + child.get(SRPart.TITLE) + "</summary>");
		}

	}

	@Override
	protected void renderFoldableEnd(SRFoldable section) {
		print("</details>");

	}

	@Override
	protected void renderFormatted(SFExternalImage child) {
		print("<img src=\"" + child.getContent() + "\">");
	}

	@Override
	protected void renderGridStart(SRGrid grid) {
		final StringBuilder style = new StringBuilder();
		style.append("display: " + grid.get("grid-style", "grid") +"; ");
		style.append("grid-gap: 0px; ");
		style.append("grid-template-columns:");

		for (int i = 0; i < grid.getColumns(); i++) {
			//String width = grid.get("grid-column-width", "fit-content(200px)");
			String width = grid.get("grid-column-width", "auto");
			style.append(" ");
			style.append(width);
			 
		}

		style.append("; ");
		style.append(getElementStyleRaw(grid));

		print("<div style=\"" + style.toString() + "\">");
	}

	@Override
	protected void renderGridEnd(SRGrid grid) {
		print("</div>");
	}

}
