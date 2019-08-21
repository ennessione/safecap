package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.CLFormulaParser;
import uk.ac.ncl.prime.sim.parser.FormulaSource;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.safecap.xdata.verification.registry.ISymbolicTransition;

public class TransitionParsed implements ISymbolicTransition {
	private List<FormulaSource> fsPreconditions;
	private List<FormulaSource> fsPostconditions;
	private List<CLExpression> preconditions;
	private List<CLExpression> postconditions;
	private final String name;

	public TransitionParsed(String name) {
		this.name = name;
	}

	public TransitionParsed(ITransition parent, TypingContext typing, CLFormulaParser parser) {
		name = parent.getName();
		fsPreconditions = new ArrayList<>();
		preconditions = new ArrayList<>();

		if (parent.getPreconditions() != null) {
			for (final LocatedString e : parent.getPreconditions()) {
				final FormulaSource fs = new FormulaSource(e.getValue());
				fsPreconditions.add(fs);
				CLExpression expr = null;
				try {
					expr = parser.parse(fs);
					if (expr != null && !fs.hasErrors()) {
						expr.setLocation(new SourceLocation(e));
						preconditions.add(expr);
					} else {
						preconditions.add(CLAtomicExpression.TRUE);
					}
				} catch (final Exception e1) {
					e1.printStackTrace();
				}

			}
		}

		fsPostconditions = new ArrayList<>();
		postconditions = new ArrayList<>();

		if (parent.getPostconditions() != null) {
			for (final LocatedString e : parent.getPostconditions()) {
				final FormulaSource fs = new FormulaSource(e.getValue());
				fsPostconditions.add(fs);
				CLExpression expr = null;
				try {
					expr = parser.parse(fs);
					if (expr != null && !fs.hasErrors()) {
						expr.setLocation(new SourceLocation(e));
						postconditions.add(expr);
					} else {
						postconditions.add(CLAtomicExpression.TRUE);
					}
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public CLExpression getOverallPrecondition() {
		return CLUtils.buildConjunctMSL(preconditions);
	}

	public CLExpression getOverallPostcondition() {
		return CLUtils.buildConjunctMSL(postconditions);
	}

	public List<FormulaSource> getFsPreconditions() {
		return fsPreconditions;
	}

	public void setFsPreconditions(List<FormulaSource> fsPreconditions) {
		this.fsPreconditions = fsPreconditions;
	}

	public List<FormulaSource> getFsPostconditions() {
		return fsPostconditions;
	}

	public void setFsPostconditions(List<FormulaSource> fsPostconditions) {
		this.fsPostconditions = fsPostconditions;
	}

	@Override
	public List<CLExpression> getPreconditions() {
		return preconditions;
	}

	public void setPreconditions(List<CLExpression> preconditions) {
		this.preconditions = preconditions;
	}

	@Override
	public List<CLExpression> getPostconditions() {
		return postconditions;
	}

	public void setPostconditions(List<CLExpression> postconditions) {
		this.postconditions = postconditions;
	}

	@Override
	public String getId() {
		return name;
	}

}
