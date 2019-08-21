package uk.ac.ncl.prime.sim.lang.mb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.typing.CLEnumType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.Builtin;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;

public abstract class ModelBuilderBaseCompiled extends ModelSolver {
	final static Integer[] INTEGER_SET = BSet.mkRange(Builtin.MIN_INT, Builtin.MAX_INT).asCollection()
			.toArray(new Integer[Builtin.MAX_INT - Builtin.MIN_INT + 1]);
	final static Boolean[] BOOLEAN_SET = new Boolean[] { Boolean.TRUE, Boolean.FALSE };

	protected boolean solvable, definate = true;
	protected Object[][] members;

	public ModelBuilderBaseCompiled(TypingContext typingContext, CLExpression predicate, List<CLParameter> variables) {
		super(typingContext, predicate, variables);
		members = new Object[variables.size()][];
		solvable = true;
		definate = true;

	}

	public ModelBuilderBaseCompiled(TypingContext typingContext, CLExpression predicate, Collection<String> variables) {
		super(typingContext, predicate, makeVars(typingContext, variables));
		members = new Object[variables.size()][];
		solvable = true;
		definate = true;
	}

	private static List<CLParameter> makeVars(TypingContext typingContext, Collection<String> vars) {
		final List<CLParameter> pars = new ArrayList<>();
		for (final String s : vars) {
			pars.add(new CLParameter(s, typingContext.getType(s)));
		}
		return pars;
	}

	@SuppressWarnings("rawtypes")
	void buildIndices() throws CLExecutionException, InvalidSetOpException {
		int i = 0;
		for (final CLParameter v : getVariables()) {
			boolean done = false;
			// final String name = v.getName();
			final CLType type = v.getTypeSafely(getTypingContext());
			final Object exactValue = inferredExactValue(getPredicate(), v.getName());
			if (exactValue != null) {
				if (exactValue instanceof BSet) {
					final BSet set = (BSet) exactValue;
					if (set.isEmpty()) {
						solvable = false;
						return;
					}
				}
				members[i] = new Object[] { exactValue };
				done = true;
			} else {
//				Object setValues = inferredSetValues(getPredicate(), name);
//				if (setValues instanceof BSet) {
//					BSet set = (BSet) setValues;
//					if (set.card() > 0) {
//						members[i] = set.asCollection().toArray();
//						done = true;
//					}
//				}
			}

			if (!done) {
				if (type == null) {
					solvable = false;
					return;
				} else if (type instanceof CLEnumType) {
					final CLEnumType enumType = (CLEnumType) type;
					buildMembers(i, enumType);
				} else if (type instanceof CLTypeAny) {
					final CLTypeAny any = (CLTypeAny) type;
					if (any.getBakedType() instanceof CLEnumType) {
						final CLEnumType enumType = (CLEnumType) any.getBakedType();
						buildMembers(i, enumType);
					} else if (any.getBakedType() == CLTypeBool.INSTANCE) {
						members[i] = BOOLEAN_SET;
					} else if (any.getBakedType() == CLTypeInteger.INSTANCE) {
						members[i] = INTEGER_SET;
						definate = false;
					} else {
						solvable = false;
						return;
					}
				} else if (type == CLTypeBool.INSTANCE) {
					members[i] = BOOLEAN_SET;
				} else if (type == CLTypeInteger.INSTANCE) {
					members[i] = INTEGER_SET;
					definate = false;
				} else {
					solvable = false;
					return;
				}
			}
			i++;
		}
	}

	private Object inferredSetValues(CLExpression expr, String v) {
		try {
			if (expr instanceof CLMultiExpression) {
				final CLMultiExpression multi = (CLMultiExpression) expr;
				if (multi.getTag() == uk.ac.ncl.prime.sim.lang.parser.alphabet.OP_AND) {
					for (final CLExpression e : multi.getParts()) {
						final Object set = inferredSetValues(e, v);
						if (set != null) {
							return set;
						}
					}
				}
			} else if (expr.getTag() == uk.ac.ncl.prime.sim.lang.parser.alphabet.OP_IN) {
				final CLBinaryExpression binary = (CLBinaryExpression) expr;
				if (binary.getLeft() instanceof CLIdentifier && binary.getRight().getBoundIdentifiers().isEmpty()) {
					final CLIdentifier id = (CLIdentifier) binary.getLeft();
					if (id.getName().equals(v)) {
						return binary.getRight().getValue(getTypingContext().getRuntimeExecutionContext());
					}
				}
			}
		} catch (final Throwable e) {
			return null;
		}

		return null;
	}

	private Object inferredExactValue(CLExpression expr, String v) throws CLExecutionException, InvalidSetOpException {
		try {
			if (expr instanceof CLMultiExpression) {
				final CLMultiExpression multi = (CLMultiExpression) expr;
				if (multi.getTag() == uk.ac.ncl.prime.sim.lang.parser.alphabet.OP_AND) {
					for (final CLExpression e : multi.getParts()) {
						final Object set = inferredExactValue(e, v);
						if (set != null) {
							return set;
						}
					}
				}
			} else if (expr.getTag() == uk.ac.ncl.prime.sim.lang.parser.alphabet.OP_EQL) {
				final CLBinaryExpression binary = (CLBinaryExpression) expr;
				if (binary.getLeft() instanceof CLIdentifier && binary.getRight().getBoundIdentifiers().isEmpty()) {
					final CLIdentifier id = (CLIdentifier) binary.getLeft();
					if (id.getName().equals(v)) {
						return binary.getRight().getValue(getTypingContext().getRuntimeExecutionContext());
					}
				} else if (binary.getRight() instanceof CLIdentifier && binary.getLeft().getBoundIdentifiers().isEmpty()) {
					final CLIdentifier id = (CLIdentifier) binary.getRight();
					if (id.getName().equals(v)) {
						return binary.getLeft().getValue(getTypingContext().getRuntimeExecutionContext());
					}
				}
			}
		} catch (final Throwable e) {
			return null;
		}
		return null;
	}

	private void buildMembers(int i, CLEnumType enumType) throws CLExecutionException {
		members[i] = new Object[enumType.getMembers().length];
		// super.getTypingContext().getRuntimeExecutionContext().getValue(enumType.getMembers()[j]);
		for (int j = 0; j < enumType.getMembers().length; j++) {
			members[i][j] = getTypingContext().getRuntimeExecutionContext().getValue(enumType.getMembers()[j]);
		}
	}
}
