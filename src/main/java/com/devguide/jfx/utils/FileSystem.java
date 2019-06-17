package com.devguide.jfx.utils;

import com.devguide.jfx.execute.Execute;
import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.view.components.console.Console;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Try;

import java.awt.*;
import java.io.File;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.OperationSystem.*;
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
            .replace('\\', '/');


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

    Predicate<File> isItNullOrNotExists = file -> {
        if (isNull.apply(file)) return true;
        if (!file.exists()) return true;
        return false;
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

    /***
     * Open Windows directory
     */
    Consumer<File> openWindowsDirectory = file -> Try.run(() -> Desktop.getDesktop().open(file));

    /***
     * Open Linux directory
     */
    Function1<File, java.util.List<String>> openLinuxDirectory = file -> Execute.run.apply(
            f("xdg-open {0}", file), file, ShellType.BASH);
    /**
     * Open location
     */
    Consumer<File> openLocation = location -> {
        if (isMyOperationSystem.test(WINDOWS10)) {
            openWindowsDirectory.accept(location);
        } else {
            openLinuxDirectory.apply(location);
        }
    };

}
