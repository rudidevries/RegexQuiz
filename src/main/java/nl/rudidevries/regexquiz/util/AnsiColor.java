package nl.rudidevries.regexquiz.util;

/**
 * Helper for using ANSI colors in Console.
 */
public enum AnsiColor {
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    RESET("\u001B[0m")
    ;

    private String colorCode;

    /**
     * Constructor
     * @param colorCode color code
     */
    AnsiColor(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Apply the color code to a input string, and terminate
     * the string with a reset code.
     *
     * @param input the string to be colored.
     * @return the colored string.
     */
    public String apply(String input) {
        return this.colorCode + input + RESET.colorCode;
    }
}
