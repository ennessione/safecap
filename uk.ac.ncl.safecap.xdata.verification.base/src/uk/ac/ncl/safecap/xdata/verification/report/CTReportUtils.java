package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import uk.ac.ncl.safecap.common.report.SFBold;
import uk.ac.ncl.safecap.common.report.SFPlain;
import uk.ac.ncl.safecap.common.report.SFType;
import uk.ac.ncl.safecap.common.report.SMError;
import uk.ac.ncl.safecap.common.report.SRBlock;
import uk.ac.ncl.safecap.common.report.SRContainer;
import uk.ac.ncl.safecap.common.report.SRFormatted;
import uk.ac.ncl.safecap.common.report.SRGrid;
import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.common.report.SRSection;
import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ITablePart;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TableModel;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;

public class CTReportUtils {

	public static SRPart buildAllTables(ISDADataProvider context, TableModel table) {
		final ColumnRendererDomain cellRenderer = new ColumnRendererDomain(context);

		final Collection<Object> fullDomain = getFullDomain(context, table);

		final SRSection all = new SRSection("Control table");
		all.set(HtmlRenderer.CLASS, "section1flex");

		if (fullDomain != null && !fullDomain.isEmpty()) {
			for (final Object z : fullDomain) {
				all.add(buildTable(table, table.getName() + " - " + z.toString(), cellRenderer, z));
			}
		}

		return all;

	}

	public static SRPart buildTable(TableModel table) {
		return buildTable(table, STReportColumnContentsDefault.INSTANCE, null);
	}

	public static SRPart buildTable(TableModel table, STReportColumnContents columnCP, Object context) {
		return buildTable(table, table.getName(), columnCP, context);
	}

	public static SRPart buildTable(TableModel table, String title, STReportColumnContents columnCP, Object context) {
		final SRSection section = new SRSection(title);
		section.set(HtmlRenderer.CLASS, "section1flex");

		// add artificial line break
		section.add(new SRBlock().set(SRPart.WIDTH, "100%"));

		final List<List<ITablePart>> clusters = cluster(table);
		for (final List<ITablePart> cluster : clusters) {
			buildTableCluster(section, cluster, columnCP, context);
			section.add(new SRBlock().add(" ").set(SRPart.WIDTH, "100%").set(SRPart.HEIGHT, "20px"));
		}

		return section;
	}

	private static void buildTableCluster(SRSection section, List<ITablePart> cluster, STReportColumnContents columnCP, Object context) {

		final boolean needsWrapper = needsWrapper(cluster);
		SRContainer container = section;
		if (needsWrapper) {
			final SRGrid wrapper = new SRGrid(cluster.size());
			wrapper.set("grid-style", "inline-grid");
			wrapper.set(SRPart.BORDER, "0px 1px 0px 0px");
			container = wrapper;
			section.add(wrapper);
		}

		for (final ITablePart part : cluster) {
			container.add(buildPart(part, columnCP, context, true));
		}
	}

	private static List<List<ITablePart>> cluster(TableModel table) {
		final List<List<ITablePart>> result = new ArrayList<>();

		List<ITablePart> current = new ArrayList<>();

		int currentcolumnCount = 0;
		for (final ITablePart x : table.getParts()) {
			currentcolumnCount += columnCount(x);
			current.add(x);
			if (currentcolumnCount > 5) {
				result.add(current);
				current = new ArrayList<>();
				currentcolumnCount = 0;
			}
		}

		if (current.size() > 0) {
			result.add(current);
		}

		return result;
	}

	public static Collection<Object> getFullDomain(ISDADataProvider context, TableModel model) {
		return getFullDomain(context, getAllColumns(model));
	}

	public static Collection<Object> getFullDomain(ISDADataProvider context, List<ColumnModel> list) {
		final Collection<Object> result = new HashSet<>();
		for (final ColumnModel cm : list) {
			if (cm.getConceptPaths() != null) {
				for (final String p : cm.getConceptPaths()) {
					result.addAll(getFullDomain(context, p));
				}
			}
		}
		return result;
	}

