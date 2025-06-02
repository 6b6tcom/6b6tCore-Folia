package me.txmc.core.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class providing basic helpers for 6b6t.
 */
public class GlobalUtils {
    private static final Logger LOGGER = Logger.getLogger("6b6tCore");

    public static void log(Level level, String message, Object... args) {
        LOGGER.log(level, String.format(message, args));
    }

    public static String getStringContent(String input) {
        return input;
    }
}
