package com.devguide.jfx.utils;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Try;


import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/***
 * Basic Utils
 */
public interface BasicUtils {

    /***
     * Returns true if its true
     */
    Predicate<Boolean> isItTrue = bool -> bool == true;

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
     * Returns true if equal
     */
    Function2<Object,Object, Boolean> doesItEqualTo = Object::equals;


    /***
     * Throw error with message
     */
    Function<Throwable, RuntimeException> throwError = throwable -> new RuntimeException(throwable.getMessage());

    /***
     * Run or throw error
     */
    Consumer<Runnable> runOrThrowError = runnable -> Try.run( () -> runnable.run()).getOrElseThrow(throwError);


}
