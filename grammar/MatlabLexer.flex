package com.github.korniloval.matlab.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.korniloval.matlab.psi.MatlabTypes.*;

%%

%{
    private boolean isTranspose = false;
    private int blockCommentLevel = 0;
    private boolean lineCommentStarted = false;

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

NEWLINE=(\R( \t)*)
WHITE_SPACE=[ \t\x0B\f]+ // do not match new line
SINGLE_QUOTE='
LINE_COMMENT=%.*
BLOCK_COMMENT_PREFIX={WHITE_SPACE}* "%{" {WHITE_SPACE}*
BLOCK_COMMENT_SUFFIX={WHITE_SPACE}* "%}" {WHITE_SPACE}*
FLOAT=(([\d]*\.[\d]+)|([\d]+\.))i?
FLOAT_EXPONENTIAL=(([\d]*\.[\d]+)|([\d]+\.)|\d+)e[\+-]?[\d]+i?
IDENTIFIER = [:jletter:] [:jletterdigit:]*
INTEGER=[0-9]+i?

/* double quote literal does not allow single \ character. Sequence \" gives double quote */
ESCAPE_SEQUENCE = \\[^\r\n]
DOUBLE_QUOTE_STRING = \" ([^\\\"\r\n] | {ESCAPE_SEQUENCE})* \"?
/* single quote literal allows single \ character. Sequence '' gives single quote */
SINGLE_QUOTE_EXCAPE_SEQUENCE=\\[\\bfnrt]|''

%state STRING_SINGLE_STATE
%state BLOCKCOMMENT_STATE
%state LINECOMMENT_STATE
%state FILE_NAME_STATE

%%
<YYINITIAL> {
  {WHITE_SPACE}         { isTranspose = false; return WHITE_SPACE; }

  {SINGLE_QUOTE}$       { if (isTranspose) {
                              isTranspose = false;
                              return TRANSPOSE;
                          } else {
                              return SINGLE_QUOTE_STRING;
                          }
                        }

  {SINGLE_QUOTE}        { if (isTranspose) {
                              isTranspose = false;
                              return TRANSPOSE;
                          } else {
                              yybegin(STRING_SINGLE_STATE);
                          }
                        }

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
  events                { isTranspose = false; return EVENTS; }
  switch                { isTranspose = false; return SWITCH; }
  case                  { isTranspose = false; return CASE; }
  otherwise             { isTranspose = false; return OTHERWISE; }
  load/" "+[^ (]        { isTranspose = false; yybegin(FILE_NAME_STATE); return LOAD; }
  dir/" "+[^ (]         { isTranspose = false; yybegin(FILE_NAME_STATE); return DIR; }
  ls/" "+[^ (]          { isTranspose = false; yybegin(FILE_NAME_STATE); return LS; }
  cd/" "+[^ (]          { isTranspose = false; yybegin(FILE_NAME_STATE); return CD; }
  true                  { return TRUE; }
  false                 { return FALSE; }

  "("                   { isTranspose = false; return LPARENTH; }
  ")"                   { isTranspose = true; return RPARENTH; }
  "."                   { isTranspose = false; return DOT; }
  "<="                  { isTranspose = false; return LESS_OR_EQUAL; }
  "-"                   { isTranspose = false; return MINUS; }
  "+"                   { isTranspose = false; return PLUS; }
  "./"                  { isTranspose = false; return DOT_RDIV; }
  "/"                   { isTranspose = false; return RDIV; }
  "\\"                  { isTranspose = false; return LDIV; }
  ".\\"                 { isTranspose = false; return DOT_LDIV; }
  ".*"                  { isTranspose = false; return DOT_MUL; }
  "*"                   { isTranspose = false; return MUL; }
  ".^"                  { isTranspose = false; return DOT_POW; }
  "^"                   { isTranspose = false; return POW; }
  "&&"                  { isTranspose = false; return AND; }
  "&"                   { isTranspose = false; return MATRIX_AND; }
  "||"                  { isTranspose = false; return OR; }
  "|"                   { isTranspose = false; return MATRIX_OR; }
  ".'"                  { isTranspose = false; return DOT_TRANSPOSE; }
  "~"                   { isTranspose = false; return TILDA; }
  "="                   { isTranspose = false; return ASSIGN; }
  ">="                  { isTranspose = false; return MORE_OR_EQUAL; }
  ">"                   { isTranspose = false; return MORE; }
  "<"                   { isTranspose = false; return LESS; }
  "=="                  { isTranspose = false; return EQUAL; }
  "~="                  { isTranspose = false; return NOT_EQUAL; }
  ","                   { isTranspose = false; return COMMA; }
  ":"                   { isTranspose = false; return COLON; }
  ";"                   { isTranspose = false; return SEMICOLON; }
  "["                   { isTranspose = false; return LBRACKET; }
  "]"                   { isTranspose = true; return RBRACKET; }
  "{"                   { isTranspose = false; return LBRACE; }
  "}"                   { isTranspose = false; return RBRACE; }
  "..." / {WHITE_SPACE}? \n   { isTranspose = false; return ELLIPSIS; }
  "..."                 { isTranspose = false; yybegin(LINECOMMENT_STATE); return ELLIPSIS; }
  "@"                   { isTranspose = false; return AT; }
  "?"                   { isTranspose = false; return QUESTION_MARK; }

  {NEWLINE}             { isTranspose = false; return NEWLINE; }
  {LINE_COMMENT}        { isTranspose = false; return COMMENT; }
  ^{BLOCK_COMMENT_PREFIX}$ { isTranspose = false; blockCommentLevel = 1; yybegin(BLOCKCOMMENT_STATE); }
  {FLOAT_EXPONENTIAL}   { isTranspose = false; return FLOAT_EXPONENTIAL; }
  {FLOAT}               { isTranspose = false; return FLOAT; }
  {INTEGER}             { isTranspose = false; return INTEGER; }
  {IDENTIFIER}          { isTranspose = true; return IDENTIFIER; }
  {DOUBLE_QUOTE_STRING} { return DOUBLE_QUOTE_STRING; }

  <<EOF>>               { return null; }
}

<STRING_SINGLE_STATE> {
    {SINGLE_QUOTE_EXCAPE_SEQUENCE} / \n  { yybegin(YYINITIAL); return SINGLE_QUOTE_STRING; }
    {SINGLE_QUOTE_EXCAPE_SEQUENCE}       {  }
    "'"                                  { yybegin(YYINITIAL); return SINGLE_QUOTE_STRING; }
    <<EOF>>                              { yybegin(YYINITIAL); return SINGLE_QUOTE_STRING; }

    /* line should not consume \n character */
    . / \n                               { yybegin(YYINITIAL); return SINGLE_QUOTE_STRING; }
    .                                    {  }
}

<LINECOMMENT_STATE> {
    <<EOF>>                              { yybegin(YYINITIAL); lineCommentStarted = false; return COMMENT; }
    /* comment should not consume \n character */
    . / \n                               { yybegin(YYINITIAL); lineCommentStarted = false; return COMMENT; }
    {WHITE_SPACE}                        { if (!lineCommentStarted) return WHITE_SPACE; }
    .                                    { lineCommentStarted = true; }
}

<BLOCKCOMMENT_STATE> {
    ^{BLOCK_COMMENT_PREFIX}$ { blockCommentLevel += 1; }

    ^{BLOCK_COMMENT_SUFFIX}$ { blockCommentLevel -= 1;
                               if (blockCommentLevel == 0) {
                                   yybegin(YYINITIAL);
                                   return COMMENT;
                               }
                             }
    <<EOF>>                  { yybegin(YYINITIAL); return COMMENT; }

    [^]                      {  }
}

<FILE_NAME_STATE> {
    /* stop consuming filename when find newline */
    [^(]/[\n ]        { yybegin(YYINITIAL); return FILE_NAME; }
    "("               { yybegin(YYINITIAL); }
    <<EOF>>           { yybegin(YYINITIAL); return FILE_NAME; }
    .                 {  }
}

[^] { return BAD_CHARACTER; }
