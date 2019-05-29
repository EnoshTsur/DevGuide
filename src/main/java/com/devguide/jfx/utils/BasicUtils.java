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

}
