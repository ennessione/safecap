package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.common.report.SRBlock;
import uk.ac.ncl.safecap.common.report.SRDocument;
import uk.ac.ncl.safecap.common.report.SRGrid;
import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TableModel;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.EIResult;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.EIResult.QUERY;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;

public class CTMapReport extends BaseReport {
	private ISDADataProvider context;
	private final List<TableModel> models;
	private EIResult result;

	public CTMapReport(List<TableModel> models) {
		this.models = models;
	}

	public CTMapReport(ISDADataProvider context, List<TableModel> models) {
		this.context = context;
		this.models = models;
	}

	public CTMapReport(ISDADataProvider context, List<TableModel> models, EIResult result) {
		this.context = context;
		this.models = models;
		this.result = result;
	}

	@Override
	public void build(final IReportBuilder builder, final File file) throws Exception {
		final SRDocument document = new SRDocument("Table model");

		if (result == null) {
			buildPlainTable(document);
		} else {
			buildFITable(document);
		}

		builder.buildReport(document, file, true);
	}

	private void buildFITable(SRDocument document) {
		class ColumnRendererFI extends STReportColumnContents {

			private SRPart getInjectionInfo(Map<String, int[]> valid, Map<String, int[]> invalid, ColumnModel column,
					List<IXFunction> functions, String kind, int index) {
				int validTotal = 0;
				int invalidTotal = 0;
				for (final IXFunction f : functions) {
					if (valid.containsKey(f.getName())) {
						validTotal += valid.get(f.getName())[index];
					}
					if (invalid.containsKey(f.getName())) {
						invalidTotal += invalid.get(f.getName())[index];
					}
				}
				final SRBlock block = new SRBlock();
				colourBlock(block, kind, validTotal, invalidTotal);
				return block;
			}

			@Override
			public SRPart getColumnBody(ColumnModel column, Object user) {
				if (column.getConceptPaths().size() == 0) {
					final SRBlock block = new SRBlock();
					block.set(SRPart.BACKGROUND, "#FFAAAA");
					return block;
				}

				final List<IXFunction> functions = CTReportUtils.getAllConcepts(context, Collections.singletonList(column));

				if (functions.size() == 0) {
					final SRBlock block = new SRBlock();
					// block.set(SRPart.BACKGROUND, "#FFBBBB");
					return block;
				}

				final SRGrid grid = new SRGrid(1);
				grid.set(SRPart.PADDING, "0px");
				final Map<String, int[]> valid = result.getByFunction(QUERY.VALID);
				final Map<String, int[]> invalid = result.getByFunction(QUERY.INVALID);

				final List<String> kinds = Arrays.asList(result.getKinds());
				for (final String kind : new String[] { "M", "A", "R", "S", "X" }) {
					if (kinds.contains(kind)) {
						final SRPart block = getInjectionInfo(valid, invalid, column, functions, kind, kinds.indexOf(kind));
						grid.add(block);
					} else {
						final SRBlock block = new SRBlock();
						colourBlock(block, kind, 0, 0);
						grid.add(block);
					}
				}

				return grid;
			}

			private void colourBlock(SRBlock block, String kind, int validTotal, int invalidTotal) {
				String rating = InvestigationReport.rating(invalidTotal, validTotal, kind);
				if (validTotal + invalidTotal == 0) {
					block.add("-");
				} else if (invalidTotal * 2 > validTotal) {
					final double ratio = 2 * invalidTotal / (2 * invalidTotal + validTotal);
					final int extra = (int) ((1 - ratio) * 160.0);
					final int r = extra;
					final int g = 220;
					final int b = extra;
					final String hex = String.format("#%02x%02x%02x", r, g, b);
					//block.set(SRPart.BACKGROUND, hex);
				} else if (2 * invalidTotal < validTotal) {
					final double ratio = validTotal / (2 * invalidTotal + validTotal);
					final int extra = (int) ((1 - ratio) * 160.0);
					final int r = 220;
					final int g = extra;
					final int b = extra;
					final String hex = String.format("#%02x%02x%02x", r, g, b);
					// block.set(SRPart.BACKGROUND, hex);
				}

				block.set(SRPart.ALIGN, "center");
				block.add(rating);
			}
		}

		final ColumnRendererFI renderer = new ColumnRendererFI();

		for (final TableModel table : models) {
			document.add(CTReportUtils.buildTable(table, renderer, null));
		}
	}

	private void buildPlainTable(SRDocument document) {
		if (context == null) {
			for (final TableModel table : models) {
				document.add(CTReportUtils.buildTable(table));
			}
		} else {
			for (final TableModel table : models) {
				document.add(CTReportUtils.buildAllTables(context, table));
			}
		}
	}

}
