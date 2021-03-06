package com.devguide.jfx.view.components.console;

import com.devguide.jfx.utils.Consumer3;
import io.vavr.Function1;
import io.vavr.collection.List;
import java.util.Date;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.StringUtils.*;

public interface ConsoleUtils {

    /***
     * Greetings
     */
    Function1<String, String> greetings = name -> {
        System.out.println(System.getProperty("user.name"));
        return doesItNullOrEmpty.test(name) ?
                f("Welcome Back {0}!", System.getProperty("user.name")) :
                f("Welcome Back {0}!", name);
    };

    /**
     * Get all drivers ( A B C )
     */
    List<String> allDrivers = List.ofAll(
            Stream.of(ABC.split(EMPTY_STRING))
                    .map(driver -> f("{0}:", driver))
                    .collect(Collectors.toList()));



    /***
     * Linux Commands
     */
    List<String> linuxCommands = List.of(
            "rm",
            "rf",
            "ls",
            "show",
            "cat",
            "vi",
            "sudo"
    ).sorted();

    /***
     * Windows Commands
     */
    List<String> windowsCommands = List.of(
            "mkdir",
            "rmdir",
            "del",
            "move",
            "echo",
            "ping",
            "dir",
            "type"
    ).sorted();

    String INPUT_MESSAGE = "Command Line";

    double OUTPUT_WIDTH = 780,
            OUTPUT_HEIGHT = 610,
            INPUT_WIDTH = OUTPUT_WIDTH,
            INPUT_HEIGHT = 40;


    List<String> gitCommands = List.of(
            "git init",
            "git status",
            "git add .",
            "git commit -m",
            "git pull",
            "git push",
            "git fetch",
            "git merge",
            "git upmaster",
            "git rebase master",
            "git rebase --continue",
            "git rebase abort",
            "git s",
            "git checkout",
            "git log",
            "git reflog",
            "git commit --amend",
            "git stash",
            "git stash pop",
            "git stash save",
            "git stash apply",
            "git reset HEAD",
            "git reset --hard",
            "git rm --cached",
            CD,
            f("{0} {1}", CD, BACKWARDS)
    ).sorted();

    /***
     * Logging error
     */
    Function1<String, String> logError = text ->
            f("[Error]{0}: {1}", new Date(), text);


    /***
     * Help Message by console functions
     */
    Supplier<List<String>> helpMessage = () -> List.of(
              EMPTY_STRING,
              EMPTY_STRING,
              "~~~~~~~~~~~~~~~~~~~~~~~~~",
              "~~ ConsoleView Application ~~",
              "~~~~~~~~~~~~~~~~~~~~~~~~~",
              EMPTY_STRING,
              "ConsoleView app - contains common console methods.",
              "And few custom methods such as:",
              EMPTY_STRING,
              "1) open - Opens directory location",
              "   Directory's location is next to the console output )",
              EMPTY_STRING,
              "2) color - Change console text color.",
              "   Example: color red",
              EMPTY_STRING,
              "3) login - Performs login to Frontend Masters",
              "   In order to watch courses online.",
              EMPTY_STRING,
              "4) exit - Quit program",
              EMPTY_STRING,
              "5) help - Provides informative help message ( this )",
              EMPTY_STRING,
              EMPTY_STRING,
              "*************************************************************************",
              "***                                                                   ***",
              "***   Important: Original commands depends on your operation system   ***",
              "***   Example: On Linux: 'pwd', on Windows: 'echo %cd%'               ***",
              "***                                                                   ***",
              "*************************************************************************"
      );

}
