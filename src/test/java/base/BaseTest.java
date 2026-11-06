
package base;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import pages.HomePage;
import pages.LoginPage;
import utils.ScreenshotUtils;

import java.time.Duration;
import java.util.logging.Logger;

public class BaseTest {

    protected WebDriver driver;

    private static final Logger LOG =
            Logger.getLogger(BaseTest.class.getName());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--start-maximized",
                "--disable-notifications",
                "--remote-allow-origins=*"
        );

        if (Config.isHeadless()) {
            options.addArguments(
                    "--headless=new",
                    "--window-size=1920,1080"
            );
        }

        driver = new ChromeDriver(options);

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(Config.getPageLoadTimeout())
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (driver == null) {
            return;
        }

        try {

            // Capture screenshot BEFORE closing browser
            if (result.getStatus() == ITestResult.FAILURE) {

                LOG.info("Test failed. Capturing failure screenshot.");

                ScreenshotUtils.capture(
                        driver,
                        result.getName() + "_FAILED"
                );

                // HTML dump: shows the real element ids/names if a locator broke
                ScreenshotUtils.savePageSource(
                        driver,
                        result.getName() + "_FAILED"
                );

            } else if (
                    result.getStatus() == ITestResult.SUCCESS
                    && Config.captureOnSuccess()
            ) {

                LOG.info("Test passed. Capturing success screenshot.");

                ScreenshotUtils.capture(
                        driver,
                        result.getName() + "_PASSED"
                );
            }

        } catch (Exception e) {

            LOG.warning("Screenshot process error: "
                    + e.getMessage());

        } finally {

            // Close browser AFTER screenshot attempt
            try {
                driver.quit();
                LOG.info("Browser closed successfully.");

            } catch (Exception e) {
                LOG.warning("Browser close error: "
                        + e.getMessage());

            } finally {
                driver = null;
            }
        }
    }

    protected void requireCredentials() {

        if (!Config.hasCredentials()) {
            throw new SkipException(
                    "LINKEDIN_USERNAME / LINKEDIN_PASSWORD "
                    + "environment variables are not set"
            );
        }
    }

    protected HomePage loginAsValidUser() {

        requireCredentials();

        LoginPage login = new LoginPage(driver).open();

        login.login(
                Config.getUsername(),
                Config.getPassword()
        );

        if (login.isSecurityChallenge()) {
            throw new SkipException(
                    "LinkedIn showed a security challenge. "
                    + "Complete verification manually."
            );
        }

        return new HomePage(driver);
    }
}
