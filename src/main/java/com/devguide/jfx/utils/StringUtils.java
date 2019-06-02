package com.devguide.jfx.utils;

import io.vavr.Function1;

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
    Function1<String, Boolean> isEmpty = str -> str.equals(EMPTY_STRING);

    /***
     * Not Returns the opposite of the input function output
     */
    Function1<Function1<String, Boolean>, Function1<String, Boolean>> not =
            fn -> str -> !fn.apply(str);

    /***
     * Return true if String is not empty
     */
    Function1<String, Boolean> isNotEmpty = not.apply(isEmpty);
}