	public static List<IXFunction> getAllConcepts(ISDADataProvider context, List<ColumnModel> list) {
		final List<IXFunction> result = new ArrayList<>();
		for (final ColumnModel cm : list) {
			if (cm.getConceptPaths() != null) {
				for (final String p : cm.getConceptPaths()) {
					result.addAll(getAllConcepts(context, p));
				}
			}
		}
		return result;
	}

	private static Collection<IXFunction> getAllConcepts(ISDADataProvider context, String path) {
		final IXFunction ff = getFunctionByPath(context, path);
		if (ff != null) {
			return Collections.singleton(ff);
		}

		return Collections.emptyList();
	}

	private static Collection<Object> getFullDomain(ISDADataProvider context, String path) {
		final IXFunction ff = getFunctionByPath(context, path);
		if (ff != null) {
			return ff.domain();
		}
		
		return Collections.emptyList();
	}

	public static List<ColumnModel> getAllColumns(TableModel model) {
		final List<ColumnModel> result = new ArrayList<>();
		getAllColumns(model, result);
		return result;
	}

	private static void getAllColumns(ITablePart x, List<ColumnModel> list) {
		if (x instanceof TableModel) {
			final TableModel tm = (TableModel) x;
			for (final ITablePart z : tm.getParts()) {
				getAllColumns(z, list);
			}
		} else if (x instanceof ColumnModel) {
			list.add((ColumnModel) x);
		}
	}

	private static int columnCount(ITablePart part) {
		if (part instanceof TableModel) {
			final TableModel tm = (TableModel) part;
			int count = 0;
			for (final ITablePart x : tm.getParts()) {
				count += columnCount(x);
			}
			return count;
		} else if (part instanceof ColumnModel) {
			return 1;
		} else {
			return 0;
		}
	}

	private static boolean needsWrapper(List<ITablePart> cluster) {
		for (final ITablePart part : cluster) {
			if (part instanceof ColumnModel) {
				return true;
			}
		}

		return false;
	}

	private static SRPart buildPart(ITablePart part, STReportColumnContents columnCP, Object context, boolean topLevel) {
		if (part instanceof TableModel) {
			return buildSubTable((TableModel) part, columnCP, context, topLevel);
		} else if (part instanceof ColumnModel) {
			return buildColumn((ColumnModel) part, columnCP, context);
		} else {
			return new SMError("Invalid part");
		}
	}

	private static SRPart buildSubTable(TableModel table, STReportColumnContents columnCP, Object context, boolean topLevel) {
		final SRGrid outer = new SRGrid(1);
		final SRGrid inner = new SRGrid(table.getParts().size());
		outer.set(SRPart.BORDER, "1px 0px 0px 0px");
		outer.set(SRPart.RAWSTYLE, "grid-template-rows: 40px auto");
		if (topLevel) {
			outer.set("grid-column-width", "auto");
			outer.set("grid-style", "inline-grid");
			outer.set(SRPart.BORDER, "1px 1px 0px 0px");
		} else {
			outer.set(SRPart.BORDER, "1px 0px 0px 0px");
		}
		SRPart title = new SRBlock().add(new SFBold(table.getName())).set(SRPart.PADDING, "10px").set(SRPart.NOWRAP, true).set(SRPart.ALIGN,
				"center");
		title.set(SRPart.BORDER, "0px 0px 0px 1px");
		outer.add(title);

		outer.add(inner);

		for (final ITablePart part : table.getParts()) {
			inner.add(buildPart(part, columnCP, context, false));
		}

		return outer;
	}

