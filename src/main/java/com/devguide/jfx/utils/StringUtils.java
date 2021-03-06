package com.devguide.jfx.utils;

import io.vavr.Function1;
import io.vavr.Function2;

import java.text.MessageFormat;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/***
 * String Utils Functions
 */
public interface StringUtils {

    String ABC = "abcdefghijklmnopqrstuvwxyz";

    String EMPTY_STRING = "";

    String SPACE = " ";

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

    /***
     * Returns String trim
     */
    Function1<String, String> trim = String::trim;

    /***
     * Trim & Lower
     */
    Function1<String, String> trimAndLower = str -> trim.apply(str).toLowerCase();

    /***
     * Trim & Upper
     */
    Function1<String, String> trimAndUpper = str -> trim.apply(str).toUpperCase();

    /***
     * Returns True if both strings are equal
     */
    Function2<String, String, Boolean> doesItEqualsTo = (str1, str2) -> str1.equals(str2);

    /***
     * Return true if second string contains the first one
     */
    Function2<String, String, Boolean> doesItContains = (str1, str2) ->
            str2.toLowerCase().contains(str1.toLowerCase());

    /***
     * Returns true if String is null or empty
     */
    Predicate<String> doesItNullOrEmpty = str ->
            BasicUtils.isNull.apply(str) || isEmpty.apply(str);



}
