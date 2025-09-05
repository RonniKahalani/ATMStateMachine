package com.example.atm.util;

/**
 * Color codes for console texts
 */
public enum ConsoleColors {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String code;

    /**
     * Constructor
     * @param code representing the color
     */
    ConsoleColors(String code) {
        this.code = code;
    }

    /**
     * Returns the chars representing the color.
     *
     * @return color code
     */
    public String getCode() {
        return code;
    }
}
