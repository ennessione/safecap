package uk.ac.ncl.safecap.capacity.ranking;

import uk.ac.ncl.safecap.scitus.base.World;

public class CompletionTimeRanking extends SolutionRankingProvider {
	public static final String ID = "Completion time ranking";
	private double offset = 0;

	protected CompletionTimeRanking(String arg) {
		super(ID);
		try {
			if (arg.trim().length() > 0) {
				offset = Integer.parseInt(arg);
			}
		} catch (final NumberFormatException e) {
			offset = 0;
		}
	}

	@Override
	public double rank(World basis, World world) {
		if (world.isDeadlocked()) {
			return Double.NEGATIVE_INFINITY;
		}

		return offset + (basis.getTime() - world.getTime());
	}

}
