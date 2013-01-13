package edu.pjwstk.jps.parser;
  
import java_cup.runtime.Symbol;

import static edu.pjwstk.jps.parser.Symbols.*;
 


%%
%{ 
	private Symbol createToken(int id) {
		return new Symbol(id, yyline, yycolumn);
	}
	private Symbol createToken(int id, Object o) {
		return new Symbol(id, yyline, yycolumn, o);
	}
%}
 
%public
%class Lexer 
%cup
%line 
%column
%char
%eofval{
	return createToken(EOF);
%eofval}

INTEGER = [0-9]+
BOOLEAN = true|false
IDENTIFIER = [_a-zA-Z][0-9a-zA-Z]*
DOUBLE = [0-9]+\.[0-9]+
STRING = [\"][^\"]*[\"]
CHAR = [\'][^\"][\']
LineTerminator = \r|\n|\r\n 
WHITESPACE = {LineTerminator} | [ \t\f]
  
%% 
 
<YYINITIAL> {
	"+"						{ return createToken(PLUS				); }
	"-"						{ return createToken(MINUS				); }
	"*"						{ return createToken(MULTIPLY			); }
	"/"						{ return createToken(DIVIDE				); }
	"("						{ return createToken(LEFT_ROUND_BRACKET	); }
	")"						{ return createToken(RIGHT_ROUND_BRACKET); }	
	 

	{WHITESPACE} { }
	{INTEGER} {
		int val;
		try {
			val = Integer.parseInt(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(INTEGER_LITERAL, new Integer(val));
	}
	{DOUBLE} {
		double val;
		try {
			val = Double.parseDouble(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(DOUBLE_LITERAL, new Double(val));
	}
	{BOOLEAN} {
		boolean val;
		try {
			val = Boolean.parseBoolean(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(BOOLEAN_LITERAL, new Boolean(val));
	} 
}
