package com.devguide.jfx.utils;

import io.vavr.Function1;
import io.vavr.collection.List;

import java.io.File;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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

    Supplier<String> operationSystem = () -> System.getProperty(OS_NAME);

    /***
     * Checking if my operation system is equals to given enum
     * Both names getting trim & Upper case
     * Windows 10 || Linux
     */
    Predicate<OperationSystem> isMyOperationSystem = kind ->
            trimAndUpper.apply(operationSystem.get())
                    .equals(trimAndUpper.apply(kind.getName()));

    /***
     * Get Absolute Path by Relative
     */
    Function1<String, File> getAbsolutePath =
            path -> new File(path).getAbsoluteFile();

    /***
     * Set File Path (file://) & Replace backward slash
     */
    Function1<Function1<String, File>, Function1<String, String>>
    setFilePath = pathFunction -> path -> f("file:{0}", pathFunction.apply(path))
            .replace('\\','/');


    /***
     * Aet Absolute Path + Set File (file://)
     */
    Function1<String, String> setAbsolutePath = setFilePath.apply(getAbsolutePath);

    /***
     * Returns true if file / directory
     */
    Function1<String, Boolean> isFileOrDirExist = address -> {
        File destination = new File(address);
        return destination.exists();
    };

    /***
     * Returns the last folder from the path
     */
    Function1<File, String> getLastFolder = fullPath -> {
        String path = fullPath.getPath();
        if (doesItContains.apply(FORWARD_SLASH, path)) {
            List<String> lastTwo = List.ofAll(
                    Stream.of(
                            path.split(FORWARD_SLASH)
                    ).collect(Collectors.toList())
            );
            return lastTwo.reduce((first, second) -> second);
        }
        return path;
    };

}
