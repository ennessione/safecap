package uk.ac.ncl.safecap.xdata.provers;

public class DefaultContextProvider implements IContextProvider {
	public static final DefaultContextProvider INSTANCE = new DefaultContextProvider();

	public static final String[] ALL_THEORIES = {
			// Integers and booleans
			"int.Int", "bool.Bool", "int.EuclideanDivision",
			// Set-theoretic definitions
			"set.Set", "set.SetComprehension", "set.Fset", "settheory.Interval", "settheory.PowerSet", "settheory.Relation",
			"settheory.Image", "settheory.InverseDomRan", "settheory.Function", "settheory.PowerSet", "settheory.PowerRelation",
			// Event-B axiomatization
			"eventb.Prods", "eventb.FuncRel", "eventb.SetProp", "eventb.SetRelMod", "eventb.Extras", "eventb.Prods" };

	private DefaultContextProvider() {
	}

	@Override
	public void addHeader(StringBuilder sb) {
		for (final String theory : ALL_THEORIES) {
			sb.append("\tuse import " + theory + "\n");
		}
	}

}
