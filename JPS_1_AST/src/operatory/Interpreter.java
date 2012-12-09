package operatory;

import result.BagResult;
import result.BooleanResult;
import result.DoubleResult;
import result.IntegerResult;
import result.StringResult;
import interpreter.envs.ENVS;
import interpreter.qres.QResStack;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;
import edu.pjwstk.jps.ast.auxname.IGroupAsExpression;
import edu.pjwstk.jps.ast.binary.IAndExpression;
import edu.pjwstk.jps.ast.binary.ICloseByExpression;
import edu.pjwstk.jps.ast.binary.ICommaExpression;
import edu.pjwstk.jps.ast.binary.IDivideExpression;
import edu.pjwstk.jps.ast.binary.IDotExpression;
import edu.pjwstk.jps.ast.binary.IEqualsExpression;
import edu.pjwstk.jps.ast.binary.IForAllExpression;
import edu.pjwstk.jps.ast.binary.IForAnyExpression;
import edu.pjwstk.jps.ast.binary.IGreaterOrEqualThanExpression;
import edu.pjwstk.jps.ast.binary.IGreaterThanExpression;
import edu.pjwstk.jps.ast.binary.IInExpression;
import edu.pjwstk.jps.ast.binary.IIntersectExpression;
import edu.pjwstk.jps.ast.binary.IJoinExpression;
import edu.pjwstk.jps.ast.binary.ILessOrEqualThanExpression;
import edu.pjwstk.jps.ast.binary.ILessThanExpression;
import edu.pjwstk.jps.ast.binary.IMinusExpression;
import edu.pjwstk.jps.ast.binary.IMinusSetExpression;
import edu.pjwstk.jps.ast.binary.IModuloExpression;
import edu.pjwstk.jps.ast.binary.IMultiplyExpression;
import edu.pjwstk.jps.ast.binary.INotEqualsExpression;
import edu.pjwstk.jps.ast.binary.IOrExpression;
import edu.pjwstk.jps.ast.binary.IOrderByExpression;
import edu.pjwstk.jps.ast.binary.IPlusExpression;
import edu.pjwstk.jps.ast.binary.IUnionExpression;
import edu.pjwstk.jps.ast.binary.IWhereExpression;
import edu.pjwstk.jps.ast.binary.IXORExpression;
import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;
import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;
import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;
import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.ast.terminal.IStringTerminal;
import edu.pjwstk.jps.ast.unary.IAvgExpression;
import edu.pjwstk.jps.ast.unary.IBagExpression;
import edu.pjwstk.jps.ast.unary.ICountExpression;
import edu.pjwstk.jps.ast.unary.IExistsExpression;
import edu.pjwstk.jps.ast.unary.IMaxExpression;
import edu.pjwstk.jps.ast.unary.IMinExpression;
import edu.pjwstk.jps.ast.unary.INotExpression;
import edu.pjwstk.jps.ast.unary.IStructExpression;
import edu.pjwstk.jps.ast.unary.ISumExpression;
import edu.pjwstk.jps.ast.unary.IUniqueExpression;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IInterpreter;
import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBooleanResult;
import edu.pjwstk.jps.result.IDoubleResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStringResult;

public class Interpreter implements IInterpreter {
	private IQResStack qres;
	private IENVS envs;
	private ISBAStore store;

	public Interpreter(ISBAStore store) {
		qres = new QResStack();
		envs = new ENVS();
		this.store = store;
		envs.init(store.getEntryOID(), store);
	}

