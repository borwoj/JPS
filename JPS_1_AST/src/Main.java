import qres.QResStack;
import qres.collection.BagResult;
import qres.single.BinderResult;
import qres.single.DoubleResult;
import qres.single.IntegerResult;
import qres.single.StringResult;
import qres.single.StructResult;
import ast.Expression;
import ast.binary.CommaExpression;
import ast.binary.DotExpression;
import ast.binary.EqualsExpression;
import ast.binary.GreaterThanExpression;
import ast.binary.InExpression;
import ast.binary.WhereExpression;
import ast.terminal.IntegerTerminal;
import ast.terminal.NameTerminal;
import ast.terminal.StringTerminal;
import ast.unary.BagExpression;
import ast.unary.CountExpression;

/**
 * @author £ukasz Kobyliñski (s6709)
 * @author Borys Wojciechowski (s6427)
 **/
public class Main {

	public static void main(String[] args) {
		// mini-projekt 1
		Expression exampleFromPdf = new DotExpression(new DotExpression(
				new WhereExpression(new NameTerminal("osoba"),
						new NameTerminal("¿onaty")),
				new NameTerminal("ksi¹¿ka")), new NameTerminal("autor"));

		// zad. 1
		Expression expression1 = new WhereExpression(new NameTerminal("osoba"),
				new GreaterThanExpression(new CountExpression(new NameTerminal(
						"imie")), new IntegerTerminal(1)));

		// zad. 2
		Expression expression2 = new WhereExpression(new NameTerminal("firma"),
				new InExpression(new NameTerminal("lokalizacja"),
						new BagExpression(new CommaExpression(
								new StringTerminal("Warszawa"),
								new StringTerminal("£ódŸ")))));

		// zad. 3
		Expression expression3 = new InExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))));

		// zad. 4
		Expression expression4 = new DotExpression(new WhereExpression(
				new NameTerminal("firma"), new EqualsExpression(
						new NameTerminal("nazwa"), new StringTerminal("XYZ"))),
				new WhereExpression(new NameTerminal("zatrudnia"),
						new EqualsExpression(new NameTerminal("nazwisko"),
								new StringTerminal("Kowalski"))));

		// mini-projekt 2
		QResStack qres = new QResStack();
		qres.push(new IntegerResult(1));
		qres.push(new IntegerResult(2));
		qres.push(new IntegerResult(3));
		IntegerResult multiRight = (IntegerResult) qres.pop(); // 3
		IntegerResult multiLeft = (IntegerResult) qres.pop(); // 2
		IntegerResult multiRes = new IntegerResult(multiLeft.getValue()
				* multiRight.getValue());
		qres.push(multiRes);
		IntegerResult plusRight = (IntegerResult) qres.pop(); // 6
		IntegerResult plusLeft = (IntegerResult) qres.pop(); // 1
		IntegerResult plusRes = new IntegerResult(plusLeft.getValue()
				+ plusRight.getValue());
		qres.push(plusRes);
		qres.push(new IntegerResult(4));
		IntegerResult minusRight = (IntegerResult) qres.pop(); // 4
		IntegerResult minusLeft = (IntegerResult) qres.pop(); // 7
		IntegerResult minusRes = new IntegerResult(minusLeft.getValue()
				- minusRight.getValue());
		qres.push(minusRes);

		// zad. 1
		QResStack qres1 = new QResStack();
		qres1.push(new IntegerResult(1)); // 1
		qres1.push(new DoubleResult(2.1)); // 2
		IntegerResult structRight = (IntegerResult) qres1.pop();
		IntegerResult structLeft = (IntegerResult) qres1.pop();
		StructResult s1 = new StructResult();
		s1.add(structRight);
		s1.add(structLeft);
		qres1.push(s1); // 3
		StructResult s2 = (StructResult) qres1.pop();
		BagResult b1 = new BagResult();
		b1.add(s2);
		qres1.push(b1); // 4
		qres1.push(new IntegerResult(3)); // 5
		qres1.push(new IntegerResult(4)); // 6
		IntegerResult plusRight2 = (IntegerResult) qres1.pop();
		IntegerResult plusLeft2 = (IntegerResult) qres1.pop(); 
		IntegerResult plusRes2 = new IntegerResult(plusLeft2.getValue() + plusRight2.getValue());
		qres1.push(plusRes2); // 7
		qres1.push(new StringResult("test")); // 8
		StringResult structRight2 = (StringResult) qres1.pop();
		IntegerResult structLeft2 = (IntegerResult) qres1.pop();
		StructResult s3 = new StructResult();
		s3.add(structRight2);
		s3.add(structLeft2);
		qres1.push(s3);  // 9
		StructResult s4 = (StructResult) qres1.pop();
		BagResult b2 = new BagResult();
		b1.add(s4);
		qres1.push(b2); // 10
		BagResult b3 = (BagResult) qres1.pop();
		BagResult b4 = (BagResult) qres1.pop();
		StructResult s5 = new StructResult();
		s5.add(b3);
		s5.add(b4);
		qres1.push(s5); // 11
		StructResult s6 = (StructResult) qres1.pop();
		BinderResult br1 = new BinderResult("nazwa", s6);
		qres1.push(br1); // 12
		
		// zad. 2
		
		// zad. 3

	}
}
