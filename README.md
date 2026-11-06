# LinkedInAutomation – IT3153 Assignment No. 04

Java + Selenium WebDriver + TestNG UI automation for the LinkedIn login, search, and open-search-result flows.

## Requirements
- Java 17 or later
- Maven 3.9+
- Google Chrome
- VS Code (Extension Pack for Java recommended)

## Open the correct folder
Open the `LinkedInAutomation` folder — the folder that directly contains `pom.xml`.
Do not run Maven from the outer ZIP extraction folder.

## Configure credentials (PowerShell)
Use your own account or a dedicated test account. In the VS Code PowerShell terminal:
```powershell
$env:LINKEDIN_USERNAME = "your-test-account@example.com"
$env:LINKEDIN_PASSWORD = "your-test-password"
```
Do not paste credentials into source files or commit them to Git.

## Run
```powershell
mvn clean test
```
Chrome is configured to run visibly (`browser.headless=false`).
The test suite runs sequentially. It contains:
- TC_LOGIN_001 – valid login
- TC_LOGIN_002 – invalid-password negative test
- TC_SEARCH_001 – search for the configured term
- TC_SEARCH_002 – open the first suitable search result

## Configuration
Edit `src/test/resources/config.properties` for non-secret settings such as:
- `browser.headless`
- `wait.explicit.seconds`
- `wait.pageload.seconds`
- `search.term`
- `screenshot.dir`
- `screenshot.on.success`

## Evidence and reports
- Screenshots: `screenshots/`
- TestNG / Surefire reports: `reports/`
- Test report PDF: `IT3153_Assignment_04_Test_Report.pdf`

The included `reports/testng-results.xml` records one captured run with 4 passed, 0 failed, 0 skipped. Rerun the suite on your machine and use the newly generated results as the final submission evidence.

## Security and limitations
LinkedIn may show CAPTCHA or a security checkpoint. Do not bypass it. Stop or allow the test to skip, then document the limitation. Use only an account you own or a dedicated test account, and avoid repeated invalid-password attempts.
