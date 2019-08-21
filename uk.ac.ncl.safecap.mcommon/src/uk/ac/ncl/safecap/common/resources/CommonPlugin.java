package uk.ac.ncl.safecap.common.resources;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CommonPlugin extends AbstractUIPlugin implements BundleActivator {
	// The plug-in ID
	public static final String PLUGIN_ID = "uk.ac.ncl.safecap.mcommon"; //$NON-NLS-1$
	private static BundleContext context;

	// The shared instance
	private static CommonPlugin plugin;
	public static final NumberFormat formatter = new DecimalFormat("#0.00");

	/**
	 * The constructor
	 */
	public CommonPlugin() {
	}

	@Override
	protected void initializeImageRegistry(final ImageRegistry reg) {
		ImageRegister.initializeImageRegistry(reg);
		super.initializeImageRegistry(reg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		CommonPlugin.context = context;
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		CommonPlugin.context = null;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CommonPlugin getDefault() {
		return plugin;
	}

	public static URL getLibFile(String name) {
		return context.getBundle().getEntry("/seq/" + name);
	}

	public static String getStringPreference(String name) {
		return getDefault().getPreferenceStore().getString(name);
	}

	public static boolean getBooleanPreference(String name) {
		return getDefault().getPreferenceStore().getBoolean(name);
	}

	public static String getFileContents(URL file) {
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
}
