package uk.ac.ncl.eventb.why3.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;

public class Why3LemmaReaderBuiltIn {
	private Set<Why3RawSexprLemma> result;
	private BufferedReader input;
	private boolean hasErrors;

	public Why3LemmaReaderBuiltIn() throws IOException {
		try {
			result = new HashSet<>();
			final URL file = VerificationBasePlugin.getLibFile("support_lemmata.sexpr");
			if (file != null) {
				input = new BufferedReader(new InputStreamReader(file.openStream(), "UTF-8"));
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public Set<Why3RawSexprLemma> read() {
		if (input == null) {
			return result;
		}

		try {
			try {
				String s = input.readLine();
				while (s != null && !hasErrors) {
					processLine(s);
					s = input.readLine();
				}
				input.close();

			} catch (final IOException e) {

				e.printStackTrace();
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	private final StringBuilder buf = new StringBuilder();

	public void processLine(String line) {
		if (!hasErrors) {
			line = line.trim();
			if (!line.startsWith("(*") && !line.startsWith("theory") && !line.startsWith("end")) {
				buf.append(" ");
				buf.append(line);
				if (line.endsWith("])")) {
					processBuffer();
					buf.setLength(0);
				}
			}
		}
	}

	private void processBuffer() {
		try {
			final int pos0 = buf.indexOf("::=");
			final int pos1 = buf.indexOf("([");
			final int pos2 = buf.indexOf("])");

			if (pos0 == -1 || pos1 < pos0 || pos2 < pos1) {
				hasErrors = true;
				return;
			}

			final String id = buf.substring(0, pos0).trim();
			final String sexpr = buf.substring(pos0 + 3, pos1).trim();
			final String lemma = buf.substring(pos1 + 2, pos2).trim();

			if (id.startsWith("lemma_")) {
				final Why3RawSexprLemma slemma = new Why3RawSexprLemma(id, lemma, sexpr);
				result.add(slemma);
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}
}
