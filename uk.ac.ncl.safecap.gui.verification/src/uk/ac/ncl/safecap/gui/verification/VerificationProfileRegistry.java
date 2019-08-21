package uk.ac.ncl.safecap.gui.verification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class VerificationProfileRegistry {
	private static VerificationProfileRegistry _instance;

	public static VerificationProfileRegistry getInstance() {
		if (_instance == null) {
			_instance = new VerificationProfileRegistry();
		}
		return _instance;
	}

	public List<IVerificationProfile> profiles = new ArrayList<>();

	public IVerificationProfile get(String name) {
		for (final IVerificationProfile profile : profiles) {
			if (profile.getName().equals(name)) {
				return profile;
			}
		}
		return null;
	}

	public void loadFromPrefs() {
		final VerificationToolRegistry toolRegistry = VerificationToolRegistry.getInstance();
		// toolRegistry.updateScripts();

		profiles.clear();

		try {
			final Preferences preferences = InstanceScope.INSTANCE.getNode("uk.ac.ncl.safecap.gui.verification.Profiles");
			final Preferences profilesNode = preferences.node("profilesList");
			final String[] names = profilesNode.keys();
			for (final String profileName : names) {
				final List<IVerificationTool> tools = new ArrayList<>();
				final String[] toolNames = profilesNode.get(profileName, "").split(":");
				for (final String toolName : toolNames) {
					final IVerificationTool tool = toolRegistry.getTool(toolName);
					if (tool != null) {
						tools.add(tool);
					} else {
						tools.add(new NonExistentVerificationTool(toolName));
					}
				}
				profiles.add(new ListVerificationProfile(profileName, tools));
			}
		} catch (final BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public void saveToPrefs() {
		try {
			final Preferences preferences = InstanceScope.INSTANCE.getNode("uk.ac.ncl.safecap.gui.verification.Profiles");
			final Preferences profilesNode = preferences.node("profilesList");
			profilesNode.clear();
			for (final IVerificationProfile profile : profiles) {
				final List<IVerificationTool> tools = profile.getTools();
				final StringBuilder sb = new StringBuilder();
				for (final Iterator<IVerificationTool> iter = tools.iterator(); iter.hasNext();) {
					final IVerificationTool tool = iter.next();
					sb.append(tool.getName());
					if (iter.hasNext()) {
						sb.append(":");
					}
				}
				profilesNode.put(profile.getName(), sb.toString());
			}
			preferences.flush();
		} catch (final BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
