package com.devguide.jfx.utils;

import java.text.MessageFormat;
import java.util.function.Function;

/***
 * String Utils Functions
 */
public interface StringUtils {

    String EMPTY_STRING = "";

    /***
     * Formatting String by index inside braces {0} {1}
     * @param freeText
     * @param params
     * @return Formatted String
     */
    static String f(String freeText, Object... params) {
        return new MessageFormat(freeText).format(params);
    }

    /**
     * Returns true if given String is empty
     */
    Function<String, Boolean> isEmpty = str -> str.equals(EMPTY_STRING);
}
