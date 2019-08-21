package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TransitionCache {
	private final Map<File, TransitionContainer> cache;
	private final Collection<ITransitionFileListener> listeners;

	public TransitionCache() {
		cache = new HashMap<>();
		listeners = new ArrayList<>();
	}

	public void addListener(ITransitionFileListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public TransitionContainer get(File file) {
		try {
			if (cache.containsKey(file)) {
				return cache.get(file);
			}

			TransitionContainer container = null;
			if (file.exists()) {
				container = TransitionUtil.getTransitionContainerFromFile(file);
			}

			if (container == null) {
				container = new TransitionContainer();
			}

			cache.put(file, container);

			return container;
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	private File fromTransitionContainer(TransitionContainer container) {
		for (final Entry<File, TransitionContainer> e : cache.entrySet()) {
			if (e.getValue() == container) {
				return e.getKey();
			}
		}

		return null;
	}

	public void fireChange(File file, Object element) {
		for (final ITransitionFileListener l : listeners) {
			l.refresh(file, element);
		}
	}

	public void fireChange(File file) {
		for (final ITransitionFileListener l : listeners) {
			l.refresh(file, null);
		}
	}

	public void fireChange(TransitionContainer container) {
		final File file = fromTransitionContainer(container);

		if (file != null) {
			fireChange(file);
		}
	}

	public void fireChange(TransitionContainer container, Object element) {
		final File file = fromTransitionContainer(container);

		if (file != null) {
			fireChange(file, element);
		}
	}

	public void ivalidate(File file) {
		cache.remove(file);
	}

	public void removeListener(ITransitionFileListener listener) {
		listeners.remove(listener);

	}

}
