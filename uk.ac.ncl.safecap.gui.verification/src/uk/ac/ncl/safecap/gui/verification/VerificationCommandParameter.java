package uk.ac.ncl.safecap.gui.verification;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;

public class VerificationCommandParameter implements IParameterValues {
	@Override
	public Map getParameterValues() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("main", "main");
		return map;
	}

}
