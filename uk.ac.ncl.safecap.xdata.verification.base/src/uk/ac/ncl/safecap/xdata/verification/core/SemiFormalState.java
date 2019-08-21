package uk.ac.ncl.safecap.xdata.verification.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.xmldata.IConceptMap;
import uk.ac.ncl.safecap.xmldata.IXFunction;

public class SemiFormalState {
	private static final String FOR = "[for]";

	private static final String IT_HOLDS_THAT = "[it holds that]";

	public static final List<String> KEYWORDS = Arrays.asList("for", "it holds that", "and", "or");
	public static final List<String> PROPERTIES = Arrays.asList("date", "author", "confidence", "profile", "testing");

	private final SDAContext dataContext;
	private final IConceptMap conceptmap;
	private final String original;
	private final Collection<String> functions;
	private final Collection<String> references;
	private final Collection<String> errors;
	private final Collection<String> missingData;
	private final Map<String, String> properties;

	public SemiFormalState(SDAContext dataContext, String text) {
		original = text.trim();
		this.dataContext = dataContext;
		conceptmap = dataContext.getConceptMap();
		errors = new ArrayList<>();
		references = new ArrayList<>();
		functions = new ArrayList<>();
		missingData = new ArrayList<>();
		properties = new HashMap<>();
		build(true);
	}

	private SemiFormalState(SDAContext dataContext, String text, boolean checkProtocol) {
		original = text.trim();
		this.dataContext = dataContext;
		conceptmap = dataContext.getConceptMap();
		errors = new ArrayList<>();
		references = new ArrayList<>();
		functions = new ArrayList<>();
		missingData = new ArrayList<>();
		properties = new HashMap<>();
		build(checkProtocol);
	}

	public String synthesiseFirstAttempt() {
		if (!errors.isEmpty()) {
			return "";
		}

		final int holds = original.indexOf(IT_HOLDS_THAT);
		final String predicate = original.substring(FOR.length(), holds);
		String statement = original.substring(holds + IT_HOLDS_THAT.length());
		final int semic = statement.indexOf(";");
		if (semic > 0) {
			statement = statement.substring(0, semic);
		}

		final SemiFormalState state0 = new SemiFormalState(dataContext, predicate, false);
		final SemiFormalState state1 = new SemiFormalState(dataContext, statement, false);

		final StringBuilder sb = new StringBuilder();

		sb.append(state0.original + "\n");
		for (final String f : state0.getFunctions()) {
			sb.append("\t" + f + "\n");
		}
		for (final String r : state0.getReferences()) {
			sb.append("\t" + r + ": " + referenceToFunction(r) + "\n");
		}
		sb.append("=>\n");
		sb.append(state1.original + "\n");
		for (final String f : state1.getFunctions()) {
			sb.append("\t" + f + "\n");
		}
		for (final String r : state1.getReferences()) {
			sb.append("\t" + r + ": " + referenceToFunction(r) + "\n");
		}

		return sb.toString();
	}

	public String getPredicatet() {
		if (!errors.isEmpty()) {
			return "";
		}

		final int holds = original.indexOf(IT_HOLDS_THAT);
		return original.substring(FOR.length(), holds);
	}

	public String getStatement() {
		if (!errors.isEmpty()) {
			return "";
		}

		final int holds = original.indexOf(IT_HOLDS_THAT);
		String statement = original.substring(holds + IT_HOLDS_THAT.length());
		final int semic = statement.indexOf(";");
		if (semic > 0) {
			statement = statement.substring(0, semic);
		}

		return statement;
	}

	private String referenceToFunction(String reference) {
		final String ffname = referenceToFunctionRaw(reference);
		if (ffname == null) {
			return "?";
		} else {
			return ffname;
		}
	}

	private String referenceToFunctionRaw(String reference) {
		final String canonical = conceptmap.getProvenanceConcept(reference);
		if (canonical == null) {
			return null;
		}

		for (final String f : dataContext.getFunctionIds()) {
			final IXFunction ff = dataContext.getFunction(f);
			if (ff.getCanonicalName().equals(canonical)) {
				if (!CLUtils.needsQuotes(ff.getName())) {
					return ff.getName();
				} else {
					return CLUtils.quoteString(ff.getName());
				}
			}
		}

		return null;
	}

