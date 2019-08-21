package uk.ac.ncl.safecap.common;

import java.io.IOException;
import java.io.StringReader;

public class MyReader extends StringReader {
	public int position;

	public MyReader(String s) {
		super(s);
		position = 0;
	}

	@Override
	public int read() throws IOException {
		position++;
		return super.read();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		final int read = super.read(cbuf, off, len);
		position += read;
		return read;
	}

	@Override
	public void reset() throws IOException {
		position = 0;
		super.reset();
	}
}