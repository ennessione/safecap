package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLBase;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForeignLocationExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.Transforms;

public class ProofStateExplainer extends CLBase {
	private boolean full = true;
	ProofGoal goal;
	Map<String, Collection<CLForeignLocationExpression>> state;
	private boolean contradictionInGoal = false;
	
	public ProofStateExplainer(ProofGoal goal) {
		this.goal = goal;
		state = new HashMap<>();
		prepare();
		build();
	}

	public ProofStateExplainer(ProofGoal goal, boolean full) {
		this.goal = goal;
		state = new HashMap<>();
		this.full = full;
		prepare();
		build();
	}
	
	private void prepare() {
		if (isConstantFalse(goal.getFormula())) {
			contradictionInGoal = true;
			// unroll backward
			Transforms g = goal.getOrigin();
			while (g != null && isConstantFalse(g.getFormula()))
				g = g.getOrigin();
			
			if (g != null) {
				goal = new ProofGoal(goal.getGoalContainer(), g, goal.getName());
			}
		}
	}	

	public static String explain(ProofGoal goal) {
		final ProofStateExplainer explainer = new ProofStateExplainer(goal, false);
		if (explainer.state.isEmpty()) {
			return null;
		}
		final String key = explainer.state.keySet().iterator().next();
		return key + ": " + explainer.state.get(key);
	}

	private void build() {
		populate(in(tem("a", alphabet.ID, true), varq("subroute_l")), "Sub routes locked");
		populate(notin(tem("a", alphabet.ID, true), varq("subroute_l")), "Sub routes free");
		populate(in(tem("a", alphabet.ID, true), varq("overlap_l")), "Sub overlaps locked");
		populate(notin(tem("a", alphabet.ID, true), varq("overlap_l")), "Sub overlaps free");
		populate(in(tem("a", alphabet.ID, true), varq("track_o")), "Sections occupied");
		populate(notin(tem("a", alphabet.ID, true), varq("track_o")), "Sections free");
		populate(in(tem("a", alphabet.ID, true), varq("route_a")), "Routes available");
		populate(notin(tem("a", alphabet.ID, true), varq("route_a")), "Routes not available");
		populate(in(tem("a", alphabet.ID, true), varq("route_s")), "Routes set");
		populate(notin(tem("a", alphabet.ID, true), varq("route_s")), "Routes not set");
		populate(in(tem("a", alphabet.ID, true), varq("latch_s")), "Latches set");
		populate(notin(tem("a", alphabet.ID, true), varq("latch_s")), "Latches not set");
		populate(in(tem("a", alphabet.ID, true), varq("request")), "Requests, active");
		populate(notin(tem("a", alphabet.ID, true), varq("request")), "Requests, inactive");
		populate(in(tem("a", alphabet.ID, true), varq("signal_bpull")), "Signal button pull bit set");
		populate(notin(tem("a", alphabet.ID, true), varq("signal_bpull")), "Signal button pull bit clear");

		populate(eql(fapp(varq("point_c"), tem("a", alphabet.ID, true)), varq("NORMAL")), "Points commanded normal");
		populate(eql(fapp(varq("point_c"), tem("a", alphabet.ID, true)), varq("REVERSE")), "Points commanded reverse");

		populate(noteql(fapp(varq("point_c"), tem("a", alphabet.ID, true)), varq("NORMAL")), "Points not commanded normal");
		populate(noteql(fapp(varq("point_c"), tem("a", alphabet.ID, true)), varq("REVERSE")), "Points not commanded reverse");

		populate(eql(fapp(varq("point_d"), tem("a", alphabet.ID, true)), varq("NORMAL")), "Points key driven normal");
		populate(eql(fapp(varq("point_d"), tem("a", alphabet.ID, true)), varq("REVERSE")), "Points key driven reverse");

		populate(eql(varq("route_s'"), union(varq("route_s"), var("a"))), "Routes set (next state)");
		populate(eql(varq("route_s'"), setminus(union(varq("route_s"), var("a")), var("b"))), "Routes set (next state)",
				"Routes unset (next state)");

		populate(eql(varq("subroute_l'"), union(varq("subroute_l"), var("a"))), "Sub routes locked (next state)");
		populate(eql(varq("subroute_l'"), setminus(union(varq("subroute_l"), var("a")), var("b"))), "Sun routes locked (next state)",
				"Sub routes freed (next state)");

		populate(eql(varq("overlap_l'"), union(varq("overlap_l"), var("a"))), "Sub overlap locked (next state)");
		populate(eql(varq("overlap_l'"), setminus(union(varq("overlap_l"), var("a")), var("b"))), "Sun overlap locked (next state)",
				"Sub overlap freed (next state)");
	}

	private void populate(CLExpression query, String... explanation) {
		if (full) {
			final Map<String, Collection<CLForeignLocationExpression>> map = goal.harvest(query);
			char c = 'a';
			for (int i = 0; i < explanation.length; i++) {
				final String key = "" + c;
				if (map != null && map.containsKey(key)) {
					final Collection<CLForeignLocationExpression> result = map.get(key);
					state.put(explanation[i], result);
				}
				c++;
			}
		} else {
			Map<String, CLExpression> map = goal.getFormula().match(query, goal.getTypingContext());
			if (contradictionInGoal && map == null) {
				 map = augmentedMatch(goal.getFormula(), query, goal.getTypingContext());
			}
			char c = 'a';
			for (int i = 0; i < explanation.length; i++) {
				final String key = "" + c;
				if (map != null && map.containsKey(key)) {
					final CLExpression result = map.get(key);
					CLForeignLocationExpression ee = new CLForeignLocationExpression(result, goal.getFormula().getLocation());
					state.put(explanation[i], Collections.singleton(ee));
				}
				c++;
			}
		}
	}

	private Map<String, CLExpression> augmentedMatch(CLExpression formula, CLExpression query, TypingContext typingContext) {
		if (formula.getTag() == alphabet.OP_AND) {
			CLMultiExpression and = (CLMultiExpression) formula;
			for(CLExpression e: and.getParts()) {
				Map<String, CLExpression> m = augmentedMatch(e, query, typingContext);
				if (m != null)
					return m;
			}
		}
		
		Map<String, CLExpression> map = formula.match(query, typingContext);;
		if (map != null)
			return map;
		CLExpression query2 = union(query, var("z"));
		map = formula.match(query2, typingContext);
		if (map != null)
			return map;
		CLExpression query3 = and(query, var("z"));
		map = formula.match(query3, typingContext);
		if (map != null)
			return map;	

		return null;
	}
}