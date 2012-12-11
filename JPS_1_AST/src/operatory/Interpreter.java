package operatory;

import interpreter.envs.ENVS;
import interpreter.qres.QResStack;
import result.AbstractQueryResult;
import result.BagResult;
import result.BinderResult;
import result.BooleanResult;
import result.CollectionResult;
import result.DoubleResult;
import result.IntegerResult;
import result.StringResult;
import result.StructResult;
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
import edu.pjwstk.jps.result.IStructResult;

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

	// fixed
	@Override
	public void visitAsExpression(IAsExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult bag = InterpreterUtils.toBag(innerRes);

		BagResult bagRes = new BagResult();

		for (ISingleResult el : bag.getElements()) {
			bagRes.add(new BinderResult(expr.getAuxiliaryName(),
					(AbstractQueryResult) el));

		}

		qres.push(bagRes);
	}

	@Override
	public void visitGroupAsExpression(IGroupAsExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		BinderResult bindRes = new BinderResult(expr.getAuxiliaryName(),
				(AbstractQueryResult) innerRes);
		qres.push(bindRes);

	}

	@Override
	public void visitAllExpression(IForAllExpression expr) {
		expr.getLeftExpression().accept(this);
		// System.out.println(qres);
		IAbstractQueryResult leftRes = qres.pop();
		IBagResult leftBag = InterpreterUtils.toBag(leftRes);

		BooleanResult boolRes;
		boolean forAll = true;

		for (ISingleResult leftEl : leftBag.getElements()) {
			envs.push(envs.nested(leftEl, store));
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = qres.pop();
			rightRes = InterpreterUtils.toSingleResult(rightRes);
			rightRes = InterpreterUtils.deref(rightRes, store);

			if (rightRes instanceof IBooleanResult) {
				if (((IBooleanResult) rightRes).getValue() == false) {
					forAll = false;
					break;

				}
			} else
				throw new RuntimeException(
						"nieprawidlowe typy rezultatow, lewy="
								+ leftRes.getClass() + " prawy="
								+ rightRes.getClass());
			envs.pop();
		}

		boolRes = new BooleanResult(forAll);
		qres.push(boolRes);

	}

	@Override
	public void visitAndExpression(IAndExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		BooleanResult boolRes;

		if (leftRes instanceof BooleanResult
				&& rightRes instanceof BooleanResult) {
			BooleanResult left = (BooleanResult) leftRes;
			BooleanResult right = (BooleanResult) rightRes;

			if (left.getValue() && right.getValue()) {
				boolRes = new BooleanResult(true);
				qres.push(boolRes);
			} else {
				boolRes = new BooleanResult(false);
				qres.push(boolRes);
			}
		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

	}

	@Override
	public void visitAnyExpression(IForAnyExpression expr) {
		expr.getLeftExpression().accept(this);

		IAbstractQueryResult leftRes = qres.pop();
		IBagResult leftBag = InterpreterUtils.toBag(leftRes);

		BooleanResult boolRes;
		boolean forAny = false;

		for (ISingleResult leftEl : leftBag.getElements()) {
			envs.push(envs.nested(leftEl, store));
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = qres.pop();
			rightRes = InterpreterUtils.toSingleResult(rightRes);
			rightRes = InterpreterUtils.deref(rightRes, store);

			if (rightRes instanceof IBooleanResult) {
				if (((IBooleanResult) rightRes).getValue() == true) {
					forAny = true;
					break;

				}
			}

			else
				throw new RuntimeException(
						"nieprawidlowe typy rezultatow, lewy="
								+ leftRes.getClass() + " prawy="
								+ rightRes.getClass());

			envs.pop();
		}

		boolRes = new BooleanResult(forAny);
		qres.push(boolRes);

	}

	@Override
	public void visitCloseByExpression(ICloseByExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCommaExpression(ICommaExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		IBagResult rightBag = InterpreterUtils.toBag(rightRes);

		BagResult bagRes = new BagResult();

		for (IAbstractQueryResult leftEl : leftBag.getElements()) {
			for (IAbstractQueryResult rightEl : rightBag.getElements()) {
				StructResult structRes = new StructResult();
				if (leftEl instanceof IStructResult) {
					IStructResult polaStruktury = new StructResult();
					for (IAbstractQueryResult pole : polaStruktury.elements()) {
						structRes.add((ISingleResult) pole);
					}
				} else
					structRes.add((ISingleResult) leftEl);

				if (rightEl instanceof IStructResult) {
					IStructResult polaStruktury = new StructResult();
					for (IAbstractQueryResult pole : polaStruktury.elements()) {
						structRes.add((ISingleResult) pole);
					}
				} else
					structRes.add((ISingleResult) rightEl);

				bagRes.add(structRes);
			}
		}

		qres.push(bagRes);
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
			envs.push(envs.nested(leftEl, store));
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
			Double lDouble = leftDouble.getValue();
			Double rDouble = rightDouble.getValue();
			Boolean result = false;
			if (lDouble.equals(rDouble)) {
				result = true;
			}
			BooleanResult res = new BooleanResult(result);
			qres.push(res);
		} else if (leftRes instanceof IStringResult
				&& rightRes instanceof IStringResult) {
			IStringResult leftString = (IStringResult) leftRes;
			IStringResult rightString = (IStringResult) rightRes;
			String lStr = leftString.getValue();
			String rStr = rightString.getValue();
			Boolean result = false;
			if (lStr.equals(rStr)) {
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
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		boolean result = false;
		if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Integer lInt = leftInt.getValue();
			Integer rInt = rightInt.getValue();
			if (lInt > rInt) {
				result = true;
			}

		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Double rDouble = rightDouble.getValue();
			if (lDouble > rDouble) {
				result = true;
			}

		} else if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IDoubleResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Integer lDouble = leftInt.getValue();
			Double rDouble = rightDouble.getValue();
			if (lDouble > rDouble) {
				result = true;
			}

		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IIntegerResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Integer rDouble = rightInt.getValue();
			if (lDouble > rDouble) {
				result = true;
			}

		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

		BooleanResult res = new BooleanResult(result);
		qres.push(res);

	}

	@Override
	public void visitGreaterOrEqualThanExpression(
			IGreaterOrEqualThanExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		boolean result = false;
		if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Integer lInt = leftInt.getValue();
			Integer rInt = rightInt.getValue();
			if (lInt >= rInt) {
				result = true;
			}

		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Double rDouble = rightDouble.getValue();
			if (lDouble >= rDouble) {
				result = true;
			}

		} else if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IDoubleResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Integer lDouble = leftInt.getValue();
			Double rDouble = rightDouble.getValue();
			if (lDouble >= rDouble) {
				result = true;
			}

		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IIntegerResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Integer rDouble = rightInt.getValue();
			if (lDouble >= rDouble) {
				result = true;
			}

		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

		BooleanResult res = new BooleanResult(result);
		qres.push(res);

	}

	// fixed
	@Override
	public void visitInExpression(IInExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		IBagResult rightBag = InterpreterUtils.toBag(rightRes);

		BooleanResult boolRes;

		boolean contains = leftBag.getElements().retainAll(
				rightBag.getElements());

		boolRes = new BooleanResult(contains);
		qres.push(boolRes);
	}

	// fixed
	@Override
	public void visitIntersectExpression(IIntersectExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		IBagResult rightBag = InterpreterUtils.toBag(rightRes);

		// BagResult bagRes = new BagResult();

		leftBag.getElements().retainAll(rightBag.getElements());

		qres.push(leftBag);

	}

	@Override
	public void visitJoinExpression(IJoinExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitLessOrEqualThanExpression(ILessOrEqualThanExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		boolean result = false;
		if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Integer lInt = leftInt.getValue();
			Integer rInt = rightInt.getValue();
			if (lInt <= rInt) {
				result = true;
			}

		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Double rDouble = rightDouble.getValue();
			if (lDouble <= rDouble) {
				result = true;
			}

		} else if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IDoubleResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Integer lDouble = leftInt.getValue();
			Double rDouble = rightDouble.getValue();
			if (lDouble <= rDouble) {
				result = true;
			}

		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IIntegerResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Integer rDouble = rightInt.getValue();
			if (lDouble <= rDouble) {
				result = true;
			}

		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

		BooleanResult res = new BooleanResult(result);
		qres.push(res);

	}

	@Override
	public void visitLessThanExpression(ILessThanExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		boolean result = false;
		if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Integer lInt = leftInt.getValue();
			Integer rInt = rightInt.getValue();
			if (lInt < rInt) {
				result = true;
			}

		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Double rDouble = rightDouble.getValue();
			if (lDouble < rDouble) {
				result = true;
			}

		} else if (leftRes instanceof IIntegerResult
				&& rightRes instanceof IDoubleResult) {
			IIntegerResult leftInt = (IIntegerResult) leftRes;
			IDoubleResult rightDouble = (IDoubleResult) rightRes;
			Integer lDouble = leftInt.getValue();
			Double rDouble = rightDouble.getValue();
			if (lDouble < rDouble) {
				result = true;
			}

		} else if (leftRes instanceof IDoubleResult
				&& rightRes instanceof IIntegerResult) {
			IDoubleResult leftDouble = (IDoubleResult) leftRes;
			IIntegerResult rightInt = (IIntegerResult) rightRes;
			Double lDouble = leftDouble.getValue();
			Integer rDouble = rightInt.getValue();
			if (lDouble < rDouble) {
				result = true;
			}

		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

		BooleanResult res = new BooleanResult(result);
		qres.push(res);

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

	// fixed
	@Override
	public void visitMinusSetExpression(IMinusSetExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		IBagResult rightBag = InterpreterUtils.toBag(rightRes);

		// BagResult bagRes = new BagResult();

		leftBag.getElements().removeAll(rightBag.getElements());

		qres.push(leftBag);

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
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		IBagResult rightBag = InterpreterUtils.toBag(rightRes);

		BagResult bagRes = new BagResult();

		for (IAbstractQueryResult el : leftBag.getElements()) {
			bagRes.add(el);
		}
		for (IAbstractQueryResult el : rightBag.getElements()) {
			bagRes.add(el);
		}
		qres.push(bagRes);
	}

	@Override
	public void visitWhereExpression(IWhereExpression expr) {
		expr.getLeftExpression().accept(this);
		IAbstractQueryResult leftRes = qres.pop();
		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		BagResult whereRes = new BagResult();
		for (ISingleResult leftEl : leftBag.getElements()) {
			envs.push(envs.nested(leftEl, store));
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
		Boolean value = expr.getValue();
		qres.push(new BooleanResult(value));

	}

	@Override
	public void visitDoubleTerminal(IDoubleTerminal expr) {
		Double value = expr.getValue();
		qres.push(new DoubleResult(value));

	}

	@Override
	public void visitIntegerTerminal(IIntegerTerminal expr) {
		Integer value = expr.getValue();
		qres.push(new IntegerResult(value));
	}

	@Override
	public void visitNameTerminal(INameTerminal expr) {
		qres.push(envs.bind(expr.getName()));

	}

	@Override
	public void visitStringTerminal(IStringTerminal expr) {
		String value = expr.getValue();
		qres.push(new StringResult(value));

	}

	// TODO
	@Override
	public void visitBagExpression(IBagExpression expr) {
		expr.getInnerExpression().accept(this);

		CollectionResult res = InterpreterUtils.toCollectionResult(qres.pop());

		BagResult eres = new BagResult();
		if (res.size() == 1
				&& res.getElements().iterator().next() instanceof IStructResult) {
			eres.addElements((IStructResult) res.getElements().iterator()
					.next());
		} else {
			eres.add(res);
		}

		qres.push(eres);

	}

	@Override
	public void visitCountExpression(ICountExpression expr) {
		// TODO Auto-generated method stub

	}

	// fixed
	@Override
	public void visitExistsExpression(IExistsExpression expr) {
		expr.getInnerExpression().accept(this);
		IAbstractQueryResult innerRes = qres.pop();
		IBagResult bagRes = InterpreterUtils.toBag(innerRes);

		BooleanResult boolRes;

		boolean exists = false;
		if (bagRes.getElements().size() > 0)
			exists = true;

		boolRes = new BooleanResult(exists);
		qres.push(boolRes);
	}

	@Override
	public void visitMaxExpression(IMaxExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult innerBag = InterpreterUtils.toBag(innerRes);

		DoubleResult doubleRes;

		Double max = null;
		for (ISingleResult element : innerBag.getElements()) {
			if (element instanceof IIntegerResult) {
				IntegerResult iRes = (IntegerResult) element;
				if (max == null)
					max = (double) iRes.getValue();
				else if (iRes.getValue() > max)
					max = (double) iRes.getValue();
			} else if (element instanceof IDoubleResult) {
				DoubleResult dRes = (DoubleResult) element;
				if (max == null)
					max = dRes.getValue();
				else if (dRes.getValue() > max)
					max = dRes.getValue();
			} else
				throw new RuntimeException(
						"nieprawidlowe typy rezultatow, inner="
								+ innerRes.getClass());

		}

		doubleRes = new DoubleResult(max);
		qres.push(doubleRes);

	}

	@Override
	public void visitMinExpression(IMinExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult innerBag = InterpreterUtils.toBag(innerRes);

		DoubleResult doubleRes;

		Double min = null;
		for (ISingleResult element : innerBag.getElements()) {
			if (element instanceof IIntegerResult) {
				IntegerResult iRes = (IntegerResult) element;
				if (min == null)
					min = (double) iRes.getValue();
				else if (iRes.getValue() < min)
					min = (double) iRes.getValue();
			} else if (element instanceof IDoubleResult) {
				DoubleResult dRes = (DoubleResult) element;
				if (min == null)
					min = dRes.getValue();
				else if (dRes.getValue() < min)
					min = dRes.getValue();
			} else
				throw new RuntimeException(
						"nieprawidlowe typy rezultatow, inner="
								+ innerRes.getClass());

		}

		doubleRes = new DoubleResult(min);
		qres.push(doubleRes);

	}

	@Override
	public void visitNotExpression(INotExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitStructExpression(IStructExpression expr) {
		// TODO Auto-generated method stub

	}

	// fixed
	@Override
	public void visitSumExpression(ISumExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult innerBag = InterpreterUtils.toBag(innerRes);

		double sum = 0;
		boolean foundDouble = false;

		for (ISingleResult element : innerBag.getElements()) {
			if (element instanceof IIntegerResult) {
				IntegerResult iRes = (IntegerResult) element;
				sum += (double) iRes.getValue();
			} else if (element instanceof IDoubleResult) {
				foundDouble = true;
				DoubleResult dRes = (DoubleResult) element;
				sum += dRes.getValue();
			} else
				throw new RuntimeException(
						"nieprawidlowe typy rezultatow, inner="
								+ innerRes.getClass());

		}

		if (foundDouble) {
			DoubleResult doubleRes = new DoubleResult(sum);
			qres.push(doubleRes);
		} else {
			IntegerResult intRes = new IntegerResult((int) sum);
			qres.push(intRes);
		}

	}

	@Override
	public void visitUniqueExpression(IUniqueExpression expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitAvgExpression(IAvgExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult innerBag = InterpreterUtils.toBag(innerRes);

		DoubleResult doubleRes;

		double sum = 0;

		for (ISingleResult element : innerBag.getElements()) {
			if (element instanceof IIntegerResult) {
				IntegerResult iRes = (IntegerResult) element;
				sum += (double) iRes.getValue();
			} else if (element instanceof IDoubleResult) {
				DoubleResult dRes = (DoubleResult) element;
				sum += dRes.getValue();
			} else
				throw new RuntimeException(
						"nieprawidlowe typy rezultatow, inner="
								+ innerRes.getClass());

		}

		doubleRes = new DoubleResult(sum / innerBag.getElements().size());
		qres.push(doubleRes);

	}

	@Override
	public IAbstractQueryResult eval(IExpression queryTreeRoot) {
		queryTreeRoot.accept(this);
		return qres.pop();
	}

	public String toString() {
		return "" + qres;
	}

}
