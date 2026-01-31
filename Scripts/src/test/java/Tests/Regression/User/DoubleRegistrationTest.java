package Tests.Regression.User;

import Base.TestBase;
import Components.HeaderComponents;
import Data.TestData;
import Pages.RegisterPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class DoubleRegistrationTest extends TestBase {

    HeaderComponents header;
    RegisterPage register;

    String gender = "female";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    @Test(priority = 1)
    public void registerUser() {

        header = new HeaderComponents(driver);
        header.openRegister();
        waitFor().until(ExpectedConditions.urlContains("/register"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/register"));

        register = new RegisterPage(driver);
        register.registerUser(gender, firstName, lastName, email, password);

        waitFor().until(ExpectedConditions.urlContains("registerresult/1"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("registerresult/1"));

        waitFor().until(ExpectedConditions.visibilityOf(register.registerResult));
        Assert.assertEquals(register.registerResult.getText().trim(), "Your registration completed");
        waitFor().until(ExpectedConditions.visibilityOf(header.account));
        Assert.assertEquals(header.account.getText(), email);

        isLoggedIn = true;
    }

    @Test(dependsOnMethods = {"registerUser"})
    public void logoutUser() {

        header = new HeaderComponents(driver);
        header.logoutUser();

        waitFor().until(ExpectedConditions.urlMatches("https://demowebshop.tricentis.com/"));
        Assert.assertEquals(Objects.requireNonNull(driver.getCurrentUrl()), "https://demowebshop.tricentis.com/");
        isLoggedIn = false;
    }

    @Test(dependsOnMethods = {"logoutUser"})
    public void reRegisterUser() {

        header = new HeaderComponents(driver);
        header.openRegister();
        waitFor().until(ExpectedConditions.urlContains("/register"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/register"));

        register = new RegisterPage(driver);
        register.registerUser(gender, firstName, lastName, email, password);

        waitFor().until(ExpectedConditions.visibilityOf(register.validationErrors));
        Assert.assertEquals(register.validationErrors.getText().trim(), "The specified email already exists");
    }
}
