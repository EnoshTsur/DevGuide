package com.devguide.jfx.execute;

import io.vavr.Function1;

public interface ShellFactory {

    String CMD = "cmd";
    String BASH = "bash";

    /***
     * Shell Factory - return shell by type
     */
    Function1<ShellType, String[]> getShell = type -> {

        String[] cmd = new String[] {CMD, "/c", ""};
        String[] bash = new String[] {BASH, "-c", ""};

        switch (type) {
            case CMD:
                return cmd;
            case BASH:
                return bash;
                default:
                    return bash;
        }
    };
}
