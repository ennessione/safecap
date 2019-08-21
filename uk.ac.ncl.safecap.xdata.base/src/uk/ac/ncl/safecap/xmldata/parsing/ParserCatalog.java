package uk.ac.ncl.safecap.xmldata.parsing;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.xmldata.DataContext;

public class ParserCatalog {
	public static ParserCatalog INSTANCE = new ParserCatalog();

	private final List<IDataParser> parsers;

	private ParserCatalog() {
		parsers = new ArrayList<>();
		parsers.add(new Sliding());
		parsers.add(new Raw());
		parsers.add(new TwoLevel());
	}

	public List<IDataParser> getParsers() {
		return parsers;
	}

	public IDataParser getDefault() {
		return parsers.get(0);
	}

	public IDataParser getByName(String name) {
		for (final IDataParser p : parsers) {
			if (p.getName().equals(name)) {
				return p;
			}
		}

		return null;
	}

	static class TwoLevel implements IDataParser {

		@Override
		public String getName() {
			return "two level parser";
		}

		@Override
		public void process(DataContext context, String filename) {
			try {
				final TwoLevelParser parser = new TwoLevelParser(context);
				parser.parse(filename);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}

		@Override
		public String toString() {
			return getName();
		}
	}

	static class Sliding implements IDataParser {

		@Override
		public String getName() {
			return "sliding parser";
		}

		@Override
		public void process(DataContext context, String filename) throws Exception {
			try {
				final SlidingParser parser = new SlidingParser(context);
				parser.parse(filename);
			} catch (final Throwable e) {
				throw new Exception(e.getMessage());
			}
		}

		@Override
		public String toString() {
			return getName();
		}
	}

	static class Raw implements IDataParser {

		@Override
		public String getName() {
			return "raw parser";
		}

		@Override
		public void process(DataContext context, String filename) {
			try {
				final RawDataParser parser = new RawDataParser(context);
				parser.parse(filename);
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}

		@Override
		public String toString() {
			return getName();
		}

	}

}
