# Test Report – LinkedInAutomation

**Module:** IT3153 – Software Quality Assurance
**Tester:** HJMYD Jayasundara D/BIT/24/0067
**Date executed:** 
**Environment:** Windows 11, Java 21, Maven 3.10, Chrome 156, Selenium 4.25.0, TestNG 7.10.2

## 1. Scope
Automated UI testing of LinkedIn's login, search, and open-search-result flows.

## 2. Test cases and results
| ID | Title | Expected result | Actual result | Status | Evidence |
|----|-------|-----------------|---------------|--------|----------|
| TC_LOGIN_001 | Valid login | Feed opens; search box/navigation visible | <fill in> | <PASS/FAIL> | screenshots/tcLogin001_validLogin_PASSED_*.png |
| TC_LOGIN_002 | Invalid login | Authentication error displayed; not logged in | <fill in> | <PASS/FAIL> | screenshots/tcLogin002_invalidLogin_PASSED_*.png |
| TC_SEARCH_001 | Search functionality | Results URL contains search path + term; at least 1 visible result | <fill in> | <PASS/FAIL> | screenshots/tcSearch001_*.png |
| TC_SEARCH_002 | Open search result | Browser navigates to the clicked result's URL | <fill in> | <PASS/FAIL> | screenshots/tcSearch002_*.png |

## 3. Summary
| Total | Passed | Failed | Skipped |
|-------|--------|--------|---------|
| 4 | <n> | <n> | <n> |

## 4. Defects / observations
<List failures, CAPTCHA/checkpoint occurrences, locator changes, flaky behaviour.>

## 5. Conclusion
<Short paragraph on framework quality, maintainability (POM), and limitations.>
