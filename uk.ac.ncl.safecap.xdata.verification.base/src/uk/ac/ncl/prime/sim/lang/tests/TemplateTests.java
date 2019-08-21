package uk.ac.ncl.prime.sim.lang.tests;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;

public class TemplateTests extends TestBase {
	private final CLExpression x, y, z;
	private final CLExpression a, b, c;
	private final CLExpression f, g, h;

	public TemplateTests() {
		x = var("x");
		y = var("y");
		z = var("z");

		a = var("a");
		b = var("b");
		c = var("c");

		f = var("f", new CLPowerType(new CLProductType(CLTypeInteger.INSTANCE, CLTypeInteger.INSTANCE)));
		g = var("g", new CLPowerType(new CLProductType(CLTypeInteger.INSTANCE, CLTypeInteger.INSTANCE)));
		h = var("h", new CLPowerType(new CLProductType(CLTypeInteger.INSTANCE, CLTypeInteger.INSTANCE)));
	}

	@Test
	public void basic0() {
		final CLExpression e = add(x, num(7), num(15));
		final CLExpression e_t = add(num(7), z, num(15));
		final Map<String, CLExpression> map = e.match(e_t, null);
		assertTrue(map != null && map.containsKey("z") && map.get("z").equals(x));
	}

	@Test
	public void basic1() {
		final CLExpression e = eql(x, fapp(converse(f), setc(y)));
		final CLExpression e_t = eql(a, fapp(converse(b), setc(c)));
		final Map<String, CLExpression> map = e.match(e_t, null);
		check(map, "a", "x", "b", "f", "c", "y");
	}

	@Test
	public void basic2() {
		final CLExpression e = eql(x, fapp(converse(f), setc(fapp(g, y))));
		final CLExpression e_t = eql(a, fapp(converse(b), setc(c)));
		final Map<String, CLExpression> map = e.match(e_t, null);
		check(map, "a", "x", "b", "f", "c", "g(y)");
	}

}
