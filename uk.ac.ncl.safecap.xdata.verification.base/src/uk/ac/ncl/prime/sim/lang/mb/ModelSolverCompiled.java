package uk.ac.ncl.prime.sim.lang.mb;

import java.util.List;

import gnu.jel.CompiledExpression;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

/**
 * variables should be ordered first variable is compiled as set0[x0], second as
 * set1[x1] and so on
 */
public abstract class ModelSolverCompiled extends ModelBuilderBaseCompiled implements CompiledBase {
	public int x0;
	public Object[] set0;
	public int x1;
	public Object[] set1;
	public int x2;
	public Object[] set2;
	public int x3;
	public Object[] set3;
	public int x4;
	public Object[] set4;
	public int x5;
	public Object[] set5;
	public int x6;
	public Object[] set6;

//	private String[] fiNames; // array of free identifier names
//	public Object[] fi; // array of free identifier values

	private CompiledExpression test;
	protected Object[] context;

	public static Boolean quantifier(Object value) throws CLExecutionException {
		final CompiledBase cp = (CompiledBase) value;
		return (Boolean) cp.execute();
	}

	@SuppressWarnings("rawtypes")
	public static BSet setcomprehension(Object value) throws CLExecutionException {
		final CompiledBase cp = (CompiledBase) value;
		return (BSet) cp.execute();
	}

	public final static boolean equality(Object a, Object b) {
		return a.equals(b);
	}

	public final static boolean inequality(Object a, Object b) {
		return !a.equals(b);
	}

	public final static Integer mkint(int value) {
		return new Integer(value); // caching?
	}

	public final static int toInt(Object value) {
		return ((Integer) value).intValue();
	}

	public final static double toDbl(Object value) {
		return ((Double) value).doubleValue();
	}

	@SuppressWarnings("rawtypes")
	public final static BSet toSet(Object value) {
		return (BSet) value;
	}

	@SuppressWarnings("rawtypes")
	public final static BSeq toSeq(Object value) {
		return (BSeq) value;
	}

	public ModelSolverCompiled(SDARuntimeExecutionContext ctx, CLExpression predicate, List<CLParameter> variables)
			throws CLExecutionException, InvalidSetOpException {
		super(ctx.getRootContext(), predicate, variables);
		context = new Object[] { ctx.getFastRuntimeContext(), this };
		assert variables.size() < 8;
		setSolution(SOLUTION.UNKNOWN);
	}

	public boolean evalForEmptyIndices() throws Throwable {
		return test.evaluate_boolean(context);
	}

	public void csolve0() throws Throwable {
		for (x0 = 0; x0 < set0.length; x0++) {
			if (test.evaluate_boolean(context)) {
				if (solutionFound(x0)) {
					return;
				}
			}
		}
	}

	public void csolve1() throws Throwable {
		for (x0 = 0; x0 < set0.length; x0++) {
			for (x1 = 0; x1 < set1.length; x1++) {
				if (test.evaluate_boolean(context)) {
					if (solutionFound(x0, x1)) {
						return;
					}
				}
			}
		}
	}

	public void csolve2() throws Throwable {
		for (x0 = 0; x0 < set0.length; x0++) {
			for (x1 = 0; x1 < set1.length; x1++) {
				for (x2 = 0; x2 < set2.length; x2++) {
					if (test.evaluate_boolean(context)) {
						if (solutionFound(x0, x1, x2)) {
							return;
						}
					}
				}
			}
		}
	}

	public void csolve3() throws Throwable {
		for (x0 = 0; x0 < set0.length; x0++) {
			for (x1 = 0; x1 < set1.length; x1++) {
				for (x2 = 0; x2 < set2.length; x2++) {
					for (x3 = 0; x3 < set3.length; x3++) {
						if (test.evaluate_boolean(context)) {
							if (solutionFound(x0, x1, x2, x3)) {
								return;
							}
						}
					}
				}
			}
		}

	}

	public void csolve4() throws Throwable {
		for (x0 = 0; x0 < set0.length; x0++) {
			for (x1 = 0; x1 < set1.length; x1++) {
				for (x2 = 0; x2 < set2.length; x2++) {
					for (x3 = 0; x3 < set3.length; x3++) {
						for (x4 = 0; x4 < set4.length; x4++) {
							if (test.evaluate_boolean(context)) {
								if (solutionFound(x0, x1, x2, x3, x4)) {
									return;
								}
							}
						}
					}
				}
			}
		}
	}

	public void csolve5() throws Throwable {
		for (x0 = 0; x0 < set0.length; x0++) {
			for (x1 = 0; x1 < set1.length; x1++) {
				for (x2 = 0; x2 < set2.length; x2++) {
					for (x3 = 0; x3 < set3.length; x3++) {
						for (x4 = 0; x4 < set4.length; x4++) {
							for (x5 = 0; x5 < set5.length; x5++) {
								if (test.evaluate_boolean(context)) {
									if (solutionFound(x0, x1, x2, x3, x4, x5)) {
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void csolve6() throws Throwable {
		for (x0 = 0; x0 < set0.length; x0++) {
			for (x1 = 0; x1 < set1.length; x1++) {
				for (x2 = 0; x2 < set2.length; x2++) {
					for (x3 = 0; x3 < set3.length; x3++) {
						for (x4 = 0; x4 < set4.length; x4++) {
							for (x5 = 0; x5 < set5.length; x5++) {
								for (x6 = 0; x6 < set6.length; x6++) {
									if (test.evaluate_boolean(context)) {
										if (solutionFound(x0, x1, x2, x3, x4, x5, x6)) {
											return;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected abstract boolean solutionFound(int... index) throws CLExecutionException;

	public void prepare() throws CLNonExecutableException {
		try {
			buildIndices();
			test = getPredicate().prepareCompiled(getTypingContext().getRuntimeExecutionContext());
		} catch (final Throwable e) {
			test = null;
			throw new CLNonExecutableException(getPredicate());
		}
	}

	@Override
	public void solve() throws CLExecutionException, InvalidSetOpException {
		try {
			super.definate = false;

			switch (getVariables().size()) {
			case 1:
				set0 = members[0];
				csolve0();
				super.definate = true;
				break;
			case 2:
				set0 = members[0];
				set1 = members[1];
				// long start = System.currentTimeMillis();
				csolve1();
				// long done = System.currentTimeMillis();
				// System.out.println("csolve1 (" + members[0].length + "x" + members[1].length
				// +") time: " + (done - start));
				super.definate = true;
				break;
			case 3:
				set0 = members[0];
				set1 = members[1];
				set2 = members[2];
				csolve2();
				super.definate = true;
				break;
			case 4:
				set0 = members[0];
				set1 = members[1];
				set2 = members[2];
				set3 = members[3];
				csolve3();
				super.definate = true;
				break;
			case 5:
				set0 = members[0];
				set1 = members[1];
				set2 = members[2];
				set3 = members[3];
				set4 = members[4];
				csolve4();
				super.definate = true;
				break;
			case 6:
				set0 = members[0];
				set1 = members[1];
				set2 = members[2];
				set3 = members[3];
				set4 = members[4];
				set5 = members[5];
				csolve5();
				super.definate = true;
				break;
			case 7:
				set0 = members[0];
				set1 = members[1];
				set2 = members[2];
				set3 = members[3];
				set4 = members[4];
				set5 = members[5];
				set6 = members[6];
				csolve6();
				super.definate = true;
				break;
			}
		} catch (final Throwable e) {
			super.definate = false;
			setSolution(SOLUTION.UNKNOWN);
			// e.printStackTrace();
		}

	}
}
