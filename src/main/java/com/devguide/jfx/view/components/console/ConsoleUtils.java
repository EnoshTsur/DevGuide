package com.devguide.jfx.view.components.console;

import com.devguide.jfx.utils.FileSystem;
import com.devguide.jfx.utils.StringUtils;
import io.vavr.Function1;
import io.vavr.collection.List;

import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.StringUtils.*;

public interface ConsoleUtils {

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
            INPUT_HEIGHT = 50;


    List<String> basicCommands = List.of(
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

}
