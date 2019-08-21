package uk.ac.ncl.safecap.prover.tactic.parser;

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

"(" 						{ return symbol(sym.LPAR); }
")" 						{ return symbol(sym.RPAR); }
"[" 						{ return symbol(sym.LSQB); }
"]" 						{ return symbol(sym.RSQB); }
"{" 						{ return symbol(sym.LCUR); }
"}" 						{ return symbol(sym.RCUR); }

"--" 						{ return symbol(sym.EXC); }
"++" 						{ return symbol(sym.INC); }
">" 						{ return symbol(sym.LSS); }
"<<" 						{ return symbol(sym.TOP); }
">>" 						{ return symbol(sym.BOT); }
 

[\$a-zA-Z][_a-zA-Z0-9]* 	{ return symbol(sym.ID, new String(yytext())); }
[\"]([^\n\"])*[\"]			{ return symbol(sym.STRING, yytext()); }
[ \t\r\n\f] 				{ /* ignore white space. */ }
"#".*[\n]  					{ /* normal comment */ }
. 							{ return symbol(sym.ERROR); }
