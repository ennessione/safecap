package uk.ac.ncl.safecap.scitus.types;

import java.util.ArrayList;

import uk.ac.ncl.safecap.scitus.base.INothing;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.SpeedBound;

public class USeq<T> extends ArrayList<T> implements INothing {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7767020381723242139L;

	public USeq() {
		super();
		System.out.println("xxcc:" + new TrainEvent(25.0, new SpeedBound(25f, "hello")));
	}

	public USeq<T> _add(T e) {
		super.add(e);
		return this;
	}
}
