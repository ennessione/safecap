package uk.ac.ncl.safecap.xmldata.ldl;

import java.util.List;

import uk.ac.ncl.safecap.lldl.ErrInfo;

public class LdlImportException extends Exception {
	private static final long serialVersionUID = 3123423634161965274L;
	private List<ErrInfo> info;

	public LdlImportException(String message) {
		super(message);
	}

	public LdlImportException(String message, List<ErrInfo> info) {
		super(message);
		this.info = info;
	}

	public List<ErrInfo> getInfo() {
		return info;
	}
}
