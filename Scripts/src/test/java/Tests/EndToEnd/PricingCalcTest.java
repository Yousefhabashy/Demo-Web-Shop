package Tests.EndToEnd;

import Base.TestBase;
import Components.HeaderComponents;
import Data.TestData;
import Pages.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class PricingCalcTest extends TestBase {
    HeaderComponents header;
    RegisterPage register;
    AccountPage account;
    LoginPage login;
    HomePage home;
    ProductPage product;
    CartPage cart;
    CheckoutPage checkout;

    String productTitle;
    String productPrice;
    int productQuantity;

    String gender = "male";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();
    String newPassword = TestData.generatePassword();

    String fullName = firstName + " " + lastName;
    String company = TestData.generateCompany();
    String country;
    String state;
    String city = TestData.generateCity();
    String address1 = TestData.generateAddress();
    String address2 = TestData.generateAddress();
    String postalCode = TestData.generatePostalCode();
    String phone = TestData.generateTelephoneNumber();
    String fax = TestData.generateFax();

    String paymentMethod = "Cash On Delivery (COD)";
    String paymentCardType = "Visa";
    String shippingMethod = "Next Day Air";

    String cardNumber = TestData.generateMaserCard();
    String expiryMonth = TestData.generateExpiryMonth();
    String expiryYear = TestData.generateExpiryYear();
    String CVV = TestData.generateCVV();

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
        header.openAccessories();
        waitFor().until(ExpectedConditions.urlContains("/accessories"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/accessories"));

        home = new HomePage(driver);
        WebElement Product = home.getRandomProduct();
        productTitle = home.getProductTitle(Product);
        home.openProductPage(Product);

        product = new ProductPage(driver);
        String productName = product.getProductTitle();
        Assert.assertEquals(productName, productTitle);
        productPrice = product.getProductPrice();
        productQuantity = product.getProductQuantity();
        product.addToCart();

        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));
        Assert.assertEquals(header.successMessage.getText().trim(), "The product has been added to your shopping cart");
    }

    @Test(dependsOnMethods = {"addProductToCart"})
    public void checkCartProduct() {

        header = new HeaderComponents(driver);
        header.openCart();
        waitFor().until(ExpectedConditions.urlContains("/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/cart"));

        cart = new CartPage(driver);
        CartPage.CartProduct cartProduct = cart.getProduct(1);

        String cartProductTitle = cartProduct.getProductTitle();
        String cartProductPrice = cartProduct.getProductUnitPrice();
        int cartProductQuantity = cartProduct.getProductQuantity();


        Assert.assertEquals(cartProductTitle, productTitle);
        Assert.assertEquals(cartProductPrice, productPrice);
        Assert.assertEquals(cartProductQuantity, productQuantity);

        cart.openCheckout();
        waitFor().until(ExpectedConditions.urlContains("/onepagecheckout"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/onepagecheckout"));
    }

    @Test(dependsOnMethods = {"checkCartProduct"})
    public void checkoutProduct() {

        checkout = new CheckoutPage(driver);
        // add billing address
        String[] countryState =checkout.addNewAddress(firstName, lastName, email, company, city,
                address1, address2, postalCode, phone, fax);
        country = countryState[0];
        state = countryState[1];

        // shipping address
        waitFor().until(ExpectedConditions.visibilityOf(checkout.pickUpInStore));
        waitFor().until(ExpectedConditions.elementToBeClickable(checkout.pickUpInStore));
        checkout.clickShippingContinue();

        // select shipping method
        waitFor().until(ExpectedConditions.visibilityOf(checkout.ground));
        checkout.selectNextDay();

        // fill payment details
        waitFor().until(ExpectedConditions.visibilityOf(checkout.COD));
        checkout.selectCreditCard();

        checkout.fillPaymentInfo(paymentCardType, fullName,
                cardNumber, expiryMonth, expiryYear, CVV);
    }

    @Test(dependsOnMethods = {"checkoutProduct"})
    public void confirmOrder() {

        checkout = new CheckoutPage(driver);

        Assert.assertTrue(checkout.isPriceCalculatedCorrectly());
        checkout.confirmOrder();

        waitFor().until(ExpectedConditions.urlContains("/checkout/completed/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/checkout/completed/"));

        waitFor().until(ExpectedConditions.visibilityOf(checkout.successMessage));
        Assert.assertEquals(checkout.successMessage.getText().trim(), "Your order has been successfully processed!");
    }
}