	private void build(boolean checkProtocol) {
		if (original == null) {
			return;
		}

		if (checkProtocol) {

			if (!original.startsWith(FOR)) {
				errors.add("Does not start with [for]");
				return;
			}

			if (original.lastIndexOf(FOR) != 0) {
				errors.add("Extra [for]");
				return;
			}

			final int holds = original.indexOf(IT_HOLDS_THAT);
			if (holds == -1) {
				errors.add("Missing [it holds that]");
				return;
			}

			if (holds != original.lastIndexOf(IT_HOLDS_THAT)) {
				errors.add("Extra [it holds that]");
				return;
			}
		}

		boolean bracket_square = false;
		boolean bracket_curly = false;
		boolean quote = false;
		int semiColon = -1;

		final StringBuilder buffer = new StringBuilder();

		for (int i = 0; i < original.length(); i++) {
			final char c = original.charAt(i);
			if (!quote & !bracket_square & !bracket_curly) {
				if (c == '[') {
					bracket_square = true;
					buffer.setLength(0);
				} else if (c == '{') {
					bracket_curly = true;
					buffer.setLength(0);
				} else if (c == '"') {
					quote = true;
					buffer.setLength(0);
				} else if (c == ';') {
					if (semiColon != -1) {
						errors.add("Syntax error");
					}
					semiColon = i;
				}
			} else if (quote) {
				if (c == '"') {
					final boolean correct = dataContext.getFunction(buffer.toString()) != null;
					if (!correct) {
						errors.add("Invalid function: " + buffer.toString());
					} else {
						functions.add(buffer.toString());
					}
					quote = false;
					buffer.setLength(0);
				} else {
					buffer.append(c);
				}
			} else if (bracket_square) {
				if (c == ']') {
					final String value = buffer.toString();
					if (value.indexOf("=") > 0) {
						final String[] parts = value.split("=");
						if (semiColon == -1) {
							errors.add("No semicolon before options");
						}
						if (parts.length == 2) {
							final String prop = parts[0].trim();
							final String val = parts[1].trim();
							final boolean correct = PROPERTIES.contains(prop);
							if (correct) {
								properties.put(prop, val);
							} else {
								errors.add("Invalid property name: " + prop);
							}

							if (prop.equals("profile")) {
								final String[] vp = val.split(",");
								outer: for (final String svp : vp) {
									final String zsvp = svp.trim();
									for (int k = 0; k < zsvp.length(); k++) {
										if (!Character.isLetter(zsvp.charAt(k))) {
											errors.add("Invalid character " + zsvp.charAt(k) + " in profile name");
											break outer;
										}
									}
								}
							}

						} else {
							errors.add("Invalid property assigment: " + value);
						}
					} else {
						final boolean correct = KEYWORDS.contains(buffer.toString());
						if (!correct) {
							errors.add("Invalid keyword: " + buffer.toString());
						}
					}
					bracket_square = false;
					buffer.setLength(0);
				} else {
					buffer.append(c);
				}
			} else if (bracket_curly) {
				if (c == '}') {
					if (conceptmap != null) {
						final boolean correct = conceptmap.getConceptsAll().contains(buffer.toString());
						if (!correct) {
							errors.add("Invalid reference: " + buffer.toString());
						} else {
							if (referenceToFunctionRaw(buffer.toString()) == null) {
								missingData.add(buffer.toString());
							}
							references.add(buffer.toString());
						}
					}
					bracket_curly = false;
					buffer.setLength(0);
				} else {
					buffer.append(c);
				}
			}
		}
	}

	public String getOrginal() {
		return original;
	}

	public Collection<String> getFunctions() {
		return functions;
	}

	public Collection<String> getReferences() {
		return references;
	}

	public Collection<String> getErrors() {
		return errors;
	}

	public Collection<String> getMissingData() {
		return missingData;
	}

	public String getProperty(String property) {
		return properties.get(property);
	}
}
