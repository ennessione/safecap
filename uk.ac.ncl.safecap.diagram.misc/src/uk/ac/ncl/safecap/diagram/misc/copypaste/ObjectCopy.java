package uk.ac.ncl.safecap.diagram.misc.copypaste;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;

public class ObjectCopy {

	EObject source_object;
	EObject copy;
	EditPart source_part;

	public ObjectCopy(EObject obj, EditPart prt) {
		source_object = obj;
		source_part = prt;
		copy = EcoreUtil.copy(obj);
	}

	public EObject getSourceObject() {
		return source_object;
	}

	public EObject getCopy() {
		return copy;
	}

	public EditPart getSourcePart() {
		return source_part;
	}

}
