package uk.ac.ncl.eventb.why3.filter;

import java.util.Arrays;
import java.util.Collection;

import uk.ac.ncl.safecap.xdata.provers.TranslationException;

public interface IContextProvider {
	Collection<String> ALL_THEORIES = Arrays.asList(new String[] {
			// Integers and booleans
			"Int", "Bool", "Abs", "EuclideanDivision", "Set", "Membership", "Equality", "Subset", "EmptySet", "ElementAddition",
			"ElementRemove", "Union", "Intersection", "Difference",
			// "Choose",
			"Singleton", "Integer", "Natural", "Interval", "PowerSet", "Relation", "Image", "Inverse", "Domain", "Range", "PartialFunction",
			"TotalFunction", "Apply", "Times", "CartesianProduct", "DirectProduct", "ParallelProduct", "TotalRelation",
			"SurjectiveRelation", "TotalSurjectiveRelation", "PartialInjection", "PartialSurjection", "TotalInjection", "TotalSurjection",
			"Bijection", "ForwardComposition", "BackwardComposition", "ProperSubset", "Finite", "Minimum", "Maximum", "Power1Set",
			"Cardinality", "MaxSet", "DomainRestriction", "DomainSubtraction", "RangeRestriction", "RangeSubtraction", "Overriding",
			"GeneralizedUnion", "GeneralizedIntersection", "Partition", "SetComprehension", "Filter", "Map", "Natural1", "Boolean",
			"Identity", "Projection1", "Projection2", "UnaryMinus", "Exponentiation" });

	/**
	 * Inserts definition of theories on which the translation of some current
	 * condition depends on. These theories appear in the same output file and
	 * typically dynamically generated
	 * 
	 * @param sb string builder to write into
	 * @throws GenException
	 * @throws TranslationException
	 */
	Collection<Why3FilterableLemma> getInplaceLemmas();

	/**
	 * Inserts import clauses for theories on which the translation of some current
	 * condition depends on. These are statically defined theories shared among many
	 * conditions and placed in external files (/theories folder of why3)
	 * 
	 * @param sb
	 */
	Collection<String> getTheoryImports();

	FilterContext getFilterContext();
}
