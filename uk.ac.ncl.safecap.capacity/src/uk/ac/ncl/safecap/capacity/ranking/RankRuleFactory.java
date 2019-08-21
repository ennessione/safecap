package uk.ac.ncl.safecap.capacity.ranking;

public class RankRuleFactory {
	private static final String[] ids = { CompletionTimeRanking.ID };

	public static String[] getRankRules() {
		return ids;
	}

	public static SolutionRankingProvider makeRule(int index, String argument) {
		switch (index) {
		case 0:
			return new CompletionTimeRanking(argument);
		default:
			throw new RuntimeException("Invalid rule index " + index);
		}
	}
}
