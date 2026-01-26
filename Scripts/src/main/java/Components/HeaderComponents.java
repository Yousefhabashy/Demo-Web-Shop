package Components;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderComponents extends PagesBase {
    public HeaderComponents(WebDriver driver) {
        super(driver);
        PagesBase.driver = driver;
    }

    @FindBy(linkText = "Register")
    WebElement Register;
    public void openRegister() {
        waitFor().until(ExpectedConditions.elementToBeClickable(Register));
        clickElementJS(Register);
    }

}
