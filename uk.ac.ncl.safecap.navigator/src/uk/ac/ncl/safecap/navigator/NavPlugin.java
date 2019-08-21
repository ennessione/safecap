package uk.ac.ncl.safecap.navigator;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.internal.wizards.AbstractExtensionWizardRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class NavPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "uk.ac.ncl.safecap.navigator"; //$NON-NLS-1$
	public static final String NAVIGATOR_ID = "uk.ac.ncl.safecap.navigator.main"; //$NON-NLS-1$

	// The shared instance
	private static NavPlugin plugin;

	/**
	 * The constructor
	 */
	public NavPlugin() {
	}

	@Override
	protected void initializeImageRegistry(final ImageRegistry reg) {
		super.initializeImageRegistry(reg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@SuppressWarnings("restriction")
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		final AbstractExtensionWizardRegistry wizardRegistry = (AbstractExtensionWizardRegistry) PlatformUI.getWorkbench()
				.getNewWizardRegistry();
		final IWizardCategory[] categories = PlatformUI.getWorkbench().getNewWizardRegistry().getRootCategory().getCategories();
		for (final IWizardCategory category : categories) {
			if (!category.getId().startsWith("uk.ac.ncl.safecap")) {
				for (final IWizardDescriptor wizard : category.getWizards()) {
					final WorkbenchWizardElement wizardElement = (WorkbenchWizardElement) wizard;
					wizardRegistry.removeExtension(wizardElement.getConfigurationElement().getDeclaringExtension(),
							new Object[] { wizardElement });
					// System.out.println("Suppressed wizard " + wizard.getId());
				}
			}
		}
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
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static NavPlugin getDefault() {
		return plugin;
	}

}
