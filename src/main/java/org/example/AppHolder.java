package org.example;

public class AppHolder {
    private static App instance;

    public static void set(App app) {
        instance = app;
    }

    public static App get() {
        return instance;
    }
}
