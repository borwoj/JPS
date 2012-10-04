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
						new BagExpression(new CommaExpression(new NameTerminal(
								"Warszawa"), new NameTerminal("£ódŸ")))));
		
		// zad. 3
		Expression expression3 = new InExpression(
				new BagExpression(
						new CommaExpression(
								new IntegerTerminal(1), 
								new IntegerTerminal(2)
						)
				), 
				new BagExpression(
						new CommaExpression(
								new CommaExpression(
										new IntegerTerminal(1), 
										new IntegerTerminal(2)
								), 
								new IntegerTerminal(3))
				)
		);

		// zad. 4
		Expression expression4 = new DotExpression(
				new WhereExpression(
						new NameTerminal("firma"), 
						new EqualsExpression(
								new NameTerminal("nazwa"), 
								new StringTerminal("XYZ")
						)
				), 
				new WhereExpression(
						new NameTerminal("zatrudnia"), 
						new EqualsExpression(
								new NameTerminal("nazwisko"), 
								new StringTerminal("Kowalski")
						)
				)
		);
	}
}
