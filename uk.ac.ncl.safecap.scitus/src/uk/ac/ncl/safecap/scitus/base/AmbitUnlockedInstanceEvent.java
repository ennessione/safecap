package uk.ac.ncl.safecap.scitus.base;

import safecap.model.Ambit;

public class AmbitUnlockedInstanceEvent extends InstanceEvent {
	public AmbitUnlockedInstanceEvent(Ambit ambit) {
		super(ambit);
	}

	public Ambit getAmbit() {
		return (Ambit) getSubject();
	}

	@Override
	public String toString() {
		return "AMBIT " + getAmbit() + " unlocked";
	}
}
