package uk.ac.ncl.safecap.analytics.ctextract.parser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTEToken;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTETokenList;
import uk.ac.ncl.safecap.cteparser.ErrInfo;
import uk.ac.ncl.safecap.cteparser.Parser;
import uk.ac.ncl.safecap.cteparser.TEContext;
import uk.ac.ncl.safecap.cteparser.TEMarker;
import uk.ac.ncl.safecap.cteparser.ctesym;
import uk.ac.ncl.safecap.cteparser.syntree;

public class CTBuilder {

	public static CTEPartBase build(String text) {
		final TEContext context = new TEContext();
		final CTEPartBase result = build(text, context);
		if (!context.getErrors().isEmpty()) {
			System.err.println("[CTE-parser] parse failed for " + text);
			for (final TEMarker err : context.getErrors()) {
				System.err.println("\t\t" + err.start + "-" + err.end + ": " + err.message);
			}
			return null;
		}

		return result;
	}

	public static CTEPartBase build(String text, TEContext context) {
		try {
			final syntree s = Parser.parse(new ByteArrayInputStream(text.getBytes("UTF-8")), context);
			if (s != null) {
				return build(s, context);
			} else {
				return null;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			context.addError(new ErrInfo(0, 0, "Exception " + e.getMessage()));
			return null;
		}
	}

	public static CTEPartBase build(syntree s, TEContext context) {
		switch (s.op()) {
		case ctesym.TOKEN: {
			final String token = s.value().toString();
			return new CTEToken(token);
		}
		case ctesym.LIST: {
			final List<CTEPartBase> list = new ArrayList<>();
			for (final syntree z : s.getSiblings()) {
				final CTEPartBase p = build(z, context);
				list.add(p);
			}
			return new CTETokenList(list);
		}
		}
		return null;
	}
}
