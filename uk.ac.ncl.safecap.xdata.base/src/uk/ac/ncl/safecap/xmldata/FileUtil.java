package uk.ac.ncl.safecap.xmldata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FileUtil {

	public static void setFileContents(String str, File file) throws Exception {
		final FileWriter writer = new FileWriter(file);
		writer.append(str);
		writer.flush();
		writer.close();
	}

	public static String replaceExtension(String filename, String newExtension) {
		final int dotIndex = filename.lastIndexOf('.');
		if (dotIndex > -1) {
			filename = filename.substring(0, dotIndex + 1) + newExtension;
		}
		return filename;
	}

	public static String removeExtension(String filename) {
		final int dotIndex = filename.lastIndexOf('.');
		if (dotIndex > -1) {
			filename = filename.substring(0, dotIndex);
		}
		return filename;
	}

	public static String getFileContents(File file) throws Exception {
		final StringBuffer buffer = new StringBuffer();
		final String lineSeparator = System.getProperty("line.separator");

		for (final String line : getFileLineContents(file)) {
			buffer.append(line);
			buffer.append(lineSeparator);
		}

		return buffer.toString();
	}

	public static Collection<String> getFileLineContents(File file) throws Exception {
		final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		final List<String> lines = new LinkedList<>();

		String line = bufferedReader.readLine();

		while (line != null) {
			lines.add(line);
			line = bufferedReader.readLine();
		}

		bufferedReader.close();

		return lines;
	}

	public static String getAbsolutePath(String basePath, String relativePath) {
		File file = new File(relativePath);
		if (!file.isAbsolute()) {
			file = new File(basePath, relativePath);
		}
		return file.getAbsolutePath();
	}

	public static File getFile(String name, Class<?> relativeTo) {
		try {
			final File clazz = new File(
					URLDecoder.decode(relativeTo.getResource(relativeTo.getSimpleName() + ".class").getFile(), "UTF-8"));

			return new File(clazz.getParentFile(), name);

		} catch (final UnsupportedEncodingException e) {
			throw new IllegalArgumentException(name + " could not be located relative to " + relativeTo);
		}
	}

	public static File getDirectoryOf(Class<?> clazz) {
		return getFile(clazz.getSimpleName() + ".class", clazz).getParentFile();
	}

	public static String getPath(String name, Class<?> relativeTo) {
		return getFile(name, relativeTo).getAbsolutePath();
	}

	public static void checkFileExists(final File file) throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException("File " + file.getPath() + " does not exist");
		}
	}

	public static File copyToTemp(File srcFile) throws IOException {
		final File tmpFile = File.createTempFile("filecompare", "tmp");
		if (srcFile.isDirectory()) {
			tmpFile.delete();
			tmpFile.mkdir();
		}
		copy(srcFile, tmpFile);
		return tmpFile;
	}

	public static void copy(File srcFile, File dstFile) throws IOException {
		if (srcFile.isDirectory()) {
			dstFile.mkdir();
			for (final File entry : srcFile.listFiles()) {
				copy(entry, new File(dstFile, entry.getName()));
			}
		} else {
			// Based on the second answer in http://stackoverflow.com/questions/106770.
			FileInputStream isSrc = null;
			FileOutputStream osDst = null;
			FileChannel chSrc = null;
			FileChannel chDst = null;
			try {
				isSrc = new FileInputStream(srcFile);
				osDst = new FileOutputStream(dstFile);
				chSrc = isSrc.getChannel();
				chDst = osDst.getChannel();
				final long srcBytes = srcFile.length();
				long transferred = 0;
				while (transferred < srcBytes) {
					transferred += chDst.transferFrom(chSrc, transferred, srcBytes);
					chDst.position(transferred);
				}
			} finally {
				if (chDst != null) {
					chDst.close();
				} else if (osDst != null) {
					osDst.close();
				}

				if (chSrc != null) {
					chSrc.close();
				} else if (isSrc != null) {
					isSrc.close();
				}
			}
		}
	}

	public static HashSet<String> listFilesAsSet(File fileExpected) {
		return new HashSet<>(Arrays.asList(fileExpected.list()));
	}

	/**
	 * We implement our own comparison algorithm here, so we don't need Eclipse
	 * Compare to compute differences, but rather only to show them in the UI.
	 */
	public static boolean sameContents(File fileExpected, File fileActual) throws IOException {
		if (fileExpected.isDirectory() != fileActual.isDirectory()) {
			// One is a file, the other is a directory: not the same
			return false;
		}

		if (fileExpected.isDirectory()) {
			// Both are directories: they should contain the same filenames,
			// and each pair should have the same contents
			final Set<String> expectedFilenames = listFilesAsSet(fileExpected);
			final Set<String> actualFilenames = listFilesAsSet(fileActual);
			if (!expectedFilenames.equals(actualFilenames)) {
				return false;
			}
			for (final String filename : expectedFilenames) {
				final File expectedEntry = new File(fileExpected, filename);
				final File actualEntry = new File(fileActual, filename);
				if (!sameContents(expectedEntry, actualEntry)) {
					return false;
				}
			}
			return true;
		} else {
			if (fileExpected.length() != fileActual.length()) {
				// Different length: no need to read the files
				return false;
			}

			final FileInputStream isExpected = new FileInputStream(fileExpected);
			final FileInputStream isActual = new FileInputStream(fileActual);
			return sameContents(isExpected, isActual);
		}
	}

	public static boolean sameContents(InputStream isExpected, InputStream isActual) throws IOException {
		int chExpected, chActual;

		do {
			chExpected = isExpected.read();
			chActual = isActual.read();
		} while (chExpected == chActual && chExpected > 0 && chActual > 0);

		return chExpected == chActual;
	}
}