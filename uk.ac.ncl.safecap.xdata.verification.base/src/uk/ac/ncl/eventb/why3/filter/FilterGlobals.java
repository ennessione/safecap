package uk.ac.ncl.eventb.why3.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class FilterGlobals {
	private static final int maxOperatorCountLog2 = 7;

	public static boolean isFilterEnabled() {
		return Why3Properties.P_F_FILTER_LEMMA;
	}

	public static int getFilterDepth() {
		return Why3Properties.P_F_DEPTH;
	}

	public static int getFilterWidth() {
		return Why3Properties.P_F_WIDTH;
	}

	public static int getWidthDepthRatio() {
		return Why3Properties.P_F_RATIO;
	}

	public static String getCutOff() {
		return Why3Properties.P_F_CUTOFF;
	}

	public static int getCutOffArg() {
		return Why3Properties.P_F_CUTOFF_PAR;
	}

	public static String getTheoryFile() {
		return Why3Properties.P_WHY3_FILE;
	}

	public static void printFilterOptions(StringBuilder sb) {
		sb.append("lemma filter: ");
		sb.append(isFilterEnabled());
		sb.append("\n");
		sb.append("filter depth: ");
		sb.append(getFilterDepth());
		sb.append("\n");
		sb.append("filter width: ");
		sb.append(getFilterWidth());
		sb.append("\n");
		sb.append("width/depth ratio: ");
		sb.append(getWidthDepthRatio());
		sb.append("\n");
		sb.append("cutoff: ");
		sb.append(getCutOff());
		sb.append(" (");
		sb.append(getCutOffArg());
		sb.append(")\n");
	}

	public static List<Why3FilterableLemma> lemmatFilter(GlobalLemmataLibrary library, FilterContext filterContext) {
		final List<RankedLemma> ranked = new ArrayList<>();
		int count = 0;
		for (final Why3FilterableLemma l : library.getLemmata()) {
			final double rank = l.rank(filterContext);
			if (rank > 0) {
				ranked.add(new RankedLemma(rank, l));
				count++;
			}
		}

		Collections.sort(ranked, library);

		final List<Why3FilterableLemma> result = new ArrayList<>();

		if (getCutOff().equals("count")) {
			final int stop = Math.max(count - getCutOffArg(), 0);
			for (int i = count - 1; i >= stop; i--) {
				result.add(ranked.get(i).getLemma());
			}
		} else if (getCutOff().equals("percentage")) {
			final int par = getCutOffArg();
			assert par > 0 && par <= 100;
			final int count_top = count * par / 100;
			final int stop = Math.max(count - count_top, 0);
			for (int i = count - 1; i >= stop; i--) {
				result.add(ranked.get(i).getLemma());
			}
		} else if (getCutOff().equals("threshold")) {
			final double th = getCutOffArg() / 100.0;
			for (final RankedLemma rl : ranked) {
				if (rl.getRank() > th) {
					result.add(rl.getLemma());
				}
			}
		}

		return result;
	}

	public static void insertFilter(Stack<Integer> stack, SparseBitSet bitset) {
		final int depth = Math.min(stack.size(), getFilterDepth());

		int index = 0;
		for (int i = 0; i < depth; i++) {
			index += stack.get(stack.size() - i - 1) << maxOperatorCountLog2 * i;
			bitset.set(index);
		}
	}

	public static void insertFilter(Integer opcode, SparseBitSet bitset) {
		bitset.set(opcode);
	}

	public static void insertFilter(GlobalLemmataLibrary library, TreeNode<String> node, SparseBitSet bitset) {
		final int depth = Math.min(node.getChildren().size() + 1, getFilterWidth());

		int index = library.mapWhy3Operator(node.getData());

		for (int i = 0; i < depth - 1; i++) {
			index += library.mapWhy3Operator(node.getChildren().get(i).getData()) << maxOperatorCountLog2 * (i + 1);
		}

		bitset.set(index);
	}

	public static Set<List<String>> decodeFilterDepth(GlobalLemmataLibrary library, SparseBitSet bitset) {
		int i = -1;
		final Set<List<String>> result = new HashSet<>();
		while ((i = bitset.nextSetBit(i + 1)) != -1) {
			result.add(decodeDepth(library, i));
		}
		return result;
	}

	public static Set<TreeNode<String>> decodeFilterWidth(GlobalLemmataLibrary library, SparseBitSet bitset) {
		int i = -1;
		final Set<TreeNode<String>> result = new HashSet<>();
		while ((i = bitset.nextSetBit(i + 1)) != -1) {
			result.add(decodeWidth(library, i));
		}
		return result;
	}

	private static List<String> decodeDepth(GlobalLemmataLibrary library, int code) {
		final List<String> result = new ArrayList<>();
		for (int i = getFilterDepth() - 1; i >= 0; i--) {
			final int opcode = code >> maxOperatorCountLog2 * i & 0b1111111;
			if (opcode != 0) {
				result.add(library.inverseMapWhy3Operator(opcode));
			}
		}

		return result;
	}

	private static TreeNode<String> decodeWidth(GlobalLemmataLibrary library, int code) {
		int opcode = code & 0b1111111;
		final TreeNode<String> result = new TreeNode<>(library.inverseMapWhy3Operator(opcode));

		for (int i = 1; i <= getFilterWidth(); i++) {
			opcode = code >> maxOperatorCountLog2 * i & 0b1111111;
			if (opcode != 0) {
				result.addChild(library.inverseMapWhy3Operator(opcode));
			}
		}

		return result;
	}

}
