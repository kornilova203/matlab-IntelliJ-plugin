package com.github.kornilova_l.matlab.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.github.kornilova_l.matlab.psi.MatlabTypes.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;

%%

%{
    private boolean isTranspose = false;

    public static FlexAdapter getAdapter() {
        return new FlexAdapter(new MatlabLexer());
    }

    private MatlabLexer() {
        this(null);
    }
%}

%public
%class MatlabLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

NEWLINE=(\R( \t)*)+
WHITE_SPACE=[ \t\x0B\f]+ // do not match new line
SINGLE_QUOTE='
LINECOMMENT=%.*
FLOAT=(([\d]*\.[\d]+)|([\d]+\.))i?
FLOATEXPONENTIAL=(([\d]*\.[\d]+)|([\d]+\.)|\d+)e[\+-]?[\d]+i?
IDENTIFIER = [:jletter:] [:jletterdigit:]*
INTEGER=[0-9]+i?

/* double quote literal does not allow single \ character. Sequence \" gives double quote */
ESCAPE_SEQUENCE = \\[^\r\n]
DOUBLE_QUOTE_STRING_LITERAL = \" ([^\\\"\r\n] | {ESCAPE_SEQUENCE})* \"?
/* single quote literal allows single \ character. Sequence '' gives single quote */
SINGLE_QUOTE_EXCAPE_SEQUENCE=\\[\\bfnrt]|''

%state STRING_SINGLE_STATE
%state BLOCKCOMMENT_STATE
%state FILE_NAME_STATE

%%
<YYINITIAL> {
  {WHITE_SPACE}         { isTranspose = false; return WHITE_SPACE; }

  {SINGLE_QUOTE}$       { if (isTranspose) { isTranspose = false; return TRANSPOSE; }
                          else return SINGLEQUOTESTRINGLITERAL; }
  {SINGLE_QUOTE}        { if (isTranspose) { isTranspose = false; return TRANSPOSE; }
                          else yybegin(STRING_SINGLE_STATE); }

  function              { isTranspose = false; return FUNCTION; }
  elseif                { isTranspose = false; return ELSEIF; }
  else                  { isTranspose = false; return ELSE; }
  end                   { isTranspose = false; return END; }
  if                    { isTranspose = false; return IF; }
  for                   { isTranspose = false; return FOR; }
  while                 { isTranspose = false; return WHILE; }
  classdef              { isTranspose = false; return CLASSDEF; }
  properties            { isTranspose = false; return PROPERTIES; }
  methods               { isTranspose = false; return METHODS; }
  load/" "+[^ (]        { isTranspose = false; yybegin(FILE_NAME_STATE); return LOAD; }

  "("                   { isTranspose = false; return OPENBRACKET; }
  ")"                   { isTranspose = true; return CLOSEBRACKET; }
  "."                   { isTranspose = false; return DOT; }
  "<="                  { isTranspose = false; return LESSOREQUAL; }
  "-"                   { isTranspose = false; return MINUS; }
  "+"                   { isTranspose = false; return PLUS; }
  "./"                  { isTranspose = false; return DOTDELETE; }
  "/"                   { isTranspose = false; return DELETE; }
  "\\"                  { isTranspose = false; return BACKSLASH; }
  ".\\"                 { isTranspose = false; return DOTBACKSLASH; }
  ".*"                  { isTranspose = false; return DOTMUL; }
  "*"                   { isTranspose = false; return MUL; }
  ".^"                  { isTranspose = false; return DOTPOW; }
  "^"                   { isTranspose = false; return POW; }
  "&&"                  { isTranspose = false; return AND; }
  "&"                   { isTranspose = false; return MATRIXAND; }
  "||"                  { isTranspose = false; return OR; }
  "|"                   { isTranspose = false; return MATRIXOR; }
  ".'"                  { isTranspose = false; return DOTTRANSPOSE; }
  "~"                   { isTranspose = false; return TILDA; }
  "="                   { isTranspose = false; return ASSIGN; }
  ">="                  { isTranspose = false; return MOREOREQUAL; }
  ">"                   { isTranspose = false; return MORE; }
  "<"                   { isTranspose = false; return LESS; }
  "=="                  { isTranspose = false; return EQUAL; }
  "!="                  { isTranspose = false; return NOTEQUAL; }
  ","                   { isTranspose = false; return COMA; }
  ":"                   { isTranspose = false; return COLON; }
  ";"                   { isTranspose = false; return SEMICOLON; }
  "["                   { isTranspose = false; return OPENSQUAREBRACKET; }
  "]"                   { isTranspose = true; return CLOSESQUAREBRACKET; }
  "{"                   { isTranspose = false; return OPENCURLYBRACKET; }
  "}"                   { isTranspose = false; return CLOSECURLYBRACKET; }
  "%{"                  { isTranspose = false; yybegin(BLOCKCOMMENT_STATE); }
  "."                   { isTranspose = false; return DOT; }

  {NEWLINE}             { isTranspose = false; return NEWLINE; }
  {LINECOMMENT}         { isTranspose = false; return COMMENT; }
  {FLOATEXPONENTIAL}    { isTranspose = false; return FLOATEXPONENTIAL; }
  {FLOAT}               { isTranspose = false; return FLOAT; }
  {INTEGER}             { isTranspose = false; return INTEGER; }
  {IDENTIFIER}          { isTranspose = true; return IDENTIFIER; }
  {DOUBLE_QUOTE_STRING_LITERAL} { return DOUBLEQUOTESTRINGLITERAL; }
}

<STRING_SINGLE_STATE> {
    {SINGLE_QUOTE_EXCAPE_SEQUENCE} / \n  { yybegin(YYINITIAL); return SINGLEQUOTESTRINGLITERAL; }
    {SINGLE_QUOTE_EXCAPE_SEQUENCE}       {  }
    "'"                                  { yybegin(YYINITIAL); return SINGLEQUOTESTRINGLITERAL; }

    /* line should not consume \n character */
    . / \n                               { yybegin(YYINITIAL); return SINGLEQUOTESTRINGLITERAL; }
    .                                    {  }
}

<BLOCKCOMMENT_STATE> {
    "%}"                { yybegin(YYINITIAL); return COMMENT; }
    [^\n\r'\\]+         {  }
    \n                  {  }
    \\t                 {  }
    \\n                 {  }
    \\r                 {  }
    \\'                 {  }
    \\                  {  }
}

<FILE_NAME_STATE> {
    /* stop consuming filename when find newline */
    [^\n(]/\n         { yybegin(YYINITIAL); return FILENAME; }
    "("               { yybegin(YYINITIAL); }
    [^\n(]            {  }
}

[^] { return BAD_CHARACTER; }
