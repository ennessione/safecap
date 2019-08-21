package uk.ac.ncl.safecap.textentry.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class TEPlugin implements BundleActivator {
	private static BundleContext context;
	private static TERegistry elements;

	public static BundleContext getContext() {
		return context;
	}

	public static TERegistry getTERegistry() {
		return elements;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		TEPlugin.context = context;
		elements = new TERegistry();
		elements.build();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		TEPlugin.context = null;
	}

}
