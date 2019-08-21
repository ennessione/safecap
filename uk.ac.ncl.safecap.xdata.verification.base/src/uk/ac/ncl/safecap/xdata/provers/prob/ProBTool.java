package uk.ac.ncl.safecap.xdata.provers.prob;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ncl.safecap.common.resources.ToolPathRegistry;
import uk.ac.ncl.safecap.misc.core.Log;
import uk.ac.ncl.safecap.xdata.provers.ReportingTool;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class ProBTool extends ReportingTool<VerificationResult.RESULT> implements IProBTool {
	private File fileinput;
	private final int timelimit;
	private static final String ERROR_HEADER1 = "! setup_constants_fails";
	private int index;
	private int state;

	protected ProBTool(int time) {
		super(ToolPathRegistry.getInstance().get("prob"), new String[] {}, null, null);
		timelimit = time;

	}

	@Override
	public IProBTool getInstance(String model) throws IOException {
		final ProBTool tool = new ProBTool(timelimit);
		tool.setup(model);
		return tool;
	}

	public void setup(String model) throws IOException {
		if (super.getToolPath() == null) {
			throw new IOException("Tool path is not valid");
		}
		setFileInput(model);
	}

	@Override
	public VerificationResult.RESULT statusReport(String line) {
		if (getStatus() != null) {
			return null;
		} else if (line.contains(ERROR_HEADER1)) {
			return VerificationResult.RESULT.INVALID;
		} else {
			return statusReport1(line);
		}
	}

	public int getIndex() {
		return index;
	}

	public int getState() {
		return state;
	}

	private VerificationResult.RESULT statusReport1(String line) {
		if (line.startsWith("[total/") && line.endsWith("]")) {
			line = line.substring(1, line.length() - 1);
			final String[] parts = line.split(",");
			if (parts.length == 11) {
				final Map<String, Integer> values = new HashMap<>(11);
				for (final String part : parts) {
					final String[] sub_parts = part.split("/");
					if (sub_parts.length == 2) {
						values.put(sub_parts[0], Integer.valueOf(sub_parts[1]));
					}
				}
				final int ptotal = values.get("total");
				final int ptrue = values.get("true");
				final int pfalse = values.get("false");
				if (pfalse != 0) {
					return VerificationResult.RESULT.INVALID;
				} else if (ptotal == ptrue) {
					return VerificationResult.RESULT.VALID;
				} else {
					return VerificationResult.RESULT.UNKNOWN;
				}
			}
		} else if (line.startsWith("! setup_constants_fails")) {
			return VerificationResult.RESULT.INVALID;
		}

		return null;
	}

	@Override
	public VerificationResult.RESULT check(IProgressMonitor monitor) {

		if (fileinput == null) {
			return VerificationResult.RESULT.FAILED;
		}

		try {
			process(monitor);
		} catch (final Throwable e) {
			e.printStackTrace();
			return VerificationResult.RESULT.FAILED;
		}

		final VerificationResult.RESULT status = getStatus();
		if (status == null) {
			return VerificationResult.RESULT.UNKNOWN;
		} else {
			return status;
		}
	}

	private void setFileInput(String input) {
		try {
			fileinput = File.createTempFile("data", ".mch", null);
			fileinput.deleteOnExit();
			FileUtil.setFileContents(input, fileinput);
		} catch (final Throwable e) {
			Log.Error("ExternalTool.setFileInput failed:" + e.getMessage());
		}
	}

	@Override
	public String[] getToolArguments() {
		return new String[] { fileinput.getAbsolutePath(), "-properties ", "-timeout " + timelimit * 1000, "-p MAXINT 4096",
				"-p MININT -32", "-p MAX_DISPLAY_SET -1" };
	}

	@Override
	public void parseReport(String line) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalReport() {
		// TODO Auto-generated method stub

	}

}
