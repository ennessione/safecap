package uk.ac.ncl.safecap.textentry.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.safecap.textentry.parser.ErrInfo;
import uk.ac.ncl.safecap.textentry.parser.Parser;
import uk.ac.ncl.safecap.textentry.parser.TEContext;
import uk.ac.ncl.safecap.textentry.parser.syntree;
import uk.ac.ncl.safecap.textentry.parser.tesym;
import uk.ac.ncl.safecap.textentry.types.ITEType;
import uk.ac.ncl.safecap.textentry.types.TypeType;

public class TEBuilder {

	public static TEPart build(InputStream stream) {
		final TEContext context = new TEContext();
		final TEPart part = build(stream, context);
		if (!context.getErrors().isEmpty()) {
			System.err.println("[TE build failed] " + context.getErrors().toString());
			return null;
		}

		return part;
	}

	public static TEPart build(String text) {
		final TEContext context = new TEContext();
		final TEPart part = build(text, context);
		if (context.getErrors().isEmpty()) {
			return part;
		}

		return null;
	}

	public static TEPart build(String text, TEContext context) {
		try {
			final syntree s = Parser.parse(new ByteArrayInputStream(text.getBytes("UTF-8")), context);
			if (s != null) {
				return build(s, null, true, context);
			} else {
				return null;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			context.addError(new ErrInfo(0, 0, "Exception " + e.getMessage()));
			return null;
		}
	}

	public static TEPart build(InputStream stream, TEContext context) {
		try {
			final syntree s = Parser.parse(stream, context);
			if (s != null) {
				return build(s, null, true, context);
			} else {
				return null;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			context.addError(new ErrInfo(0, 0, "Exception " + e.getMessage()));
			return null;
		}
	}

	private static TEPart build(syntree s, TEPart parentSchema, boolean topLevel, TEContext context) {
		if (s.op() != tesym.RECORD) {
			return null;
		}

		final String type = s.sibling(0).value().toString();
		final String name = s.sibling(1).value().toString();
		final syntree body = s.sibling(2);
		final TEPart part = new TEPart(type, name);
		TEPart schema = null;
		if (topLevel) {
			schema = TEPlugin.getTERegistry().getSchemaFor(type);
			if (schema == null) {
				context.addWarning(s.sibling(0), "No schema defined for type " + type);
			}
		} else if (parentSchema != null) {
			schema = parentSchema.getChildOfTypeAndName("schema", type);
			if (schema == null && parentSchema.getParent() != null) {
				schema = parentSchema.getParent().getChildOfTypeAndName("schema", type);
			}
			if (schema == null) {
				schema = parentSchema.getRoot().getChildOfTypeAndName("schema", type);
			}
			if (schema == null) {
				context.addWarning(s.sibling(1), "Element " + type + "::" + name + " is not expected");
			}
		}

		assert body.op() == tesym.ENTRIES;
		for (final syntree x : body.getSiblings()) {
			buildBody(part, x, schema, context);
		}

		validatePart(part, s, schema, context);

		return part;
	}

	@SuppressWarnings("unchecked")
	private static void buildBody(TEPart part, syntree s, TEPart schema, TEContext context) {
		switch (s.op()) {
		case tesym.ENTRY: {
			final String name = s.sibling(0).value().toString();
			final Object body = buildValue(s.sibling(1));
			validateEntry(part, s, name, body, schema, context);
			part.addEntry(name, body);
			if (s.siblings() > 2 && s.sibling(2).op() == tesym.META && s.sibling(2).siblings() > 0) {
				final Map<String, Object> map = (Map<String, Object>) buildValue(s.sibling(2).sibling(0));
				part.setFlagRaw(name, map);
			}

			break;
		}
		case tesym.RECORD: {
			final TEPart other = build(s, schema, false, context);
			part.addChild(other);
			validatePart(other, s, schema, context);
			break;
		}
		case tesym.META: {
			if (s.siblings() > 0) {
				final Map<String, Object> map = (Map<String, Object>) buildValue(s.sibling(0));
				part.setProperty(map);
			}
			break;
		}
		case tesym.ARRAY: {
			for (final syntree x : s.getSiblings()) {
				final Object body = buildValue(x);
				part.addEntry(body);
			}
			break;
		}
		}
	}

	private static void validateEntry(TEPart part, syntree s, String name, Object value, TEPart schema, TEContext context) {
		if (schema != null) {
			final String targetType = schema.getString(name);
			final String targetType2 = schema.getString("?" + name);
			if (targetType == null && targetType2 == null) {
				context.addWarning(s, "Entry " + name + " is not expected");
				return;
			}

			final String key = targetType2 == null ? name : "?" + name;

			final String schemaType = schema.getString(key.toString());
			if (schemaType != null && !isOptional(schemaType)) {
				checkValueType(s, context, part, name, value, schemaType);
			}

		}
	}

	private static void validatePart(TEPart part, syntree s, TEPart schema, TEContext context) {
		if (schema != null) {
			for (final Object key : schema.getRecordKeys()) {
				final String keyid = normaliseRecordKey(key.toString());
				if (!isOptional(key.toString()) && !part.getRecordKeys().contains(keyid)) {
					context.addWarning(s, "Entry " + keyid + " is not defined");
					return;
				}

				final String schemaType = schema.getString(key.toString());
				if (schemaType != null && part.getRecordKeys().contains(keyid)) {
					final List<Object> values = part.getValues(keyid);
					final Object cardValue = schema.getFlag(key, "card");
					checkCardinality(s, context, keyid, values, cardValue);
				}

			}

		}
	}

	private static void checkValueType(syntree s, TEContext context, TEPart part, String keyid, Object value, String schemaType) {
		if (value instanceof String) {
			if (!TypeType.TYPE_STRING.equals(schemaType)) {
				if (TypeType.isBuiltIn(schemaType)) {
					context.addWarning(s, "Invalid value type string for entry " + keyid + ", expect type " + schemaType);
					return;
				}

				final ITEType typeInfo = TEPlugin.getTERegistry().getTypeInfo(schemaType);
				if (typeInfo != null) {
					final String error = typeInfo.checkMember(part, value);
					if (error != null) {
						context.addWarning(s, "Invalid type for entry " + keyid + ": " + error);
						return;
					}
				}
			}
		} else if (value instanceof Boolean) {
			if (!TypeType.TYPE_BOOL.equals(schemaType)) {
				context.addWarning(s, "Invalid value type bool for entry " + keyid + ", expect type " + schemaType);
				return;
			}
		} else if (value instanceof Integer) {
			if (!TypeType.TYPE_INT.equals(schemaType)) {
				context.addWarning(s, "Invalid value type int for entry " + keyid + ", expect type " + schemaType);
				return;
			}
		} else if (value instanceof Double) {
			if (!TypeType.TYPE_FLOAT.equals(schemaType)) {
				context.addWarning(s, "Invalid value type float for entry " + keyid + ", expect type " + schemaType);
				return;
			}
		}

	}

	@SuppressWarnings("rawtypes")
	private static void checkCardinality(syntree s, TEContext context, String keyid, List<Object> values, Object cardValue) {
		try {
			if (cardValue instanceof Integer) {
				final int card = (Integer) cardValue;
				if (card > 0) {
					if (card != values.size()) {
						if (card == 1) {
							context.addWarning(s, "Only one instance of " + keyid + " is expected");
						} else {
							context.addWarning(s, "Expect " + card + " instances of " + keyid);
						}
						return;
					}
				} else {
					context.addNote(s, "Error in schema: card connot be zero or negative");
					return;
				}
			} else if (cardValue instanceof BSeq) {
				final BSeq seq = (BSeq) cardValue;
				if (seq.card() != 2 || !(seq.getSeqElement(0) instanceof Integer && seq.getSeqElement(1) instanceof Integer)) {
					context.addNote(s, "Error in schema: card must be an array of two values");
					return;
				}

				final int lower = (Integer) seq.getSeqElement(0);
				final int upper = (Integer) seq.getSeqElement(1);

				if (lower < 0) {
					context.addNote(s, "Error in schema: card lower bound must be positive");
					return;
				}

				if (values.size() < lower) {
					context.addWarning(s, "Expect at least " + lower + " instances of " + keyid);
					return;
				}

				if (upper != -1) {

					if (upper < lower) {
						context.addNote(s, "Error in schema: card upper bound must be above the lower bound");
						return;
					}

					if (values.size() > upper) {
						context.addWarning(s, "Expect at most " + upper + " instances of " + keyid);
						return;
					}
				}

			}
		} catch (final Exception e) {
			context.addNote(s, "Validation error: " + e.getMessage());
		}
	}

	private static String normaliseRecordKey(String string) {
		if (string.startsWith("?")) {
			return string.substring(1);
		}
		return string;
	}

	private static boolean isOptional(String key) {
		return key.startsWith("?");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object buildValue(syntree s) {
		switch (s.op()) {
		case tesym.INTEGER:
			return s.value().getValue();
		case tesym.REAL:
			return s.value().getValue();
		case tesym.TRUE:
			return Boolean.TRUE;
		case tesym.FALSE:
			return Boolean.FALSE;
		case tesym.NAME: {
			return s.value().getValue();
		}
		case tesym.ARRAY: {
			final List<Object> l = new ArrayList<>(s.siblings());
			for (final syntree x : s.getSiblings()) {
				final Object body = buildValue(x);
				l.add(body);
			}
			return BSeq.make(l);
		}
		case tesym.ENTRY: {
			final String name = s.sibling(0).value().toString();
			final Object body = buildValue(s.sibling(1));
			return new BMap(name, body);
		}
		case tesym.ENTRIES: {
			final Map<String, Object> result = new HashMap<>();
			for (final syntree x : s.getSiblings()) {
				final BMap map = (BMap) buildValue(x);
				result.put(map.prj1().toString(), map.prj2());
			}
			return result;
		}
		}
		return null;
	}
}
