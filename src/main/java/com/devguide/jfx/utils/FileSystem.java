package com.devguide.jfx.utils;

import io.vavr.Function1;

import java.io.File;
import java.util.function.Supplier;

import static com.devguide.jfx.utils.StringUtils.*;

/**
 * Utils for Operation System Drivers..
 */
public interface FileSystem {

    String SYSTEM_NAME = "os.name";

    Supplier<String> getOperationSystem = () -> {
        System.getProperties().list(System.out);
        return System.getProperty(SYSTEM_NAME);
    };

    Function1<String, File> getAbsolutePath =
            path -> new File(path).getAbsoluteFile();

    Function1<Function1<String, File>, Function1<String, String>>
    setFilePath = pathFunction -> path -> f("file:{0}", pathFunction.apply(path))
            .replace('\\','/');


    Function1<String, String> setAbsoluePath = setFilePath.apply(getAbsolutePath);
}
