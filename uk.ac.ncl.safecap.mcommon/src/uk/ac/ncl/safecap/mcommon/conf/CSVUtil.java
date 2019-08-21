package uk.ac.ncl.safecap.mcommon.conf;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class CSVUtil {

	public static int getInt(String s) {
		try {
			final String us = unquote(s);
			return Integer.valueOf(us);
		} catch (final NumberFormatException e) {
			return 0;
		}
	}

	public static String unquote(String d) {
		if (d == null) {
			return null;
		} else if (d.startsWith("\"") && d.endsWith("\"")) {
			return d.substring(1, d.length() - 1).trim();
		} else {
			return d.trim();
		}
	}

	public static String quote(String d) {
		return "\"" + d + "\"";
	}

	public static String[] trim(String[] parts) {
		int lastNotEmpty = 0;
		for (int i = 0; i < parts.length; i++) {
			final String s = parts[i].trim();
			if (s.length() > 0) {
				lastNotEmpty = i;
			}
		}

		final String[] result = Arrays.copyOf(parts, lastNotEmpty + 1);
		for (int i = 0; i < result.length; i++) {
			result[i] = result[i].trim();
		}

		return result;
	}

	public static Reader getInputFile(Shell shell, String title, String... extensions) {
		try {
			if (shell == null) {
				shell = Display.getCurrent().getActiveShell();
			}
			if (shell == null) {
				return null;
			}
			final FileDialog fd = new FileDialog(shell, SWT.OPEN);
			fd.setText(title);
			fd.setFilterExtensions(extensions);
			final String filename = fd.open();
			// String filename = "/Users/cmdadmin/dev/marylebone/route_import.csv"; //
			if (filename != null) {
				final File file = new File(filename);
				if (file.exists() && file.isFile()) {
					final Reader in = new FileReader(file);
					return in;
				}
			}

			return null;
		} catch (final Throwable e) {
			return null;
		}
	}
}
