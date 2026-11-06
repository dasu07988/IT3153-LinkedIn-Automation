package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;
import java.util.Optional;

/**
 * Global search box + search results page.
 * NOTE: LinkedIn changes its DOM often. If a locator stops working, update ONLY the constants below.
 */
public class SearchPage {

    private static final By SEARCH_BOX = By.cssSelector(
            "input.search-global-typeahead__input, input[placeholder='Search'], input[aria-label='Search']");

    /** Candidate result links (people, companies, schools) inside the main results area. */
    private static final By RESULT_LINKS = By.cssSelector(
            "main a[href*='/in/'], main a[href*='/company/'], main a[href*='/school/']");

    private final WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public SearchPage searchFor(String term) {
        WebElement box = WaitUtils.waitForClickable(driver, SEARCH_BOX);
        box.click();
        box.clear();
        box.sendKeys(term);
        box.sendKeys(Keys.ENTER);
        return this;
    }

    public boolean waitForResultsPage() {
        return WaitUtils.waitForUrlContains(driver, "/search/results");
    }

    public List<WebElement> getVisibleResults() {
        return WaitUtils.waitForAnyVisible(driver, RESULT_LINKS);
    }

    public boolean hasVisibleResults() {
        try {
            return !getVisibleResults().isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** First displayed result link that has a real destination and readable text. */
    public Optional<WebElement> findFirstSuitableResult() {
        return getVisibleResults().stream()
                .filter(e -> {
                    String href = e.getAttribute("href");
                    return href != null && !href.isBlank()
                            && !href.contains("/search/results")
                            && !e.getText().isBlank();
                })
                .findFirst();
    }

    public void clickResult(WebElement result) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", result);
        try {
            result.click();
        } catch (WebDriverException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", result);
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
