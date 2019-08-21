package uk.ac.ncl.safecap.capacity.ranking;

import uk.ac.ncl.safecap.scitus.base.World;

public abstract class SolutionRankingProvider {
	private final String name;

	protected SolutionRankingProvider(String name) {
		this.name = name;
	}

	/**
	 * The way this ranking calls itself (i.e., for gui integration)
	 * 
	 * @return a descriptive name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Associates a real number with a given simulation output. The number is
	 * used to build a total ordering of a set of solutions
	 * 
	 * The following properties must hold: 1) Double.NEGATIVE_INIFINITY for any
	 * simulation deemed broken, improbable or empty 2) the same ranking value
	 * for same simulation outputs 3) negative values for solutions deemed worse
	 * than 'basis' 4) positive values for solutions deemed better than 'basis'
	 * 5) 0 if the solution is only as good as the basis
	 * 
	 * @param basis
	 *            a benchmark (unsupervised) solution defining the 0 of the
	 *            ranking scale
	 * @param world
	 *            a proposed solution to be ranked
	 * @return ranking value
	 */
	public abstract double rank(World basis, World world);

}
