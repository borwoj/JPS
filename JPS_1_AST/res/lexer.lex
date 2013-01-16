package parser;
  
import java_cup.runtime.Symbol;

import static parser.Symbols.*;


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

INTEGER = [-]?[0-9]+
BOOLEAN = true|false
IDENTIFIER = [_a-zA-Z][0-9a-zA-Z]*
DOUBLE = [-]?[0-9]+\.[0-9]+
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
	"%"						{ return createToken(MODULO				); } 
	"=="					{ return createToken(EQUALS				); }
	"!="					{ return createToken(NOT_EQUALS			); }
	"("						{ return createToken(LEFT_ROUND_BRACKET	); }
	")"						{ return createToken(RIGHT_ROUND_BRACKET); }	
	"OR"|"or"|"\|\|"		{ return createToken(OR					); }
	"AND"|"and"|"&&"		{ return createToken(AND				); }
	"SUM"|"sum"				{ return createToken(SUM				); } 
	"AVG"|"avg"				{ return createToken(AVG				); } 
	"UNIQUE"|"unique"		{ return createToken(UNIQUE				); } 
	"UNION"|"union"			{ return createToken(UNION				); } 
	"MIN"|"min"				{ return createToken(MIN				); }
	"MAX"|"max"				{ return createToken(MAX				); } 
	"COUNT"|"count"			{ return createToken(COUNT				); }
	"AS"|"as"				{ return createToken(AS					); }
	"GROUP AS"|"group as" 	{ return createToken(GROUP_AS			); }
	"BAG"|"bag"				{ return createToken(BAG				); }
	"."                     { return createToken(DOT                ); }
	">"                     { return createToken(MORE               ); }
    "<"                     { return createToken(LESS               ); }
    ">="                    { return createToken(MORE_OR_EQUAL      ); }
    "<="                    { return createToken(LESS_OR_EQUAL      ); }
    ","                     { return createToken(COMMA              ); }
	"IN"|"in"				{ return createToken(IN					); }
	"WHERE"|"where"			{ return createToken(WHERE				); } 
	"EXISTS"|"exists"		{ return createToken(EXISTS				); }
	"NOT"|"not"|"!"			{ return createToken(NOT				); }
	"MINUS"|"minus" 		{ return createToken(MINUS_FUNCTION		); }
	"STRUCT"|"struct" 		{ return createToken(STRUCT				); }
	"INTERSECT"|"intersect" { return createToken(INTERSECT			); }
	"JOIN"|"join" 	 		{ return createToken(JOIN				); }
	"ALL"|"all" 			{ return createToken(FORALL				); }
	"ANY"|"any"  			{ return createToken(FORANY				); }
	"ORDER BY"|"order by" 	{ return createToken(ORDER_BY			); }
	"close by"|"CLOSE BY" 	{ return createToken(CLOSE_BY			); }
	"xor"|"XOR" 			{ return createToken(XOR				); }

	{WHITESPACE} { }
	{STRING} {return createToken(STRING_LITERAL, yytext().substring(1,yytext().length()-1)) ; }
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
	{IDENTIFIER} {  
        return createToken(IDENTIFIER, yytext());
    }
}
