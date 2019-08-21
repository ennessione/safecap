package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class Html2Text extends HTMLEditorKit.ParserCallback {
	StringBuffer s;

	public Html2Text() {
	}

	public String convert(String html) {
		try {
			if (html != null) {
				final Reader reader = new StringReader(html);
				parse(reader);
				return s.toString();
			} else {
				return "";
			}
		} catch (final IOException e) {
			return "";
		}
	}

	public void parse(Reader in) throws IOException {
		s = new StringBuffer();
		final ParserDelegator delegator = new ParserDelegator();
		delegator.parse(in, this, Boolean.TRUE);
	}

	@Override
	public void handleText(char[] text, int pos) {
		s.append(text);
	}

	public String getText() {
		return s.toString();
	}
}