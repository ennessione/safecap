package uk.ac.ncl.prime.sim.lang.tests;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLFunAppExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLIntegerExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUnaryExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;

public class TestBase {
	public TypingContext typing = new TypingContext();

	public CLIdentifier var(String name) {
		final CLIdentifier v = new CLIdentifier(name);
		typing.addSymbol(name, CLTypeInteger.INSTANCE, SYMBOL_CLASS.IDENTIFIER);
		return v;
	}

	public CLIntegerExpression num(int val) {
		final CLIntegerExpression v = new CLIntegerExpression(val);
		return v;
	}

	public CLIdentifier var(String name, CLType type) {
		final CLIdentifier v = new CLIdentifier(name);
		typing.addSymbol(name, type, SYMBOL_CLASS.IDENTIFIER);
		return v;
	}

	public CLIdentifier temp(String name) {
		final CLIdentifier v = new CLIdentifier("$" + name);
		return v;
	}

	public CLExpression add(CLExpression... parts) {
		final CLExpression expr = new CLMultiExpression(alphabet.OP_PLUS, null, parts);
		return expr;
	}

	public CLExpression sub(CLExpression... parts) {
		final CLExpression expr = new CLMultiExpression(alphabet.OP_MINUS, null, parts);
		return expr;
	}

	public CLExpression fapp(CLExpression f, CLExpression... parts) {
		return new CLFunAppExpression(f, new CLCollection<>(parts));
	}

	public CLExpression converse(CLExpression f) {
		return new CLUnaryExpression(alphabet.OP_CONVERSE, f);
	}

	public CLExpression setc(CLExpression... parts) {
		return new CLMultiExpression(alphabet.SETC, null, parts);
	}

	public CLExpression eql(CLExpression a, CLExpression b) {
		return new CLBinaryExpression(alphabet.OP_EQL, a, b);
	}

	public void check(Map<String, CLExpression> map, String... args) {
		assertTrue(map != null && map.size() == args.length >> 1);

		for (int i = 0; i < args.length; i += 2) {
			assertTrue(map.containsKey(args[i]));
			assertTrue("For " + args[i] + " expect " + args[i + 1] + " but have " + map.get("$" + args[i]),
					map.get(args[i]).toString().equals(args[i + 1]));
		}
	}

}
