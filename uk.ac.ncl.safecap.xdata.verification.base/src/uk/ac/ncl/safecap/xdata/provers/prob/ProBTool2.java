package uk.ac.ncl.safecap.xdata.provers.prob;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import uk.ac.ncl.safecap.common.resources.ToolPathRegistry;
import uk.ac.ncl.safecap.misc.core.Log;
import uk.ac.ncl.safecap.xdata.provers.ReportingTool;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class ProBTool2 extends ReportingTool<VerificationResult.RESULT> implements IProBTool {
	private File fileinput;
	private File filelog;
	private final int timelimit;

	protected ProBTool2(int time) {
		super(ToolPathRegistry.getInstance().get("prob"), new String[] {}, null, null);
		timelimit = time;
	}

	@Override
	public IProBTool getInstance(String model) throws IOException {
		final ProBTool2 tool = new ProBTool2(timelimit);
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
		return null;
	}

	@Override
	public VerificationResult.RESULT check(IProgressMonitor monitor) throws Exception {

		if (fileinput == null) {
			return VerificationResult.RESULT.FAILED;
		}

		process(monitor);

		return parseLog();
	}

	private synchronized VerificationResult.RESULT parseLog() throws Exception {
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final Document document = documentBuilder.parse(filelog);
		final Node node = document.getElementsByTagName("probcli-run").item(0);
		if (node instanceof Element) {
			final Element root = (Element) node;
			final Node node2 = root.getElementsByTagName("probcli-errors").item(0);
			if (node2 instanceof Element) {
				final Element report = (Element) node2;
				final String errors = report.getAttribute("errors");
				if ("0".equals(errors)) {
					return VerificationResult.RESULT.VALID;
				}

				return VerificationResult.RESULT.INVALID;
			}
		}

		return VerificationResult.RESULT.UNKNOWN;
	}

	private synchronized void setFileInput(String input) {
		try {
			fileinput = File.createTempFile("data", ".mch", null);
			fileinput.deleteOnExit();

			filelog = File.createTempFile("out", ".xml", null);
			filelog.deleteOnExit();

			FileUtil.setFileContents(input, fileinput);
		} catch (final Throwable e) {
			Log.Error("ExternalTool.setFileInput failed:" + e.getMessage());
		}
	}

	@Override
	public String[] getToolArguments() {
		return new String[] { fileinput.getAbsolutePath(), " -properties -logxml " + filelog + " -silent ", "-timeout " + timelimit * 1000,
				"-p MAXINT 4096", "-p MININT -32" };
		// return new String[] { fileinput.getAbsolutePath(), " -logxml " + filelog + "
		// -silent", "-timeout " + timelimit * 1000, "-p MAXINT 4096", "-p MININT -32"};
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
