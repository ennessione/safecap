package uk.ac.ncl.safecap.xdata.base;

import java.io.InputStream;
import java.net.URL;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BaseDataPlugin implements BundleActivator {
	private static BundleContext context;

	public static URL getLibFile(String name) {
		return getContext().getBundle().getEntry("/conf/" + name);
	}

	public static String getLibFileContents(String name) {
		return getFileContents(getLibFile(name));
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

	@Override
	public void start(BundleContext context) throws Exception {
		BaseDataPlugin.context = context;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

	public static BundleContext getContext() {
		return context;
	}

}
