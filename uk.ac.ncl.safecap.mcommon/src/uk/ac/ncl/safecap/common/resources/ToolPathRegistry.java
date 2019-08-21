package uk.ac.ncl.safecap.common.resources;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class ToolPathRegistry {
	private static ToolPathRegistry registryInstance;

	public static synchronized ToolPathRegistry getInstance() {
		if (registryInstance == null) {
			registryInstance = new ToolPathRegistry();
			registryInstance.loadFromPreferences();
		}
		return registryInstance;
	}

	private final Map<String, String> _registry = new HashMap<>();
	private String _accessedNull;

	public synchronized void put(String name, String path) {
		_registry.put(name, path);
	}

	public String get(String name) {
		return _registry.get(name);
	}

	public String getReplaced(String name) {
		String bu = _registry.get(name);
		if (bu != null) {
			bu = bu.replace("\\", "\\\\");
		} else {
			_accessedNull = name;
		}
		return bu;
	}

	public String getNullAccessedAndClean() {
		final String bu = _accessedNull;
		_accessedNull = null;
		return bu;
	}

	public synchronized void remove(String name) {
		_registry.remove(name);
	}

	public Map<String, String> getAll() {
		return new HashMap<>(_registry);
	}

	public synchronized void loadFromPreferences() {
		final Preferences preferences = InstanceScope.INSTANCE.getNode("uk.ac.ncl.safecap.gui.toolpaths");
		final Preferences sub2 = preferences.node("list");
		final String str = sub2.get("list", "");
		if (str.length() > 0) {
			final Preferences sub1 = preferences.node("table");
			final String[] names = str.split(":");
			for (final String name : names) {
				if (name.length() > 0) {
					final String path = sub1.get(name, "");
					if (path.length() > 0) {
						_registry.put(name, path);
					}
				}
			}
		}

		_accessedNull = null;
	}

	public synchronized void saveToPreferences() {
		try {
			final Preferences preferences = InstanceScope.INSTANCE.getNode("uk.ac.ncl.safecap.gui.toolpaths");
			final Preferences sub1 = preferences.node("table");
			sub1.clear();
			final StringBuilder sb = new StringBuilder();
			for (final String name : _registry.keySet()) {
				sub1.put(name, _registry.get(name));
				sb.append(name);
				sb.append(":");
			}

			final Preferences sub2 = preferences.node("list");
			sub2.clear();
			sub2.put("list", sb.toString());
			preferences.flush();
		} catch (final BackingStoreException e2) {
			e2.printStackTrace();
		}
	}
}
