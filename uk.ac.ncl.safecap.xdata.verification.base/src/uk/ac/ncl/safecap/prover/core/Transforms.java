package uk.ac.ncl.safecap.prover.core;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.common.PropertyHolder;

public class Transforms extends PropertyHolder implements ICLFormulaProvider {
	private boolean exclude;
	private CLExpression formula;
	private String transform;
	private Transforms origin;
	private int stage = 0;

	public Transforms(CLExpression formula) {
		this.formula = formula;
		exclude = false;
	}

	public Transforms(Transforms origin, CLExpression formula, String id, IGoalContainer mpo) {
		this.formula = formula;
		this.origin = origin;
		transform = id;
		stage = mpo.getStage();
		exclude = false;
	}

	public Transforms(Transforms origin, CLExpression formula, String id, boolean exclude, IGoalContainer mpo) {
		this.formula = formula;
		this.origin = origin;
		transform = id;
		this.exclude = exclude;
		stage = mpo.getStage();
	}

	public Transforms(CLExpression formula, String id, IGoalContainer mpo) {
		this.formula = formula;
		transform = id;
		stage = mpo.getStage();
		exclude = false;
	}	
	
	public int getStage() {
		return stage;
	}

	public boolean isExclude() {
		return exclude;
	}

	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}

	@Override
	public CLExpression getFormula() {
		return formula;
	}

	public void setFormula(CLExpression origin) {
		formula = origin;
	}

	public String getTransform() {
		return transform == null ? "*" : transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public Transforms getOrigin() {
		return origin;
	}

	public void purge() {
		origin = null;
	}
}
