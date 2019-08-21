package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExperimentResults {
	private final List<EIResult> results;

	public ExperimentResults() {
		results = new ArrayList<>();
	}

	public void add(EIResult result) {
		results.add(result);
	}

	public List<EIResult> getResults() {
		return results;
	}

	public void export(String filename) {
		FileWriter fstream = null;
		PrintWriter out = null;
		try {
			fstream = new FileWriter(filename, false);
			out = new PrintWriter(fstream);
			out.println("// Statistical experiments history");
			out.println("// Produced by Safecap on " + new Date());

			for (final EIResult result : results) {
				export(out, result);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (fstream != null) {
				try {
					fstream.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void export(PrintWriter out, EIResult result) {
		if (result.getError() == null && result.getRecords() != null && result.getRecords().size() > 0) {
			final int[] cumulative = new int[result.getRecords().get(0).size()];
			int index = 1;
			int totalValid = 0;
			int j = 0;
			out.print("Index, Invalid ratio (Overall)");
			for (final EIRecord pp : result.getRecords().get(0)) {
				out.print(", \"Invalid ratio (" + pp.getProperty() + ")\"");
			}
			out.println("");

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
				final double invalidRatio = (double) totalInvalid / (double) index;
				out.print(index + ", " + invalidRatio);
				for (int k = 0; k < cumulative.length; k++) {
					out.print(", " + (double) (index - cumulative[k]) / (double) index);
				}
				out.println("");

				index++;
			}
		} else {
			out.println("// Error: " + result.getError());
		}
	}
}
