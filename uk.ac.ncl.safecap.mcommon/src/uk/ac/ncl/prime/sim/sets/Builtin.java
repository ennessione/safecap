package uk.ac.ncl.prime.sim.sets;

public class Builtin {
	public static final int MIN_INT = -10;
	public static final int MAX_INT = 250;

	public static final BSet<Integer> IntSet = BSet.mkRange(MIN_INT, MAX_INT);
	public static final BSet<Integer> NatSet = BSet.mkRange(0, MAX_INT);
	public static final BSet<Integer> Nat1Set = BSet.mkRange(1, MAX_INT);
	@SuppressWarnings("unchecked")
	public static final BSet<Boolean> BoolSet = BSet.BSet_SETC(Boolean.TRUE, Boolean.FALSE);
}
