package Base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class PagesBase {
    protected static WebDriver driver;
    public static JavascriptExecutor jsEx;

    public PagesBase(WebDriver driver) {

        PageFactory.initElements(driver,this);
    }

    public static void setElementText(WebElement element, String value) {
        element.sendKeys(value);
    }

    //  Clicks on an element using JavaScript, ignores overlays and ensures element is in view
    public static void clickElementJS(WebElement element) {
        try {
            // Scroll the element into view
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

            // Click using JavaScript
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);

        } catch (Exception e) {
            System.out.println("JS click failed: " + e.getMessage());
        }
    }

    public static Wait<WebDriver> waitFor() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(NoSuchElementException.class);
    }

    public static void scrollToBottom() {
        jsEx.executeScript("scrollBy(0,2500)");
    }
}
