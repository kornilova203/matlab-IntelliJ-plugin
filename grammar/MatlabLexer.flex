package com.github.korniloval.matlab.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import java.util.Stack;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.korniloval.matlab.psi.MatlabTypes.*;

%%

%{
    private Stack<Integer> stack = new Stack<>();

    public static FlexAdapter getAdapter() {
        return new FlexAdapter(new MatlabLexer());
    }

    private MatlabLexer() {
        this(null);
    }

    private void yypushState(int newState) {
      stack.push(yystate());
      yybegin(newState);
    }

    private void yypopState() {
        if (stack.isEmpty()) return;
        yybegin(stack.pop());
    }

    private void yyclearStack() {
        while (!stack.isEmpty()) stack.pop();
    }

    private void stopLookForCtrans() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
    }

    private void lookForCtrans() {
        if (yystate() != LOOK_FOR_CTRANS) yypushState(LOOK_FOR_CTRANS);
    }

    private void startWsDoesNotMatter() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
        yypushState(YYINITIAL);
    }

    private void stopWsDoesNotMatter() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
        if (yystate() == YYINITIAL) yypopState();
    }

    private void startWsMatters() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
        yypushState(WS_MATTERS);
    }

    private void stopWsMatters() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
        if (yystate() == WS_MATTERS) yypopState();
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

%state WS_MATTERS
%state LOOK_FOR_CTRANS
%state SINGLE_QOUTE_STRING_STATE
%state BLOCKCOMMENT_STATE
%state LOOK_FOR_LINECOMMENT
%state LINECOMMENT_STATE
%state FILE_NAME_STATE

