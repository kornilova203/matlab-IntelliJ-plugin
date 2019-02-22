package com.github.korniloval.matlab;

import com.github.korniloval.matlab.psi.MatlabElementType;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import org.jetbrains.annotations.NotNull;

import static com.github.korniloval.matlab.psi.MatlabTypes.END;
import static com.github.korniloval.matlab.psi.MatlabTypes.IDENTIFIER;

/**
 * @author Liudmila Kornilova
 **/
public class MatlabParserUtil extends GeneratedParserUtilBase {

    public static boolean parseIdentifierOrRemapEndToken(PsiBuilder builder, int level) {
        if (builder.getTokenType() == IDENTIFIER) {
            builder.advanceLexer();
            return true;
        } else if (builder.getTokenType() == END) {
            Marker mark = builder.mark();
            builder.advanceLexer();
            mark.collapse(IDENTIFIER);
            return true;
        }
        return false;
    }

    public static boolean parseIdentifierAsKeyword(PsiBuilder builder, int level, @NotNull MatlabElementType type) {
        if (builder.getTokenType() == IDENTIFIER && type.toString().equals(builder.getTokenText())) {
            Marker mark = builder.mark();
            builder.advanceLexer();
            mark.collapse(type);
            return true;
        }
        return false;
    }
}
