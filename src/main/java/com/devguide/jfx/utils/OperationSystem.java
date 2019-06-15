package com.devguide.jfx.utils;

import java.util.function.Supplier;

public enum OperationSystem {

    WINDOWS10 {
        @Override
        public String getName() {
            return "Windows 10";
        }
    }, LINUX {
        @Override
        public String getName() {
            return "Linux";
        }
    };

    public abstract String getName();
}
