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

SPACE=[ \t\n\x0B\f\r]+
COMMENT="//".*
NUMBER=[0-9]+(\.[0-9]*)?
ID=[:letter:][a-zA-Z_0-9]*
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")

%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  ";"                { return SEMI; }
  "="                { return EQ; }
  "("                { return LP; }
  ")"                { return RP; }
  "end"              { return END; }
  "if"               { return IF; }
  "+"                { return OP_1; }
  "-"                { return OP_2; }
  "*"                { return OP_3; }
  "/"                { return OP_4; }
  "!"                { return OP_5; }
  "float"            { return FLOAT; }

  {SPACE}            { return SPACE; }
  {COMMENT}          { return COMMENT; }
  {NUMBER}           { return NUMBER; }
  {ID}               { return ID; }
  {STRING}           { return STRING; }

}

[^] { return BAD_CHARACTER; }
