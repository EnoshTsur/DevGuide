package com.devguide.jfx.utils;

import io.vavr.Function1;

/***
 * Basic Utils
 */
public interface BasicUtils {

    Function1<Double, Double> addOne = number -> number + 0.1;
    Function1<Double, Double> minusOne = number -> number - 0.1;

    /**
     * Null validation : return true if object is null
     */
    Function1<Object, Boolean> isNull = object -> object == null;

    /***
     * Not - returns the opposite of the input function output
     * Takes : Function < Object, Boolean >
     * Returns: Not Function < Object , Boolean ></>
     */
    Function1<Function1<Object, Boolean>, Function1<Object, Boolean>> not =
            fn -> object -> !fn.apply(object);

    /***
     * Is Not Null
     */
    Function1<Object, Boolean> isNotNull = not.apply(isNull);
    /***
     * Double or zero
     */
    Function1<Double, Double> doubleOrZero =
            value -> isNull.apply(value) ? 0 : value;

}
