package uk.ac.ncl.safecap.gui.verification;

import java.util.ArrayList;
import java.util.List;

public class ListVerificationProfile implements IVerificationProfile {
	public String name;
	public List<IVerificationTool> tools = new ArrayList<>();

	public ListVerificationProfile(List<IVerificationTool> tools) {
		this.tools.addAll(tools);
	}

	public ListVerificationProfile(String name, List<IVerificationTool> tools) {
		this.tools.addAll(tools);
		this.name = name;
	}

	@Override
	public List<IVerificationTool> getTools() {
		return tools;
	}

	@Override
	public String getName() {
		return name;
	}
}
