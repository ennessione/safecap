package uk.ac.ncl.safecap.xmldata.parsing;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.xml.stream.XMLStreamException;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.misc.core.Location;
import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataNamespace;
import uk.ac.ncl.safecap.xmldata.Pair;

public class SlidingParser extends ParserHandlerBase {
	public static final boolean STORE_LOCATION = false;

	private ParseEntity root, current;
	private Stack<String> path;
	private Set<String> functions;
	private final Stack<Location> locations;

	public SlidingParser(DataContext context) {
		super(context);
		locations = new Stack<>();
	}

	@Override
	public void parse(String file) throws IOException, XMLStreamException {
		root = new ParseEntity("root", null);
		current = root;
		path = new Stack<>();
		functions = new HashSet<>();
		super.parse(file);
		buildBlocks();
	}

	private void buildBlocks() {
		assert root != null;
		assert root.getParent() == null;
		try {
			traverse(root);
			// System.out.println("Collected functions: " + functions.size());
			dataContext.addCollectedFunctions(functions);
		} catch (final Throwable e) {
			e.printStackTrace();
		}

	}

	private void traverse(ParseEntity entity) {
		// System.out.println("Traversing " + entity);
		for (final ParseEntity pe : entity.getChildren()) {
			traverse(pe);
		}

		if (entity.isBlock()) {
			// System.out.println("Discovered block " + entity.getName() +
			// " with id " + entity.getId());
			entity.normalise(functions);
			final Block block = new Block(entity.getName());
			for (final Pair x : entity.getValues()) {
				block.enterAttribute(x.getKey());
				block.enterValue(x.getR());
			}
			getNamespace(entity.getCanonicalPrefix(), entity.getCompressedPrefix()).addBlock(block);
		}
	}

	private DataNamespace getNamespace(String canonical, String namespace) {
		DataNamespace r = dataContext.getNamespaceByCanonicalId(namespace);
		if (r == null) {
			r = new DataNamespace(canonical, namespace);
			dataContext.addNamespace(r);
		}

		return r;
	}

	@Override
	public void start(String localName, int line, int column, int offset) {
		// System.out.println("startElement " + localName + " to " +
		// current.getParent());
		path.push(localName);
		locations.push(new Location(line, column, offset - localName.length(), 0));

		// current = new ParseEntity(localName, current);
		current = getParseEntity(localName, current);
	}

	private ParseEntity getParseEntity(String localName, ParseEntity current) {
		final ParseEntity entity = getSibling(localName, current);
		if (entity != null) { // enforce sequencing
			entity.next();
			return entity;
		}

		return new ParseEntity(localName, current);
	}

	private ParseEntity getSibling(String localName, ParseEntity current) {
		for (final ParseEntity entity : current.getChildren()) {
			if (!entity.isBlock() && entity.getName().equals(localName)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void addAttribute(String name, String value) {
		current.addValue(name, value);

	}

	@Override
	public void end(String localName, int line, int column, int offset) {
		if (current.getParent() != null) {
			current.getParent().addChild(current);
			// System.out.println(current.getParent() + " add " + current);
		}

		final Location loc = locations.pop();
		if (current.getRawData() instanceof LocatedValue) {
			final LocatedValue dataloc = (LocatedValue) current.getRawData();
			dataloc.setOffset1(loc.getOffset());
			dataloc.setOffset2(offset - localName.length());
		}

		current = current.getParent();

		path.pop();
	}

	@Override
	public void character(String text, int line, int column, int offset) {

		String value;

		if (current.isRaw()) {
			value = text;
		} else {
			value = removeSpaces(text);
		}

		if (value.length() > 0) {
			if (STORE_LOCATION) {
				current.setRawData(new LocatedValue(value, line, column, offset - text.length(), text.length()));
			} else {
				current.setRawData(value);
			}
		}
	}
}
