
package utils;

import config.Config;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public final class ScreenshotUtils {

    private static final Logger LOG =
            Logger.getLogger(ScreenshotUtils.class.getName());

    private static final DateTimeFormatter TS =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver, String name) {

        if (driver == null) {
            LOG.warning("Screenshot failed: WebDriver is null.");
            return null;
        }

        try {
            // Create screenshot directory
            Path dir = Paths.get(Config.getScreenshotDir());
            Files.createDirectories(dir);

            // Capture screenshot
            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            // Generate filename
            String safeName = name.replaceAll(
                    "[^a-zA-Z0-9_-]", "_"
            );

            String fileName = safeName + "_"
                    + LocalDateTime.now().format(TS) + ".png";

            Path target = dir.resolve(fileName);

            // Save screenshot
            Files.copy(
                    source.toPath(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

            LOG.info("Screenshot saved successfully: "
                    + target.toAbsolutePath());

            return target.toString();

        } catch (IOException | WebDriverException e) {

            LOG.warning("Screenshot capture failed: "
                    + e.getMessage());

            return null;
        }
    }

    /**
     * Saves the page HTML next to the screenshots. When a locator breaks, this file shows the
     * real element ids/names so the Page Object can be fixed quickly.
     */
    public static String savePageSource(WebDriver driver, String name) {
        if (driver == null) {
            return null;
        }
        try {
            Path dir = Paths.get(Config.getScreenshotDir());
            Files.createDirectories(dir);
            Path target = dir.resolve(name.replaceAll("[^a-zA-Z0-9_-]", "_") + "_" + LocalDateTime.now().format(TS) + ".html");
            Files.writeString(target, "<!-- URL: " + driver.getCurrentUrl() + " -->\n" + driver.getPageSource(), StandardCharsets.UTF_8);
            LOG.info("Page source saved: " + target);
            return target.toString();
        } catch (IOException | RuntimeException e) {
            LOG.warning("Could not save page source: " + e.getMessage());
            return null;
        }
    }
}
