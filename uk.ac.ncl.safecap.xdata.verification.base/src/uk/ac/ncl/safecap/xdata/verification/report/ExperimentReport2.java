package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.File;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.common.report.SMError;
import uk.ac.ncl.safecap.common.report.SRBlock;
import uk.ac.ncl.safecap.common.report.SRDocument;
import uk.ac.ncl.safecap.common.report.SRFigure;
import uk.ac.ncl.safecap.common.report.SRList;
import uk.ac.ncl.safecap.common.report.SRSection;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.EIRecord;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.EIResult;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.ExperimentResults;

public class ExperimentReport2 {
	private final ExperimentResults expResults;

	public ExperimentReport2(ExperimentResults expResults) {
		this.expResults = expResults;
	}

	private XDataChart<Integer> buildConvergence(EIResult result) {
		try {

			if (result.getRecords() != null && result.getRecords().size() > 0) {
				final String[] legend = new String[result.getRecords().get(0).size() + 1];
				legend[0] = "Overall";
				for (int i = 0; i < result.getRecords().get(0).size(); i++) {
					legend[i + 1] = result.getRecords().get(0).get(i).getProperty();
				}

				final XDataChart<Integer> chart = new XDataChart<>("conjecture", "number", legend);
				final int[] cumulative = new int[result.getRecords().get(0).size()];
				int index = 1;
				int totalValid = 0;
				int j = 0;
				for (final List<EIRecord> set : result.getRecords()) {
					boolean isValid = true;
					assert set.size() == cumulative.length;
					j = 0;
					for (final EIRecord r : set) {
						isValid = isValid && r.getResult() == 1;
						cumulative[j] += r.getResult();
						j++;
					}

					totalValid += isValid ? 1 : 0;
					final int totalInvalid = index - totalValid;
					final double invalidRatio = totalInvalid / (double) index;
					final double[] vals = new double[cumulative.length + 1];
					vals[0] = invalidRatio;
					for (int k = 0; k < cumulative.length; k++) {
						vals[k + 1] = (double) (index - cumulative[k]) / (double) index;
					}
					chart.set(index, vals);

					index++;
				}
				return chart;
			}
			return null;
		} catch (final Throwable e) {
			e.printStackTrace();
		}

		return null;
	}

	public void build(final IReportBuilder builder, final File file) throws Exception {

		final SRDocument document = new SRDocument("Experiment report");

		for (final EIResult er : expResults.getResults()) {
			document.add(processExperiment(er));
		}

		builder.buildReport(document, file, true);
	}

	private SRSection processExperiment(EIResult er) {

		final SRSection section = new SRSection("Unknown");

		if (er.getRecords() != null) {
			final XDataChart<Integer> h2 = buildConvergence(er);
			if (h2 != null) {
				// h2.cutTailOff(0.4); // remove flat tailing
				section.add(new SRFigure("Convergence").add(new PlotSVGRenderer(h2).render()));
			}

			final Map<String, Integer> cumulative = er.getCumulativeValid();

			for (final String p : cumulative.keySet()) {
				final SRSection subsection = new SRSection("Conjecture " + p);
				section.add(subsection);
				final int valid = cumulative.get(p);
				final int invalid = er.getRecords().size() - valid;

				final SRList list = new SRList();
				subsection.add(list);

				list.add("Runs: " + er.getRecords().size());
				list.add("Valid: " + valid);
				list.add("Invalid: " + invalid);
			}

		} else {
			section.add(new SRBlock().add(new SMError(er.getError())));
		}

		return section;
	}
}
