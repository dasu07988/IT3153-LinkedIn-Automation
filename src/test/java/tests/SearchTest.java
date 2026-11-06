package tests;

import base.BaseTest;
import config.Config;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SearchTest extends BaseTest {

    @Test(description = "TC_SEARCH_001 - Search functionality")
    public void tcSearch001_searchReturnsResults() {
        HomePage home = loginAsValidUser();
        Assert.assertTrue(home.isLoaded(), "Login precondition failed; at: " + home.getCurrentUrl());

        String term = Config.getSearchTerm();
        SearchPage search = new SearchPage(driver);
        search.searchFor(term);

        Assert.assertTrue(search.waitForResultsPage(), "Search results page did not open; at: " + search.getCurrentUrl());

        String decodedUrl = URLDecoder.decode(search.getCurrentUrl(), StandardCharsets.UTF_8).toLowerCase();
        Assert.assertTrue(decodedUrl.contains("/search/results"), "URL should contain the search results path: " + decodedUrl);
        Assert.assertTrue(decodedUrl.contains(term.toLowerCase()), "URL should contain the search term '" + term + "'");
        Assert.assertTrue(search.hasVisibleResults(), "At least one search result should be visible");
    }
}
