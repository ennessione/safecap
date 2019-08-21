package uk.ac.ncl.safecap.xdata.provers.why3;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ncl.safecap.common.resources.ToolPathRegistry;
import uk.ac.ncl.safecap.xdata.provers.Properties;
import uk.ac.ncl.safecap.xdata.provers.ReportingTool;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xmldata.FileUtil;

public class Why3Tool extends ReportingTool<VerificationResult.RESULT> {
	private final String model;
	private final long timelimit;
	private String filePath;
	private final String prover;

	private static Map<String, VerificationResult.RESULT> status_map;

	static {
		status_map = new HashMap<>(5);
		status_map.put(" g0 : Valid ", VerificationResult.RESULT.VALID);
		status_map.put(" g0 : Invalid ", VerificationResult.RESULT.INVALID);
		status_map.put(" g0 : Unknown ", VerificationResult.RESULT.UNKNOWN);
		status_map.put(" g0 : Timeout ", VerificationResult.RESULT.UNKNOWN);
		status_map.put(" g0 : Failure ", VerificationResult.RESULT.FAILED);
	}

	public Why3Tool(String model, String prover, int time) throws IOException {
		super(ToolPathRegistry.getInstance().get("why3"), null, VerificationResult.RESULT.UNKNOWN, status_map);
		this.prover = prover;
		this.model = model;
		timelimit = time;
	}

	@Override
	public String[] getToolArguments() {
		return new String[] { "prove", "-P " + prover, "-t " + timelimit, filePath, "-T POx", "-G g0" };
	}

	@Override
	public void parseReport(String line) {
		// do nothing
	}

	public VerificationResult.RESULT check(IProgressMonitor monitor) {

		final File fileinput = getFileInput(model, "dsl.why");

		if (monitor.isCanceled()) {
			return VerificationResult.RESULT.CANCELLED;
		}

		if (fileinput == null) {
			return VerificationResult.RESULT.FAILED;
		}

		filePath = fileinput.getAbsolutePath();
		return check(fileinput, monitor);
	}

	private VerificationResult.RESULT check(File fileinput, IProgressMonitor monitor) {

		monitor.setTaskName("Calling " + prover);

		final long start = System.currentTimeMillis();

		try {
			process(monitor);
		} catch (final Throwable e) {
			e.printStackTrace();
			return VerificationResult.RESULT.FAILED;
		}

		final VerificationResult.RESULT result = getStatus();
		final long time = System.currentTimeMillis() - start;

		if (Properties.DEBUG) {
			if (getStatus() == VerificationResult.RESULT.VALID) {
				System.out.print("/" + getStatus());
				System.out.print("/" + prover);
				System.out.println("/" + (time - start) / 1000.0);
			}
		}

		return result;
	}

	@Override
	public void finalReport() {

	}

	// fixed-size LRU cache
	// private static Map<VerificationProperty, File> cache = lruCache(5000);

	public static synchronized File getFileInput(String model, String suffix) {
		// try cache first
		// File f = cache.get(input);
		// if (f != null)
		// return f;

		// pretty-print into a file
		try {
			final File fileinput = mktempFile("proof_file", suffix);
			fileinput.deleteOnExit();
			FileUtil.setFileContents(model, fileinput);
			// cache.put(input, fileinput);
			return fileinput;
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}

	}

	private static File mktempFile(String prefix, String suffix) throws IOException {
		return File.createTempFile(prefix, suffix, null);
	}

	/*
	 * @SuppressWarnings("serial") private static <K, V> Map<K, V> lruCache(final
	 * int maxSize) { return new LinkedHashMap<K, V>(maxSize * 4 / 3, 0.75f, true) {
	 * 
	 * @Override protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
	 * return size() > maxSize; } }; }
	 */
}
