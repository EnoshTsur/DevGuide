package com.devguide.jfx.utils;

import io.vavr.Function2;
import io.vavr.collection.List;

import java.util.function.Predicate;

public interface ListUtils {

    /***
     * Returns true if Vavr List contains object
     */
    Function2<Object, List<Object>, Boolean> isOneOfVavr = (obj , list) ->
            list.contains(obj);

    /***
     * Returns true if Util List contains object
     */
    Function2<Object, java.util.List<Object>, Boolean> isOneOfUtil = (obj, list) ->
            list.contains(obj);
}

