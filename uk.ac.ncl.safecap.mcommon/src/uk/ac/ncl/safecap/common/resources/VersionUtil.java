package uk.ac.ncl.safecap.common.resources;

import org.eclipse.core.resources.IFile;

public class VersionUtil {
	public static final String MAGIC = "___";

	public static String getModelName(String fileName) {
		final String[] arr = fileName.split(MAGIC);
		if (arr.length > 0) {
			return arr[0];
		} else {
			return "";
		}
	}

	public static String getVersionName(String fileName) {
		final String[] arr = fileName.split(MAGIC);
		if (arr.length > 1) {
			return arr[1];
		} else {
			return "0";
		}
	}

	public static IFile getNextVersion(IFile file) {
		final String fileName = file.getFullPath().removeFileExtension().lastSegment();
		final String modelName = getModelName(fileName);
		final String versionName = getVersionName(fileName);
		final String extension = file.getFileExtension() == null ? "" : "." + file.getFileExtension();

		String baseNum = "";
		for (int i = versionName.length() - 1; i >= 0; i--) {
			if (Character.isDigit(versionName.charAt(i))) {
				baseNum = versionName.charAt(i) + baseNum;
			}
		}
		final String baseWord = versionName.substring(0, versionName.length() - baseNum.length());

		String baseName;
		int versionNum;
		try {
			versionNum = Integer.parseInt(baseNum) + 1;
			baseName = modelName + MAGIC + baseWord;
		} catch (final NumberFormatException e) {
			baseName = modelName + MAGIC + versionName;
			versionNum = 0;
		}

		String newName;
		IFile newFile;
		do {
			newName = baseName + versionNum + extension;
			newFile = file.getProject().getFile(newName);
			versionNum++;
		} while (newFile.exists());
		return newFile;
	}
}
