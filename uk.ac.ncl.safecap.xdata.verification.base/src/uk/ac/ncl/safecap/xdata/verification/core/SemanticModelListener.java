package uk.ac.ncl.safecap.xdata.verification.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementHandle;
import org.eclipse.sapphire.ElementList;
import org.eclipse.sapphire.Event;
import org.eclipse.sapphire.FilteredListener;
import org.eclipse.sapphire.Listener;
import org.eclipse.sapphire.Property;
import org.eclipse.sapphire.ValuePropertyContentEvent;

public abstract class SemanticModelListener {
	private final Map<Object, Listener> subscriptions;
	private final Set<String> propertyNames;

	private final Listener propertyListener;

	public SemanticModelListener(Collection<String> names) {
		subscriptions = new HashMap<>();
		propertyNames = new HashSet<>(names);

		propertyListener = new FilteredListener<ValuePropertyContentEvent>() {
			@Override
			protected void handleTypedEvent(final ValuePropertyContentEvent event) {
				// semanticReaction(event.property());
			}
		};

	}

	public void subscribeTopLevel(ElementList<?> x) {
		subscribeList(x);
		for (final Element y : x) {
			subscribe(y);
		}
	}

	public void subscribe(ElementList<?> x) {
		for (final Element y : x) {
			subscribe(y);
		}
	}

	@SuppressWarnings("rawtypes")
	public void subscribe(Element y) {
		// System.out.println("... subscribe " + y);
		for (final Property p : y.properties()) {
			if (p instanceof ElementList) {
				subscribeList((ElementList<?>) p);
				subscribe((ElementList<?>) p);
			} else {
				if (propertyNames.contains(p.name())) {
					subscribeProperty(p);
				}
				if (p instanceof ElementHandle) {
					final ElementHandle eh = (ElementHandle) p;
					if (eh.content() != null) {
						subscribe(eh.content());
					}
				}
			}
		}
	}

	private void subscribeProperty(Property p) {
		if (subscriptions.containsKey(p)) {
			return;
		}
		subscriptions.put(p, propertyListener);
		p.attach(propertyListener);
	}

	private void subscribeList(final ElementList<?> x) {
		if (!subscriptions.containsKey(x)) {
			final Listener listListener = new Listener() {
				@Override
				public void handle(final Event event) {
					// System.out.println("<<<< !!!! react in list with " + event.toString());
					subscribe(x);
				}
			};
			x.attach(listListener);
			subscriptions.put(x, listListener);
			// System.out.println("<<<< subscribe list " + x.name());
		}
	}

	public abstract void semanticReaction(Property property);
}