	@Override
	public void visitAsExpression(IAsExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitGroupAsExpression(IGroupAsExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitAllExpression(IForAllExpression expr) {
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = qres.pop();
		IBagResult leftBag = InterpreterUtils.toBag(leftRes);

		BooleanResult boolRes;
		boolean forAll = true;

		for (ISingleResult leftEl : leftBag.getElements()) {
			envs.nested(leftEl, store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = qres.pop();
			rightRes = InterpreterUtils.toSingleResult(rightRes);
			rightRes = InterpreterUtils.deref(rightRes, store);

			if (rightRes instanceof IBooleanResult) {
				if (((IBooleanResult) rightRes).getValue() == false) {
					forAll = false;
					break;

				}
			}
			envs.pop();
		}

		boolRes = new BooleanResult(forAll);
		qres.push(boolRes);

	}

	@Override
	public void visitAndExpression(IAndExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitAnyExpression(IForAnyExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCloseByExpression(ICloseByExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCommaExpression(ICommaExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitDivideExpression(IDivideExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitDotExpression(IDotExpression expr) {
		expr.getLeftExpression().accept(this);
		IAbstractQueryResult leftRes = qres.pop();
		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		BagResult dotRes = new BagResult();
		for (ISingleResult leftEl : leftBag.getElements()) {
			envs.nested(leftEl, store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = qres.pop();
			dotRes.add(rightRes);
			envs.pop();
		}
		qres.push(dotRes);
	}

	@Override
	public void visitEqualsExpression(IEqualsExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Integer lInt = leftInt.getValue();
			Integer rInt = rightInt.getValue();
			Boolean result = false;
			if (lInt.equals(rInt)) {
				result = true;
			}
			BooleanResult res = new BooleanResult(result);
			qres.push(res);
		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Double lInt = leftDouble.getValue();
			Double rInt = rightDouble.getValue();
			Boolean result = false;
			if (lInt.equals(rInt)) {
				result = true;
			}
			BooleanResult res = new BooleanResult(result);
			qres.push(res);
		} else if (leftRes instanceof IStringResult
				&& rightRes instanceof IStringResult) {
			IStringResult leftString = (IStringResult) leftRes;
			IStringResult rightString = (IStringResult) rightRes;
			String lInt = leftString.getValue();
			String rInt = rightString.getValue();
			Boolean result = false;
			if (lInt.equals(rInt)) {
				result = true;
			}
			BooleanResult res = new BooleanResult(result);
			qres.push(res);
		} else {
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());
		}

	}

	@Override
	public void visitGreaterThanExpression(IGreaterThanExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitGreaterOrEqualThanExpression(
			IGreaterOrEqualThanExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitInExpression(IInExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitIntersectExpression(IIntersectExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitJoinExpression(IJoinExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitLessOrEqualThanExpression(ILessOrEqualThanExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitLessThanExpression(ILessThanExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitMinusExpression(IMinusExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Integer lInt = leftInt.getValue();
			Integer rInt = rightInt.getValue();
			Integer result = lInt - rInt;
			IntegerResult res = new IntegerResult(result);
			qres.push(res);
		} else if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IDoubleResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Integer lInt = leftInt.getValue();
			Double rDouble = rightDouble.getValue();
			Double result = lInt - rDouble;
			DoubleResult res = new DoubleResult(result);
			qres.push(res);
		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IIntegerResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Integer rInt = rightInt.getValue();
			Double result = lDouble - rInt;
			DoubleResult res = new DoubleResult(result);
			qres.push(res);
		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Double rDouble = rightDouble.getValue();
			Double result = lDouble - rDouble;
			DoubleResult res = new DoubleResult(result);
			qres.push(res);
		} else {
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());
		}

	}

	@Override
	public void visitMinusSetExpression(IMinusSetExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitModuloExpression(IModuloExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitMultiplyExpression(IMultiplyExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitNotEqualsExpression(INotEqualsExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Integer lInt = leftInt.getValue();
			Integer rInt = rightInt.getValue();
			Boolean result = false;
			if (!lInt.equals(rInt)) {
				result = true;
			}
			BooleanResult res = new BooleanResult(result);
			qres.push(res);
		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Double lInt = leftDouble.getValue();
			Double rInt = rightDouble.getValue();
			Boolean result = false;
			if (!lInt.equals(rInt)) {
				result = true;
			}
			BooleanResult res = new BooleanResult(result);
			qres.push(res);
		} else if (leftRes instanceof IStringResult
				&& rightRes instanceof IStringResult) {
			IStringResult leftString = (IStringResult) leftRes;
			IStringResult rightString = (IStringResult) rightRes;
			String lInt = leftString.getValue();
			String rInt = rightString.getValue();
			Boolean result = false;
			if (!lInt.equals(rInt)) {
				result = true;
			}
			BooleanResult res = new BooleanResult(result);
			qres.push(res);
		} else {
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());
		}
	}

	@Override
	public void visitOrderByExpression(IOrderByExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitOrExpression(IOrExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitPlusExpression(IPlusExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Integer lInt = leftInt.getValue();
			Integer rInt = rightInt.getValue();
			Integer result = lInt + rInt;
			IntegerResult res = new IntegerResult(result);
			qres.push(res);
		} else if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IDoubleResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Integer lInt = leftInt.getValue();
			Double rDouble = rightDouble.getValue();
			Double result = lInt + rDouble;
			DoubleResult res = new DoubleResult(result);
			qres.push(res);
		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IIntegerResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Integer rInt = rightInt.getValue();
			Double result = lDouble + rInt;
			DoubleResult res = new DoubleResult(result);
			qres.push(res);
		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Double rDouble = rightDouble.getValue();
			Double result = lDouble + rDouble;
			DoubleResult res = new DoubleResult(result);
			qres.push(res);
		} else if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IStringResult) {
			IIntegerResult leftInteger = (IIntegerResult) leftRes;
			IStringResult rightString = (IStringResult) rightRes;
			Integer lInt = leftInteger.getValue();
			String rString = rightString.getValue();
			String result = lInt + rString;
			StringResult res = new StringResult(result);
			qres.push(res);
		} else if (leftRes instanceof IStringResult
				&& rightRes instanceof IIntegerResult) {
			IStringResult leftString = (IStringResult) leftRes;
			IIntegerResult rightInteger = (IIntegerResult) rightRes;
			String lString = leftString.getValue();
			Integer rInt = rightInteger.getValue();
			String result = lString + rInt;
			StringResult res = new StringResult(result);
			qres.push(res);
		} else if (leftRes instanceof IStringResult
				&& rightRes instanceof IStringResult) {
			IStringResult leftString = (IStringResult) leftRes;
			IStringResult rightString = (IStringResult) rightRes;
			String lString = leftString.getValue();
			String rString = rightString.getValue();
			String result = lString + rString;
			StringResult res = new StringResult(result);
			qres.push(res);
		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IStringResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IStringResult rightString = (IStringResult) rightRes;
			Double lDouble = leftDouble.getValue();
			String rString = rightString.getValue();
			String result = lDouble + rString;
			StringResult res = new StringResult(result);
			qres.push(res);
		} else if (leftRes instanceof IStringResult
				&& rightRes instanceof IDoubleResult) {
			IStringResult leftString = (IStringResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			String lString = leftString.getValue();
			Double rDouble = rightDouble.getValue();
			String result = lString + rDouble;
			StringResult res = new StringResult(result);
			qres.push(res);
		} else {
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());
		}

	}

	@Override
	public void visitUnionExpression(IUnionExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitWhereExpression(IWhereExpression expr) {
		expr.getLeftExpression().accept(this);
		IAbstractQueryResult leftRes = qres.pop();
		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		BagResult whereRes = new BagResult();
		for (ISingleResult leftEl : leftBag.getElements()) {
			envs.nested(leftEl, store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = qres.pop();
			rightRes = InterpreterUtils.toSingleResult(rightRes);
			rightRes = InterpreterUtils.deref(rightRes, store);
			if (rightRes instanceof IBooleanResult) {
				boolean val = ((IBooleanResult) rightRes).getValue();
				if (val) {
					whereRes.add(leftEl);
				}

			}
			envs.pop();
		}
		qres.push(whereRes);
	}

	@Override
	public void visitXORExpression(IXORExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitBooleanTerminal(IBooleanTerminal expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitDoubleTerminal(IDoubleTerminal expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitIntegerTerminal(IIntegerTerminal expr) {
		Integer value = expr.getValue();
		qres.push(new IntegerResult(value));
	}

	@Override
	public void visitNameTerminal(INameTerminal expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitStringTerminal(IStringTerminal expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitBagExpression(IBagExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCountExpression(ICountExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitExistsExpression(IExistsExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitMaxExpression(IMaxExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitMinExpression(IMinExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitNotExpression(INotExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitStructExpression(IStructExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitSumExpression(ISumExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitUniqueExpression(IUniqueExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitAvgExpression(IAvgExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public IAbstractQueryResult eval(IExpression queryTreeRoot) {
		queryTreeRoot.accept(this);
		return qres.pop();
	}

}
