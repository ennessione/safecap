package uk.ac.ncl.safecap.prover.core;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGoalContainer implements IGoalContainer {
	protected List<IGoalContainer> children;
	protected List<ProofGoal> goals;

	public BaseGoalContainer() {
		goals = new ArrayList<>();
		children = new ArrayList<>();
	}

	public void replace(ProofGoal goal, List<ProofGoal> newGoals) {
		final int index = goals.indexOf(goal);
		if (index == -1) {
			throw new IllegalArgumentException();
		}
		if (newGoals.size() == 0) { // goal deleted
			goals.remove(index);
		} else if (newGoals.size() == 1 && newGoals.get(0) != goal) { // goal changed
			newGoals.get(0).setName(goal.getName());
			goals.set(index, newGoals.get(0));
		} else if (newGoals.size() > 1) { // new goals
			goals.remove(index);
			goals.addAll(index, newGoals);
			for (int i = 0; i < newGoals.size(); i++) {
				newGoals.get(i).setName(goal.getName() + "/" + i);
			}
		}
	}

	@Override
	public void addGoal(ProofGoal goal) {
		goals.add(goal);
		goal.setParent(this);
	}

	@Override
	public void addChild(IGoalContainer child) {
		children.add(child);
	}

	@Override
	public List<IGoalContainer> getChildren() {
		return children;
	}

	@Override
	public List<ProofGoal> getGoals() {
		return goals;
	}

	@Override
	public boolean visitGoals(IGoalVisitor visitor) {
		
		for(IGoalContainer c: children) {
			if (!c.visitGoals(visitor))
				return false;
		}
		
		for(ProofGoal goal: goals)
			if (!visitor.visit(goal))
				return false;
		
		return true;
	}
	
	@Override
	public ProofGoal getGoalByName(String name) {
		for (final ProofGoal goal : goals) {
			if (goal.getName().equals(name)) {
				return goal;
			}
		}

		for (final IGoalContainer x : children) {
			final ProofGoal g = x.getGoalByName(name);
			if (g != null) {
				return g;
			}
		}

		return null;
	}

	@Override
	public void purge() {
		for (final IGoalContainer x : children) {
			x.purge();
		}

		for (final ProofGoal pg : goals) {
			pg.purge();
		}
	}

	@Override
	public long getTime() {
		long time = 0;

		for (final IGoalContainer x : children) {
			time += x.getTime();
		}

		for (final ProofGoal goal : goals) {
			if (goal.isClosed() && goal.getStatus() != null) {
				time += goal.getStatus().getTime();
			}
		}

		return time;
	}
}
