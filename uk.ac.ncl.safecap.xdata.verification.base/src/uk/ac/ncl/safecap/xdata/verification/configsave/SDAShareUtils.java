package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import uk.ac.ncl.safecap.misc.core.SafecapConstants;

public class SDAShareUtils {
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	public static String[] getProfiles(String path) {
		final List<String> result = new ArrayList<>();
		final File folder = new File(path + File.separator + SafecapConstants.SDA_PROFILE_FOLDER);
		if (folder.exists() && folder.isDirectory() && folder.canRead()) {
			for (final String sf : folder.list()) {
				if (isValidProfile(path, sf)) {
					result.add(sf);
				}
			}
		}

		return result.toArray(new String[result.size()]);
	}

	public static Map<String, String> getProjects(String path) {
		final Map<String, String> result = new HashMap<>();
		final File folder = new File(path + File.separator + SafecapConstants.SDA_PROJECT_FOLDER);
		if (folder.exists() && folder.isDirectory() && folder.canRead()) {
			for (final String sf : folder.list()) {
				final File f = new File(path + File.separator + SafecapConstants.SDA_PROJECT_FOLDER + File.separator + sf);
				if (isValidProject(f)) {
					result.put(remExtension(f.getName()) + ", " + getFileDate(f) + " [shared]", f.getAbsolutePath());
				}
			}
		}

		return result;
	}

	public static String getFileDate(File file) {
		try {
			final BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			final FileTime date = attr.lastModifiedTime();
			return df.format(date.toMillis());
		} catch (final Throwable e) {
			return "?";
		}
	}

	public static boolean prepareProjectFolder(String path, Shell shell) {
		final File folder = new File(path + File.separator + SafecapConstants.SDA_PROJECT_FOLDER);
		if (folder.exists()) {
			if (folder.isDirectory() && folder.canRead() && folder.canWrite()) {
				return true;
			} else if (shell != null) {
				MessageDialog.openError(shell, "Project folder", "Project folder " + folder.getAbsolutePath() + " is not accessible");
			}
			return false;
		} else {
			if (folder.mkdirs()) {
				return true;
			}
			if (shell != null) {
				MessageDialog.openError(shell, "Project folder", "Failed creating project folder " + folder.getAbsolutePath());
			}
			return false;
		}
	}

	static String remExtension(String str) {
		final int pos = str.lastIndexOf(".");
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	private static String getExtension(File file) {
		final String name = file.getName();
		final int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return "";
		}
		return name.substring(lastIndexOf);
	}

	private static boolean isValidProject(File file) {
		return file.exists() && file.isFile() && getExtension(file).equals(".sda");
	}

	public static boolean isValidProfile(String sdaShare, String profile) {
		final File folder = new File(sdaShare + File.separator + SafecapConstants.SDA_PROFILE_FOLDER + File.separator + profile);
		if (folder.exists() && folder.isDirectory() && folder.canRead()) {
			final ProfileInfo info = ProfileInfo.make(folder);
			return info != null;
		}
		return false;
	}

	public static ProfileInfo getProfileInfo(String sdaShare, String profile) {
		final File folder = new File(sdaShare + File.separator + SafecapConstants.SDA_PROFILE_FOLDER + File.separator + profile);
		if (folder.exists() && folder.isDirectory() && folder.canRead()) {
			final ProfileInfo info = ProfileInfo.make(folder);
			return info;
		}
		return null;
	}
}
