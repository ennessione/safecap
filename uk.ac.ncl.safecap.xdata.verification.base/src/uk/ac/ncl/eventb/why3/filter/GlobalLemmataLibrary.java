package uk.ac.ncl.eventb.why3.filter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public class GlobalLemmataLibrary implements Comparator<RankedLemma> {
	private static GlobalLemmataLibrary cachedInstance = null;
	private static final List<String> requiredTheories = Arrays.asList(new String[] { "Set", "Relation", "MaxSet", "Bool" });

	private Map<String, Why3Symbol> operators;
	private Map<String, Integer> operatorMap;
	private Map<Integer, String> operatorMapInverse;
	private Set<Why3FilterableLemma> lemmata;
	private Map<Integer, Double> bitWeight;
	private int operatorIndex = 1;

	private void defineKnownOperators() {
		operators = new HashMap<>();
		operatorMap = new HashMap<>();
		operatorMapInverse = new HashMap<>();
		defineOperator("true");
		defineOperator("false");
		defineOperator("+", "Int");
		defineOperator("-", "Int");
		defineOperator("bcomp", "BackwardComposition");
		defineOperator("boolean", "Boolean");
		defineOperator("inter", "Intersection");
		defineOperator("union", "Union");
		defineOperator("inverse", "Inverse");
		defineOperator("cprod", "CartesianProduct");
		defineOperator("bsetc", "SetComprehension");
		defineOperator("div", "EuclideanDivision");
		defineOperator("<|", "DomainRestriction");
		defineOperator("<<|", "DomainSubtraction");
		defineOperator("dprod", "DirectProduct");
		defineOperator("empty", "EmptySet");
		defineOperator("is_empty", "EmptySet");
		defineOperator("=");
		defineOperator("exists");
		defineOperator("power", "Exponentiation");
		defineOperator("fcomp", "ForwardComposition");
		defineOperator("forall");
		defineOperator("apply", "Apply");
		defineOperator(">=", "Int");
		defineOperator(">", "Int");
		defineOperator("mem", "Membership");
		defineOperator("integer", "Integer");
		defineOperator("card", "Cardinality");
		defineOperator("dom", "Domain");
		defineOperator("ran", "Range");
		defineOperator("finite", "Finite");
		defineOperator("id", "Identity");
		defineOperator("ginter", "GeneralizedIntersection");
		defineOperator("gunion", "GeneralizedUnion");
		defineOperator("max", "Maximum");
		defineOperator("min", "Minimum");
		defineOperator("partition", "Partition");
		defineOperator("prj1", "Projection1");
		defineOperator("prj2", "Projection2");
		defineOperator("/\\");
		defineOperator("\\/");
		defineOperator("<=", "Int");
		defineOperator("<", "Int");
		defineOperator("<->");
		defineOperator("->");
		defineOperator(",");
		defineOperator("mod", "EuclideanDivision");
		defineOperator("*", "Int");
		defineOperator("natural", "Natural");
		defineOperator("bnatural1", "Natural1");
		defineOperator("not");
		defineOperator("<+", "Overriding");
		defineOperator("+->", "PartialFunction");
		defineOperator("-->", "TotalFunction");
		defineOperator("<<->", "TotalRelation");
		defineOperator("<->>", "SurjectiveRelation");
		defineOperator("<<->>", "TotalSurjectiveRelation");
		defineOperator(">+>", "PartialInjection");
		defineOperator(">->", "TotalInjection");
		defineOperator("+->>", "PartialSurjection");
		defineOperator("-->>", "TotalSurjection");
		defineOperator(">->>", "Bijection");
		defineOperator("pprod", "ParallelProduct");
		defineOperator("|>", "RangeRestriction");
		defineOperator("|>>", "RangeSubtraction");
		defineOperator("pow", "PowerSet");
		defineOperator("pow1", "Power1Set");
		defineOperator("image", "Image");
		defineOperator("singleton", "Singleton");
		defineOperator("add", "ElementAddition");
		defineOperator("remove", "ElementRemove");
		defineOperator("diff", "Difference");
		defineOperator("times", "Times");
		defineOperator("one", "Int");
		defineOperator("zero", "Int");
		defineOperator("subset", "Subset");
		defineOperator("maxset", "MaxSet");
		defineOperator("mk", "Interval");
		defineOperator("==", "Equality");
		defineOperator("unMinus", "UnaryMinus");
		defineOperator("abs", "Abs");
		defineOperator("subsetprop", "ProperSubset");
		defineOperator("comprehension", "SetComprehension");
		defineOperator("@");
		defineOperator(",");
		defineOperator("True", "SetComprehension");
		defineOperator("map", "Map");
		defineOperator("filter", "Filter");
		defineOperator("<>");

	}

	public static GlobalLemmataLibrary getInstance() {
		if (cachedInstance == null) {
			cachedInstance = new GlobalLemmataLibrary();
		}

		return cachedInstance;

	}

	@Override
	public int compare(RankedLemma arg0, RankedLemma arg1) {
		return (int) Math.signum(arg0.getRank() - arg1.getRank());
	}

	private void defineOperator(String name, String theory) {
		operators.put(name, new Why3Symbol(name, theory));
		operatorMap.put(name, operatorIndex);
		operatorMapInverse.put(operatorIndex, name);
		operatorIndex++;
	}

	private void defineOperator(String name) {
		operators.put(name, new Why3Symbol(name));
		operatorMap.put(name, operatorIndex);
		operatorMapInverse.put(operatorIndex, name);
		operatorIndex++;
	}

	public Set<String> getOperators() {
		return operators.keySet();
	}

	public int mapWhy3Operator(String name) {
		final Integer index = operatorMap.get(name.trim());
		if (index == null) {
			System.err.println("unknown why3 operator: \"" + name + "\"");
			return 0;
		}

		return index;
	}

	public String inverseMapWhy3Operator(int code) {
		final String name = operatorMapInverse.get(code);
		if (name == null) {
			System.err.println("unknown why3 operator code: \"" + code + "\"");
			return "?";
		}

		return name;
	}

	public Set<String> getOperatorTheories(String operator) {
		return operators.get(operator).getDependentTheories();
	}

	public GlobalLemmataLibrary() {
		lemmata = new HashSet<>(500);
		bitWeight = new HashMap<>(500);

		defineKnownOperators();

		try {
			// Why3LemmaReader reader = new Why3LemmaReader();
			final Why3LemmaReaderBuiltIn reader = new Why3LemmaReaderBuiltIn();
			final Set<Why3RawSexprLemma> rawResult = reader.read();
			if (rawResult == null) {
				System.out.println("Failed reading lemmata");
				return;
			}
			for (final Why3RawSexprLemma rawLemma : rawResult) {
				final Why3FilterableLemma lemma = new Why3FilterableLemma(this, rawLemma);
				lemmata.add(lemma);
			}

			computeBitWeights();

			System.out.println("Lemmata: " + lemmata);
			System.out.println("Weights: " + bitWeight);

			cachedInstance = this;
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public Set<String> getTheoryDependencies(FilterContext fc) {
		final Set<String> result = new HashSet<>();
		result.addAll(requiredTheories);

		for (final CLExpression p : fc.getHypothesis()) {
			addDependencies(result, fc.getHypothesisFilterDepth(p));
		}

		addDependencies(result, fc.getGoalFilterDepth());

		addDependencies(result, fc.getExtraLemmataFilter());

		return result;
	}

	private void addDependencies(Set<String> set, SparseBitSet bitset) {
		int i = -1;
		while ((i = bitset.nextSetBit(i + 1)) != -1) {
			final int opcode = i & 0b1111111;
			if (opcode != 0) {
				final Set<String> theories = getOperatorTheories(inverseMapWhy3Operator(opcode));
				if (theories != null) {
					set.addAll(theories);
				}
			}
		}
	}

	public Set<Why3FilterableLemma> getLemmata() {
		return lemmata;
	}

	public double getBitWeight(int index) {
		if (bitWeight.containsKey(index)) {
			return bitWeight.get(index);
		} else {
			return 0.0;
		}
	}

	private void computeBitWeights() {
		for (final IFilterable f : lemmata) {
			int i = -1;
			while ((i = f.getBitSet().nextSetBit(i + 1)) != -1) {
				if (bitWeight.containsKey(i)) {
					bitWeight.put(i, bitWeight.get(i) + 1.0);
				} else {
					bitWeight.put(i, 1.0);
				}
			}
		}

//		// normalize;
//		double sum = 0;
//		for(Integer i: bitWeight.keySet())
//			sum += bitWeight.get(i);

		for (final Integer i : bitWeight.keySet()) {
			bitWeight.put(i, 1 / Math.log(Math.E + bitWeight.get(i)));
		}

	}

}
