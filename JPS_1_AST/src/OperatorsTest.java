import static org.junit.Assert.*;
import org.junit.*;
import interpreter.envs.Interpreter;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import result.*;
import ast.Expression;
import ast.auxname.AsExpression;
import ast.auxname.GroupAsExpression;
import ast.binary.AndExpression;
import ast.binary.CommaExpression;
import ast.binary.DivideExpression;
import ast.binary.DotExpression;
import ast.binary.EqualsExpression;
import ast.binary.ForAllExpression;
import ast.binary.ForAnyExpression;
import ast.binary.GreaterOrEqualThanExpression;
import ast.binary.GreaterThanExpression;
import ast.binary.IntersectExpression;
import ast.binary.PlusExpression;
import ast.terminal.BooleanTerminal;
import ast.terminal.DoubleTerminal;
import ast.terminal.IntegerTerminal;
import ast.terminal.NameTerminal;
import ast.terminal.StringTerminal;
import ast.unary.BagExpression;
import datastore.SBAStore;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;

public class OperatorsTest {
	private static final String OPERATORS_DATA = "operators_data.xml";

	Interpreter i;
	SBAStore store;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {

		store = new SBAStore();
		store.loadXML(OPERATORS_DATA);
		i = new Interpreter(store);
	}

	@Test
	public void test_2() {
		Expression expr = new ForAllExpression(new IntegerTerminal(1),
				new BooleanTerminal(true));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_3() {
		Expression expr = new ForAllExpression(new AsExpression(
				new BagExpression(new CommaExpression(new IntegerTerminal(1),
						new GroupAsExpression(new BagExpression(
								new CommaExpression(new IntegerTerminal(2),
										new IntegerTerminal(3))), "wew"))),
				"num"), new EqualsExpression(new NameTerminal("num"),
				new IntegerTerminal(2)));

		assertFalse(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_4() {
		Expression expr = new ForAllExpression(new NameTerminal("emp"),
				new NameTerminal("married"));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_5() {
		Expression expr = new AndExpression(new BooleanTerminal(true),
				new BooleanTerminal(false));

		assertFalse(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_6() {
		Expression expr = new AndExpression(new NameTerminal("booleanValue"),
				new BooleanTerminal(true));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_7() {
		Expression expr = new AndExpression(new AndExpression(
				new BooleanTerminal(false), new BooleanTerminal(true)),
				new IntegerTerminal(1));

		assertFalse(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_8() {
		Expression expr = new ForAnyExpression(new IntegerTerminal(1),
				new BooleanTerminal(true));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_10() {
		Expression expr = new ForAnyExpression(new NameTerminal("emp"),
				new NameTerminal("married"));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_11() {
		Expression expr = new AsExpression(new IntegerTerminal(1), "liczba");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_12() {
		Expression expr = new AsExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), "num");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_13() {
		Expression expr = new AsExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)), "num");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_14() {
		Expression expr = new BagExpression(new IntegerTerminal(1));
		BagResult expected = new BagResult();
		expected.add(new IntegerResult(1));

		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_15() {
		Expression expr = new BagExpression(new CommaExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3)));
		BagResult expected = new BagResult();
		expected.add(new IntegerResult(1));
		expected.add(new IntegerResult(2));
		expected.add(new IntegerResult(3));
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_16() {
		Expression expr = new BagExpression(new CommaExpression(
				new PlusExpression(new IntegerTerminal(1), new IntegerTerminal(
						2)), new IntegerTerminal(3)));

		BagResult expected = new BagResult();
		expected.add(new IntegerResult(1));
		expected.add(new IntegerResult(2));
		expected.add(new IntegerResult(3));
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_17() {
		Expression expr = new BagExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))));

		BagResult inner = new BagResult();
		inner.add(new IntegerResult(1));
		inner.add(new IntegerResult(2));
		inner.add(new IntegerResult(3));
		BagResult expected = new BagResult();
		expected.add(inner);
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_18() {
		Expression expr = new NameTerminal("integerNumber");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_19() {
		Expression expr = new NameTerminal("realNumber");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_20() {
		Expression expr = new NameTerminal("booleanValue");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_21() {
		Expression expr = new NameTerminal("stringValue");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_22() {
		Expression expr = new NameTerminal("pomidor");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_23() {
		Expression expr = new NameTerminal("sampleComplexObj");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_24() {
		Expression expr = new CommaExpression(new IntegerTerminal(1),
				new IntegerTerminal(2));
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_25() {
		Expression expr = new CommaExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new IntegerTerminal(3));
		// TODO
		i.eval(expr);

	}

	@Test
	public void test_26() {
		Expression expr = new CommaExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new BagExpression(
				new CommaExpression(new IntegerTerminal(3),
						new IntegerTerminal(4))));
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_27() {
		Expression expr = new DivideExpression(new IntegerTerminal(10),
				new IntegerTerminal(5));

		assertEquals(2, ((DoubleResult) i.eval(expr)).getValue().doubleValue(),
				0.001);
	}

	@Test
	public void test_28() {
		Expression expr = new DivideExpression(new IntegerTerminal(5),
				new DoubleTerminal(3.50));

		assertEquals(1.4285714285714286, ((DoubleResult) i.eval(expr))
				.getValue().doubleValue(), 0.001);
	}

	@Test
	public void test_29() {
		Expression expr = new DivideExpression(new DoubleTerminal(3.50),
				new IntegerTerminal(5));

		assertEquals(0.7, ((DoubleResult) i.eval(expr)).getValue()
				.doubleValue(), 0.001);
	}

	@Test
	public void test_30() {
		Expression expr = new DivideExpression(new DoubleTerminal(3.50),
				new DoubleTerminal(5.50));

		assertEquals(0.63636363636364, ((DoubleResult) i.eval(expr)).getValue()
				.doubleValue(), 0.001);
	}

	@Test
	public void test_31() {
		Expression expr = new DotExpression(new AsExpression(
				new IntegerTerminal(1), "x"), new NameTerminal("x"));

		assertEquals(1, ((DoubleResult) i.eval(expr)).getValue().doubleValue(),
				0.001);
	}

	@Test
	public void test_32() {
		Expression expr = new DotExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new StringTerminal("Ala"));

		assertEquals("Ala", ((StringResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_33() {
		Expression expr = new DotExpression(new DotExpression(new NameTerminal(
				"emp"), new NameTerminal("book")), new NameTerminal("author"));
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_34() {
		Expression expr = new DotExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new StringTerminal("Ala"));

		BagResult expected = new BagResult();
		expected.add(new StringResult("Ala"));
		expected.add(new StringResult("Ala"));
		assertEquals(expected, (BagResult) i.eval(expr));// TODO dlaczego nie
															// dziala
	}

	@Test
	public void test_35() {
		Expression expr = new EqualsExpression(new IntegerTerminal(1),
				new IntegerTerminal(2));

		assertFalse(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_36() {
		Expression expr = new EqualsExpression(
				new NameTerminal("integerNumber"), new IntegerTerminal(10));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_37() {
		Expression expr = new EqualsExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new IntegerTerminal(2));

		exception.expect(RuntimeException.class);

		i.eval(expr);
	}

	@Test
	public void test_38() {
		Expression expr = new EqualsExpression(
				new NameTerminal("booleanValue"), new BooleanTerminal(true));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_39() {
		Expression expr = new EqualsExpression(new NameTerminal("stringValue"),
				new StringTerminal("Ala"));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_40() {
		Expression expr = new EqualsExpression(new IntegerTerminal(1),
				new BooleanTerminal(true));

		assertFalse(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_41() {
		Expression expr = new EqualsExpression(new IntegerTerminal(5),
				new StringTerminal("5"));

		assertFalse(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_42() {
		Expression expr = new EqualsExpression(new DoubleTerminal(5.50),
				new IntegerTerminal(5));

		assertFalse(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_43() {
		Expression expr = new GreaterThanExpression(new IntegerTerminal(1),
				new IntegerTerminal(1));

		assertFalse(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_44() {
		Expression expr = new GreaterThanExpression(new IntegerTerminal(3),
				new DoubleTerminal(2.99));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_45() {
		Expression expr = new GreaterThanExpression(new DoubleTerminal(24.35),
				new DoubleTerminal(24.34));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_46() {
		Expression expr = new GreaterOrEqualThanExpression(new IntegerTerminal(
				1), new IntegerTerminal(1));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_47() {
		Expression expr = new GreaterOrEqualThanExpression(new IntegerTerminal(
				3), new DoubleTerminal(2.99));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_48() {
		Expression expr = new GreaterOrEqualThanExpression(new DoubleTerminal(
				24.35), new DoubleTerminal(24.34));

		assertTrue(((BooleanResult) i.eval(expr)).getValue());
	}

	@Test
	public void test_49() {
		Expression expr = new GroupAsExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))),
				"num");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_50() {
		Expression expr = new GroupAsExpression(new IntegerTerminal(1),
				"liczba");
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_51() {
		Expression expr = new IntersectExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))),
				new BagExpression(new CommaExpression(new IntegerTerminal(2),
						new IntegerTerminal(3))));

		BagResult expected = new BagResult();
		expected.add(new IntegerResult(2));
		expected.add(new IntegerResult(3));
		assertEquals(expected, (BagResult) i.eval(expr));// TODO dlaczego nie
															// dziala
	}

	@Test
	public void test_52() {
		Expression expr = new IntersectExpression(new IntegerTerminal(1),
				new IntegerTerminal(1));

		BagResult expected = new BagResult();
		expected.add(new IntegerResult(1));
		assertEquals(expected, (BagResult) i.eval(expr));// TODO dlaczego nie
															// dziala
	}

	@Test
	public void test_53() {
		Expression expr = new IntersectExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new CommaExpression(new IntegerTerminal(2),
						new IntegerTerminal(3)));

		BagResult expected = new BagResult();
		assertEquals(expected, (BagResult) i.eval(expr));// TODO dlaczego nie
															// dziala
	}

	@Test
	public void test_54() {
		Expression expr = new IntersectExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)));
		// TODO
		i.eval(expr);
	}

	@Test
	public void test_55() {
		Expression expr = new IntersectExpression(
				new BagExpression(new CommaExpression(new CommaExpression(
						new StringTerminal("ala"), new IntegerTerminal(2)),
						new IntegerTerminal(3))), new BagExpression(
						new CommaExpression(new IntegerTerminal(2),
								new DoubleTerminal(3.40))));

		BagResult expected = new BagResult();
		expected.add(new IntegerResult(2));
		assertEquals(expected, (BagResult) i.eval(expr));// TODO dlaczego nie
															// dziala
	}

}
