package com.devguide.jfx.utils;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/***
 * Basic Utils
 */
public interface BasicUtils {

    Object Nothing = null;

    Function1<Double, Double> addOne = number -> number + 0.1;
    Function1<Double, Double> minusOne = number -> number - 0.1;

    /**
     * Null validation : return true if object is null
     */
    Function1<Object, Boolean> isNull = Objects::isNull;

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


    /***
     * Is Zero returns true if any number is zero
     */
    Function1<Number, Boolean> isZero = number -> number.equals(0);

    /***
     * Return true if second string contains the first one
     */
    Function2<String, String, Boolean> doesItContains = (str1, str2) ->
            str2.toLowerCase().contains(str1.toLowerCase());


    /***
     * Returns true if equal
     */
    Function2<Object,Object, Boolean> doesItEqualTo = Object::equals;


}
