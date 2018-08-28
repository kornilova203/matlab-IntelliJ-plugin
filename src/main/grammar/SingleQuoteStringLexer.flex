package com.github.kornilova_l.matlab.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.github.kornilova_l.matlab.psi.MatlabTypes.SINGLE_QUOTE_STRING;
import static com.intellij.psi.StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN;
import static com.intellij.psi.TokenType.BAD_CHARACTER;

%%

%{
    private boolean hasUnmatchedText = false;

    public static FlexAdapter getAdapter() {
        return new FlexAdapter(new SingleQuoteStringLexer());
    }

    private SingleQuoteStringLexer() {
        this(null);
    }
%}

%public
%class SingleQuoteStringLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

/* It is guaranteed that a string literal has following properties:
 * - it starts with single quote (but may not end with quote)
 * - it does not contain \n characters
 * - stand alone single quote always may be only at the end of string */

%state AFTER_FIRST_QUOTE
%state AFTER_LAST_QUOTE
%state ESCAPE_SEQUENCE_STATE

%%
<YYINITIAL> {
  ' / (\\[nrbtf\\] | '')  { yybegin(AFTER_FIRST_QUOTE); return SINGLE_QUOTE_STRING; } // string starts with escaped char
  '                       { yybegin(AFTER_FIRST_QUOTE); hasUnmatchedText = true; }
}

<AFTER_FIRST_QUOTE> {
  <<EOF>>                 { yybegin(AFTER_LAST_QUOTE);
                            if (hasUnmatchedText) return SINGLE_QUOTE_STRING;
                            else return null; }
  \\[nrbtf\\]             { hasUnmatchedText = false; return VALID_STRING_ESCAPE_TOKEN; }
  ''                      { hasUnmatchedText = false; return VALID_STRING_ESCAPE_TOKEN; }

  /* in the case of ''' group symbols: ('')' */
  [^'] / ''               { hasUnmatchedText = false; return SINGLE_QUOTE_STRING; }
  /* in the case of \\\ group symbols: (\\)\ */
  [^\\] / \\[nrbtf\\]     { hasUnmatchedText = false; return SINGLE_QUOTE_STRING; }
  .                       { hasUnmatchedText = true; }
}

<AFTER_LAST_QUOTE> {
 .  { return null; }
}


[^] { return BAD_CHARACTER; }
