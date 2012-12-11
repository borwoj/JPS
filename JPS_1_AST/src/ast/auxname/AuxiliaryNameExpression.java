package ast.auxname;

import ast.Expression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAuxiliaryNameExpression;

public abstract class AuxiliaryNameExpression extends Expression implements
		IAuxiliaryNameExpression {

	private IExpression innerExpression;
	private String auxiliaryName;

	public AuxiliaryNameExpression(IExpression innerExpression,
			String auxiliaryName) {
		this.innerExpression = innerExpression;
		this.auxiliaryName = auxiliaryName;
	}

	@Override
	public String getAuxiliaryName() {
		return auxiliaryName;
	}

	@Override
	public IExpression getInnerExpression() {
		return innerExpression;
	}

}