%%
<YYINITIAL,WS_MATTERS,LOOK_FOR_CTRANS> {
  {WHITE_SPACE}         { if (yystate() == LOOK_FOR_CTRANS &&
                                    !stack.isEmpty() &&
                                    stack.peek() == WS_MATTERS) {
                                yypopState();
                           }
                            return WHITE_SPACE;
                        }

  {SINGLE_QUOTE} / \n   { if (yystate() == LOOK_FOR_CTRANS) {
                              return TRANS;
                          } else {
                              return SINGLE_QUOTE_STRING;
                          }
                        }

  {SINGLE_QUOTE}        { if (yystate() == LOOK_FOR_CTRANS) {
                                return CTRANS;
                            } else {
                                yypushState(SINGLE_QOUTE_STRING_STATE);
                            }
                        }

  function              { stopLookForCtrans(); return FUNCTION; }
  elseif                { stopLookForCtrans(); return ELSEIF; }
  else                  { stopLookForCtrans(); return ELSE; }
  end                   { stopLookForCtrans(); return END; }
  if                    { stopLookForCtrans(); return IF; }
  for                   { stopLookForCtrans(); return FOR; }
  while                 { stopLookForCtrans(); return WHILE; }
  classdef              { stopLookForCtrans(); return CLASSDEF; }
  switch                { stopLookForCtrans(); return SWITCH; }
  case                  { stopLookForCtrans(); return CASE; }
  otherwise             { stopLookForCtrans(); return OTHERWISE; }
  try                   { stopLookForCtrans(); return TRY; }
  catch                 { stopLookForCtrans(); return CATCH; }
  load/" "+[^ (]        { stopLookForCtrans(); yypushState(FILE_NAME_STATE); return LOAD; }
  dir/" "+[^ (]         { stopLookForCtrans(); yypushState(FILE_NAME_STATE); return DIR; }
  ls/" "+[^ (]          { stopLookForCtrans(); yypushState(FILE_NAME_STATE); return LS; }
  cd/" "+[^ (]          { stopLookForCtrans(); yypushState(FILE_NAME_STATE); return CD; }

  "("                   { startWsDoesNotMatter(); return LPARENTH; }
  ")"                   { stopWsDoesNotMatter(); lookForCtrans(); return RPARENTH; }
  "."                   { stopLookForCtrans(); return DOT; }
  "<="                  { stopLookForCtrans(); return LESS_OR_EQUAL; }
  "-"                   { stopLookForCtrans(); return MINUS; }
  "--"                  { stopLookForCtrans(); return MINUSMINUS; }
  "+"                   { stopLookForCtrans(); return PLUS; }
  "++"                  { stopLookForCtrans(); return PLUSPLUS; }
  "./"                  { stopLookForCtrans(); return DOT_RDIV; }
  "/"                   { stopLookForCtrans(); return RDIV; }
  "\\"                  { stopLookForCtrans(); return LDIV; }
  ".\\"                 { stopLookForCtrans(); return DOT_LDIV; }
  ".*"                  { stopLookForCtrans(); return DOT_MUL; }
  "*"                   { stopLookForCtrans(); return MUL; }
  ".^"                  { stopLookForCtrans(); return DOT_POW; }
  "^"                   { stopLookForCtrans(); return POW; }
  "&&"                  { stopLookForCtrans(); return AND; }
  "&"                   { stopLookForCtrans(); return MATRIX_AND; }
  "||"                  { stopLookForCtrans(); return OR; }
  "|"                   { stopLookForCtrans(); return MATRIX_OR; }
  ".'"                  { stopLookForCtrans(); return TRANS; }
  "~"                   { stopLookForCtrans(); return TILDA; }
  "="                   { stopLookForCtrans(); return ASSIGN; }
  ">="                  { stopLookForCtrans(); return MORE_OR_EQUAL; }
  ">"                   { stopLookForCtrans(); return MORE; }
  "<"                   { stopLookForCtrans(); return LESS; }
  "=="                  { stopLookForCtrans(); return EQUAL; }
  "~="                  { stopLookForCtrans(); return NOT_EQUAL; }
  ","                   { stopLookForCtrans(); return COMMA; }
  ":"                   { stopLookForCtrans(); return COLON; }
  ";"                   { stopLookForCtrans(); return SEMICOLON; }
  "["                   { startWsMatters(); return LBRACKET; }
  "]"                   { stopWsMatters(); lookForCtrans(); return RBRACKET; }
  "{"                   { startWsMatters(); return LBRACE; }
  "}"                   { stopWsMatters(); lookForCtrans(); return RBRACE; }
  "..." / {WHITE_SPACE}? \n   { stopLookForCtrans(); return ELLIPSIS; }
  "..."                 { stopLookForCtrans(); yypushState(LOOK_FOR_LINECOMMENT); return ELLIPSIS; }
  "@"                   { stopLookForCtrans(); return AT; }
  "?"                   { stopLookForCtrans(); return QUESTION_MARK; }

  {NEWLINE}             { stopLookForCtrans(); return NEWLINE; }
  {LINE_COMMENT}        { stopLookForCtrans(); return COMMENT; }
  ^{BLOCK_COMMENT_PREFIX}$ { stopLookForCtrans(); yypushState(BLOCKCOMMENT_STATE); }
  {FLOAT_EXPONENTIAL}   { stopLookForCtrans(); return FLOAT_EXPONENTIAL; }
  {FLOAT}               { stopLookForCtrans(); return FLOAT; }
  {INTEGER}             { stopLookForCtrans(); return INTEGER; }
  {IDENTIFIER}          { lookForCtrans(); return IDENTIFIER; }
  {DOUBLE_QUOTE_STRING} { lookForCtrans(); return DOUBLE_QUOTE_STRING; }

  <<EOF>>               { return null; }
}

<SINGLE_QOUTE_STRING_STATE> {
    {SINGLE_QUOTE_EXCAPE_SEQUENCE} / \n  { yypopState(); return SINGLE_QUOTE_STRING; }
    {SINGLE_QUOTE_EXCAPE_SEQUENCE}       {  }
    "'"                                  { yypopState(); lookForCtrans(); return SINGLE_QUOTE_STRING; }
    <<EOF>>                              { yyclearStack(); yybegin(YYINITIAL); return SINGLE_QUOTE_STRING; }

    /* line should not consume \n character */
    . / \n                               { yypopState(); return SINGLE_QUOTE_STRING; }
    .                                    {  }
}

<LOOK_FOR_LINECOMMENT,LINECOMMENT_STATE> {
    <<EOF>>                              { yyclearStack(); yybegin(YYINITIAL); return COMMENT; }
}

<LOOK_FOR_LINECOMMENT> {
    {WHITE_SPACE}                        { return WHITE_SPACE; }
    .                                    { yypopState(); yypushState(LINECOMMENT_STATE); }
}

<LINECOMMENT_STATE> {
    /* comment should not consume \n character */
    . / \n                               { yypopState(); return COMMENT; }
    .                                    {  }
}

<BLOCKCOMMENT_STATE> {
    ^{BLOCK_COMMENT_PREFIX}$ { yypushState(BLOCKCOMMENT_STATE); }

    ^{BLOCK_COMMENT_SUFFIX}$ { yypopState();
                               if (yystate() != BLOCKCOMMENT_STATE) return COMMENT;
                             }
    <<EOF>>                  { yyclearStack(); yybegin(YYINITIAL); return COMMENT; }

    [^]                      {  }
}

<FILE_NAME_STATE> {
    /* stop consuming filename when find newline */
    [^(]/[\n ]        { yypopState(); return FILE_NAME; }
    "("               { yypopState(); }
    <<EOF>>           { yyclearStack(); yybegin(YYINITIAL); return FILE_NAME; }
    .                 {  }
}

[^] { return BAD_CHARACTER; }
