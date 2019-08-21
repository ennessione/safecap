package uk.ac.ncl.safecap.gui.verification;

import java.util.ArrayList;
import java.util.List;

public class VerificationToolRegistry {
	private static VerificationToolRegistry _instance;

	public static VerificationToolRegistry getInstance() {
		if (_instance == null) {
			_instance = new VerificationToolRegistry();
		}
//        _instance.updateScripts();
		return _instance;
	}

	public List<IVerificationTool> tools = new ArrayList<>();

//    public void updateScripts()
//    {
//        IVerificationProfile allProfile = VerificationToolPopulator.createVerificationProfileAll();
//        List<IVerificationTool> internalTools = allProfile.getTools();
//        tools.clear();
//        tools.addAll(internalTools);
//    }

	public IVerificationTool getTool(String name) {
		for (final IVerificationTool tool : tools) {
			if (tool.getName().equals(name)) {
				return tool;
			}
		}
		return null;
	}
}
