package uk.ac.ncl.prime.sim.lang;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;

public class CLBase {
	public static final CLExpression True() {
		return CLAtomicExpression.TRUE;
	}

	public static final CLExpression False() {
		return CLAtomicExpression.FALSE;
	}
	
	public static final boolean isConstantTruth(CLExpression e) {
		return e == CLAtomicExpression.TRUE;
	}

	public static final boolean isConstantFalse(CLExpression e) {
		return e == CLAtomicExpression.FALSE;
	}

	public static final CLExpression quote(CLExpression expr) {
		final Map<String, CLExpression> map = new HashMap<>();
		for (final String v : expr.getFreeIdentifiers()) {
			map.put(v, varq(v));
		}
		return expr.rewrite(map);
	}

	public static final CLIdentifier varq(String name) {
		return new CLIdentifier("$" + name);
	}

	public static final CLIdentifier var(String name) {
		return new CLIdentifier(name);
	}

	public static final CLIdentifier tem(String name, int tag) {
		final CLIdentifier id = new CLIdentifier(name);
		id.setTagConstraint(tag);
		return id;
	}

	public static final CLIdentifier temid(String name) {
		final CLIdentifier id = new CLIdentifier(name);
		id.setTagConstraint(alphabet.ID);
		return id;
	}

	public static final CLIdentifier tem(String name, int tag, boolean cons) {
		final CLIdentifier id = new CLIdentifier(name);
		id.setTagConstraint(tag);
		id.setConstantConstraint(cons);
		return id;
	}

	public static final CLIdentifier tem(String name, boolean cons) {
		final CLIdentifier id = new CLIdentifier(name);
		id.setConstantConstraint(cons);
		return id;
	}

	public static final CLExpression ovr(CLExpression... parts) {
		return new CLMultiExpression(alphabet.OP_OVR, parts[0].getLocation(), parts);
	}

	public static final CLExpression union(CLExpression... parts) {
		return new CLMultiExpression(alphabet.OP_UNION, parts[0].getLocation(), parts);
	}

	public static final CLExpression inter(CLExpression... parts) {
		return new CLMultiExpression(alphabet.OP_INTER, parts[0].getLocation(), parts);
	}

	public static final CLExpression setminus(CLExpression... parts) {
		return new CLMultiExpression(alphabet.OP_SETMINUS, parts[0].getLocation(), parts);
	}

	public static final CLExpression setc(CLExpression... parts) {
		return new CLMultiExpression(alphabet.SETC, parts[0].getLocation(), parts);
	}

	public static final CLExpression in(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.OP_IN, a, b, a.getLocation());
	}

	public static final CLExpression notin(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.OP_NOTIN, a, b, a.getLocation());
	}

	public static final CLExpression image(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.IMAGE, a, b, a.getLocation());
	}

	public static CLExpression add(CLExpression... parts) {
		final CLExpression expr = new CLMultiExpression(alphabet.OP_PLUS, parts[0].getLocation(), parts);
		return expr;
	}

	public static CLExpression and(CLExpression... parts) {
		final CLExpression expr = new CLMultiExpression(alphabet.OP_AND, parts[0].getLocation(), parts);
		return expr;
	}

	public static CLExpression or(CLExpression... parts) {
		final CLExpression expr = new CLMultiExpression(alphabet.OP_OR, parts[0].getLocation(), parts);
		return expr;
	}

	public static CLExpression sub(CLExpression... parts) {
		final CLExpression expr = new CLMultiExpression(alphabet.OP_MINUS, parts[0].getLocation(), parts);
		return expr;
	}

	public static CLExpression fapp(CLExpression f, CLExpression... parts) {
		return new CLFunAppExpression(f, new CLCollection<>(parts));
	}

	public static CLExpression converse(CLExpression f) {
		return new CLUnaryExpression(alphabet.OP_CONVERSE, f, f.getLocation());
	}

	public static CLExpression eql(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.OP_EQL, a, b, a.getLocation());
	}

	public static CLExpression subset(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.OP_SUBSET, a, b, a.getLocation());
	}

	public static CLExpression subseteq(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.OP_SUBSETEQ, a, b, a.getLocation());
	}
	
	
	public static CLExpression noteql(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.OP_NEQ, a, b, a.getLocation());
	}

	public static CLExpression limp(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.OP_IMPLIES, a, b, a.getLocation());
	}

	public static CLIntegerExpression num(int val) {
		final CLIntegerExpression v = new CLIntegerExpression(val);
		return v;
	}
}
