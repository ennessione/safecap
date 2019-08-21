package uk.ac.ncl.safecap.xdata.provers;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.common.MyReader;

public class TNameMapper implements INameMapper {

	private boolean obfuscate = true;
	private boolean prefix = true;
	private final Map<String, Identifier> map;
	private final Map<String, String> inverse;
	private static final String IDENTIFIER_PREFIX = "id_";
	private static final String BOUND_IDENTIFIER_PREFIX = "b_";
	private static final String TYPELITERAL_PREFIX = "T_";
	private static final String TYPE_PREFIX = "tp_";
	private static final int OBF_IDENTIFIER_LENGTH = 10;
	private final Random random;
	private final TNameMapper parent;
	private boolean noNewTypes = false;

	public TNameMapper(TNameMapper parent, boolean obfuscate, boolean prefix) {
		this.parent = parent;
		this.obfuscate = obfuscate;
		this.prefix = prefix;
		map = new HashMap<>();
		inverse = new HashMap<>();
		random = new Random(System.currentTimeMillis());
	}

	public boolean isNoNewTypes() {
		return noNewTypes;
	}

	public void setNoNewTypes(boolean noNewTypes) {
		this.noNewTypes = noNewTypes;
	}

	private void addToMap(String s, Identifier i) {
		if (map.containsKey(s)) {
			i.setShadowed(map.get(s));
		}
		map.put(s, i);
		inverse.put(i.getName(), s);
	}

	private void removeFromMap(String s) {
		if (map.containsKey(s)) {
			final Identifier i = map.get(s);
			inverse.remove(map.get(s).name);
			map.remove(s);
			if (i.getShadowed() != null) {
				addToMap(s, i.getShadowed());
			}
		}
	}

	private Identifier getMapping(String identifier) {
		final Identifier result = map.get(identifier);
		if (result == null && parent != null) {
			return parent.getMapping(identifier);
		} else {
			return result;
		}
	}

	public String map(String identifier) {
		final Identifier ii = getMapping(identifier);
		if (ii != null) {
			return ii.name;
		} else {
			return null;
		}
	}

	public String mapFreeIdentifier(String identifier) {
		if (isKnown(identifier)) {
			final Identifier ii = getMapping(identifier);
			if (ii.getKind() == KIND.FREE) {
				return ii.getName();
			} else {
				System.out.println("redefine free " + identifier);
			}
		}

		final String newName = generateIdentifierName(identifier);
		addToMap(identifier, new Identifier(newName, KIND.FREE));
		return newName;
	}

	public String mapBoundIdentifier(String identifier) {
		if (isKnown(identifier)) {
			final Identifier ii = getMapping(identifier);
			if (ii.getKind() == KIND.BOUND) {
				return ii.getName();
			} else {
				System.out.println("redefine bound " + identifier);
			}
		}

		final String newName = generateBoundIdentifierName(identifier);
		addToMap(identifier, new Identifier(newName, KIND.BOUND));
		return newName;
	}

	@Override
	public String mapType(String identifier) throws TranslationException {
		if (isKnown(identifier)) {
			final Identifier ii = getMapping(identifier);
			assert ii.getKind() == KIND.TYPE;
			return ii.getName();
		} else {
			if (isNoNewTypes()) {
				throw new TranslationException("Unknown type " + identifier);
			}
			final String newName = generateType(identifier);
			addToMap(identifier, new Identifier(newName, KIND.TYPE));
			return newName;
		}
	}

	@Override
	public String mapTypeLiteral(String identifier) {
		if (isKnown(identifier)) {
			final Identifier ii = getMapping(identifier);
			assert ii.getKind() == KIND.TYPE_LITERAL;
			return ii.getName();
		} else {
			final String newName = generateTypeLiteral(identifier);
			addToMap(identifier, new Identifier(newName, KIND.TYPE_LITERAL));
			return newName;
		}
	}

	public boolean isKnown(String name) {
		return map.containsKey(name) || parent != null && parent.isKnown(name);
	}

	@Override
	public KIND getKind(String name) {
		if (map.containsKey(name)) {
			return map.get(name).kind;
		} else if (parent != null) {
			return parent.getKind(name);
		} else {
			return KIND.UNKNOWN;
		}
	}

	private String _generate(String base) {
		String candidate = base;
		final int c = 1;
		while (map(candidate) != null) {
			candidate = base + "__" + c;
		}

		return candidate;
	}

	private String generateTypeLiteral(String identifier) {
		return _generate(_generateTypeLiteral(identifier));
	}

	private String _generateTypeLiteral(String identifier) {
		if (obfuscate) {
			final StringBuffer sb = new StringBuffer();
			sb.append(Character.toChars(random.nextInt(90 - 65) + 65)[0]);
			fill(sb, OBF_IDENTIFIER_LENGTH - 1);
			return sb.toString();
		} else {
			final String candidate = prefix ? TYPELITERAL_PREFIX + sanitize(identifier) : sanitize(identifier);
			return candidate;
		}
	}

	private String generateType(String identifier) {
		return _generate(_generateType(identifier));
	}

