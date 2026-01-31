package Tests.Regression.User;

import Base.TestBase;
import Components.HeaderComponents;
import Data.TestData;
import Pages.AccountPage;
import Pages.LoginPage;
import Pages.RegisterPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class ChangePasswordTest extends TestBase {

    HeaderComponents header;
    RegisterPage register;
    AccountPage account;
    LoginPage login;

    String gender = "male";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();
    String newPassword = TestData.generatePassword();
    @Test(priority = 1)
    public void  registerUser() {

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
    public void changePassword() {

        header = new HeaderComponents(driver);
        header.openAccount();
        waitFor().until(ExpectedConditions.urlContains("/customer/info"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/customer/info"));

        account = new AccountPage(driver);
        account.openChangePassword();
        waitFor().until(ExpectedConditions.urlContains("/customer/changepassword"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/customer/changepassword"));

        account.changePassword(password, newPassword);

        waitFor().until(ExpectedConditions.visibilityOf(account.successMessage));
        Assert.assertEquals(account.successMessage.getText().trim(), "Password was changed");
    }
    @Test(dependsOnMethods = {"changePassword"})
    public void logoutUser() {

        header = new HeaderComponents(driver);
        header.logoutUser();

        waitFor().until(ExpectedConditions.urlMatches("https://demowebshop.tricentis.com/"));
        Assert.assertEquals(Objects.requireNonNull(driver.getCurrentUrl()), "https://demowebshop.tricentis.com/");
        isLoggedIn = false;
    }
    @Test(dependsOnMethods = {"logoutUser"})
    public void loginUser() {

        header = new HeaderComponents(driver);
        header.openLogin();

        waitFor().until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/login"));

        login = new LoginPage(driver);
        login.loginUser(email, newPassword, true);

        waitFor().until(ExpectedConditions.urlMatches("https://demowebshop.tricentis.com/"));
        Assert.assertEquals(Objects.requireNonNull(driver.getCurrentUrl()), "https://demowebshop.tricentis.com/");

        waitFor().until(ExpectedConditions.visibilityOf(header.account));
        Assert.assertEquals(header.account.getText(), email);

        isLoggedIn = true;
    }
}
