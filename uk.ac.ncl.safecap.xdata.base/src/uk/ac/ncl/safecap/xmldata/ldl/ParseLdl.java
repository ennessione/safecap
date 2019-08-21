package uk.ac.ncl.safecap.xmldata.ldl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.lldl.ErrInfo;
import uk.ac.ncl.safecap.lldl.Parser;
import uk.ac.ncl.safecap.lldl.alphabet;
import uk.ac.ncl.safecap.lldl.syntree;
import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataNamespace;
import uk.ac.ncl.safecap.xmldata.Pair;
import uk.ac.ncl.safecap.xmldata.ValueList;

public class ParseLdl {
	public static final ParseLdl INSTANCE = new ParseLdl();

	private ParseLdl() {
	}

	public DataContext parseFile(File file) throws FileNotFoundException, LdlImportException {
		final InputStream is = new FileInputStream(file);
		final List<ErrInfo> info = new ArrayList<>(20);

		syntree result;
		try {
			result = Parser.parse(is, info);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new LdlImportException("Parsing failed : " + e.toString());
		}
		if (result == null || !info.isEmpty()) {
			for (final ErrInfo ei : info) {
				System.err.println("" + ei.line + " - " + ei.column + ": " + ei.message);
			}
			throw new LdlImportException("Parsing failed", info);
		}

		final DataContext context = new DataContext();
		context.setSourceFile(file.getAbsolutePath());

		try {
			walk(context, result);
		} catch (final Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return context;
	}

	private void expect(syntree ast, int tag) throws LdlImportException {
		if (ast.op() != tag) {
			throw new LdlImportException("Expect " + tag + ", got tag " + ast.op());
		}
	}

	private void walk(DataContext context, syntree ast) throws LdlImportException {
		expect(ast, alphabet.RECORD_LIST);
		switch (ast.op()) {
		case alphabet.RECORD_LIST:
			for (final syntree child : ast.getSiblings()) {
				buildRecord(context, child);
			}
			break;
		}
	}

	private void buildRecord(DataContext context, syntree ast) throws LdlImportException {
		expect(ast, alphabet.RECORD);

		// String level = ast.sibling(0).value().toString();
		// int version = (Integer) ast.sibling(1).value().getValue();
		final String type = ast.sibling(2).value().toString();
		String name = ast.sibling(3).value().toString();
		final syntree items = ast.sibling(4);
		expect(items, alphabet.ITEM_LIST);

		name = attemptToRepairRouteName(name, items);

		final Block block = new Block(type);
		block.getValues().add(new Pair(Block.C_ID.get(0), name));
		final DataNamespace namespace = fetchNamespace(context, type);
		namespace.addBlock(block);

		for (int i = 0; i < items.siblings(); i++) {
			// block.getValues().add(buildItem(items.sibling(i)));
			buildItem(block, items.sibling(i));
		}
	}

	private String attemptToRepairRouteName(String name, syntree items) throws LdlImportException {
		for (int i = 0; i < items.siblings(); i++) {
			final syntree ast = items.sibling(i);
			final String id = ast.sibling(0).value().toString();
			if (id.equals("ROUTE_CONFIRM_RESPONSE")) {
				return buildExpression(ast.sibling(1)).toString();
			}
		}
		return name;
	}

	private DataNamespace fetchNamespace(DataContext context, String id) {
		DataNamespace ns = context.getNamespaceById(id);
		if (ns == null) {
			ns = new DataNamespace(id, id);
			context.addNamespace(ns);
		}

		return ns;
	}

	private void buildItem(Block block, syntree ast) throws LdlImportException {
		expect(ast, alphabet.ITEM);

		final String id = ast.sibling(0).value().toString();
		final Object e = buildExpression(ast.sibling(1));
		// if (e instanceof ValueList) {
		// ValueList vl = (ValueList) e;
		// for(Object x: vl.getValues())
		// block.getValues().add(new Pair<Object>(id, x));
		// } else {
		block.getValues().add(new Pair(id, e));
		// }
	}

	private Object buildExpression(syntree ast) throws LdlImportException {
		switch (ast.op()) {
		case alphabet.ID:
			if (ast.value().getValue().toString().toLowerCase().equals(Block.C_TRUE)) {
				return Boolean.TRUE;
			} else if (ast.value().getValue().toString().toLowerCase().equals(Block.C_FALSE)) {
				return Boolean.FALSE;
			} else {
				return ast.value().getValue();
			}
		case alphabet.INTEGER:
			return ast.value().getValue();
		case alphabet.REAL: {
			final Double r = (Double) ast.value().getValue();

			// see if the number may be rounded to an integer
			if (Math.floor(r) == r) {
				return new Integer((int) (double) r);
			} else {
				return r;
			}
		}
		case alphabet.TUPLE: {
			return buildExpression(ast.sibling(0));
		}
		case alphabet.SET: {
			return buildExpression(ast.sibling(0));
		}
		case alphabet.TAGGED: {
			return buildExpression(ast.sibling(1));
		}
		case alphabet.ITEM_LIST: {
			final ValueList value = new ValueList(ast.siblings(), false);
			for (int i = 0; i < ast.siblings(); i++) {
				value.add(buildExpression(ast.sibling(i).sibling(1)));
			}
			return value;
		}
		case alphabet.EXP_LIST: {
			final ValueList value = new ValueList(ast.siblings(), false);
			for (int i = 0; i < ast.siblings(); i++) {
				value.add(buildExpression(ast.sibling(i)));
			}
			return value;
		}
		}
		throw new LdlImportException("Cannot process expression tag " + ast.op());
	}
}
