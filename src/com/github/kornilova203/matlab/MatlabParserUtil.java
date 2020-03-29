package com.github.kornilova203.matlab;

import com.github.kornilova203.matlab.psi.MatlabElementType;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.util.Key;
import gnu.trove.TObjectLongHashMap;
import org.jetbrains.annotations.NotNull;

import static com.github.kornilova203.matlab.psi.MatlabTypes.*;

/**
 * @author Liudmila Kornilova
 **/
public class MatlabParserUtil extends GeneratedParserUtilBase {
    static final String ALLOW_END_AS_IDENTIFIER = "ALLOW_END_AS_IDENTIFIER";
    static final String NEW_LINE_ALLOWED = "NEW_LINE_ALLOWED";
    private static final Key<TObjectLongHashMap<String>> MODES_KEY = Key.create("MODES_KEY");

    public static boolean parseIdentifier(PsiBuilder builder, int level) {
        if (builder.getTokenType() == IDENTIFIER) {
            builder.advanceLexer();
            return true;
        } else if (isOn(builder, level, ALLOW_END_AS_IDENTIFIER) && builder.getTokenType() == END) {
            Marker mark = builder.mark();
            builder.advanceLexer();
            mark.collapse(IDENTIFIER);
            return true;
        }
        return false;
    }

    public static boolean parseWhiteSpace(PsiBuilder builder, int level, Parser brParser) {
        Boolean newLineAllowed = null;
        while (true) {
            if (brParser.parse(builder, level)) continue;
            if (builder.getTokenType() == NEWLINE) {
                if (newLineAllowed == null) newLineAllowed = isOn(builder, level, NEW_LINE_ALLOWED);
                if (newLineAllowed) {
                    builder.advanceLexer();
                    continue;
                }
            }
            break;
        }
        return true;
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

    public static boolean withOn(PsiBuilder builder, int level, String mode, Parser parser) {
        return withImpl(builder, level, mode, true, parser);
    }

    public static boolean withOff(PsiBuilder builder, int level, String mode, Parser parser) {
        return withImpl(builder, level, mode, false, parser);
    }

    private static boolean withImpl(PsiBuilder builder, int level, String mode, boolean onOff, Parser parser) {
        TObjectLongHashMap<String> map = getParsingModes(builder);
        long prev = map.get(mode);
        boolean change = ((prev & 1) == 0) == onOff;
        if (change) map.put(mode, prev << 1 | (onOff ? 1 : 0));
        boolean result = parser.parse(builder, level);
        if (change) map.put(mode, prev);
        return result;
    }

    private static boolean isOn(PsiBuilder builder, int level, String mode) {
        TObjectLongHashMap<String> map = getParsingModes(builder);
        return (map.get(mode) & 1) != 0;
    }

    private static TObjectLongHashMap<String> getParsingModes(PsiBuilder builder) {
        TObjectLongHashMap<String> flags = builder.getUserData(MODES_KEY);
        if (flags == null) builder.putUserData(MODES_KEY, flags = new TObjectLongHashMap<>());
        return flags;
    }
}
