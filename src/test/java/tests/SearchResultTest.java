package tests;

import base.BaseTest;
import config.Config;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.WaitUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchResultTest extends BaseTest {

    @Test(description = "TC_SEARCH_002 - Open search result")
    public void tcSearch002_openFirstSearchResult() {
        HomePage home = loginAsValidUser();
        Assert.assertTrue(home.isLoaded(), "Login precondition failed; at: " + home.getCurrentUrl());

        SearchPage search = new SearchPage(driver);
        search.searchFor(Config.getSearchTerm());
        Assert.assertTrue(search.waitForResultsPage(), "Search results page did not open");

        WebElement first = search.findFirstSuitableResult()
                .orElseThrow(() -> new AssertionError("No suitable search result found"));

        // Capture the destination BEFORE clicking (the element may go stale afterwards)
        String expectedUrl = normalise(first.getAttribute("href"));
        String resultText = first.getText().split("\n")[0].trim();

        List<String> handlesBefore = new ArrayList<>(driver.getWindowHandles());
        search.clickResult(first);

        // Some results open in a new tab
        List<String> handlesAfter = new ArrayList<>(driver.getWindowHandles());
        if (handlesAfter.size() > handlesBefore.size()) {
            handlesAfter.removeAll(handlesBefore);
            driver.switchTo().window(handlesAfter.get(0));
        }

        Assert.assertTrue(WaitUtils.waitForUrlNotContains(driver, "/search/results"),
                "Browser should have left the search results page after clicking '" + resultText + "'");
        Assert.assertTrue(normalise(driver.getCurrentUrl()).startsWith(expectedUrl),
                "Expected destination '" + expectedUrl + "' but was '" + normalise(driver.getCurrentUrl()) + "'");
        Assert.assertFalse(driver.getTitle().isBlank(), "Destination page should have a title");
    }

    /** Strips query string, fragment and trailing slash so URLs can be compared reliably. */
    private static String normalise(String url) {
        String u = url.split("[?#]")[0];
        return u.endsWith("/") ? u.substring(0, u.length() - 1) : u;
    }
}
