package com.github.kornilova_l.matlab;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.kornilova_l.matlab.psi.MatlabTypes.*;

%%

%{
    private boolean isTranspose = false;

    public MatlabLexer() {
        this((java.io.Reader)null);
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
TRANSPOSE='
LINECOMMENT=%.*
FLOAT=(([\d]*\.[\d]+)|([\d]+\.))i?
FLOATEXPONENTIAL=(([\d]*\.[\d]+)|([\d]+\.)|\d+)e[\+-]?[\d]+i?
ID=[A-Za-z_]+[A-Za-z\d]*
INTEGER=[0-9]+i?

%state STRING_DOUBLE_STATE
%state STRING_SINGLE_STATE
%state BLOCKCOMMENT_STATE
%state FILE_NAME_STATE

%%
<YYINITIAL> {
  {WHITE_SPACE}         { isTranspose = false; return WHITE_SPACE; }

  \"                    { yybegin(STRING_DOUBLE_STATE); }
  {TRANSPOSE}           { if (isTranspose) { isTranspose = false; return TRANSPOSE; } else yybegin(STRING_SINGLE_STATE); }

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
  {ID}                  { isTranspose = true; return ID; }
}

<STRING_DOUBLE_STATE> {
    \"                  { yybegin(YYINITIAL); return STRING; }
    [^\n\r\"\\]+        {  }
    \n                  { yybegin(YYINITIAL); return BAD_CHARACTER; }
    \\t                 {  }
    \\n                 {  }
    \\r                 {  }
    \\\"                {  }
    \\                  {  }
    .                   {  }
}

<STRING_SINGLE_STATE> {
    "'"                 { yybegin(YYINITIAL); return STRING; }
    [^\n\r'\\]+         {  }
    \n                  { yybegin(YYINITIAL); return BAD_CHARACTER; }
    \\t                 {  }
    \\n                 {  }
    \\r                 {  }
    \\'                 {  }
    \\                  {  }
    .                   {  }
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
