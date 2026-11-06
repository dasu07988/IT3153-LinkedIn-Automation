package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads configuration. Lookup order:
 * 1) JVM system property (-Dkey=value)
 * 2) Environment variable (key upper-cased, '.' replaced by '_')
 * 3) config.properties on the classpath
 */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on the classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        return get(key, null);
    }

    public static String get(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (isBlank(value)) {
            value = System.getenv(key.toUpperCase().replace('.', '_'));
        }
        if (isBlank(value)) {
            value = PROPS.getProperty(key);
        }
        return isBlank(value) ? defaultValue : value.trim();
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
