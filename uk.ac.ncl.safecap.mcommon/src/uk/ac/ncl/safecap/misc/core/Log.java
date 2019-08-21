package uk.ac.ncl.safecap.misc.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import uk.ac.ncl.safecap.common.resources.CommonPlugin;

public class Log {
	public static void Error(final String message) {
		final IStatus status = new Status(IStatus.ERROR, CommonPlugin.PLUGIN_ID, message);
		CommonPlugin.getDefault().getLog().log(status);
		System.err.println("LOG:" + message);
	}

	public static void Warning(final String message) {
		final IStatus status = new Status(IStatus.WARNING, CommonPlugin.PLUGIN_ID, message);
		CommonPlugin.getDefault().getLog().log(status);
	}

	public static void Info(final String message) {
		final IStatus status = new Status(IStatus.INFO, CommonPlugin.PLUGIN_ID, message);
		CommonPlugin.getDefault().getLog().log(status);
	}
}
