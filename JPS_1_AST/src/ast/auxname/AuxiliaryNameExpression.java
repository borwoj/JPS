package ast.auxname;

import ast.Expression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAuxiliaryNameExpression;

public abstract class AuxiliaryNameExpression extends Expression implements
		IAuxiliaryNameExpression {

	String auxiliaryName; // a moze jakis terminal zamiast zwyklego stringa
	Expression innerExp;

	public AuxiliaryNameExpression(Expression innerExp, String auxiliaryName) {
		this.innerExp = innerExp;
		this.auxiliaryName = auxiliaryName;
	}

	@Override
	public String getAuxiliaryName() {
		return auxiliaryName;
	}

	@Override
	public IExpression getInnerExpression() {
		return innerExp;
	}

}
