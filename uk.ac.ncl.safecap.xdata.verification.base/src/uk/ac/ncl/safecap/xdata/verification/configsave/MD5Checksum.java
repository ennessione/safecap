package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5Checksum {

	public static byte[] createChecksum(String filename) throws Exception {
		final InputStream fis = new FileInputStream(filename);

		final byte[] buffer = new byte[1024];
		final MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}

	public static String getMD5Checksum(String filename) throws Exception {
		final byte[] b = createChecksum(filename);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

}