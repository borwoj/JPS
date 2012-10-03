import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NameTerminal extends TerminalExpression implements INameTerminal {

	public NameTerminal(String name) {
		super(name);
	}

	@Override
	public String getName() {
		return (String) value;
	}

}
