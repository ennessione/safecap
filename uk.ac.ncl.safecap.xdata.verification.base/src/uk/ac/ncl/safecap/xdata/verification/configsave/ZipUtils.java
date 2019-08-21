package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	public static String getEntryFromZip(byte[] zip, String file) throws FileNotFoundException, IOException {
		final ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(zip));
		ZipEntry entry = null;
		while ((entry = zipStream.getNextEntry()) != null) {

			final String entryName = entry.getName();
			if (entryName.equals(file)) {

				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final byte[] byteBuff = new byte[4096];
				int bytesRead = 0;
				while ((bytesRead = zipStream.read(byteBuff)) != -1) {
					out.write(byteBuff, 0, bytesRead);
				}
				out.close();
				return new String(out.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);
			}
			zipStream.closeEntry();
		}
		zipStream.close();

		return null;
	}

	public static byte[] zipFolder(File folder) {
		final byte[] buffer = new byte[8192];
		final List<File> files = new ArrayList<>();
		makeFileList(folder, files, 1);
		ByteArrayOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(fos);

			FileInputStream in = null;

			for (final File part : files) {
				final ZipEntry ze = new ZipEntry(part.getName());
				zos.putNextEntry(ze);
				try {
					in = new FileInputStream(part);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				} finally {
					in.close();
				}
			}

			zos.closeEntry();
			return fos.toByteArray();
		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				zos.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static void makeFileList(File file, List<File> list, int depth) {
		if (file.isFile()) {
			list.add(file);
		}

		if (file.isDirectory() && depth > 0) {
			for (final String part : file.list()) {
				makeFileList(new File(file, part), list, depth - 1);
			}
		}
	}

}