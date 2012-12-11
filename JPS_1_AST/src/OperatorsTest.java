import operatory.Interpreter;
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

public class OperatorsTest {

	/*
	 * test_x(Interpreter i) gdzie x = linijka w pliku excela
	 */

	public static void test_2(Interpreter i) {
		Expression expr = new ForAllExpression(new IntegerTerminal(1),
				new BooleanTerminal(true));

		System.out.println(i.eval(expr));
	}

	public static void test_3(Interpreter i) {
	//	try{
			Expression expr = new ForAllExpression(new AsExpression(
					new BagExpression(new CommaExpression(new IntegerTerminal(1),
							new GroupAsExpression(new BagExpression(
									new CommaExpression(new IntegerTerminal(2),
											new IntegerTerminal(3))), "wew"))),
					"num"), new EqualsExpression(new NameTerminal("num"),
					new IntegerTerminal(2)));
	
			System.out.println(i.eval(expr));
		//}
	//	catch(Exception e){
			//System.out.println(e);
	//	}
	}

	public static void test_4(Interpreter i) {
		Expression expr = new ForAllExpression(new NameTerminal("emp"),
				new NameTerminal("married"));

		System.out.println(i.eval(expr));
	}

	public static void test_5(Interpreter i) {
		Expression expr = new AndExpression(new BooleanTerminal(true),
				new BooleanTerminal(false));

		System.out.println(i.eval(expr));
	}

	public static void test_6(Interpreter i) {
		Expression expr = new AndExpression(new NameTerminal("booleanValue"),
				new BooleanTerminal(true));

		System.out.println(i.eval(expr));
	}

	public static void test_7(Interpreter i) {
		Expression expr = new AndExpression(new AndExpression(
				new BooleanTerminal(false), new BooleanTerminal(true)),
				new IntegerTerminal(1));

		System.out.println(i.eval(expr));
	}

	public static void test_8(Interpreter i) {
		Expression expr = new ForAnyExpression(new IntegerTerminal(1),
				new BooleanTerminal(true));

		System.out.println(i.eval(expr));
	}

	public static void test_10(Interpreter i) {
		Expression expr = new ForAnyExpression(new NameTerminal("emp"),
				new NameTerminal("married"));

		System.out.println(i.eval(expr));
	}

	public static void test_11(Interpreter i) {
		Expression expr = new AsExpression(new IntegerTerminal(1), "liczba");

		System.out.println(i.eval(expr));
	}

