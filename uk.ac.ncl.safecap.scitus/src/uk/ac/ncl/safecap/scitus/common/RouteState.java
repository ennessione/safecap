package uk.ac.ncl.safecap.scitus.common;

import java.io.Serializable;

public class RouteState implements Serializable {
	private static final long serialVersionUID = 660641227724608313L;

	private int aspect = 0;

	public static int RED = 0;

	public RouteState(int initial_aspect) {
		aspect = initial_aspect;
	}

	public boolean isProceedable() {
		return aspect > 0;
	}

	public int getAspect() {
		return aspect;
	}

	public void setAspect(int aspect) {
		this.aspect = aspect;
	}

	@Override
	public String toString() {
		return "A" + aspect;
	}
}
