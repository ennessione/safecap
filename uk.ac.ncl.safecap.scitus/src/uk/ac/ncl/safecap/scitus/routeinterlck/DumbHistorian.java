package uk.ac.ncl.safecap.scitus.routeinterlck;

import uk.ac.ncl.safecap.scitus.base.IActor;
import uk.ac.ncl.safecap.scitus.base.World;

public class DumbHistorian extends Historian {

	public DumbHistorian(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	final public void sees(IActor actor, String data) {
	}

	@Override
	final public void sees(String data) {
	}
}
