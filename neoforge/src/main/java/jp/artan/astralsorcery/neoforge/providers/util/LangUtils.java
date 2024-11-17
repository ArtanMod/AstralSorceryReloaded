package jp.artan.astralsorcery.neoforge.providers.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class LangUtils {
    private static final String NORMAL_CHARS = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_,;.?!/\\'";
    private static final String UPSIDE_DOWN_CHARS = "ɐqɔpǝɟbɥıظʞןɯuuodbɹsʇnʌʍxʎzⱯᗺƆᗡƎℲ⅁HIſʞꞀWNOԀὉᴚS⟘∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86‾'؛˙¿¡/\\,";

    public LangUtils() {
    }

    public static String toEnglishName(String internalName) {
        return (String) Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }

    public static String toUpsideDownEnglish(String internalName) {
        return toUpsideDown(toEnglishName(internalName));
    }

    private static String toUpsideDown(String normal) {
        char[] ud = new char[normal.length()];

        for(int i = 0; i < normal.length(); ++i) {
            char c = normal.charAt(i);
            if (c != '%') {
                int lookup = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_,;.?!/\\'".indexOf(c);
                if (lookup >= 0) {
                    c = "ɐqɔpǝɟbɥıظʞןɯuuodbɹsʇnʌʍxʎzⱯᗺƆᗡƎℲ⅁HIſʞꞀWNOԀὉᴚS⟘∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86‾'؛˙¿¡/\\,".charAt(lookup);
                }

                ud[normal.length() - 1 - i] = c;
            } else {
                String fmtArg;
                for(fmtArg = ""; Character.isDigit(c) || c == '%' || c == '$' || c == 's' || c == 'd'; c = i == normal.length() ? 0 : normal.charAt(i)) {
                    fmtArg = fmtArg + c;
                    ++i;
                }

                --i;

                for(int j = 0; j < fmtArg.length(); ++j) {
                    ud[normal.length() - 1 - i + j] = fmtArg.charAt(j);
                }
            }
        }

        return new String(ud);
    }
}
