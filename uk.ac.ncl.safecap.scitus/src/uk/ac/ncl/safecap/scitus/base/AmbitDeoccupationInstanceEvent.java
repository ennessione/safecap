package uk.ac.ncl.safecap.scitus.base;

import safecap.model.Ambit;

public class AmbitDeoccupationInstanceEvent extends InstanceEvent {
	public AmbitDeoccupationInstanceEvent(Ambit ambit) {
		super(ambit);
	}

	public Ambit getAmbit() {
		return (Ambit) getSubject();
	}

	@Override
	public String toString() {
		return "Ambit " + getAmbit() + " freed";
	}

}
