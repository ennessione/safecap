package uk.ac.ncl.safecap.xdata.base.tablesmodel;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import uk.ac.ncl.safecap.textentry.core.TEPart;
import uk.ac.ncl.safecap.xdata.base.BaseDataPlugin;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel.ERROR_KIND;
import uk.ac.ncl.safecap.xmldata.DataUtils;

public class TablesModel {
	private String name;
	private List<TableModel> tables;
	private Map<String, Object> properties;

	public TablesModel() {
		tables = new ArrayList<>();
	}

	public TablesModel copy() {
		final TablesModel c = new TablesModel();
		c.name = name;
		c.tables = new ArrayList<>();
		for (final TableModel tm : tables) {
			c.addTable(tm.copy(null));
		}
		return c;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProperty(String name, Object value) {
		if (properties == null) {
			properties = new HashMap<>();
		}
		properties.put(name, value);
	}

	public Object getProperty(String name) {
		if (properties == null) {
			return null;
		}
		return properties.get(name);
	}

	public Object getProperty(String name, Object def) {
		if (properties == null) {
			return def;
		}
		if (properties.containsKey(name)) {
			return properties.get(name);
		} else {
			return def;
		}
	}

	public void addTable(TableModel column) {
		tables.add(column);
	}

	public List<TableModel> getTables() {
		return tables;
	}

	public Collection<ColumnModelInfo> getAllTests() {
		final List<ColumnModelInfo> result = new ArrayList<>();

		for (final TableModel tm : tables) {
			collectTests(tm, result);
		}

		return result;
	}

	private void collectTests(TableModel tm, List<ColumnModelInfo> result) {
		for (final ITablePart stm : tm.getParts()) {
			if (stm instanceof TableModel) {
				collectTests((TableModel) stm, result);
			} else if (stm instanceof ColumnModel) {
				collectTests((ColumnModel) stm, result);
			}
		}
	}

	private void collectTests(ColumnModel cm, List<ColumnModelInfo> result) {
		for (final ColumnModelInfo t : cm.getInfo()) {
			if (t.isEnabled()) {
				result.add(t);
			}
		}

	}

	public static TablesModel buildTablesModel(String conf) {
		try {
			final StringReader reader = new StringReader(BaseDataPlugin.getLibFileContents(conf));
			return readInjectionTables(reader);
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public TEPart toTEPart() {
		final TEPart part = new TEPart("conceptmap", name == null ? "name" : name);

		for (final TableModel tm : tables) {
			toTEPart(part, tm);
		}

		return part;
	}

	private void toTEPart(TEPart parent, TableModel tm) {
		final TEPart part = new TEPart("table", tm.getName());
		parent.addChild(part);
		for (final ITablePart tp : tm.getParts()) {
			if (tp instanceof TableModel) {
				final TableModel m = (TableModel) tp;
				toTEPart(part, m);
			} else if (tp instanceof ColumnModel) {
				final ColumnModel c = (ColumnModel) tp;
				final TEPart column = new TEPart("column", c.getName());
				part.addChild(column);
				if (c.isPseudo()) {
					column.addEntry("pseudo", true);
				}
				if (c.getReason() != null) {
					column.addEntry("reason", c.getReason());
				}
				if (c.getSemantics() != null) {
					column.addEntry("semantics", c.getSemantics());
				}
				for (final String pp : c.getConceptPaths()) {
					column.addEntry("path", pp);
				}
			}
		}
	}

	public static TablesModel build(TEPart source) {
		final TablesModel model = new TablesModel();

		if ("conceptmap".equals(source.getType())) {
			model.name = source.getName();
			for (final TEPart t : source.getChildrenOfType("table")) {
				final TableModel tm = buildTable(null, t);
				model.addTable(tm);
			}
		}

		return model;
	}

	private static TableModel buildTable(TableModel parent, TEPart source) {
		final TableModel tm = new TableModel(parent, source.getName());
		if (parent != null) {
			parent.add(tm);
		}
		for (final TEPart p : source.getChildren()) {
			if ("table".equals(p.getType())) {
				buildTable(tm, p);
			} else if ("column".equals(p.getType())) {
				final ColumnModel column = new ColumnModel(tm, p.getName());
				tm.add(column);
				column.setPseudo(p.getBoolean("pseudo"));
				column.setReason(p.getString("reason"));
				column.setSemantics(p.getString("semantics"));
				for (final Object x : p.getValues("path")) {
					column.addConceptPath(TEPart.unquote(x.toString()));
				}
			}
		}
		return tm;
	}

	public static TablesModel readInjectionTables(Reader in) throws InjectorException, IOException {
		final TablesModel model = new TablesModel();
		int line = 1;
		String[] parts;
		final CSVParser parser = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0',
				CSVParser.DEFAULT_STRICT_QUOTES);
		final CSVReader reader = new CSVReader(in, 0, parser);
		final Stack<TableModel> tables = new Stack<>();
		ColumnModel column = null;
		try {
			while ((parts = reader.readNext()) != null) {
				if (parts.length > 0) {
					final String kind = DataUtils.unquote(parts[0]).trim();
					if ("table".equals(kind) && parts.length >= 2) {
						final TableModel table = new TableModel(null, DataUtils.unquote(parts[1]));
						model.addTable(table);
						tables.push(table);
						column = null;
					} else if ("subtable".equals(kind) && parts.length >= 2) {
						final TableModel table = new TableModel(tables.peek(), DataUtils.unquote(parts[1]));
						tables.peek().add(table);
						tables.push(table);
						column = null;
					} else if ("pop".equals(kind)) {
						tables.pop();
					} else if ("column".equals(kind) && parts.length >= 3 && tables.peek() != null) {
						column = new ColumnModel(tables.peek(), DataUtils.unquote(parts[1]));
						for (final char c : DataUtils.unquote(parts[2]).toCharArray()) {
							if (c == 'M') {
								column.addInfo(ERROR_KIND.MUTATE);
							} else if (c == 'A') {
								column.addInfo(ERROR_KIND.ADD);
							} else if (c == 'R') {
								column.addInfo(ERROR_KIND.REMOVE);
							} else if (c == 'S') {
								column.addInfo(ERROR_KIND.SWAP);
							} else if (c == 'X') {
								column.addInfo(ERROR_KIND.EXCLUDE);
							}
						}
						tables.peek().add(column);
					} else if ("path".equals(kind) && parts.length >= 2 && column != null) {
						column.addConceptPath(DataUtils.unquote(parts[1]));
					} else if ("mutate".equals(kind) && parts.length >= 2 && column != null) {
						column.getInfoFor(ERROR_KIND.MUTATE).setPropertyName(DataUtils.unquote(parts[1]));
					} else if ("add".equals(kind) && parts.length >= 2 && column != null) {
						column.getInfoFor(ERROR_KIND.ADD).setPropertyName(DataUtils.unquote(parts[1]));
					} else if ("remove".equals(kind) && parts.length >= 2 && column != null) {
						column.getInfoFor(ERROR_KIND.REMOVE).setPropertyName(DataUtils.unquote(parts[1]));
					} else if ("swap".equals(kind) && parts.length >= 2 && column != null) {
						column.getInfoFor(ERROR_KIND.SWAP).setPropertyName(DataUtils.unquote(parts[1]));
					} else if ("exclude".equals(kind) && parts.length >= 2 && column != null) {
						column.getInfoFor(ERROR_KIND.EXCLUDE).setPropertyName(DataUtils.unquote(parts[1]));
					} else if ("reason".equals(kind) && parts.length >= 2) {
						column.setReason(DataUtils.unquote(parts[1]));
					} else if ("semantics".equals(kind) && parts.length >= 2) {
						column.setSemantics(DataUtils.unquote(parts[1]));
					} else if ("pseudo".equals(kind)) {
						column.setPseudo(true);
					} else if ("comment".equals(kind)) {
						// ignore
					} else if (kind.length() == 0) {
						// ignore
					} else {
						reader.close();
						throw new InjectorException("Syntax error in line " + line + " - " + parts);
					}
				}
				line++;
			}
		} catch (final Throwable e) {
			if (e instanceof InjectorException) {
				throw (InjectorException) e;
			}
			throw new InjectorException("Syntax error in line " + line + ": " + e.getMessage());
		} finally {
			reader.close();
		}

		return model;
	}

	public ColumnModel findColumnByPath(String path) {
		for (final TableModel model : tables) {
			final ColumnModel cm = model.findColumnByPath(path);
			if (cm != null) {
				return cm;
			}
		}

		return null;
	}

	public ColumnModel findColumnByHash(TableModel table, String name) {
		name = name.substring(3).trim();
		int dash = name.lastIndexOf('_');
		if (dash != -1)
			name = name.substring(0, dash);
		int code = Integer.parseInt(name);

		return findColumnByHash(table, code);
	}	
	
	private ColumnModel findColumnByHash(TableModel table, int code) {
		for(ITablePart part: table.getParts()) {
			if (part instanceof ColumnModel) {
				ColumnModel cm = (ColumnModel) part;
				if (cm.hashCode() == code)
					return cm;
			} else if (part instanceof TableModel) {
				TableModel tm = (TableModel) part;
				ColumnModel cm = findColumnByHash(tm, code);
				if (cm != null)
					return cm;
			}
		}

		return null;
	}	
	
	public ColumnModel findColumn(String name) {
		name = name.trim();
		if (name == null || name.length() < 2) {
			return null;
		}
		final String[] parts = name.split(">");
		final ITablePart r = findPart(tables, parts, 0);
		if (r instanceof ColumnModel) {
			return (ColumnModel) r;
		}

		return null;
	}

	public TableModel findTopLevelTable(String name) {
		name = name.trim();
		if (name == null || name.length() < 2) {
			return null;
		}
		final String[] parts = name.split(">");
		final ITablePart r = findPart(tables, new String[] { parts[0] }, 0);
		if (r instanceof TableModel) {
			return (TableModel) r;
		}

		return null;
	}
	
	public ITablePart findPart(String[] parts) {
		return findPart(tables, parts, 0);
	}

	private ITablePart findPart(List<?> elements, String[] parts, int i) {
		if (i > parts.length - 1) {
			return null;
		}
		final String name = parts[i].trim();
		for (final Object z : elements) {
			if (z instanceof TableModel) {
				final TableModel tm = (TableModel) z;
				if (tm.getName().equals(name)) {
					if (i < parts.length - 1) {
						return findPart(tm.getParts(), parts, i + 1);
					} else {
						return tm;
					}
				}
			} else if (z instanceof ColumnModel && i == parts.length - 1) {
				final ColumnModel cm = (ColumnModel) z;
				if (cm.getName().equals(name)) {
					return cm;
				}
			}
		}
		return null;
	}

}
