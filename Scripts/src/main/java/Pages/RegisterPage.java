package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends PagesBase {
    public RegisterPage(WebDriver driver) {
        super(driver);

    }

    @FindBy(id = "gender-male")
    WebElement genderMale;
    @FindBy(id = "gender-female")
    WebElement genderFemale;
    @FindBy(id = "FirstName")
    WebElement firstNameBox;
    @FindBy(id = "LastName")
    WebElement lastNameBox;
    @FindBy(id = "Email")
    WebElement emailBox;
    @FindBy(id = "Password")
    WebElement passwordBox;
    @FindBy(id = "ConfirmPassword")
    WebElement confirmPasswordBox;
    @FindBy(id = "register-button")
    WebElement registerButton;

    public void registerUser(String gender, String firstName, String lastName,
                             String email, String password) {
        if(gender.equalsIgnoreCase("male")) {
            waitFor().until(ExpectedConditions.elementToBeClickable(genderMale));
            clickElementJS(genderMale);
        } else {
            waitFor().until(ExpectedConditions.elementToBeClickable(genderFemale));
            clickElementJS(genderFemale);
        }
        setElementText(firstNameBox, firstName);
        setElementText(lastNameBox, lastName);
        setElementText(emailBox, email);
        setElementText(passwordBox, password);
        setElementText(confirmPasswordBox, password);

        waitFor().until(ExpectedConditions.elementToBeClickable(registerButton));
        clickElementJS(registerButton);
    }
}
