package com.trhoanglee.ast;

%%
%public
%class Scanner
%implements sym

%line
%column

%cup
%cupdebug

%state COMMENT

%{

    int _yyline, _yycolumn;
     
    private Symbol symbol(int type) {
       return new Symbol(type, yyline+1, yycolumn+1, yytext());
    }

    private Symbol symbol(int type, Object value) {
      return new Symbol(type, yyline+1, yycolumn+1, value);
    }
    
    public String getTokName(int token) {
      return getTokenName(token);
    }
    
%}

/* main character classes */

LineTerminator = \r|\n|\r\n

WhiteSpace = {LineTerminator} | [ \t\f]

Identifier = [a-zA-Z_][a-zA-Z0-9_]*

IntegerLiteral = (0 | [1-9][0-9]*)

DoubleLiteral  = ([0-9]+\.?[0-9]*|[0-9]*\.?[0-9]+)(E[+-]?[0-9]+)?

StringLiteral = L?\"([^\\\"\n]|\\.)*\" 

%%

<YYINITIAL> {
	"int" { return symbol(INT);  }
	
	"bool" { return symbol(BOOL); }
	
	"void" { return symbol(VOID); }
	
	"true" { return symbol(TRUE, new Boolean(true)); }
	
	"false" { return symbol(FALSE, new Boolean(false)); }
	
	"NULL" { return symbol(NULL); }
	
	"if" { return symbol(IF); }
	
	"else" { return symbol(ELSE); }
	
	"while" { return symbol(WHILE); }
	
	"for" { return symbol(FOR); }
	
	"return" { return symbol(RETURN); }
	
	"sizeof" { return symbol(SIZEOF); }
	
	"struct" { return symbol(STRUCT); }
	
	"{" { return symbol(LCURLY); }
	
	"}" { return symbol(RCURLY); }
	
	"(" { return symbol(LPAREN); }
	
	")" { return symbol(RPAREN); }
	
	"[" { return symbol(LSQBRACKET); }
	
	"]" { return symbol(RSQBRACKET); }
	
	"," { return symbol(COMMA); }
	
	"=" { return symbol(ASSIGN); }
	
	";" { return symbol(SEMICOLON); }
	
	"+=" { return symbol(PLUSEQL); }
	
	"-=" { return symbol(MINUSEQL); }
	
	"*=" { return symbol(TIMESEQL); }
	 
	"/=" { return symbol(DIVEQL); }
	
	"+" { return symbol(PLUS); }
	
	"-" { return symbol(MINUS); }
	
	"*" { return symbol(TIMES); }
	 
	"/" { return symbol(DIVIDE); }
	
	"!" { return symbol(NOT); }
	
	"&"  { return symbol(ADDROF); }
	
	"&&" { return symbol(AND); }
	
	"||" { return symbol(OR); }
	
	"==" { return symbol(EQUALS); }
	
	"!=" { return symbol(NOTEQUALS); }
	
	"<" { return symbol(LESS); }
	
	">" { return symbol(GREATER); }
	
	"<=" { return symbol(LESSEQ); }
	
	">=" { return symbol(GREATEREQ); }
	
	"\." { return symbol(PERIOD); }
	
	"%" { return symbol(PERCENT); }

    {IntegerLiteral} {
        int val;
        try {
            val = (new Integer(yytext())).intValue();
        } catch (NumberFormatException e) {
            Errors.warn(yyline+1, yycolumn+1, "LEXICAL WARNING: integer literal too large; using max value");
            val = Integer.MAX_VALUE;
        } 
        return symbol(INTLITERAL, new Integer(val)); 
    }
  
    {DoubleLiteral}                { return symbol(DOUBLELITERAL, new Double(yytext())); }
  
    /* whitespace */
    {WhiteSpace}                   { /* ignore */ }

    /* identifiers */ 
    {Identifier}                   { return symbol(ID, new String(yytext())); }  
  
    /* string literal */
    {StringLiteral}                { return symbol(STRINGLITERAL, new String(yytext())); }
  
    /* comment */
    "//" [^\r\n]*                  { /* ignore */ }
    
    "/*" \**                       { _yyline = yyline; _yycolumn = yycolumn; yybegin(COMMENT); }

    \"([^\\\"\n]|\\.)*             { Errors.fatal(yyline+1, yycolumn+1, "LEXICAL ERROR: Unterminated string literal") ; System.exit(-1);}

    .   { Errors.fatal(yyline+1, yycolumn+1, "LEXICAL ERROR: Illegal character \"" + yytext()+ "\""); 
        System.exit(-1); }           

    <<EOF>>                          { return symbol(EOF); }
}

<COMMENT>{
    "*/"      { yybegin(YYINITIAL); }
    
    ([^*]|"*"[^/])*      { /* ignore */  }
    
    <<EOF>> { Errors.fatal(_yyline+1, _yycolumn+1, "LEXICAL ERROR: Missing end comment */"); 
       System.exit(-1); }
}
