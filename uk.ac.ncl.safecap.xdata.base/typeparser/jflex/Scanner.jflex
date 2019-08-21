package uk.ac.ncl.safecap.xtype;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

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


"("						{ return symbol(sym.LBRACKET, new String(yytext())); }
")"						{ return symbol(sym.RBRACKET, new String(yytext())); }
"*"						{ return symbol(sym.OP_STAR, new String(yytext())); }
"seq"						{ return symbol(sym.OP_SEQ, new String(yytext())); }
"pow"						{ return symbol(sym.OP_POW, new String(yytext())); }
"int"						{ return symbol(sym.INTEGER, new String(yytext())); }
"real"						{ return symbol(sym.REAL, new String(yytext())); }
"bool"						{ return symbol(sym.BOOL, new String(yytext())); }
"'" 						{ return symbol(sym.OP_PRIME); }



[a-zA-Z][0-9a-zA-Z\_]* 				{ return symbol(sym.ID, new String(yytext())); }
[\"]([^\n\"])*[\"]				{ return symbol(sym.STRING, yytext()); }
[ \t\n\r\f]+					{ /* ignore whitespace */ } 
. 						{ return symbol(sym.ERROR); }
