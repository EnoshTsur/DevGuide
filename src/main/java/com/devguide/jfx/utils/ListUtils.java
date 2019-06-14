package com.devguide.jfx.utils;

import io.vavr.Function1;
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
    Function2<Object, java.util.List<? extends Object>, Boolean> isOneOfUtil = (obj, list) ->
            list.contains(obj);

    /**
     * Not - negate function
     */
    Function1<Function2<Object, java.util.List<? extends Object>, Boolean>,
            Function2<Object, java.util.List<? extends Object>, Boolean>> not =
            fn -> (object, list) -> !fn.apply(object, list);

    /***
     * Negate of one of util
     */
    Function2<Object,java.util.List<? extends Object>, Boolean> isNotOneOfUtil =
            not.apply(isOneOfUtil);

}

