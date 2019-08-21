package uk.ac.ncl.safecap.analytics.ctextract.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CTEPlugin implements BundleActivator {
	private static BundleContext context;

	@Override
	public void start(BundleContext context) throws Exception {
		CTEPlugin.context = context;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		CTEPlugin.context = null;
	}

	public static BundleContext getContext() {
		return context;
	}

	public static URL getLibFile(String name) {
		return getContext().getBundle().getEntry(name);
	}

	public static String getLibFileContents(String name) {
		return getFileContents(getLibFile(name));
	}

	public static List<String> getLibFileLineContents(String name) throws Exception {
		return getFileLineContents(getLibFile(name));
	}

	private static String getFileContents(URL file) {
		if (file == null) {
			return null;
		}

		try {
			final InputStream ins = file.openStream();
			final byte[] data = new byte[ins.available()];
			ins.read(data);
			final String text = new String(data);
			return text;
		} catch (final Throwable e1) {
			e1.printStackTrace();
		}

		return null;
	}

	public static List<String> getFileLineContents(URL file) throws Exception {
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.openStream(), "UTF-8"));
		final List<String> lines = new LinkedList<>();

		String line = bufferedReader.readLine();

		while (line != null) {
			lines.add(line);
			line = bufferedReader.readLine();
		}

		bufferedReader.close();

		return lines;
	}
}
