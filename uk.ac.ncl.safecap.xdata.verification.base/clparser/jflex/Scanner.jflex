package uk.ac.ncl.prime.sim.lang.parser;

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

"%%21%%"					{ return symbol(sym.MAGIC_SUB); }
"%%24%%"					{ return symbol(sym.MAGIC_TYP); }

"(" 						{ return symbol(sym.LPAR); }
")" 						{ return symbol(sym.RPAR); }
"[" 						{ return symbol(sym.LSQB); }
"]" 						{ return symbol(sym.RSQB); }

"{" 						{ return symbol(sym.LCUR); }
"}" 						{ return symbol(sym.RCUR); }

"bool" 						{ return symbol(sym.BOOL); }
"int" 						{ return symbol(sym.INT); }
"real" 						{ return symbol(sym.TYPE_REAL); }
"seq" 						{ return symbol(sym.SEQ); }
"set" 						{ return symbol(sym.SET); }
"**" 						{ return symbol(sym.CART); }
"unit" 						{ return symbol(sym.UNIT); }

"if" 						{ return symbol(sym.IF); }
"else" 						{ return symbol(sym.ELSE); }

"=" 						{ return symbol(sym.BCMEQ); }
":|" 						{ return symbol(sym.BCMSUCH); }

"+" 						{ return symbol(sym.OP_PLUS); }
"-" 						{ return symbol(sym.OP_MINUS); }
"*" 						{ return symbol(sym.OP_MUL); }
"/" 						{ return symbol(sym.OP_DIV); }

"<" 						{ return symbol(sym.OP_LSS); }
">" 						{ return symbol(sym.OP_GRT); }
"<=" 						{ return symbol(sym.OP_LEQ); }
">=" 						{ return symbol(sym.OP_GEQ); }

"mod" 						{ return symbol(sym.OP_MOD); }

"\\" 						{ return symbol(sym.OP_SETMINUS); }
"\\/" 						{ return symbol(sym.OP_UNION); }
"/\\" 						{ return symbol(sym.OP_INTER); }
"ran" 						{ return symbol(sym.OP_RAN); }
"dom" 						{ return symbol(sym.OP_DOM); }
"card" 						{ return symbol(sym.OP_CARD); }
"<|" 						{ return symbol(sym.OP_DOMRES); }
"<<|" 						{ return symbol(sym.OP_DOMSUB); }
"{}" 						{ return symbol(sym.OP_EMPTYSET); }
"[]" 						{ return symbol(sym.OP_EMPTYSEQ); }
"->"|"|->" 					{ return symbol(sym.OP_MAP); }
"|>" 						{ return symbol(sym.OP_RANRES); }
"|>>" 						{ return symbol(sym.OP_RANSUB); }
"~" 						{ return symbol(sym.OP_CONVERSE); }
"<+" 						{ return symbol(sym.OP_OVR); }
"@" 						{ return symbol(sym.OP_FCOMP); }
"union" 					{ return symbol(sym.OP_GEN_UNION); }
"inter" 					{ return symbol(sym.OP_GEN_INTER); }
"min" 						{ return symbol(sym.OP_MIN); }
"max" 						{ return symbol(sym.OP_MAX); }
"sum" 						{ return symbol(sym.OP_SUM); }
".." 						{ return symbol(sym.OP_RANGE); }
"prj1" 						{ return symbol(sym.OP_PRJ1); }
"prj2" 						{ return symbol(sym.OP_PRJ2); }
"some" 						{ return symbol(sym.OP_SOME); }
"true" 						{ return symbol(sym.OP_TRUE); }
"false" 					{ return symbol(sym.OP_FALSE); }
"|" 						{ return symbol(sym.VLINE); }
"<:" 						{ return symbol(sym.OP_SUBSETEQ); }
"<<:" 						{ return symbol(sym.OP_SUBSET); }
"." 						{ return symbol(sym.OP_DOT); }
"'" 						{ return symbol(sym.OP_PRIME); }
"=>" 						{ return symbol(sym.OP_IMPLIES); }
"forall"					{ return symbol(sym.B_FORALL); }
"exists"					{ return symbol(sym.B_EXISTS); }
"lambda"					{ return symbol(sym.B_LAMBDA); }
"+->" 						{ return symbol(sym.OP_PFUN); }
"<->" 						{ return symbol(sym.OP_REL); }
"/:" 						{ return symbol(sym.OP_NOTIN); }
">+>" 						{ return symbol(sym.OP_PINJ); }
"finite" 					{ return symbol(sym.OP_FINITE); }
"><" 						{ return symbol(sym.OP_DIRECT_PRODUCT); }
"==" 						{ return symbol(sym.OP_EQL); }
"!=" 						{ return symbol(sym.OP_NEQ); }
"and" 						{ return symbol(sym.OP_AND); }
"or" 						{ return symbol(sym.OP_OR); }
"not" 						{ return symbol(sym.OP_NOT); }

"," 						{ return symbol(sym.OP_COMMA); } 
":" 						{ return symbol(sym.OP_IN); } 
";" 						{ return symbol(sym.SEMIC); } 
"?" 						{ return symbol(sym.QUESTION); } 


[\$a-zA-Z][_a-zA-Z0-9]* 		{ return symbol(sym.ID, new String(yytext())); }
[0-9]+ 						{ return symbol(sym.NUMBER, new Integer(yytext())); }
[\"]([^\n\"])*[\"]				{ return symbol(sym.STRING, yytext()); }
[`](([a-zA-Z][\._a-zA-Z0-9]*)|([\"]([^\n\"])*[\"]))			{ return symbol(sym.XTAG, yytext().substring(1)); }
[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?		{ return symbol(sym.REAL, new Double(yytext())); }



[ \t\r\n\f] 				{ /* ignore white space. */ }
"#".*[\n]  					{ /* normal comment */ }
. 							{ return symbol(sym.MAGIC_SUB); }
