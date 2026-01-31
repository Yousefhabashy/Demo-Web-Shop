package Base;


import Components.HeaderComponents;
import Pages.AccountPage;
import Utils.ScreenshotUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;
public class TestBase {
    public static WebDriver driver;
    public static boolean isLoggedIn = false;
    public static String downloadsPath;

    static {
        // Convert to absolute path
        File downloadsDir = new File(System.getProperty("user.dir") + "/downloads");
        downloadsPath = downloadsDir.getAbsolutePath();

        // Create the folder if it doesn't exist
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs();
        }
    }


    public static ChromeOptions chromeOptions() {

        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();

        chromePrefs.put("autofill.address_enabled", false);
        chromePrefs.put("autofill.credit_card_enabled", false);
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadsPath);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("safebrowsing.enabled", "false");
        options.setExperimentalOption("prefs", chromePrefs);
        options.setAcceptInsecureCerts(true);

        return options;
    }

    public static FirefoxOptions firefoxOptions() {

        FirefoxOptions options = new FirefoxOptions();

        options.addPreference("extensions.formautofill.addresses.enabled", false);
        options.addPreference("extensions.formautofill.creditCards.enabled", false);
        options.addPreference("browser.formfill.enable", false);
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", downloadsPath);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/pdf,application/octet-stream");
        options.addPreference("browser.download.manager.showWhenStarting", false);
        options.addPreference("pdfjs.disabled", true);
        options.setAcceptInsecureCerts(true);

        return options;
    }

    @BeforeSuite
    @Parameters({"browser"})
    public void setDriver(@Optional("chrome") String browserName) {

        if(browserName.equalsIgnoreCase("chrome")) {

            driver= new ChromeDriver(chromeOptions());
        } else if (browserName.equalsIgnoreCase("chrome-headless")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");

            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {

            driver = new FirefoxDriver(firefoxOptions());
        } else if (browserName.equalsIgnoreCase("edge")) {

            driver = new EdgeDriver();
        } else System.out.println("DRIVER NOT FOUND!!!!!");

        driver.manage().window().maximize();
        driver.navigate().to("https://demowebshop.tricentis.com/");
    }

    @AfterMethod
    public void screenshotOnFailure(ITestResult result) {

        if(ITestResult.FAILURE == result.getStatus()) {
            System.out.println("Failed!");
            System.out.println("Taking Screenshot!");
            ScreenshotUtils.takeScreenshot(driver, result.getName());
        }
    }

    @AfterSuite
    public void closeDriver() {
        driver.quit();
    }

//    public void tryLogout() {
//
//        try {
//
//            HeaderComponents header = new HeaderComponents(driver);
//            header.openAccount();
//            waitFor().until(ExpectedConditions.urlContains("account/account"));
//            Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/account"));
//
//            AccountPage account = new Account(driver);
//            account.logoutUser();
//            waitFor().until(ExpectedConditions.visibilityOf(account.mainText));
//            Assert.assertEquals(account.mainText.getText(), "ACCOUNT LOGOUT");
//            isLoggedIn = false;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @AfterClass(alwaysRun = true)
//    public void teardown() {
//        if(isLoggedIn) {
//            tryLogout();
//        }
//    }

    public static Wait<WebDriver> waitFor() {

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(NoSuchElementException.class);
    }
}
