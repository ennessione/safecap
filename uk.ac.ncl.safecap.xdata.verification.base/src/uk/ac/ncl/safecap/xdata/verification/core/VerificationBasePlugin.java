package uk.ac.ncl.safecap.xdata.verification.core;

import java.io.InputStream;
import java.net.URL;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.registry.VerificationCatalogRegistry;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionCache;

public class VerificationBasePlugin implements BundleActivator {
	private static VerificationBasePlugin plugin;

	private static BundleContext context;
	private static TransitionCache cache;
	private final VerificationCatalogRegistry<RootCatalog> vcatalogRegistry;

	public VerificationBasePlugin() {
		vcatalogRegistry = new VerificationCatalogRegistry<>(SafecapConstants.VCATALOG_EXTENSION, RootCatalog.TYPE);
	}


	public static VerificationCatalogRegistry<RootCatalog> getVCatalogRegistry() {
		return plugin.vcatalogRegistry;
	}

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

	public static TransitionCache getTransitionCache() {
		return cache;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		VerificationBasePlugin.context = context;
		cache = new TransitionCache();
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		VerificationBasePlugin.context = null;
		plugin = null;
	}

	public static BundleContext getContext() {
		return context;
	}
}
