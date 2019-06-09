package com.devguide.jfx.utils;

import io.vavr.Function1;

import java.io.File;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.devguide.jfx.utils.StringUtils.*;

/**
 * Utils for Operation System Drivers..
 */
public interface FileSystem {

    String OS_NAME = "os.name";
    String USER_DIR = "user.dir";
    String BACKWARDS = "../";
    String CD = "cd";
    String FORWARD_SLASH = "/";

    String SYSTEM_NAME = System.getProperty(OS_NAME);
    String USER_HOME = System.getProperty(USER_DIR);

    Supplier<String> getOperationSystem = () -> System.getProperty(OS_NAME);

    Function1<String, File> getAbsolutePath =
            path -> new File(path).getAbsoluteFile();

    Function1<Function1<String, File>, Function1<String, String>>
    setFilePath = pathFunction -> path -> f("file:{0}", pathFunction.apply(path))
            .replace('\\','/');


    Function1<String, String> setAbsolutePath = setFilePath.apply(getAbsolutePath);

    /***
     * Returns true if file / directory
     */
    Function1<String, Boolean> isFileOrDirExist = address -> {
        File destination = new File(address);
        return destination.exists();
    };

}
