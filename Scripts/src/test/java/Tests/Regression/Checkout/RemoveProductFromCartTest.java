package Tests.Regression.Checkout;

import Base.TestBase;
import Components.HeaderComponents;
import Data.TestData;
import Pages.CartPage;
import Pages.HomePage;
import Pages.ProductPage;
import Pages.RegisterPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class RemoveProductFromCartTest extends TestBase {

    HeaderComponents header;
    RegisterPage register;
    HomePage home;
    ProductPage product;
    CartPage cart;

    String gender = "female";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    String productTitle;
    boolean hasSize;
    String productSize;
    String productPrice;
    int productQuantity;

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
    public void addProductToCart() {

        header = new HeaderComponents(driver);
        header.openApparelAndShoes();

        waitFor().until(ExpectedConditions.urlContains("/apparel-shoes"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/apparel-shoes"));

        home = new HomePage(driver);
        WebElement Product = home.getRandomProduct();
        productTitle = home.getProductTitle(Product);
        home.openProductPage(Product);

        product = new ProductPage(driver);
        String productName = product.getProductTitle();
        Assert.assertEquals(productName, productTitle);
        hasSize = product.hasSize();
        if (hasSize){
            productSize = product.selectRandomSize();
        }
        productPrice = product.getProductPrice();
        productQuantity = product.getProductQuantity();
        product.addToCart();

        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));
        Assert.assertEquals(header.successMessage.getText().trim(), "The product has been added to your shopping cart");
    }

    @Test(dependsOnMethods = {"addProductToCart"})
    public void removeProductFromCart() {

        header = new HeaderComponents(driver);
        header.openCart();
        waitFor().until(ExpectedConditions.urlContains("/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/cart"));

        cart = new CartPage(driver);
        CartPage.CartProduct cartProduct = cart.getProduct(1);

        String cartProductTitle = cartProduct.getProductTitle();
        if (hasSize) {
            String cartProductSize = cartProduct.getProductSize();
            Assert.assertEquals(cartProductSize, productSize);
        }
        String cartProductPrice = cartProduct.getProductUnitPrice();
        int cartProductQuantity = cartProduct.getProductQuantity();

        Assert.assertEquals(cartProductTitle, productTitle);
        Assert.assertEquals(cartProductPrice, productPrice);
        Assert.assertEquals(cartProductQuantity, productQuantity);

        cartProduct.removeProduct();
        waitFor().until(ExpectedConditions.visibilityOf(cart.emptyCartMessage));
        Assert.assertEquals(cart.emptyCartMessage.getText().trim(), "Your Shopping Cart is empty!");
    }
}
