package uk.ac.ncl.safecap.gui.verification;

import java.util.Hashtable;
import java.util.Map;

import uk.ac.ncl.safecap.gui.verification.IVerificationTool.VerificationResult;

/**
 * Contains results of previously run verification tools
 * 
 * @author Ilya Lopatkin
 *
 */
public class VerificationContext {
	private final Map<IVerificationTool, Integer> _results = new Hashtable<>();

	/**
	 * Gets the result returned by tool
	 * 
	 * @param tool Verification tool to be enquired
	 * @return tool result. One of values enumerated in
	 *         IVerificationTool.VerificationResult. Returns RESULT_NONE if tool has
	 *         not run yet.
	 */
	public int getToolResult(IVerificationTool tool) {
		if (tool == null) {
			return VerificationResult.RESULT_NONE;
		}
		final Integer res = _results.get(tool);
		if (res != null) {
			return res.intValue();
		} else {
			return VerificationResult.RESULT_NONE;
		}
	}

	/**
	 * Gets the result returned by tool
	 * 
	 * @param toolName Name of verification tool
	 * @return tool result. One of values enumerated in
	 *         IVerificationTool.VerificationResult. Returns RESULT_NONE if tool has
	 *         not run yet.
	 */
	public int getToolResult(String toolName) {
		if (toolName == null) {
			return VerificationResult.RESULT_NONE;
		}
		for (final IVerificationTool tool : _results.keySet()) {
			final String name = tool.getName();
			if (name != null && name.equals(toolName)) {
				return getToolResult(tool);
			}
		}
		return VerificationResult.RESULT_NONE;
	}

	void setToolResult(IVerificationTool tool, int result) {
		_results.put(tool, result);
	}
}
