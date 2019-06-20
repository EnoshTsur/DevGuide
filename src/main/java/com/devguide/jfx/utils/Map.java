package com.devguide.jfx.utils;


import java.util.*;

public class Map{

    public static <K, V> java.util.Map<K, V> of1(K k1, V v1) {
        java.util.Map<K, V> unmodified = new HashMap<K, V>(){{ put(k1, v1); }};
        return Collections.unmodifiableMap(unmodified);
    }


    public static <K, V> java.util.Map<K, V> of2(K k1, V v1, K k2, V v2) {
        java.util.Map<K, V> unmodified = new HashMap<K, V>(){{ put(k1, v1); put(k2, v2); }};
        return Collections.unmodifiableMap(unmodified);
    }

    public static <K, V> java.util.Map<K, V> of3(K k1, V v1, K k2, V v2, K k3, V v3) {
        java.util.Map<K, V> unmodified = new HashMap<K, V>(){{ put(k1, v1); put(k2, v2); put(k3, v3); }};
        return Collections.unmodifiableMap(unmodified);
    }

    public static <K, V> java.util.Map<K, V> of4(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        java.util.Map<K, V> unmodified = new HashMap<K, V>(){{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4); }};
        return Collections.unmodifiableMap(unmodified);
    }


    public static <K, V> java.util.Map<K, V> of5(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        java.util.Map<K, V> unmodified = new HashMap<K, V>(){{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4); put(k5, v5); }};
        return Collections.unmodifiableMap(unmodified);
    }



}