	private String _generateType(String identifier) {
		if (obfuscate) {
			final StringBuffer sb = new StringBuffer();
			sb.append(Character.toChars(random.nextInt(122 - 97) + 97)[0]);
			fill(sb, OBF_IDENTIFIER_LENGTH - 1);
			return sb.toString();
		} else {
			final String candidate = prefix ? TYPE_PREFIX + sanitize(identifier) : sanitize(identifier);
			return candidate;
		}
	}

	private String generateIdentifierName(String identifier) {
		return _generate(_generateIdentifierName(identifier));
	}

	private String _generateIdentifierName(String identifier) {
		if (obfuscate) {
			final StringBuffer sb = new StringBuffer();
			sb.append(Character.toChars(random.nextInt(122 - 97) + 97)[0]);
			fill(sb, OBF_IDENTIFIER_LENGTH - 1);
			return sb.toString();
		} else {
			final String candidate = prefix ? IDENTIFIER_PREFIX + sanitize(identifier) : sanitize(identifier);
			return candidate;
		}
	}

	private String generateBoundIdentifierName(String identifier) {
		return _generate(_generateBoundIdentifierName(identifier));
	}

	private String _generateBoundIdentifierName(String identifier) {
		if (obfuscate) {
			final StringBuffer sb = new StringBuffer();
			sb.append(Character.toChars(random.nextInt(122 - 97) + 97)[0]);
			fill(sb, OBF_IDENTIFIER_LENGTH - 1);
			return sb.toString();
		} else {
			final String candidate = prefix ? BOUND_IDENTIFIER_PREFIX + sanitize(identifier) : sanitize(identifier);
			return candidate;
		}
	}

	private String sanitize(String text) {
		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {
			final char c = text.charAt(i);
			if (i == 0 && Character.isDigit(c)) {
				sb.append("N");
				sb.append(c);
			} else if (!Character.isLetterOrDigit(c) && c != '_') {
				sb.append('_');
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	private void fill(StringBuffer sb, int length) {
		for (int i = 0; i < length; i++) {
			switch (random.nextInt(3)) {
			case 0:
				sb.append(Character.toChars(random.nextInt(57 - 48) + 48)[0]);
				break;
			case 1:
				sb.append(Character.toChars(random.nextInt(90 - 65) + 65)[0]);
				break;
			case 2:
				sb.append(Character.toChars(random.nextInt(57 - 48) + 48)[0]);
				break;
			}
		}
	}

	class Identifier {
		private final String name;
		private final KIND kind;
		private Identifier shadowed;

		public Identifier(String name, KIND kind) {
			super();
			this.name = name;
			this.kind = kind;
		}

		public String getName() {
			return name;
		}

		public KIND getKind() {
			return kind;
		}

		public Identifier getShadowed() {
			return shadowed;
		}

		public void setShadowed(Identifier shadowed) {
			this.shadowed = shadowed;
		}
	}

	@Override
	public String mapName(String name) {
		final String result = map(name);
		assert result != null;
		return result;
	}

	@Override
	public String mapLocallyBoundName(String name) {
		return mapBoundIdentifier(name);
	}

	@Override
	public String insertLocallyBoundName(String name) {
		final String newName = generateBoundIdentifierName(name);
		addToMap(name, new Identifier(newName, KIND.BOUND));
		return newName;
	}

	@Override
	public void removeLocallyBoundName(String name) {
		removeFromMap(name);
	}

	@Override
	public String reverseName(String name) {
		final String r = inverse.get(name);
		if (r != null && CLUtils.needsQuotes(r)) {
			return "\"" + r + "\"";
		} else {
			return r;
		}
	}

	/**
	 * Replaces every occurrence of a translated identifier with its originals; uses
	 * naive token scanner
	 * 
	 * @param string
	 */
	public String translateStringNaive(String string) {
		final MyReader reader = new MyReader(string);
		final StreamTokenizer tokenizer = new StreamTokenizer(reader);
		tokenizer.eolIsSignificant(false);
		tokenizer.slashStarComments(false);
		tokenizer.ordinaryChars('/', '/');
		tokenizer.ordinaryChars('\\', '\\');
		tokenizer.ordinaryChars(' ', ' ');
		tokenizer.ordinaryChars('\r', '\r');
		tokenizer.ordinaryChars('\n', '\n');
		tokenizer.ordinaryChars('\t', '\t');
		tokenizer.wordChars('0', '9');
		tokenizer.ordinaryChars('-', '-');
		tokenizer.ordinaryChars('+', '+');
		tokenizer.ordinaryChars('.', '.');
		tokenizer.wordChars('_', '_');
		tokenizer.wordChars('$', '$');

		try {
			final StringBuffer sb = new StringBuffer();
			while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
				switch (tokenizer.ttype) {
				case StreamTokenizer.TT_WORD:
					final String rm = reverseName(tokenizer.sval);
					if (rm != null) {
						if (CLUtils.needsQuotes(rm)) {
							sb.append('"');
							sb.append(rm);
							sb.append('"');
						} else {
							sb.append(rm);
						}
					} else {
						sb.append(tokenizer.sval);
					}
					break;
				case StreamTokenizer.TT_NUMBER:
					sb.append(tokenizer.nval);
					break;
				case ' ':
				case '\r':
				case '\n':
				case '\t':
					sb.append(' ');
					break;
				default:
					sb.append((char) tokenizer.ttype);
					break;
				}
			}

			return sb.toString();
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}