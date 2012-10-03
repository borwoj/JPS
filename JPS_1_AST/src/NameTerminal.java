import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NameTerminal extends TerminalExpression implements INameTerminal {
	String name;

	public NameTerminal(String name) {

	}

	@Override
	public String getName() {
		return name;
	}

}
