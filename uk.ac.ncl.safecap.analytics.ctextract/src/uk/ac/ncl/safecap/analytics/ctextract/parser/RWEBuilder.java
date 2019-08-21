package uk.ac.ncl.safecap.analytics.ctextract.parser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

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

public class RWEBuilder {
	private static final String MAGIC = "%%%rwe%%%";

	public static CTEPartBase build(String text, boolean left) {
		final TEContext context = new TEContext();
		final CTEPartBase result = build(addMagic(text), context, left);
		if (!context.getErrors().isEmpty()) {
			System.err.println("[RWR-parser] parse failed for " + text);
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

	public static CTEPartBase build(String text, TEContext context, boolean left) {
		try {
			final syntree s = Parser.parse(new ByteArrayInputStream(addMagic(text).getBytes("UTF-8")), context);
			if (s != null && context.getErrors().isEmpty()) {
				final CTEPartBase r = left ? buildLeft(s, context, new RWELeftContext()) : buildRight(s, context);
				if (left) {
					checkIndices(r, context);
				}
				return r;
			} else if (!context.getErrors().isEmpty()) {
				System.err.println("[RWR-parser] parse failed for " + text);
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

	private static void checkIndices(CTEPartBase part, TEContext context) {
		final Collection<Integer> indices = new ArrayList<>();
		checkIndices(part, context, indices);
	}

	private static void checkIndices(CTEPartBase part, TEContext context, Collection<Integer> indices) {
		if (part instanceof CTEToken) {
			final CTEToken token = (CTEToken) part;
			if (indices.contains(token.getIndex())) {
				context.addError(new ErrInfo(0, 0, "Duplicate element index " + token.getIndex()));
			} else {
				indices.add(token.getIndex());
			}
		} else if (part instanceof CTETokenList) {
			final CTETokenList list = (CTETokenList) part;
			for (final CTEPartBase p : list.getParts()) {
				checkIndices(p, context, indices);
			}
		}
	}

	public static CTEPartBase buildRight(syntree s, TEContext context) {
		switch (s.op()) {
		case ctesym.TOKEN: {
			final String ttext = s.value().toString();
			String token = "";
			final int index = 0;
			KIND kind = KIND.ELEMENT;
			if (s.siblings() == 0) {
				if (!KIND.isValidText(ttext)) {
					context.addError(new ErrInfo(s.getStart(), s.getEnd(), "Invalid token kind " + ttext));
				}
				kind = KIND.parse(ttext);
			} else if (s.siblings() == 1) {
				token = ttext;
				final String rawText = s.sibling(0).value().toString();
				if (!KIND.isValidText(rawText)) {
					context.addError(new ErrInfo(s.getStart(), s.getEnd(), "Invalid token kind " + rawText));
				}
				kind = KIND.parse(rawText);
			}
			final CTEToken t = new CTEToken(token);
			t.setKind(kind);
			t.setIndex(index);
			t.flagSet("rwe", "right");
			return t;
		}
		case ctesym.LIST: {
			final List<CTEPartBase> list = new ArrayList<>();
			for (final syntree z : s.getSiblings()) {
				final CTEPartBase p = buildRight(z, context);
				list.add(p);
			}
			return new CTETokenList(list);
		}
		}
		return null;
	}

	static class RWELeftContext {
		int currentIndex = 0;
	}

	public static CTEPartBase buildLeft(syntree s, TEContext context, RWELeftContext idxContext) {
		switch (s.op()) {
		case ctesym.TOKEN: {
			String token = s.value().toString();
			KIND kind = KIND.ELEMENT;
			if (KIND.isValidText(token) && KIND.parse(token) == KIND.NOISE && s.siblings() == 0) {
				kind = KIND.NOISE;
				token = null;
			}
			boolean hasAssignedIndex = false;
			int index = idxContext.currentIndex;
			if (s.siblings() > 0) {
				final String rawText = s.sibling(0).value().toString();
				String kindText = rawText;
				final int cpos = rawText.indexOf(':');
				if (cpos > 0) {
					kindText = rawText.substring(0, cpos);
					try {
						index = Integer.parseInt(rawText.substring(cpos + 1));
						idxContext.currentIndex = index;
						hasAssignedIndex = false;
						if (index < 0 || index > 99) {
							context.addError(new ErrInfo(s.getStart(), s.getEnd(), "Invalid token index " + rawText.substring(cpos + 1)));
						}
					} catch (final NumberFormatException e) {
						context.addError(new ErrInfo(s.getStart(), s.getEnd(), "Invalid token index " + rawText.substring(cpos + 1)));
					}
				}

				if (!KIND.isValidText(kindText)) {
					context.addError(new ErrInfo(s.getStart(), s.getEnd(), "Invalid token kind " + kindText));
				}
				kind = KIND.parse(s.sibling(0).value().toString());
			}

			if (!hasAssignedIndex) {
				idxContext.currentIndex++;
			}

			final CTEToken t = new CTEToken(token);
			if (token != null && !"??word".equals(token)) {
				try {
					Pattern.compile(token);
				} catch (final Exception e) {
					context.addError(new ErrInfo(s.getStart(), s.getEnd(), "Invalid regex syntax " + token));
				}
			}
			t.setKind(kind);
			t.setIndex(index);
			t.flagSet("rwe", "left");
			return t;
		}
		case ctesym.LIST: {
			final List<CTEPartBase> list = new ArrayList<>();
			for (final syntree z : s.getSiblings()) {
				final CTEPartBase p = buildLeft(z, context, idxContext);
				list.add(p);
			}
			return new CTETokenList(list);
		}
		}
		return null;
	}
}
