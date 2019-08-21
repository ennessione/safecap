package uk.ac.ncl.safecap.analytics.ctextract.ui;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase.KIND;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTEToken;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTETokenList;
import uk.ac.ncl.safecap.analytics.ctextract.core.IVisitor;
import uk.ac.ncl.safecap.analytics.ctextract.core.PRRule;
import uk.ac.ncl.safecap.analytics.ctextract.core.RWERule;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTBin;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProductionRule;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProjectPart;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTRewriteRule;
import uk.ac.ncl.safecap.analytics.ctextract.main.PatternInfo;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.Bins;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.ContextErrors;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.ContextWarnings;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.DataSource;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.PatternSamplePart;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.PatternSampleText;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.ProductionResult;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.ProductionRules;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RewriteRules;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RulePattern;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RuleSamplesEntry;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RuleSamplesSubEntry;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.RuleTemplate;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.Trace;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.TraceCell;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.TraceParsedCell;
import uk.ac.ncl.safecap.analytics.ctextract.ui.ExtractionContentProvider.TraceRule;
import uk.ac.ncl.safecap.common.resources.ImageRegister;
import uk.ac.ncl.safecap.cteparser.TEMarker;
import uk.ac.ncl.safecap.misc.StringUtil;
import uk.ac.ncl.safecap.misc.StyleColours;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TableModel;

