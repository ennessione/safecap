package uk.ac.ncl.safecap.analytics.ctextract.parser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase.KIND;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTEToken;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTETokenList;
import uk.ac.ncl.safecap.cteparser.ErrInfo;
import uk.ac.ncl.safecap.cteparser.Parser;
import uk.ac.ncl.safecap.cteparser.TEContext;
import uk.ac.ncl.safecap.cteparser.TEMarker;
import uk.ac.ncl.safecap.cteparser.ctesym;
import uk.ac.ncl.safecap.cteparser.syntree;

public class PRBuilder {
	private static final String MAGIC = "%%%pro%%%";

	public static CTEPartBase build(String text) {
		final TEContext context = new TEContext();
		final CTEPartBase result = build(addMagic(text), context);
		if (!context.getErrors().isEmpty()) {
			System.err.println("[PR-parser] parse failed for " + text);
			for (final TEMarker err : context.getErrors()) {
				System.err.println("\t\t" + err.start + "-" + err.end + ": " + err.message);
			}
			return null;
		}

		return result;
	}

	private static String addMagic(String text) {
		return MAGIC + text;
	}

	public static CTEPartBase build(String text, TEContext context) {
		try {
			final syntree s = Parser.parse(new ByteArrayInputStream(addMagic(text).getBytes("UTF-8")), context);
			if (s != null && context.getErrors().isEmpty()) {
				final CTEPartBase r = build(s, context);
				return r;
			} else if (!context.getErrors().isEmpty()) {
				System.err.println("[PR-parser] parse failed for " + text);
				for (final TEMarker err : context.getErrors()) {
					System.err.println("\t\t" + err.start + "-" + err.end + ": " + err.message);
				}
				return null;
			}

			return null;
		} catch (final Exception e) {
			e.printStackTrace();
			context.addError(new ErrInfo(0, 0, "Exception " + e.getMessage()));
			return null;
		}
	}

	public static CTEPartBase build(syntree s, TEContext context) {
		switch (s.op()) {
		case ctesym.TOKEN: {
			final String kindText = s.value().toString();
			KIND kind;
			String token;
			if (KIND.isValidText(kindText)) {
				kind = KIND.parse(kindText);
				token = null;
			} else {
				kind = KIND.OPERATOR;
				token = kindText;
			}

			final CTEToken t = new CTEToken(token);
			t.setKind(kind);
			t.flagSet("rwe", "production");
			return t;
		}
		case ctesym.BIN: {
			final String binName = s.sibling(0).value().toString();
			final CTEToken t = new CTEToken(null);
			t.setKind(KIND.ELEMENT);
			t.setBin(binName);
			t.flagSet("rwe", "production");
			return t;
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
