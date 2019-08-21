package uk.ac.ncl.prime.sim.lang.mb;

import java.util.Map;

public interface ISolverCallback {
	void solved(Map<String, Object> solution, int index);
}
