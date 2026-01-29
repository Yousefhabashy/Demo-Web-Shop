package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends PagesBase {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input.button-1.register-button")
    WebElement register;
    public void openRegister() {
        waitFor().until(ExpectedConditions.elementToBeClickable(register));
        clickElementJS(register);
    }

    @FindBy(id = "Email")
    WebElement emailBox;
    @FindBy(id = "Password")
    WebElement passwordBox;
    @FindBy(id = "RememberMe")
    WebElement rememberMe;
    @FindBy(css = "input.button-1.login-button")
    WebElement loginButton;
    public void loginUser(String email, String password, boolean remember) {

        setElementText(emailBox, email);
        setElementText(passwordBox, password);
        if(remember) {
            waitFor().until(ExpectedConditions.elementToBeClickable(rememberMe));
            clickElementJS(rememberMe);
        }

        waitFor().until(ExpectedConditions.elementToBeClickable(loginButton));
        clickElementJS(loginButton);
    }

    @FindBy(linkText = "Forgot password?")
    WebElement forgotPassword;
    public void openForgotPassword() {

        waitFor().until(ExpectedConditions.elementToBeClickable(forgotPassword));
        clickElementJS(forgotPassword);
    }

    @FindBy(css = "input.button-1.password-recovery-button")
    WebElement recoverButton;
    public void forgetPassword(String email) {
        setElementText(emailBox, email);
        waitFor().until(ExpectedConditions.elementToBeClickable(recoverButton));
        clickElementJS(recoverButton);
    }
    @FindBy(css = "div.result")
    public WebElement successMessage;
}
