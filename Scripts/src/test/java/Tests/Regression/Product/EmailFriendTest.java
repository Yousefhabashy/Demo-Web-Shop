package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponents;
import Data.TestData;
import Pages.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class EmailFriendTest extends TestBase {

    HeaderComponents header;
    RegisterPage register;
    HomePage home;
    ProductPage productPage;
    EmailFriendPage emailFriend;


    String gender = "female";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    String productTitle;

    String friendEmail = TestData.generateEmail();
    String message = TestData.generateReview();

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
    public void openProductReview() {

        header = new HeaderComponents(driver);
        header.openAccessories();
        waitFor().until(ExpectedConditions.urlContains("/accessories"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/accessories"));

        home = new HomePage(driver);
        WebElement product = home.getRandomProduct();
        productTitle = home.getProductTitle(product);
        home.openProductPage(product);

        productPage = new ProductPage(driver);
        String productName = productPage.getProductTitle();
        Assert.assertEquals(productName, productTitle);
        productPage.openEmailFriend();

        waitFor().until(ExpectedConditions.urlContains("/productemailafriend"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/productemailafriend"));
    }

    @Test(dependsOnMethods = {"openProductReview"})
    public void emailFriend() {

        emailFriend = new EmailFriendPage(driver);
        waitFor().until(ExpectedConditions.visibilityOf(emailFriend.productName));
        Assert.assertEquals(emailFriend.productName.getText().trim(), productTitle);

        emailFriend.sendEmail(friendEmail, email, message);
        waitFor().until(ExpectedConditions.visibilityOf(emailFriend.successMessage));
        Assert.assertEquals(emailFriend.successMessage.getText().trim(), "Your message has been sent.");
    }
}
