package uk.ac.ncl.safecap.scitus.base;

public abstract class Progression {
	protected IActor actor;

	public Progression() {
	}

	Progression(IActor actor) {
		this.actor = actor;
	}

	public IActor getActor() {
		return actor;
	}

}