public class ExtractionLabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
		final Object element = cell.getElement();
		final StyledString text = new StyledString();

		if (element instanceof TableModel) {
			final TableModel tm = (TableModel) element;
			text.append(tm.getName());
			cell.setImage(ImageRegister.getImage(ImageRegister.ICON_TABLE));
		} else if (element instanceof DataSource) {
			final DataSource cm = (DataSource) element;
			text.append("Current data set: ");
			text.append(cm.name);
		} else if (element instanceof ColumnModel) {
			final ColumnModel cm = (ColumnModel) element;
			text.append(cm.getName());
			cell.setImage(ImageRegister.getImage(ImageRegister.ICON_COLUMN));
		} else if (element instanceof CTProjectPart) {
			final CTProjectPart pp = (CTProjectPart) element;
			text.append(pp.getPath());
			cell.setImage(ImageRegister.getImage(ImageRegister.ICON_DB_BLACK));
		} else if (element instanceof PatternInfo) {
			final PatternInfo cm = (PatternInfo) element;
			ppExpression(text, cm.pattern);
			text.append(" [" + cm.samples.size() + "]", StyleColours.dateStyler);
		} else if (element instanceof RewriteRules) {
			text.append("Rewrite rules");
			cell.setImage(ImageRegister.getImage(ImageRegister.ICON_RIGHT_ARROW));
		} else if (element instanceof ProductionRules) {
			text.append("Production rules");
			cell.setImage(ImageRegister.getImage(ImageRegister.ICON_DOUBLE_RIGHT_ARROW));
		} else if (element instanceof ProductionResult) {
			text.append("Binning");
		} else if (element instanceof CTRewriteRule) {

			final CTRewriteRule rule = (CTRewriteRule) element;
			final RWERule rwerule = rule.computeRWERule();

			if (rwerule == null) {
				cell.setImage(ImageRegister.getImage(ImageRegister.ICON_VERIFICATION_FAILED));
				text.append("?");
			} else {
				cell.setImage(null);
				if (rule.isEnabled()) {
					text.append("\u2611");
				} else {
					text.append("\u2610");
				}
				ppExpression(text, rwerule.getLeft());
				text.append("-> ", StyleColours.dateStyler);
				ppExpression(text, rwerule.getRight());
			}
		} else if (element instanceof CTProductionRule) {

			final CTProductionRule rule = (CTProductionRule) element;
			final PRRule prrule = rule.computePRRule();

			if (prrule == null) {
				cell.setImage(ImageRegister.getImage(ImageRegister.ICON_VERIFICATION_FAILED));
				text.append("?");
			} else {
				cell.setImage(null);
				if (rule.isEnabled()) {
					text.append("\u2611");
				} else {
					text.append("\u2610");
				}
				ppExpression(text, prrule.getLeft());
			}
		} else if (element instanceof RulePattern) {
			final RulePattern rule = (RulePattern) element;
			text.append("Pattern: ", StyleColours.dateStyler);
			text.append(rule.rule.getPattern());
		} else if (element instanceof RuleTemplate) {
			final RuleTemplate rule = (RuleTemplate) element;
			text.append("Target: ", StyleColours.dateStyler);
			text.append(rule.rule.getTemplate());
		} else if (element instanceof RuleSamplesEntry) {
			final RuleSamplesEntry entry = (RuleSamplesEntry) element;
			text.append("Element " + entry.key);
		} else if (element instanceof RuleSamplesSubEntry) {
			final RuleSamplesSubEntry entry = (RuleSamplesSubEntry) element;
			text.append(entry.domain);
			text.append(" -> ", StyleColours.dateStyler);
			text.append("{");
			text.append(StringUtil.ppList(entry.map.get(entry.domain), ", "));
			text.append("}");
		} else if (element instanceof Bins) {
			cell.setImage(ImageRegister.getImage(ImageRegister.ICON_CATEGORY));
			text.append("Bins");
		} else if (element instanceof CTBin) {
			cell.setImage(ImageRegister.getImage(ImageRegister.ICON_MODEL));
			final CTBin bin = (CTBin) element;
			text.append(bin.getName());
			if (bin.getPath() != null) {
				if (!bin.getParent().getPath().equals(bin.getPath())) {
					text.append(" " + bin.getPath(), StyleColours.dateStyler);
				}
			}
		} else if (element instanceof PatternSamplePart) {
			final PatternSamplePart si = (PatternSamplePart) element;
			text.append(si.sample.getPattern().toString());
		} else if (element instanceof PatternSampleText) {
			final PatternSampleText si = (PatternSampleText) element;
			text.append(si.sample.getDomain().toString());
			text.append(" : ", StyleColours.dateStyler);
			text.append(si.sample.getSample());
		} else if (element instanceof Trace) {
			cell.setImage(ImageRegister.getImage(ImageRegister.ICON_REDUCE));
			text.append("Trace");
		} else if (element instanceof TraceCell) {
			final TraceCell trace = (TraceCell) element;
			text.append(trace.domain.toString());
		} else if (element instanceof TraceParsedCell) {
			final TraceParsedCell trace = (TraceParsedCell) element;
			if (trace.parsed.isEmpty()) {
				text.append("FULLY REDUCED", StyleColours.greenStyler);
			} else {
				ppExpression(text, trace.parsed);
			}
		} else if (element instanceof TraceRule) {
			final TraceRule trace = (TraceRule) element;
			text.append(trace.rule.toString());
		} else if (element instanceof ContextErrors) {
			text.append("Errors");
		} else if (element instanceof ContextWarnings) {
			text.append("Warnings");
		} else if (element instanceof TEMarker) {
			final TEMarker marker = (TEMarker) element;
			text.append(marker.message);
		} else {
			text.append(element.toString(), StyleColours.errorStyler);
		}

		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		super.update(cell);
	}

	private void ppExpression(StyledString text, final CTEPartBase expression) {
		expression.visit(new IVisitor() {

			@Override
			public void visit(CTEToken part) {
				final String type = part.flagString("rwe", "none");
				final String token = part.getToken();
				final int index = part.getIndex();
				final KIND kind = part.getKind();
				final boolean hasToken = token != null && token.length() > 0;
				final String bin = part.getBin();

				if (type.equals("none")) {

					text.append(kind.toString(), StyleColours.subTypeStyler3);
					if (token != null && part.getCardinality() == part.getCount() && kind != KIND.NOISE || kind == KIND.OPERATOR) {
						text.append('[');
						text.append(token, StyleColours.resourceStyler);
						text.append(']');
					}

					if (kind == KIND.ELEMENT && index > 0) {
						text.append(":" + index, StyleColours.subTypeStyler2);
					}
				} else if (type.equals("left")) {
					if (index == 0 && kind == KIND.ELEMENT) {
						text.append(token, StyleColours.resourceStyler);
					} else if (kind == KIND.NOISE) {
						text.append(KIND.NOISE.name(), StyleColours.subTypeStyler3);
					} else {
						text.append(kind.toString(), StyleColours.subTypeStyler3);
						if (index > 0) {
							text.append(":" + index, StyleColours.subTypeStyler2);
						}
						if (hasToken) {
							text.append('[');
							text.append(token, StyleColours.resourceStyler);
							text.append(']');
						}
					}

				} else if (type.equals("right")) {
					if (kind == KIND.NOISE) {
						text.append(KIND.NOISE.name(), StyleColours.subTypeStyler3);
					} else {
						text.append(kind.toString(), StyleColours.subTypeStyler3);
						if (hasToken) {
							text.append('[');
							text.append(token, StyleColours.resourceStyler);
							text.append(']');
						}
					}
				} else if (type.equals("production")) {
					if (kind == KIND.ELEMENT) {
						text.append('{');
						text.append(bin == null ? "?" : bin, StyleColours.greenStyler);
						text.append('}');
					} else {
						text.append(kind.toString(), StyleColours.subTypeStyler3);
						if (hasToken) {
							text.append('[');
							text.append(token, StyleColours.resourceStyler);
							text.append(']');
						}
					}
				}

			}

			@Override
			public boolean enter(CTETokenList part) {
				if (part != expression) {
					text.append("(");
				}
				return true;
			}

			@Override
			public void leave(CTETokenList part) {
				if (part != expression) {
					text.append(")");
				}
			}

			@Override
			public void next() {
				text.append(" ");
			}

		}, null);
	}

}