	public static void test_12(Interpreter i) {
		Expression expr = new AsExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), "num");

		System.out.println(i.eval(expr));
	}

	public static void test_13(Interpreter i) {
		Expression expr = new AsExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)), "num");

		System.out.println(i.eval(expr));
	}

	public static void test_14(Interpreter i) {
		Expression expr = new BagExpression(new IntegerTerminal(1));

		System.out.println(i.eval(expr));
	}

	public static void test_15(Interpreter i) {
		Expression expr = new BagExpression(new CommaExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3)));

		System.out.println(i.eval(expr));
	}

	public static void test_16(Interpreter i) {
		Expression expr = new BagExpression(new CommaExpression(
				new PlusExpression(new IntegerTerminal(1), new IntegerTerminal(
						2)), new IntegerTerminal(3)));

		System.out.println(i.eval(expr));
	}

	public static void test_17(Interpreter i) {
		Expression expr = new BagExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))));

		System.out.println(i.eval(expr));
	}

	public static void test_18(Interpreter i) {
		Expression expr = new NameTerminal("integerNumber");

		System.out.println(i.eval(expr));
	}

	public static void test_19(Interpreter i) {
		Expression expr = new NameTerminal("realNumber");

		System.out.println(i.eval(expr));
	}

	public static void test_20(Interpreter i) {
		Expression expr = new NameTerminal("booleanValue");

		System.out.println(i.eval(expr));
	}

	public static void test_21(Interpreter i) {
		Expression expr = new NameTerminal("stringValue");

		System.out.println(i.eval(expr));
	}

	public static void test_22(Interpreter i) {
		Expression expr = new NameTerminal("pomidor");

		System.out.println(i.eval(expr));
	}

	public static void test_23(Interpreter i) {
		Expression expr = new NameTerminal("sampleComplexObj");

		System.out.println(i.eval(expr));
	}

	public static void test_24(Interpreter i) {
		Expression expr = new CommaExpression(new IntegerTerminal(1),
				new IntegerTerminal(2));

		System.out.println(i.eval(expr));
	}

	public static void test_25(Interpreter i) {
		Expression expr = new CommaExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new IntegerTerminal(3));

		System.out.println(i.eval(expr));
	}

	public static void test_26(Interpreter i) {
		Expression expr = new CommaExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new BagExpression(
				new CommaExpression(new IntegerTerminal(3),
						new IntegerTerminal(4))));

		System.out.println(i.eval(expr));
	}

	public static void test_27(Interpreter i) {
		Expression expr = new DivideExpression(new IntegerTerminal(10),
				new IntegerTerminal(5));

		System.out.println(i.eval(expr));
	}

	public static void test_28(Interpreter i) {
		Expression expr = new DivideExpression(new IntegerTerminal(5),
				new DoubleTerminal(3.50));

		System.out.println(i.eval(expr));
	}

	public static void test_29(Interpreter i) {
		Expression expr = new DivideExpression(new DoubleTerminal(3.50),
				new IntegerTerminal(5));

		System.out.println(i.eval(expr));
	}

	public static void test_30(Interpreter i) {
		Expression expr = new DivideExpression(new DoubleTerminal(3.50),
				new DoubleTerminal(5.50));

		System.out.println(i.eval(expr));
	}

	public static void test_31(Interpreter i) {
		Expression expr = new DotExpression(new AsExpression(
				new IntegerTerminal(1), "x"), new NameTerminal("x"));

		System.out.println(i.eval(expr));
	}

	public static void test_32(Interpreter i) {
		Expression expr = new DotExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new StringTerminal("Ala"));

		System.out.println(i.eval(expr));
	}

	public static void test_33(Interpreter i) {
		Expression expr = new DotExpression(new DotExpression(new NameTerminal(
				"emp"), new NameTerminal("book")), new NameTerminal("author"));

		System.out.println(i.eval(expr));
	}

	public static void test_34(Interpreter i) {
		Expression expr = new DotExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new StringTerminal("Ala"));

		System.out.println(i.eval(expr));
	}

	public static void test_35(Interpreter i) {
		Expression expr = new EqualsExpression(new IntegerTerminal(1),
				new IntegerTerminal(2));

		System.out.println(i.eval(expr));
	}

	public static void test_36(Interpreter i) {
		Expression expr = new EqualsExpression(
				new NameTerminal("integerNumber"), new IntegerTerminal(10));

		System.out.println(i.eval(expr));
	}

	public static void test_37(Interpreter i) {
		Expression expr = new EqualsExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new IntegerTerminal(2));

		System.out.println(i.eval(expr));
	}

	public static void test_38(Interpreter i) {
		Expression expr = new EqualsExpression(
				new NameTerminal("booleanValue"), new BooleanTerminal(true));

		System.out.println(i.eval(expr));
	}

	public static void test_39(Interpreter i) {
		Expression expr = new EqualsExpression(new NameTerminal("stringValue"),
				new StringTerminal("Ala"));

		System.out.println(i.eval(expr));
	}

	public static void test_40(Interpreter i) {
		Expression expr = new EqualsExpression(new IntegerTerminal(1),
				new BooleanTerminal(true));

		System.out.println(i.eval(expr));
	}

	public static void test_41(Interpreter i) {
		Expression expr = new EqualsExpression(new IntegerTerminal(5),
				new StringTerminal("5"));

		System.out.println(i.eval(expr));
	}

	public static void test_42(Interpreter i) {
		Expression expr = new EqualsExpression(new DoubleTerminal(5.50),
				new IntegerTerminal(5));

		System.out.println(i.eval(expr));
	}

	public static void test_43(Interpreter i) {
		Expression expr = new GreaterThanExpression(new IntegerTerminal(1),
				new IntegerTerminal(1));

		System.out.println(i.eval(expr));
	}

	public static void test_44(Interpreter i) {
		Expression expr = new GreaterThanExpression(new IntegerTerminal(3),
				new DoubleTerminal(2.99));

		System.out.println(i.eval(expr));
	}

	public static void test_45(Interpreter i) {
		Expression expr = new GreaterThanExpression(new DoubleTerminal(24.35),
				new DoubleTerminal(24.34));

		System.out.println(i.eval(expr));
	}

	public static void test_46(Interpreter i) {
		Expression expr = new GreaterOrEqualThanExpression(new IntegerTerminal(
				1), new IntegerTerminal(1));

		System.out.println(i.eval(expr));
	}

	public static void test_47(Interpreter i) {
		Expression expr = new GreaterOrEqualThanExpression(new IntegerTerminal(
				3), new DoubleTerminal(2.99));

		System.out.println(i.eval(expr));
	}

	public static void test_48(Interpreter i) {
		Expression expr = new GreaterOrEqualThanExpression(new DoubleTerminal(
				24.35), new DoubleTerminal(24.34));

		System.out.println(i.eval(expr));
	}

	public static void test_49(Interpreter i) {
		Expression expr = new GroupAsExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))),
				"num");

		System.out.println(i.eval(expr));
	}

	public static void test_50(Interpreter i) {
		Expression expr = new GroupAsExpression(new IntegerTerminal(1),
				"liczba");

		System.out.println(i.eval(expr));
	}

	public static void test_51(Interpreter i) {
		Expression expr = new IntersectExpression(new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))),
				new BagExpression(new CommaExpression(new IntegerTerminal(2),
						new IntegerTerminal(3))));

		System.out.println(i.eval(expr));
	}

	public static void test_52(Interpreter i) {
		Expression expr = new IntersectExpression(new IntegerTerminal(1),
				new IntegerTerminal(1));

		System.out.println(i.eval(expr));
	}

	public static void test_53(Interpreter i) {
		Expression expr = new IntersectExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new CommaExpression(new IntegerTerminal(2),
						new IntegerTerminal(3)));

		System.out.println(i.eval(expr));
	}

	public static void test_54(Interpreter i) {
		Expression expr = new IntersectExpression(new CommaExpression(
				new IntegerTerminal(1), new IntegerTerminal(2)),
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)));

		System.out.println(i.eval(expr));
	}

	public static void test_55(Interpreter i) {
		Expression expr = new IntersectExpression(
				new BagExpression(new CommaExpression(new CommaExpression(
						new StringTerminal("ala"), new IntegerTerminal(2)),
						new IntegerTerminal(3))), new BagExpression(
						new CommaExpression(new IntegerTerminal(2),
								new DoubleTerminal(3.40))));

		System.out.println(i.eval(expr));
	}

}
