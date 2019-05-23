package com.devguide.jfx.utils;

import io.vavr.Function1;
import io.vavr.Function2;

import java.util.function.Function;

public interface BasicUtils {

    /**
     * Null validation : return true if object is null
     */
    Function1<Object, Boolean> isNull = object -> object == null;

    /***
     *
     */
    Function2<Function<Object, Boolean>, Object, Boolean> is = (fn, object) -> fn.apply(object);


}
