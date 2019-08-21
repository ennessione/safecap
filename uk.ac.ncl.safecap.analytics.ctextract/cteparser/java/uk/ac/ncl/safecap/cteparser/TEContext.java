package uk.ac.ncl.safecap.cteparser;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.cteparser.TEMarker;
import uk.ac.ncl.safecap.cteparser.syntree;

public class TEContext {
	private List<TEMarker> errors;
	private List<TEMarker> warnings;
	private List<TEMarker> notes;

	public TEContext() {
		errors = new ArrayList<TEMarker>();
		warnings = new ArrayList<TEMarker>();
		notes = new ArrayList<TEMarker>();
	}

	public void clear() {
		errors.clear();
		warnings.clear();
		notes.clear();
	}
	
	public void addError(TEMarker marker) {
		errors.add(marker);
	}

	public void addError(syntree s, String message) {
		errors.add(mkMarker(s, message));
	}
	
	public void addWarning(TEMarker marker) {
		warnings.add(marker);
	}

	public void addWarning(syntree s, String message) {
		warnings.add(mkMarker(s, message));
	}	
	
	public void addNote(TEMarker marker) {
		notes.add(marker);
	}

	public void addNote(syntree s, String message) {
		notes.add(mkMarker(s, message));
	}	

	private static TEMarker mkMarker(syntree s, String message) {
		TEMarker marker = new TEMarker();
		marker.column = s.getStartCol();
		marker.line = s.getStartLine();
		marker.start = s.getStart();
		marker.end = s.getEnd();
		marker.message = message;
		return marker;
	}	
	
	public List<TEMarker> getErrors() {
		return errors;
	}

	public List<TEMarker> getWarnings() {
		return warnings;
	}

	public List<TEMarker> getNotes() {
		return notes;
	}



}
