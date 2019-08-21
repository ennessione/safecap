package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import uk.ac.ncl.safecap.xdata.provers.Properties;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataUtils;

public class VerificationStep implements IVerificationStep {
	private static final long serialVersionUID = -1998046223661809976L;
	private final Date date;
	private final boolean hasSchema;
	private final boolean hasSignalling;
	private final boolean hasResult;
	private final boolean hasReport;
	private final String schemaFileName;
	private final String ctFileName;
	private final String schemaFileHash;
	private final String ctFileHash;
	private final String interlocking;
	private byte[] schemaData;
	private byte[] signallingData;
	private byte[] resultData;
	private byte[] reportZip;

	private transient DataContext schema;
	private transient DataContext signalling;
	private transient ReportResult result;
	private transient File reportFolder;

	public VerificationStep(String schemaFileName, String schemaFileHash, DataContext schema, String ctFileName, String ctFileHash,
			DataContext signalling, String interlocking, ReportResult result, File reportFolder) {
		this.schemaFileName = schemaFileName;
		this.schemaFileHash = schemaFileHash;
		this.schema = schema;
		this.ctFileName = ctFileName;
		this.ctFileHash = ctFileHash;
		this.signalling = signalling;
		this.result = result;
		this.reportFolder = reportFolder;
		this.interlocking = interlocking;
		date = new Date();
		hasSchema = schema != null;
		hasSignalling = signalling != null;
		hasResult = result != null;
		hasReport = reportFolder != null;
	}

	@Override
	public void prepareToSave() {
		try {
			ByteArrayOutputStream bos;
			if (schema != null && schemaData == null) {
				bos = new ByteArrayOutputStream();
				DataUtils.saveDataContextToStream(schema, bos);
				schemaData = bos.toByteArray();
			}

			if (signalling != null && signallingData == null) {
				bos = new ByteArrayOutputStream();
				DataUtils.saveDataContextToStream(signalling, bos);
				signallingData = bos.toByteArray();
			}

			if (result != null && resultData == null) {
				bos = new ByteArrayOutputStream();
				final ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(result);
				resultData = bos.toByteArray();
			}

			if (reportFolder != null && reportZip == null) {
				if (reportFolder.exists() && reportFolder.isDirectory()) {
					reportZip = ZipUtils.zipFolder(reportFolder);
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public boolean hasSchema() {
		return hasSchema;
	}

	@Override
	public boolean hasControlTable() {
		return hasSignalling;
	}

	@Override
	public boolean hasResult() {
		return hasResult;
	}

	@Override
	public boolean hasReport() {
		return hasReport;
	}

	@Override
	public DataContext getSchema() {
		try {
			if (schema == null && hasSchema && schemaData != null && schemaData.length > 0) {
				final ByteArrayInputStream bais = new ByteArrayInputStream(schemaData);
				schema = DataUtils.getDataContextFromFile2(bais);
				bais.close();
			}
			return schema;
		} catch (final Throwable e) {
			if (Properties.DEBUG) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@Override
	public DataContext getControlTable() {
		try {
			if (signalling == null && hasSignalling && signallingData != null && signallingData.length > 0) {
				final ByteArrayInputStream bais = new ByteArrayInputStream(signallingData);
				signalling = DataUtils.getDataContextFromFile2(bais);
				bais.close();
			}
			return signalling;
		} catch (final Throwable e) {
			if (Properties.DEBUG) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@Override
	public ReportResult getResult() {
		try {
			if (result == null && hasResult && resultData != null && resultData.length > 0) {
				final ObjectInputStream oos = new ObjectInputStream(new ByteArrayInputStream(resultData));
				final Object _result = oos.readObject();
				if (_result instanceof ReportResult) {
					result = (ReportResult) _result;
				}
				oos.close();
			}
		} catch (final Throwable e) {
			if (Properties.DEBUG) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public byte[] getReport() {
		try {
			if (reportZip != null && reportZip.length > 0) {
				return reportZip;
			}
		} catch (final Throwable e) {
			if (Properties.DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getSchemaHash() {
		return schemaFileHash;
	}

	@Override
	public String getControlTableHash() {
		return ctFileHash;
	}

	@Override
	public String getSchemaFile() {
		return schemaFileName;
	}

	@Override
	public String getControlTableFile() {
		return ctFileName;
	}

	@Override
	public String getInterlocking() {
		return interlocking;
	}

}
