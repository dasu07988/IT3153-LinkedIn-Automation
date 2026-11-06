package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class HomePage {

    private static final By SEARCH_BOX = By.cssSelector(
            "input.search-global-typeahead__input, input[placeholder='Search'], input[aria-label='Search']");
    private static final By NAVIGATION = By.cssSelector(
            "nav.global-nav__nav, nav[aria-label='Primary Navigation'], header#global-nav");

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /** Login succeeded when the URL is the feed and the search box or navigation is visible. */
    public boolean isLoaded() {
        boolean onFeed = WaitUtils.waitForUrlContains(driver, "/feed");
        return onFeed && (WaitUtils.isVisibleWithin(driver, SEARCH_BOX) || WaitUtils.isVisibleWithin(driver, NAVIGATION));
    }

    public boolean isSearchBoxVisible() {
        return WaitUtils.isVisibleWithin(driver, SEARCH_BOX);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
