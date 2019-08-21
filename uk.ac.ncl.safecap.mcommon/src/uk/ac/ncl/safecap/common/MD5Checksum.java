package uk.ac.ncl.safecap.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MD5Checksum {

	public static byte[] createChecksum(InputStream fis) throws Exception {
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

	public static byte[] createChecksum(String filename) throws Exception {
		final InputStream fis = new FileInputStream(filename);
		return createChecksum(fis);
	}

	public static String getMD5Checksum(String filename) throws Exception {
		final byte[] b = createChecksum(filename);
		return String.format("%032x", new BigInteger(1, b));
	}

	public static String getMD5Checksum(InputStream fis) throws Exception {
		final byte[] b = createChecksum(fis);
		return String.format("%032x", new BigInteger(1, b));
	}

	public static String getMD5TextChecksum(String text) {
		try {
			final MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(text));
			return String.format("%032x", new BigInteger(1, md5.digest()));
		} catch (final Throwable e) {
			return "?";
		}
	}

}