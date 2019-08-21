package uk.ac.ncl.safecap.lldl;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java.math.BigInteger;

%%
%char
%cup
%line
%column
%unicode
%public
%class Scanner
%{

   	private ExtComplexSymbolFactory sf;
	
  	public Scanner(java.io.InputStream r, ExtComplexSymbolFactory sf) {
		this(r);
        	this.sf = sf;
	}

	public Symbol symbol(int code){
		return sf.newSymbol("", code, new Location(yychar, yychar + yylength()),new Location(yyline + 1, yycolumn + 1));
    	}

	public Symbol symbol(int code, Object lexem){
		return sf.newSymbolX("", code, new Location(yychar, yychar + yylength()),new Location(yyline + 1, yycolumn + 1), lexem);
	}
       
%}
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}

%%


"(" 						{ return symbol(sym.LPAR); }
")" 						{ return symbol(sym.RPAR); }
"{" 						{ return symbol(sym.LCUR); }
"}" 						{ return symbol(sym.RCUR); }

"@@@" 						{ return symbol(sym.AND3); }
"@@" 						{ return symbol(sym.AND2); }
"@@@@" 						{ return symbol(sym.AND4); }
"-" 						{ return symbol(sym.MINUS); }

"::" 						{ return symbol(sym.DOUBLE_COLON); }
":" 						{ return symbol(sym.COLON); }
";" 						{ return symbol(sym.SEMI_COLON); }
"," 						{ return symbol(sym.COMMA); }

[a-zA-Z][_a-zA-Z0-9]* 				{ return symbol(sym.ID, new String(yytext())); }
[0-9]+ 						{ return symbol(sym.INTEGER, new BigInteger(yytext())); }
[\"]([^\n\"])*[\"]				{ return symbol(sym.STRING, yytext().substring(1, yytext().length()-1)); }
[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?		{ return symbol(sym.REAL, new Double(yytext())); }
"//".*[\n]  					{ /* normal comment */ }
"(*" [^*] ~"*)"					{ /* multi-line comment */ }
[ \t\r\n\f] 					{ /* ignore white space. */ }
. 								{ /* ignore */ }
