# Test Report – LinkedInAutomation

**Module:** IT3153 – Software Quality Assurance  
**Assignment:** No. 04  
**Tester:** HJMYD Jayasundara D/BIT/24/0067  
**Date Executed:** 06 November 2026  
**Environment:** Windows 11, Java 21, Maven 3.10, Chrome 156, Selenium 4.25.0, TestNG 7.10.2

## 1. Scope

This project performs automated UI testing of LinkedIn using Java, Selenium WebDriver, TestNG and the Page Object Model (POM).

The main test areas are:
- Valid and invalid login.
- Searching for a topic.
- Opening a search result.

## 2. Test Cases and Results

| Test ID | Description | Expected Result | Actual Result | Status |
|---|---|---|---|---|
| TC_LOGIN_001 | Valid login | User should log in successfully and access the feed. | The login test completed successfully and the expected login condition was verified. | PASS |
| TC_LOGIN_002 | Invalid login | An authentication error should appear and login should be rejected. | The negative login test completed successfully and the expected error condition was verified. | PASS |
| TC_SEARCH_001 | Search functionality | Search results should be displayed for "Software Testing". | The search test completed successfully and the expected results condition was verified. | PASS |
| TC_SEARCH_002 | Open search result | The selected search result page should open. | The selected search result opened successfully and the expected navigation condition was verified. | PASS |

## 3. Test Execution Summary

| Total Tests | Passed | Failed | Skipped |
|---|---|---|---|
| 4 | 4 | 0 | 0 |

The TestNG result file recorded four passed tests, zero failed tests and zero skipped tests. The recorded execution took approximately 58 seconds.

## 4. Locators Used

The following locator strategies were used in the Page Object classes:

- **ID:** Used to identify login fields such as username and password.
- **Name:** Used as an alternative locator for login form fields.
- **CSS Selector:** Used to identify search boxes, navigation elements, buttons and result links.
- **XPath:** Used as a fallback when elements could not be reliably identified using simpler locators.

These locator strategies help identify elements on LinkedIn's dynamic web pages.

## 5. Challenges and Observations

- LinkedIn uses dynamic page elements, so some elements may take time to become visible.
- Explicit waits were used to wait for elements before interacting with them.
- Multiple locator strategies were included to improve locator reliability.
- LinkedIn may display CAPTCHA or security checkpoints because it restricts automated access.
- The test suite must not bypass CAPTCHA or security verification. Such steps should be completed manually or documented as limitations.

## 6. Conclusion

The automated test suite successfully executed four test cases covering login, search and opening a search result. TestNG recorded four passed tests with no failures or skipped tests in the included execution evidence.

The Page Object Model improves code organization and maintainability by separating page elements and actions from test methods. However, UI automation against LinkedIn has limitations because its page structure and security controls can change.
