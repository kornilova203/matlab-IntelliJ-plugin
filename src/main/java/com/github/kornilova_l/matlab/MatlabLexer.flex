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

EOL=\R
WHITE_SPACE=\s+
TRANSPOSE='
NEWLINE=\n
SPACE=[ \t\n\x0B\f\r]+
COMMENT=%.*
ID=[A-Za-z_]+[A-Za-z\d]*
FLOAT=([\d]*.[\d]+)|([\d]+.)i?
FLOATEXPONENTIAL=(([\d]*\.[\d]+)|([\d]+\.)|\d+)e[\+-]?[\d]+i?
INTEGER=[0-9]+i?
LETTER=[A-Za-z]
DIGIT=[0-9]

%state STRING_DOUBLE_STATE
%state STRING_SINGLE_STATE

%%
<YYINITIAL> {
  {WHITE_SPACE}         { return WHITE_SPACE; }

  \"                    { yybegin(STRING_DOUBLE_STATE); }
  "'"                   { if (isTranspose) { isTranspose = false; return TRANSPOSE; } else yybegin(STRING_SINGLE_STATE); }

  function              { isTranspose = false; return FUNCTION; }
  elseif                { isTranspose = false; return ELSEIF; }
  else                  { isTranspose = false; return ELSE; }
  "end"                 { isTranspose = false; return END; }
  "if"                  { isTranspose = false; return IF; }
  "for"                 { isTranspose = false; return FOR; }
  "while"               { isTranspose = false; return WHILE; }
  "("                   { isTranspose = false; return OB; }
  ")"                   { isTranspose = true; return CB; }
  "<="                  { isTranspose = false; return LESSOREQUAL; }
  "-"                   { isTranspose = false; return MINUS; }
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

  {NEWLINE}             { isTranspose = false; return NEWLINE; }
  {SPACE}               { isTranspose = false; return SPACE; }
  {COMMENT}             { isTranspose = false; return COMMENT; }
  {ID}                  { isTranspose = true; return ID; }
  {FLOATEXPONENTIAL}    { isTranspose = false; return FLOATEXPONENTIAL; }
  {FLOAT}               { isTranspose = false; return FLOAT; }
  {INTEGER}             { isTranspose = false; return INTEGER; }
  {LETTER}              { isTranspose = false; return LETTER; }
  {DIGIT}               { isTranspose = false; return DIGIT; }

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
}

[^] { return BAD_CHARACTER; }
