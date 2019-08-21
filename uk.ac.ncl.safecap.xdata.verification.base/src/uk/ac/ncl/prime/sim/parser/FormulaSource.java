package uk.ac.ncl.prime.sim.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormulaSource {
	private final List<FormulaMarking> errors;
	private ICLSourceMarker source;
	private String text;
	private List<IFormulaSourceListener> listeners;

	public FormulaSource(ICLSourceMarker source) {
		assert source != null;
		errors = new ArrayList<>();
		this.source = source;
	}

	public FormulaSource(String text) {
		assert text != null;
		errors = new ArrayList<>();
		this.text = text;
	}

	public ICLSourceMarker getSource() {
		return source;
	}

	public void setSource(ICLSourceMarker source) {
		this.source = source;

		if (source != null) {
			text = null;
		}
	}

	public String getText() {
		if (source != null) {
			return source.getText();
		} else {
			return text;
		}
	}

	public void setText(String text) {
		assert text != null;
		this.text = text;
		source = null;
	}

	public void setText(String text, int start, int end) {
		assert text != null;
		this.text = text;
		source = null;
	}

	public void clear() {
		errors.clear();
	}

	public void mark() {
		if (source != null) {
			mark(0, getText().length());
		}

	}

	public void mark(int start, int end) {
		if (source != null) {
			source.markStart(start, end - start);
			if (!errors.isEmpty()) {
//				System.out.println(source.getText() + ": " + errors.toString());
//				System.out.println(source.getText() + ": text=" + text);
//				System.out.println(source.getText() + ": source=" + source);
				for (final FormulaMarking fe : errors) {
					int beginning = fe.getStart() + start;
					int len = fe.getEnd() - fe.getStart();
					if (beginning < 0) {
						beginning = 0;
					}
					if (len < 0) {
						len = getText().length();
					} else if (len > getText().length()) {
						len = getText().length();
					}

					source.markError(beginning, Math.min(end, len), fe.getMessage());
				}
			}
		}

		broadcast();
	}

	public void add(FormulaMarking error) {
		errors.add(error);
	}

	public void add(int start, int end, String message) {
		errors.add(new FormulaMarking(start, end, message));
	}

	public void add(int start, int end, String message, String tag) {
		removeErrors(tag);
		errors.add(new FormulaMarking(start, end, message));
	}

	public void removeErrors(String tag) {
		final Set<FormulaMarking> toRemove = new HashSet<>();
		for (final FormulaMarking ft : errors) {
			if (tag.equals(ft.getTag())) {
				toRemove.add(ft);
			}
		}

		errors.removeAll(toRemove);
	}

	public List<FormulaMarking> getErrors() {
		return errors;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		for (final FormulaMarking fe : errors) {
			sb.append(fe.toString());
			sb.append("\n");
		}

		return sb.toString();
	}

	public void subscribe(IFormulaSourceListener formulaValidationService) {
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
		if (!listeners.contains(formulaValidationService)) {
			listeners.add(formulaValidationService);
		}
	}

	public void unsubscribe(IFormulaSourceListener formulaValidationService) {
		if (listeners != null) {
			listeners.remove(formulaValidationService);
		}
	}

	private void broadcast() {
		if (listeners != null) {
			for (final IFormulaSourceListener l : listeners) {
				l.react(this);
			}
		}
	}
}
