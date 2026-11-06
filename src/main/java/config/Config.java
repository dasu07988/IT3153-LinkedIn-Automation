package config;

import utils.ConfigReader;

/** Typed access to configuration values. Credentials are read ONLY from the environment. */
public final class Config {

    /** Deliberately wrong password used by the negative login test. Not a real credential. */
    public static final String INVALID_PASSWORD = "Incorrect#Pass123!";

    private Config() {
    }

    public static String getBaseUrl() {
        return ConfigReader.get("base.url", "https://www.linkedin.com");
    }

    public static String getLoginUrl() {
        return getBaseUrl() + "/login";
    }

    public static boolean isHeadless() {
        return ConfigReader.getBoolean("browser.headless", false);
    }

    public static int getExplicitWait() {
        return ConfigReader.getInt("wait.explicit.seconds", 20);
    }

    public static int getPageLoadTimeout() {
        return ConfigReader.getInt("wait.pageload.seconds", 60);
    }

    public static String getSearchTerm() {
        return ConfigReader.get("search.term", "Software Testing");
    }

    public static String getScreenshotDir() {
        return ConfigReader.get("screenshot.dir", "screenshots");
    }

    public static boolean captureOnSuccess() {
        return ConfigReader.getBoolean("screenshot.on.success", true);
    }

    public static String getUsername() {
        String v = System.getProperty("linkedin.username");
        return (v == null || v.isBlank()) ? System.getenv("LINKEDIN_USERNAME") : v;
    }

    public static String getPassword() {
        String v = System.getProperty("linkedin.password");
        return (v == null || v.isBlank()) ? System.getenv("LINKEDIN_PASSWORD") : v;
    }

    public static boolean hasCredentials() {
        String u = getUsername();
        String p = getPassword();
        return u != null && !u.isBlank() && p != null && !p.isBlank();
    }
}
