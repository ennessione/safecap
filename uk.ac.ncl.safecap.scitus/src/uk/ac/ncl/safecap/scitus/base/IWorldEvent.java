package uk.ac.ncl.safecap.scitus.base;

public interface IWorldEvent {
	Object happens();

	boolean fire(Object arg);
}
