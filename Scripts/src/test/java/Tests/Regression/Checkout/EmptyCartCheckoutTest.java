package Tests.Regression.Checkout;

import Base.TestBase;
import Components.HeaderComponents;
import Data.TestData;
import Pages.CartPage;
import Pages.RegisterPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class EmptyCartCheckoutTest extends TestBase {

    HeaderComponents header;
    RegisterPage register;
    CartPage cart;


    String gender = "male";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

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
    public void checkoutWithEmptyCart() {

        header = new HeaderComponents(driver);
        header.openCart();
        waitFor().until(ExpectedConditions.urlContains("/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/cart"));

        cart = new CartPage(driver);
        waitFor().until(ExpectedConditions.visibilityOf(cart.emptyCartMessage));
        Assert.assertEquals(cart.emptyCartMessage.getText().trim(), "Your Shopping Cart is empty!");
    }
}
