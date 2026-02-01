package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EmailFriendPage extends PagesBase {
    public EmailFriendPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a.product")
    public WebElement productName;

    @FindBy(id = "FriendEmail")
    WebElement friendEmailBox;
    @FindBy(id = "YourEmailAddress")
    WebElement yourEmailBox;
    @FindBy(id = "PersonalMessage")
    WebElement messageBox;
    @FindBy(css = "input.button-1.send-email-a-friend-button")
    WebElement sendEmail;
    public void sendEmail(String friend, String sender, String message) {
        setElementText(friendEmailBox, friend);
        yourEmailBox.clear();
        setElementText(yourEmailBox, sender);
        setElementText(messageBox, message);
        waitFor().until(ExpectedConditions.elementToBeClickable(sendEmail));
        clickElementJS(sendEmail);
    }

    @FindBy(css = "div.result")
    public WebElement successMessage;
}