	private static SRPart buildColumn(ColumnModel column, STReportColumnContents columnCP, Object context) {
		final SRGrid grid = new SRGrid(1);
		grid.set(SRPart.BORDER, "1px 0px 0px 1px");
		grid.set(SRPart.PADDING, "0px");
		//grid.set(SRPart.RAWSTYLE, "grid-template-rows: 40px fit-content(100px) auto");

		grid.add(new SRBlock().add(new SFType(column.getName()))
				.set(SRPart.PADDING, 10)
				.set(SRPart.NOWRAP, true)
				.set(SRPart.ALIGN, "center")
				.set(SRPart.PADDING, "10px")
				.set(SRPart.BACKGROUND, "#FEFEFE")
				.set(SRPart.MINHEIGHT, "20px"));

		
		int parts = columnCP.getPartNumber(column, context);
		
		StringBuilder rowPattern = new StringBuilder();
		rowPattern.append("grid-template-rows: 40px");
		
		final SRPart columnHeader = columnCP.getColumnHeader(column, context);
		if (columnHeader != null) {
			columnHeader.set2(SRPart.BORDER, "1px 0px 0px 0px");
			grid.add(columnHeader);
			rowPattern.append(" fit-content(100px)");
		}
		
		for(int i = 0; i < parts; i++) {
			
			final SRPart partHeader = columnCP.getPartHeader(column, i, context);
			if (partHeader != null) {
				partHeader.set2(SRPart.BORDER, "1px 0px 0px 0px");
				grid.add(partHeader);
				rowPattern.append(" fit-content(100px)");
			}			
		
			final SRPart partBody = columnCP.getPartBody(column, i, context);
			partBody
				  .set2(SRPart.PADDING, "10px")
				  .set2(SRPart.MINHEIGHT, "50px");
			
			if (i == parts - 1)
				  partBody.set2(SRPart.BORDER, "0px 0px 1px 0px");
			grid.add(partBody);
			rowPattern.append(" auto");
		}
		
		if (parts == 0) {
			grid.set(SRPart.BORDER, "1px 0px 1px 1px");
		}

		
		final SRPart columnFooter = columnCP.getColumnFooter(column, context);
		if (columnFooter != null) {
			columnFooter.set2(SRPart.BORDER, "1px 0px 0px 0px");
			grid.add(columnFooter);
			rowPattern.append(" fit-content(100px)");
		}
		
		grid.set(SRPart.RAWSTYLE, rowPattern.toString());
		return grid;
	}

	public static SRFormatted formatHTMLCell(List<Object> values) {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < values.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}

			final Object o = values.get(i);
			if (o instanceof LocatedValue) {
				sb.append(o.toString().trim());
			} else if (o instanceof ValueList) {
				final ValueList vl = (ValueList) o;
				if (vl.size() == 2 && vl.get(0) instanceof Integer) {
					sb.append(vl.get(1).toString().trim());
				} else {
					sb.append(vl.get(0).toString().trim());
					sb.append("&rarr;");
					sb.append(vl.get(1).toString().trim());
				}
			} else {
				sb.append(o.toString().trim());
			}
		}

		final SRFormatted part = new SFPlain(sb.toString());

		return part;
	}

	public static IXFunction getFunctionByPath(ISDADataProvider context, String path) {
		for (final String f : context.getFunctionIds()) {
			if (context.getFunction(f).getCanonicalName().equals(path)) {
				return context.getFunction(f);
			}
		}
		return null;
	}

}

class STReportColumnContentsDefault extends STReportColumnContents {
	protected static STReportColumnContentsDefault INSTANCE = new STReportColumnContentsDefault();

	private STReportColumnContentsDefault() {
	}

	@Override
	public SRPart getColumnBody(ColumnModel column, Object context) {
		final int concepts = column.getConceptPaths().size();
		final SRBlock block = new SRBlock();
		if (concepts > 0) {
			block.add(new SFPlain("" + concepts));
		} else {
			block.add(new SFPlain("&nbsp;").set(SRPart.NOESCAPE, true));
		}

		if (concepts == 0) {
			block.set(SRPart.BACKGROUND, "#FFAAAA");
		}

		return block;
	}
}
