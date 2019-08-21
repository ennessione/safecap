package uk.ac.ncl.safecap.scitus.base;

import java.io.Serializable;

import org.eclipse.emf.ecore.EObject;

public abstract class InstanceEvent implements Serializable {
	private static final long serialVersionUID = 2269187957768835990L;
	protected EObject subject;

	public InstanceEvent(EObject subject) {
		this.subject = subject;
	}

	public EObject getSubject() {
		return subject;
	}

}
