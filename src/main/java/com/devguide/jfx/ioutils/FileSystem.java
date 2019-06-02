package com.devguide.jfx.ioutils;

import io.vavr.Function1;

/**
 * Utils for Operation System Drivers..
 */
public interface FileSystem {

    String SYSTEM_NAME = "os.name";

    static String getOperationSystem() {
        System.getProperties().list(System.out);
        return System.getProperty(SYSTEM_NAME);
    }
}
