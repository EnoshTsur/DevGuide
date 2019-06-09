package com.devguide.jfx.utils;

import java.util.Objects;

@FunctionalInterface
public interface Consumer3<T, U, E> {

    void accept(T t, U u, E e);


    default Consumer3<T, U, E> andThen(Consumer3<? super T, ? super U, ? super E> after) {
        Objects.requireNonNull(after);

        return (l, r, e) -> {
            accept(l, r, e);
            after.accept(l, r, e);
        };
    }
}
