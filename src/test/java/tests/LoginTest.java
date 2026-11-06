package tests;

import base.BaseTest;
import config.Config;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "TC_LOGIN_001 - Valid login")
    public void tcLogin001_validLogin() {
        HomePage home = loginAsValidUser();

        Assert.assertTrue(home.isLoaded(),
                "Expected the LinkedIn feed with search/navigation visible, but was at: " + home.getCurrentUrl());
    }

    @Test(priority = 2, description = "TC_LOGIN_002 - Invalid login")
    public void tcLogin002_invalidLogin() {
        requireCredentials(); // need a real username; the password is intentionally wrong

        LoginPage login = new LoginPage(driver).open();
        login.login(Config.getUsername(), Config.INVALID_PASSWORD);

        Assert.assertTrue(login.isErrorDisplayed(), "An authentication error message should be displayed");
        Assert.assertFalse(login.getErrorMessage().isBlank(), "Error message should not be empty");
        Assert.assertFalse(driver.getCurrentUrl().contains("/feed"), "User must NOT reach the feed with a wrong password");
    }
}
