package uk.ac.ncl.prime.sim.lang.parser;

import java_cup.runtime.*;
import java.io.*;
import javax.swing.text.Segment;
import org.fife.ui.rsyntaxtextarea.*;

%public
%class CoreTokenMaker
%extends AbstractJFlexCTokenMaker
%unicode
%type org.fife.ui.rsyntaxtextarea.Token

%%
%cup
%line
%column
%unicode
%class Scanner

%{
	public CTokenMaker() {
		super();
	}

	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
	}

	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}

	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so);
	}

	@Override
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start,end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * Returns the text to place at the beginning and end of a
	 * line to "comment" it in a this programming language.
	 *
	 * @return The start and end strings to add to a line to "comment"
	 *         it out.
	 */
	@Override
	public String[] getLineCommentStartAndEnd() {
		return new String[] { "#", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *                    <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = Token.NULL;

		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new TokenImpl();
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 * @exception   IOException  if any I/O-Error occurs.
	 */
	private boolean zzRefill() throws java.io.IOException {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public final void yyreset(java.io.Reader reader) throws java.io.IOException {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtBOL  = true;
		zzAtEOF  = false;
	}


%}

%state EOL_COMMENT

%%

<YYINITIAL> {
	"," |
	":" |
	";" |
	"(" |
	")" |
	"[" |
	"]" |
	"{" |
	"=" |
	":|" |
	"}" 						{ addToken(Token.SEPARATOR); }


	"unit" 	|
	"bool" 	|
	"int"  	|
	"seq" 	|
	"**"	|
	"set" 						{ addToken(Token.DATA_TYPE); }

	"event" |
	"module" |
	"system" |
	"use" |
	"as" |
	"on" |
	"par" |
	"const" |
	"actor" |
	"if" 	|
	"else" 	|
	"when" 	|
	"while" |
	"stop" 	|
	"for" 	|
	"in" 	|
	"run" 	|
	"all" 	|
	"try" 	|
	"action" |
	"return"|
	"await"|
	"repeat" 					{ addToken(Token.RESERVED_WORD); }

	"some"  |
	"out" 						{ addToken(Token.ANNOTATION); }

	"aux" 	|
	"axiom" |
	"lemma" |
	"invariant" 	|
	"variant" 	|
	"pre" 	|
	"post" 	|
	"rely" 	|
	"guar" 	 					{ addToken(Token.RESERVED_WORD_2); }


	"+"  	|
	"-" 	|
	"*" 	|
	"/" 	|
	"<" 	|
	">" 	|
	"<=" 	|
	">=" 	|
	"mod" 	|
	"\\" 	|
	"\\/" 	|
	"/\\" 	|
	"ran" 	|
	"dom" 	|
	"card" 	|
	"<<|" 	|
	"|>" 	|
	"<|" 	|
	"|>>" 	|
	"~" 	|
	"{}" 	|
	"[]" 	|
	"==" 	|
	"!=" 	|
	"and" 	|
	"or" 	|
	"sum" 	|
	"max" 	|
	"min" 	|
	"union"	|
	"inter"	|
	"<+"	|
	".." 	|
	"prj1" 	|
	"prj2" 	|
	"true" 	|
	"false"	|
	"|" 	|
	"@" 	|
	"<:" 	|
	"/:" 	|
	"<::" 	|
	"." 	|
	"::" 	|
	"'"	|
	"=>"	|
	"+->"	|
	"<->"	|
	">+>"	|
      "enabled" |
       "forall" |
       "exists" |
       "finite" |
       "lambda" |
	"not" 					{ addToken(Token.OPERATOR); }



	[a-zA-Z][_a-zA-Z0-9]* 			{ addToken(Token.IDENTIFIER); }
	[`][a-zA-Z][\._a-zA-Z0-9]*		{ addToken(Token.MARKUP_TAG_NAME); }

	[0-9]+ 					{ addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }
	[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?	{ addToken(Token.LITERAL_NUMBER_DECIMAL_DOUBLE); }

	"#"					{ start = zzMarkedPos-1; yybegin(EOL_COMMENT); }

	[\"]([^\n\"]*)[\"]			{ addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }
	[ \t\r\n\f] 				{ addToken(Token.WHITESPACE); }
	<<EOF>>					{ addNullToken(); return firstToken; }
	. 					{ addToken(Token.ERROR_IDENTIFIER); }
}

<EOL_COMMENT> {
	.*					{}
	\n					{ addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken; }
	<<EOF>>					{ addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken; }
}
