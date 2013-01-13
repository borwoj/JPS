
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Sun Jan 13 14:40:44 CET 2013
//----------------------------------------------------

package edu.pjwstk.jps.parser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.Symbol;
import ast.*;
import ast.binary.*;
import ast.terminal.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Sun Jan 13 14:40:44 CET 2013
  */
public class CalcParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public CalcParser() {super();}

  /** Constructor which sets the default scanner. */
  public CalcParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public CalcParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\010\000\002\002\004\000\002\002\003\000\002\003" +
    "\005\000\002\003\005\000\002\003\005\000\002\003\005" +
    "\000\002\003\003\000\002\003\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\020\000\006\004\005\013\006\001\002\000\004\002" +
    "\022\001\002\000\016\002\ufffb\007\ufffb\010\ufffb\011\ufffb" +
    "\012\ufffb\014\ufffb\001\002\000\006\004\005\013\006\001" +
    "\002\000\014\002\000\007\010\010\011\011\013\012\012" +
    "\001\002\000\006\004\005\013\006\001\002\000\006\004" +
    "\005\013\006\001\002\000\006\004\005\013\006\001\002" +
    "\000\006\004\005\013\006\001\002\000\016\002\ufffd\007" +
    "\ufffd\010\ufffd\011\ufffd\012\ufffd\014\ufffd\001\002\000\016" +
    "\002\ufffc\007\ufffc\010\ufffc\011\ufffc\012\ufffc\014\ufffc\001" +
    "\002\000\016\002\ufffe\007\ufffe\010\ufffe\011\013\012\012" +
    "\014\ufffe\001\002\000\016\002\uffff\007\uffff\010\uffff\011" +
    "\013\012\012\014\uffff\001\002\000\014\007\010\010\011" +
    "\011\013\012\012\014\021\001\002\000\016\002\ufffa\007" +
    "\ufffa\010\ufffa\011\ufffa\012\ufffa\014\ufffa\001\002\000\004" +
    "\002\001\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\020\000\006\002\003\003\006\001\001\000\002\001" +
    "\001\000\002\001\001\000\004\003\017\001\001\000\002" +
    "\001\001\000\004\003\016\001\001\000\004\003\015\001" +
    "\001\000\004\003\014\001\001\000\004\003\013\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$CalcParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$CalcParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$CalcParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


  /** User initialization code. */
  public void user_init() throws java.lang.Exception
    {
 	    lexer = new Lexer(new StringReader(expr)); 
    }

  /** Scan to get the next Symbol. */
  public java_cup.runtime.Symbol scan()
    throws java.lang.Exception
    {
		return lexer.next_token(); 
    }

 
	public Lexer lexer;
	private String expr;
	public Expression RESULT;
	
	public CalcParser(String expr) {
		this.symbolFactory = new DefaultSymbolFactory();   
		this.expr = expr;
	}
	
	void setResult(Expression exp) {
		this.RESULT = exp;
	}  


}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$CalcParser$actions {
  private final CalcParser parser;

  /** Constructor */
  CUP$CalcParser$actions(CalcParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$CalcParser$do_action(
    int                        CUP$CalcParser$act_num,
    java_cup.runtime.lr_parser CUP$CalcParser$parser,
    java.util.Stack            CUP$CalcParser$stack,
    int                        CUP$CalcParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$CalcParser$result;

      /* select the action based on the action number */
      switch (CUP$CalcParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // expr ::= LEFT_ROUND_BRACKET expr RIGHT_ROUND_BRACKET 
            {
              Expression RESULT =null;
		int oleft = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).right;
		String o = (String)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).value;
		int e1left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).right;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).value;
		 RESULT = e1; 
              CUP$CalcParser$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)), ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), RESULT);
            }
          return CUP$CalcParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // expr ::= INTEGER_LITERAL 
            {
              Expression RESULT =null;
		int oleft = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).left;
		int oright = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).right;
		Integer o = (Integer)((java_cup.runtime.Symbol) CUP$CalcParser$stack.peek()).value;
		 RESULT = new IntegerTerminal((Integer)o); 
              CUP$CalcParser$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), RESULT);
            }
          return CUP$CalcParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // expr ::= expr DIVIDE expr 
            {
              Expression RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).right;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).right;
		String o = (String)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).right;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.peek()).value;
		 RESULT = new DivideExpression(e1, e2); 
              CUP$CalcParser$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)), ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), RESULT);
            }
          return CUP$CalcParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // expr ::= expr MULTIPLY expr 
            {
              Expression RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).right;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).right;
		String o = (String)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).right;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.peek()).value;
		 RESULT = new MultiplyExpression(e1, e2); 
              CUP$CalcParser$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)), ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), RESULT);
            }
          return CUP$CalcParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // expr ::= expr MINUS expr 
            {
              Expression RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).right;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).right;
		String o = (String)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).right;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.peek()).value;
		 RESULT = new MinusExpression(e1, e2); 
              CUP$CalcParser$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)), ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), RESULT);
            }
          return CUP$CalcParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // expr ::= expr PLUS expr 
            {
              Expression RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).right;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).right;
		String o = (String)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).right;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.peek()).value;
		 RESULT = new PlusExpression(e1, e2); 
              CUP$CalcParser$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-2)), ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), RESULT);
            }
          return CUP$CalcParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // goal ::= expr 
            {
              Expression RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()).right;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.peek()).value;
		 RESULT = e; parser.setResult(e); 
              CUP$CalcParser$result = parser.getSymbolFactory().newSymbol("goal",0, ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), RESULT);
            }
          return CUP$CalcParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= goal EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).right;
		Expression start_val = (Expression)((java_cup.runtime.Symbol) CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)).value;
		RESULT = start_val;
              CUP$CalcParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$CalcParser$stack.elementAt(CUP$CalcParser$top-1)), ((java_cup.runtime.Symbol)CUP$CalcParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$CalcParser$parser.done_parsing();
          return CUP$CalcParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

