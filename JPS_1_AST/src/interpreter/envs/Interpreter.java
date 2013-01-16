package interpreter.envs;

import interpreter.qres.QResStack;
import result.AbstractQueryResult;
import result.BagResult;
import result.BinderResult;
import result.BooleanResult;
import result.DoubleResult;
import result.IntegerResult;
import result.SequenceResult;
import result.StringResult;
import result.StructResult;
import ast.binary.INAndExpression;
import ast.unary.IEmptyExpression;
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
import edu.pjwstk.jps.result.IBinderResult;
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

	public Interpreter() {
		qres = new QResStack();
		envs = new ENVS();
	}

	public Interpreter(ISBAStore store) {
		qres = new QResStack();
		envs = new ENVS();
		this.store = store;
		envs.init(store.getEntryOID(), store);
	}

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
				innerRes);
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

		// jesli napotykamy false to zwracamy false bez wzgledu na wartosc i typ
		// drugiego argumentu
		if (leftRes instanceof BooleanResult
				&& ((BooleanResult) leftRes).getValue() == false) {
			boolRes = new BooleanResult(false);
			qres.push(boolRes);
			return;
		} else if (leftRes instanceof BooleanResult
				&& rightRes instanceof BooleanResult) {
			BooleanResult left = (BooleanResult) leftRes;
			BooleanResult right = (BooleanResult) rightRes;
			if (left.getValue() == false || right.getValue() == false) {
				boolRes = new BooleanResult(false);
				qres.push(boolRes);
			} else {
				boolRes = new BooleanResult(true);
				qres.push(boolRes);
			}

		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

	}

	public void visitNAndExpression(INAndExpression expr) {
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
			if (left.getValue() == true || right.getValue() == true) {
				boolRes = new BooleanResult(false);
				qres.push(boolRes);
			} else {
				boolRes = new BooleanResult(true);
				qres.push(boolRes);
			}

		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

	}

	public void visitEmptyExpression(IEmptyExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult bag = InterpreterUtils.toBag(innerRes);

		BooleanResult boolRes;
		if (bag.getElements().isEmpty())
			boolRes = new BooleanResult(true);
		else
			boolRes = new BooleanResult(false);

		qres.push(boolRes);
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

		expr.getLeftExpression().accept(this);
		BagResult innerBag = (BagResult) InterpreterUtils.toBag(qres.pop());

		BagResult bagRes = new BagResult();

		bagRes.add(innerBag);
		for (ISingleResult element : innerBag.getElements()) {
			envs.push(envs.nested(element, store));
			expr.getRightExpression().accept(this);

			bagRes.add(qres.pop());

			envs.pop();
		}

		qres.push(bagRes);

	}

	@Override
	public void visitCommaExpression(ICommaExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IBagResult rightBag = InterpreterUtils.toBag(qres.pop());
		IBagResult leftBag = InterpreterUtils.toBag(qres.pop());

		BagResult bagRes = new BagResult();

		for (ISingleResult leftEl : leftBag.getElements()) {
			for (ISingleResult rightEl : rightBag.getElements()) {
				StructResult structRes = new StructResult();
				if (leftEl instanceof StructResult)
					structRes.add(((StructResult) leftEl).elements());
				else
					structRes.add(leftEl);
				if (rightEl instanceof StructResult)
					structRes.add(((StructResult) rightEl).elements());
				else
					structRes.add(rightEl);

				bagRes.add(structRes);
			}
		}

		qres.push(bagRes);
	}

	@Override
	public void visitDivideExpression(IDivideExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		if ((leftRes instanceof IIntegerResult || leftRes instanceof IDoubleResult)
				&& (rightRes instanceof IIntegerResult || rightRes instanceof IDoubleResult)) {

			Number left = leftRes instanceof IIntegerResult ? ((IIntegerResult) leftRes)
					.getValue() : ((IDoubleResult) leftRes).getValue();
			Number right = rightRes instanceof IIntegerResult ? ((IIntegerResult) rightRes)
					.getValue() : ((IDoubleResult) rightRes).getValue();

			double result = left.doubleValue() / right.doubleValue();

			qres.push(result == Math.floor(result) ? new IntegerResult(
					(int) result) : new DoubleResult(result));
		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

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

		BooleanResult boolRes = new BooleanResult(leftRes.equals(rightRes));
		qres.push(boolRes);
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

	@Override
	public void visitInExpression(IInExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		IBagResult leftBag = InterpreterUtils.toBag(leftRes);
		IBagResult rightBag = InterpreterUtils.toBag(rightRes);

		BooleanResult boolRes;

		boolean contains = !leftBag.getElements().retainAll(
				rightBag.getElements());

		boolRes = new BooleanResult(contains);
		qres.push(boolRes);
	}

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

		expr.getLeftExpression().accept(this);

		IBagResult leftRes = InterpreterUtils.toBag(qres.pop());

		BagResult bagRes = new BagResult();

		for (ISingleResult leftEl : leftRes.getElements()) {
			envs.push(envs.nested(leftEl, store));
			expr.getRightExpression().accept(this);
			IBagResult rightRes = InterpreterUtils.toBag(qres.pop());
			for (ISingleResult rightEl : rightRes.getElements()) {
				StructResult structRes = new StructResult();

				if (leftEl instanceof IBinderResult)
					structRes.add(leftEl);
				else
					structRes.add((ISingleResult) InterpreterUtils.deref(
							leftEl, store));

				if (rightEl instanceof IBinderResult)
					structRes.add(rightEl);
				else
					structRes.add((ISingleResult) InterpreterUtils.deref(
							rightEl, store));

				bagRes.add(structRes);
			}
			envs.pop();
		}

		qres.push(bagRes);

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

		if ((leftRes instanceof IIntegerResult || leftRes instanceof IDoubleResult)
				&& (rightRes instanceof IIntegerResult || rightRes instanceof IDoubleResult)) {

			Number left = leftRes instanceof IIntegerResult ? ((IIntegerResult) leftRes)
					.getValue() : ((IDoubleResult) leftRes).getValue();
			Number right = rightRes instanceof IIntegerResult ? ((IIntegerResult) rightRes)
					.getValue() : ((IDoubleResult) rightRes).getValue();

			double result = left.doubleValue() - right.doubleValue();

			qres.push(result == Math.floor(result) ? new IntegerResult(
					(int) result) : new DoubleResult(result));
		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

	}

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
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		if ((leftRes instanceof IIntegerResult || leftRes instanceof IDoubleResult)
				&& (rightRes instanceof IIntegerResult || rightRes instanceof IDoubleResult)) {

			Number left = leftRes instanceof IIntegerResult ? ((IIntegerResult) leftRes)
					.getValue() : ((IDoubleResult) leftRes).getValue();
			Number right = rightRes instanceof IIntegerResult ? ((IIntegerResult) rightRes)
					.getValue() : ((IDoubleResult) rightRes).getValue();

			double result = left.doubleValue() % right.doubleValue();

			qres.push(result == Math.floor(result) ? new IntegerResult(
					(int) result) : new DoubleResult(result));
		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

	}

	@Override
	public void visitMultiplyExpression(IMultiplyExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		if ((leftRes instanceof IIntegerResult || leftRes instanceof IDoubleResult)
				&& (rightRes instanceof IIntegerResult || rightRes instanceof IDoubleResult)) {

			Number left = leftRes instanceof IIntegerResult ? ((IIntegerResult) leftRes)
					.getValue() : ((IDoubleResult) leftRes).getValue();
			Number right = rightRes instanceof IIntegerResult ? ((IIntegerResult) rightRes)
					.getValue() : ((IDoubleResult) rightRes).getValue();

			double result = left.doubleValue() * right.doubleValue();

			qres.push(result == Math.floor(result) ? new IntegerResult(
					(int) result) : new DoubleResult(result));
		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.pjwstk.jps.visitor.ASTVisitor#visitOrderByExpression(edu.pjwstk.jps
	 * .ast.binary.IOrderByExpression)
	 * 
	 * orderby — sortowanie (niealgebraiczny). Ewaluacja: 1. Wykonaj operacje
	 * join. 2. Podnies wynik z QRES. 3. Posortuj otrzymane struktury wg
	 * drugiego pola kazdej z tych struktur, pozniej trzeciego, czwartego, etc.
	 * 3. Usun wszystkie z wyjatkiem pierwszego pola otrzymanych struktur. 4.
	 * Odloz kolekcje struktur na QRES.
	 */
	@Override
	public void visitOrderByExpression(IOrderByExpression expr) {
		expr.getLeftExpression().accept(this);
		BagResult leftRes = (BagResult) InterpreterUtils.toBag(qres.pop());

		SequenceResult seqRes = new SequenceResult();

		for (ISingleResult leftEl : leftRes.getElements()) {
			envs.push(envs.nested(leftEl, store));
			expr.getRightExpression().accept(this);
			BagResult rightRes = (BagResult) InterpreterUtils.toBag(qres.pop());

			for (ISingleResult rightEl : rightRes.getElements()) {
				StructResult structRes = new StructResult();

				if (leftEl instanceof IBinderResult)
					structRes.add(leftEl);
				else
					structRes.add((ISingleResult) InterpreterUtils.deref(
							leftEl, store));

				if (rightEl instanceof IBinderResult)
					structRes.add(rightEl);
				else
					structRes.add((ISingleResult) InterpreterUtils.deref(
							rightEl, store));

				seqRes.add(structRes);
			}
			envs.pop();
		}

//		ArrayList<StructResult> list = new ArrayList<StructResult>();
//		list.addAll((Collection<? extends StructResult>) seqRes.getElements());
//		Collections.sort(list);

		qres.push(seqRes);

	}

	@Override
	public void visitOrExpression(IOrExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		BooleanResult boolRes;

		// jesli napotykamy true to zwracamy true bez wzgledu na wartosc i typ
		// drugiego argumentu
		if (leftRes instanceof BooleanResult
				&& ((BooleanResult) leftRes).getValue() == true) {
			boolRes = new BooleanResult(true);
			qres.push(boolRes);
			return;
		} else if (leftRes instanceof BooleanResult
				&& rightRes instanceof BooleanResult) {
			BooleanResult right = (BooleanResult) rightRes;
			if (right.getValue() == true) {
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
	public void visitPlusExpression(IPlusExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);
		IAbstractQueryResult rightRes = qres.pop();
		IAbstractQueryResult leftRes = qres.pop();

		leftRes = InterpreterUtils.toSingleResult(leftRes);
		leftRes = InterpreterUtils.deref(leftRes, store);
		rightRes = InterpreterUtils.toSingleResult(rightRes);
		rightRes = InterpreterUtils.deref(rightRes, store);

		boolean isLeftNumber = leftRes instanceof IIntegerResult
				|| leftRes instanceof IDoubleResult;
		boolean isRightNumber = rightRes instanceof IIntegerResult
				|| rightRes instanceof IDoubleResult;
		boolean isLeftString = leftRes instanceof IStringResult;
		boolean isRightString = rightRes instanceof IStringResult;
		boolean isLeftBoolean = leftRes instanceof IBooleanResult;
		boolean isRightBoolean = rightRes instanceof IBooleanResult;

		if ((isLeftNumber) && (isRightNumber)) {

			Number left = leftRes instanceof IIntegerResult ? ((IIntegerResult) leftRes)
					.getValue() : ((IDoubleResult) leftRes).getValue();
			Number right = rightRes instanceof IIntegerResult ? ((IIntegerResult) rightRes)
					.getValue() : ((IDoubleResult) rightRes).getValue();
			double result = left.doubleValue() + right.doubleValue();

			qres.push(result == Math.floor(result) ? new IntegerResult(
					(int) result) : new DoubleResult(result));
		} else if (isLeftString && isRightString) {
			String left = ((StringResult) leftRes).getValue();
			String right = ((StringResult) rightRes).getValue();

			qres.push(new StringResult(left + right));
		} else if (isLeftString && isRightBoolean) {
			String left = ((StringResult) leftRes).getValue();
			String right = ((BooleanResult) rightRes).getValue().toString();

			qres.push(new StringResult(left + right));
		} else if (isLeftBoolean && isRightString) {
			String left = ((BooleanResult) leftRes).getValue().toString();
			String right = ((StringResult) rightRes).getValue();

			qres.push(new StringResult(left + right));
		} else if (isLeftString && isRightNumber) {
			String left = ((StringResult) leftRes).getValue();
			double rightNum = rightRes instanceof IIntegerResult ? ((IIntegerResult) rightRes)
					.getValue() : ((IDoubleResult) rightRes).getValue();
			String right = rightNum == Math.floor(rightNum) ? ((IntegerResult) rightRes)
					.getValue().toString() : ((DoubleResult) rightRes)
					.getValue().toString();

			qres.push(new StringResult(left + right));
		} else if (isLeftNumber && isRightString) {
			double leftNum = leftRes instanceof IIntegerResult ? ((IIntegerResult) leftRes)
					.getValue() : ((IDoubleResult) leftRes).getValue();
			String left = leftNum == Math.floor(leftNum) ? ((IntegerResult) leftRes)
					.getValue().toString() : ((DoubleResult) leftRes)
					.getValue().toString();
			String right = ((StringResult) rightRes).getValue();

			qres.push(new StringResult(left + right));
		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, lewy="
					+ leftRes.getClass() + " prawy=" + rightRes.getClass());

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
			if (left.getValue() == !right.getValue()) {
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

	@Override
	public void visitBagExpression(IBagExpression expr) {
		expr.getInnerExpression().accept(this);

		IBagResult bag = InterpreterUtils.toBag(qres.pop());

		BagResult bagRes = new BagResult();
		// jesli struct
		if (bag.getElements().size() == 1
				&& bag.getElements().iterator().next() instanceof IStructResult)
			bagRes.addElements((IStructResult) bag.getElements().iterator()
					.next());
		else
			bagRes.add(bag);

		qres.push(bagRes);

	}

	@Override
	public void visitCountExpression(ICountExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult innerBag = InterpreterUtils.toBag(innerRes);

		qres.push(new IntegerResult(innerBag.getElements().size()));

	}

	@Override
	public void visitExistsExpression(IExistsExpression expr) {
		expr.getInnerExpression().accept(this);
		IAbstractQueryResult innerRes = qres.pop();
		IBagResult bagRes = InterpreterUtils.toBag(innerRes);

		BooleanResult boolRes;

		boolean exists = false;
		if (!bagRes.getElements().isEmpty())
			exists = true;

		boolRes = new BooleanResult(exists);
		qres.push(boolRes);
	}

	@Override
	public void visitMaxExpression(IMaxExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult innerBag = InterpreterUtils.toBag(innerRes);

		boolean maxIsDouble = false;
		Double max = null;
		for (ISingleResult element : innerBag.getElements()) {
			if (element instanceof IIntegerResult) {
				IntegerResult iRes = (IntegerResult) element;
				if (max == null) {
					max = (double) iRes.getValue();
					maxIsDouble = false;
				} else if (iRes.getValue() > max) {
					max = (double) iRes.getValue();
					maxIsDouble = false;
				}
			} else if (element instanceof IDoubleResult) {
				DoubleResult dRes = (DoubleResult) element;
				if (max == null) {
					max = dRes.getValue();
					maxIsDouble = true;
				} else if (dRes.getValue() > max) {
					max = dRes.getValue();
					maxIsDouble = true;
				}
			} else
				throw new RuntimeException(
						"nieprawidlowe typy rezultatow, inner="
								+ innerRes.getClass());

		}

		qres.push(maxIsDouble ? new DoubleResult(max) : new IntegerResult(max
				.intValue()));

	}

	@Override
	public void visitMinExpression(IMinExpression expr) {
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult innerBag = InterpreterUtils.toBag(innerRes);

		boolean minIsDouble = false;
		Double min = null;
		for (ISingleResult element : innerBag.getElements()) {
			if (element instanceof IIntegerResult) {
				IntegerResult iRes = (IntegerResult) element;
				if (min == null) {
					min = (double) iRes.getValue();
					minIsDouble = false;
				} else if (iRes.getValue() < min) {
					min = (double) iRes.getValue();
					minIsDouble = false;
				}
			} else if (element instanceof IDoubleResult) {
				DoubleResult dRes = (DoubleResult) element;
				if (min == null) {
					min = dRes.getValue();
					minIsDouble = true;
				} else if (dRes.getValue() < min) {
					min = dRes.getValue();
					minIsDouble = true;
				}
			} else
				throw new RuntimeException(
						"nieprawidlowe typy rezultatow, inner="
								+ innerRes.getClass());

		}

		qres.push(minIsDouble ? new DoubleResult(min) : new IntegerResult(min
				.intValue()));

	}

	@Override
	public void visitNotExpression(INotExpression expr) {
		expr.getInnerExpression().accept(this);
		IAbstractQueryResult innerRes = qres.pop();
		innerRes = InterpreterUtils.toSingleResult(innerRes);
		innerRes = InterpreterUtils.deref(innerRes, store);

		BooleanResult boolRes;

		if (innerRes instanceof BooleanResult) {
			BooleanResult inner = (BooleanResult) innerRes;

			boolRes = new BooleanResult(!inner.getValue());
			qres.push(boolRes);

		} else
			throw new RuntimeException("nieprawidlowe typy rezultatow, inner="
					+ innerRes.getClass());

	}

	@Override
	public void visitStructExpression(IStructExpression expr) {
		expr.getInnerExpression().accept(this);
		BagResult bagRes = (BagResult) InterpreterUtils.toBag(qres.pop());

		StructResult structRes = new StructResult();

		for (ISingleResult element : bagRes.getElements()) {
			if (bagRes.size() == 1 && element instanceof IStructResult) {
				structRes.add(((IStructResult) element).elements());
				continue;
			}
			structRes.add(element);
		}
		// celowo bag
		BagResult bagResult = new BagResult();
		bagResult.add(structRes);
		qres.push(bagResult);

	}

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
		expr.getInnerExpression().accept(this);

		IAbstractQueryResult innerRes = qres.pop();
		IBagResult innerBag = InterpreterUtils.toBag(innerRes);

		BagResult bagRes = new BagResult();

		for (ISingleResult element : innerBag.getElements()) {
			if (!bagRes.getElements().contains(element))
				bagRes.add(element);
		}

		qres.push(bagRes);
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
