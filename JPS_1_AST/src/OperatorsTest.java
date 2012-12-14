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

		BagResult expected = new BagResult();
		expected.add(new BinderResult("liczba", new IntegerResult(1)));

		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_12() {
		Expression expr = new AsExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), "num");

		BagResult expected = new BagResult();
		expected.add(new BinderResult("num", new IntegerResult(1)));
		expected.add(new BinderResult("num", new IntegerResult(2)));

		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_13() {
		Expression expr = new AsExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)), "num");

		BagResult expected = new BagResult();
		StructResult struct = new StructResult();
		struct.add(new IntegerResult(1));
		struct.add(new IntegerResult(2));
		expected.add(new BinderResult("num", struct));

		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
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
		BagResult expected = new BagResult();
		StructResult struct = new StructResult();
		struct.add(new IntegerResult(1));
		struct.add(new IntegerResult(2));
		expected.add(struct);
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_25() {
		Expression expr = new CommaExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new IntegerTerminal(3));
		BagResult expected = new BagResult();
		StructResult struct1 = new StructResult();
		struct1.add(new IntegerResult(1));
		struct1.add(new IntegerResult(3));
		StructResult struct2 = new StructResult();
		struct2.add(new IntegerResult(2));
		struct2.add(new IntegerResult(3));
		expected.add(struct1);
		expected.add(struct2);
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));

	}

	@Test
	public void test_26() {
		Expression expr = new CommaExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new BagExpression(
				new CommaExpression(new IntegerTerminal(3),
						new IntegerTerminal(4))));
		BagResult expected = new BagResult();
		StructResult struct1 = new StructResult();
		struct1.add(new IntegerResult(1));
		struct1.add(new IntegerResult(3));
		StructResult struct2 = new StructResult();
		struct2.add(new IntegerResult(1));
		struct2.add(new IntegerResult(4));
		StructResult struct3 = new StructResult();
		struct3.add(new IntegerResult(2));
		struct3.add(new IntegerResult(3));
		StructResult struct4 = new StructResult();
		struct4.add(new IntegerResult(2));
		struct4.add(new IntegerResult(4));
		expected.add(struct1);
		expected.add(struct2);
		expected.add(struct3);
		expected.add(struct4);
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
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
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
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
		BagResult inner = new BagResult();
		inner.add(new IntegerResult(1));
		inner.add(new IntegerResult(2));
		inner.add(new IntegerResult(3));
		BinderResult expected = new BinderResult("num", inner);
		assertTrue(expected.equals((BinderResult) i.eval(expr)));
		// FIXME cos jest nie tak, powstaje binder(name = num , value = bag(0 =
		// 3)), czyli w bagu brakuje jedynki i dwojki
	}

	@Test
	public void test_50() {
		Expression expr = new GroupAsExpression(new IntegerTerminal(1),
				"liczba");
		BinderResult expected = new BinderResult("liczba", new IntegerResult(1));
		assertTrue(expected.equals((BinderResult) i.eval(expr)));
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
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
		// FIXME lewy bag zle sie tworzy. podobnie jak w tescie 55
	}

	@Test
	public void test_52() {
		Expression expr = new IntersectExpression(new IntegerTerminal(1),
				new IntegerTerminal(1));

		BagResult expected = new BagResult();
		expected.add(new IntegerResult(1));
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_53() {
		Expression expr = new IntersectExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new CommaExpression(new IntegerTerminal(2),
						new IntegerTerminal(3)));

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_54() {
		Expression expr = new IntersectExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)));
		BagResult expected = new BagResult();
		StructResult struct = new StructResult();
		struct.add(new IntegerResult(1));
		struct.add(new IntegerResult(2));
		expected.add(struct);
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
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
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
		// FIXME lewy bag zle sie tworzy, brakuje mu "ala" i dwojki
	}

	@Test
	public void test_56() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_57() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_58() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_59() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_60() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_61() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_62() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_63() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_64() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_65() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_66() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_67() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_68() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_69() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_70() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_71() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_72() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_73() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_74() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_75() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_76() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_77() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_78() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_79() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_80() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_81() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_82() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_83() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_84() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_85() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_86() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_87() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_88() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_89() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_90() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_91() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_92() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_93() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_94() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_95() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_96() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_97() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_98() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_99() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_100() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_101() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_102() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_103() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_104() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_105() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_106() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_107() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_108() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_109() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_110() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_111() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_112() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_113() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_114() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_115() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_116() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_117() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_118() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_119() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_120() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_121() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_122() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_123() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_124() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_125() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_126() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_127() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_128() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_129() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_130() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_131() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}

	@Test
	public void test_132() {
		Expression expr = null;

		BagResult expected = new BagResult();
		assertTrue(expected.equalsForJUnit((BagResult) i.eval(expr)));
	}
}