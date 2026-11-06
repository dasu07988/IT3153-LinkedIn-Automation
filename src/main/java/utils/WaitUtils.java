package utils;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/** Reusable explicit-wait helpers. The project never uses implicit waits or Thread.sleep. */
public final class WaitUtils {

    private WaitUtils() {
    }

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(Config.getExplicitWait()));
    }

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForUrlContains(WebDriver driver, String fragment) {
        try {
            return wait(driver).until(ExpectedConditions.urlContains(fragment));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static boolean waitForUrlNotContains(WebDriver driver, String fragment) {
        try {
            return wait(driver).until(ExpectedConditions.not(ExpectedConditions.urlContains(fragment)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Waits until at least one element matching the locator is displayed; returns the displayed ones. */
    public static List<WebElement> waitForAnyVisible(WebDriver driver, By locator) {
        return wait(driver)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .until(d -> {
                    List<WebElement> visible = d.findElements(locator).stream()
                            .filter(WebElement::isDisplayed)
                            .toList();
                    return visible.isEmpty() ? null : visible;
                });
    }

    public static boolean isVisibleWithin(WebDriver driver, By locator) {
        try {
            waitForVisible(driver, locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Waits until ANY of the given locators matches a displayed element and returns it.
     * Lets page objects survive small DOM changes by listing several fallback locators.
     */
    public static WebElement waitForAnyOf(WebDriver driver, List<By> locators) {
        return wait(driver)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .until(d -> {
                    for (By by : locators) {
                        Optional<WebElement> hit = d.findElements(by).stream()
                                .filter(WebElement::isDisplayed)
                                .findFirst();
                        if (hit.isPresent()) {
                            return hit.get();
                        }
                    }
                    return null;
                });
    }

    public static boolean isAnyVisibleWithin(WebDriver driver, List<By> locators) {
        try {
            waitForAnyOf(driver, locators);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
