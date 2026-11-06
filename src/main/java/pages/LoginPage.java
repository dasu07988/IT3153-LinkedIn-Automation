package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;
import java.util.logging.Logger;

/**
 * LinkedIn login page.
 * Every field has several fallback locators (name/type/label text) because LinkedIn changes
 * element ids from time to time. The first locator that matches a visible element wins.
 */
public class LoginPage {

    private static final Logger LOG = Logger.getLogger(LoginPage.class.getName());

    private static final List<By> USERNAME = List.of(
            By.id("username"),
            By.name("session_key"),
            By.cssSelector("input[autocomplete='username']"),
            By.cssSelector("input[type='email']"),
            // the <input> that the "Email or phone" label points to, whatever its id is
            By.xpath("//input[@id=//label[contains(normalize-space(.),'Email or phone')]/@for]"),
            By.xpath("//label[contains(normalize-space(.),'Email or phone')]/following::input[1]"));

    private static final List<By> PASSWORD = List.of(
            By.id("password"),
            By.name("session_password"),
            By.cssSelector("input[type='password']"),
            By.xpath("//input[@id=//label[normalize-space(.)='Password']/@for]"));

    private static final List<By> SIGN_IN_BUTTON = List.of(
            By.cssSelector("button[data-litms-control-urn='login-submit']"),
            By.xpath("//button[normalize-space(.)='Sign in']"),
            By.cssSelector("button[type='submit']"));

    private static final List<By> ERROR_MESSAGE = List.of(
            By.cssSelector("#error-for-password, #error-for-username, .form__label--error"),
            By.cssSelector("[role='alert']"),
            By.xpath("//*[not(*)][contains(normalize-space(.),'Wrong email or password')]"),
            By.xpath("//*[not(*)][contains(normalize-space(.),'not the right password')]"),
            By.xpath("//*[not(*)][contains(normalize-space(.),'find a LinkedIn account')]"));

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage open() {
        driver.get(Config.getLoginUrl());
        WaitUtils.waitForAnyOf(driver, USERNAME);
        return this;
    }

    public LoginPage enterUsername(String username) {
        WebElement field = WaitUtils.waitForAnyOf(driver, USERNAME);
        field.click();
        field.clear();
        field.sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement field = WaitUtils.waitForAnyOf(driver, PASSWORD);
        field.click();
        field.clear();
        field.sendKeys(password); // value is intentionally never logged
        return this;
    }

    public void clickSignIn() {
        try {
            WaitUtils.waitForAnyOf(driver, SIGN_IN_BUTTON).click();
        } catch (TimeoutException e) {
            // fallback: submit the form with Enter from the password field
            WaitUtils.waitForAnyOf(driver, PASSWORD).sendKeys(Keys.ENTER);
        }
    }

    /** Convenience flow. Neither the username nor the password is ever logged. */
    public void login(String username, String password) {
        LOG.info("Submitting login form");
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isErrorDisplayed() {
        return WaitUtils.isAnyVisibleWithin(driver, ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        return WaitUtils.waitForAnyOf(driver, ERROR_MESSAGE).getText().trim();
    }

    /** True when LinkedIn shows a CAPTCHA / verification step instead of the feed. */
    public boolean isSecurityChallenge() {
        String url = driver.getCurrentUrl();
        return url.contains("/checkpoint/challenge") || url.contains("/uas/") || url.contains("captcha");
    }
}
