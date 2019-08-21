package uk.ac.ncl.safecap.textentry.parser;

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

%state COMMENT

%%


"(" 								{ return symbol(sym.LPAR); }
")" 								{ return symbol(sym.RPAR); }
"[" 								{ return symbol(sym.LSQB); }
"]" 								{ return symbol(sym.RSQB); }
"{" 								{ return symbol(sym.LCUR); }
"}" 								{ return symbol(sym.RCUR); }
":" 								{ return symbol(sym.COLON); }
";" 								{ return symbol(sym.SEMIC); }
"," 								{ return symbol(sym.COMMA); }
"`" 								{ return symbol(sym.RQT); }
"true" 								{ return symbol(sym.XTRUE); }
"false" 							{ return symbol(sym.XFALSE); }
"::" 								{ return symbol(sym.META); }
[a-zA-Z\?\*\_][_a-zA-Z0-9\-\.]*[_a-zA-Z0-9\-]* 	{ return symbol(sym.ID, new String(yytext())); }
[-+]?[0-9]+ 						{ return symbol(sym.NUMBER, new Integer(yytext())); }
[\"]([^\"])*[\"]					{ return symbol(sym.STRING, yytext().substring(1, yytext().length() - 1)); }

[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?	{ return symbol(sym.REAL, new Double(yytext())); }
[ \t\r\n\f] 						{ /* ignore white space. */ }
"//".*[\n]  						{ /* normal comment */ }
"/*"                     			{yybegin(COMMENT);}
<COMMENT> [^"*/"]                   {}
<COMMENT> "*/"                    	{yybegin(YYINITIAL);}
. 									{ return symbol(sym.XERROR); }



