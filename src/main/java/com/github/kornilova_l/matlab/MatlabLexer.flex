package com.github.kornilova_l.matlab;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.kornilova_l.matlab.psi.MatlabTypes.*;

%%

%{
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

EOL=\R
WHITE_SPACE=\s+
TRANSPOSE='
NEWLINE=\n
SPACE=[ \t\n\x0B\f\r]+
COMMENT=%.*
ID=[A-Za-z_]+[A-Za-z\d]*
FLOATCOMPLEX=(([0-9]*\.[0-9]+(e[\+-]?[0-9]+)?)|([0-9]+\.?e[\+-]?[0-9]+))i
FLOAT=([0-9]*\.[0-9]+(e[\+-]?[0-9]+)?)|([0-9]+\.?e[\+-]?[0-9]+)
INTEGERCOMPLEX=[0-9]+i
INTEGER=[0-9]+
LETTER=[A-Za-z]
DIGIT=[0-9]

%state STRING_STATE

%%
<YYINITIAL> {
  \"                    { yybegin(STRING_STATE); }
  {WHITE_SPACE}         { return WHITE_SPACE; }

  function            { return FUNCTION; }
  elseif              { return ELSEIF; }
  else                { return ELSE; }
  "end"                 { return END; }
  "if"                  { return IF; }
  "for"                 { return FOR; }
  "while"               { return WHILE; }
  "("                   { return OB; }
  ")"                   { return CB; }
  "<="                  { return LESSOREQUAL; }
  ">="                  { return MOREOREQUAL; }
  ">"                   { return MORE; }
  "<"                   { return LESS; }
  "=="                  { return EQUAL; }
  "!="                  { return NOTEQUAL; }
  ","                   { return COMA; }
  ":"                   { return COLON; }
  ";"                   { return SEMICOLON; }
  "["                   { return OPENSQUAREBRACKET; }
  "]"                   { return CLOSESQUAREBRACKET; }
  "exprelse"            { return EXPRELSE; }

  {NEWLINE}             { return NEWLINE; }
  {SPACE}               { return SPACE; }
  {COMMENT}             { return COMMENT; }
  {ID}                  { return ID; }
  {FLOATCOMPLEX}        { return FLOATCOMPLEX; }
  {FLOAT}               { return FLOAT; }
  {INTEGERCOMPLEX}      { return INTEGERCOMPLEX; }
  {INTEGER}             { return INTEGER; }
  {LETTER}              { return LETTER; }
  {DIGIT}               { return DIGIT; }

}

<STRING_STATE> {
    \"                             { yybegin(YYINITIAL);
                                     return STRING; }
    [^\n\r\"\\]+                   {  }
    \\t                            {  }
    \\n                            {  }

    \\r                            {  }
    \\\"                           {  }
    \\                             {  }
}

[^] { return BAD_CHARACTER; }
