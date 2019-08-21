package uk.ac.ncl.safecap.prover.transforms;

import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public class UserInferenceRule {
	private final String name;
	private final List<CLExpression> premisesHyp;
	private final CLExpression premisesGoal;
	private final CLExpression condition;
	private final CLExpression conclusionGoal;
	private final CLExpression conclusionHyp;

	public UserInferenceRule(String name, List<CLExpression> hypPremises, CLExpression premisesGoal, CLExpression condition,
			CLExpression conclusionHyp, CLExpression conclusionGoal) {
		super();
		this.name = name;
		premisesHyp = hypPremises;
		this.premisesGoal = premisesGoal;
		this.condition = condition;
		this.conclusionHyp = conclusionHyp;
		this.conclusionGoal = conclusionGoal;
	}

	public String getName() {
		return name;
	}

	public CLExpression getPremisesGoal() {
		return premisesGoal;
	}

	public List<CLExpression> getHypPremises() {
		return premisesHyp;
	}

	public CLExpression getCondition() {
		return condition;
	}

	public CLExpression getConclusionHyp() {
		return conclusionHyp;
	}

	public CLExpression getConclusionGoal() {
		return conclusionGoal;
	}
}
