package com.devguide.jfx.utils;

import java.util.function.Supplier;

public enum OperationSystem {

    WINDOWS10{
        @Override
        String getName() {
            return "Windows 10";
        }
    }, LINUX{
        @Override
        String getName() {
            return "Linux";
        }
    };

     abstract String getName();
}
