package uk.ac.ncl.safecap.cteparser;

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


Suffix = "(S)"|"(M)"|"(C)"|"(W)"|"(P)"|"(NP)"|"(SP)" 

NamePattern = [a-zA-Z\@_\-0-9\<\>\/\+\$\#\:\?\!\.\'\`\~\&\*\+\=\^]+

%%

"%%%rwe%%%"							{ return symbol(sym.MAGIC1); }
"%%%pro%%%"							{ return symbol(sym.MAGIC2); }
"(" 								{ return symbol(sym.LPAR); }
")" 								{ return symbol(sym.RPAR); }
"[" 								{ return symbol(sym.LSQB); }
"]" 								{ return symbol(sym.RSQB); }
"{" 								{ return symbol(sym.LCUR); }
"}" 								{ return symbol(sym.RCUR); }
{NamePattern}{Suffix}*				{ return symbol(sym.TOKEN, yytext()); }
[\"]([^\n\"])*[\"]					{ return symbol(sym.TOKEN, yytext().substring(1, yytext().length()-1)); }
[ \t\r\n\f\,] 						{ /* ignore white space. */ }
. 									{ /* ignore everything else. */ }



