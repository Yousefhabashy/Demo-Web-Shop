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

public class GuestCheckoutTest extends TestBase {

    HeaderComponents header;
    RegisterPage register;
    HomePage home;
    ProductPage product;
    CartPage cart;
    CheckoutPage checkout;
    OrderPage order;

    String productTitle;
    String productPrice;
    int productQuantity;
    String orderId;

    String gender = "male";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    String company = TestData.generateCompany();
    String country;
    String state;
    String city = TestData.generateCity();
    String address1 = TestData.generateAddress();
    String address2 = TestData.generateAddress();
    String postalCode = TestData.generatePostalCode();
    String phone = TestData.generateTelephoneNumber();
    String fax = TestData.generateFax();

    String fullName = firstName + " " + lastName;
    String paymentMethod = "Cash On Delivery (COD)";
    String shippingMethod = "2nd Day Air";

    @Test(priority = 1)
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
        waitFor().until(ExpectedConditions.urlContains("/login/checkoutasguest?"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/login/checkoutasguest?"));

    }

    @Test(dependsOnMethods = {"checkCartProduct"})
    public void checkoutProduct() {

        checkout = new CheckoutPage(driver);
        checkout.checkoutAsGuest();
        waitFor().until(ExpectedConditions.urlContains("/onepagecheckout"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/onepagecheckout"));

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
        checkout.selectSecondDay();

        // fill payment details
        waitFor().until(ExpectedConditions.visibilityOf(checkout.COD));
        checkout.selectCOD();

        waitFor().until(ExpectedConditions.visibilityOf(checkout.paymentInfo));
        Assert.assertEquals(checkout.paymentInfo.getText().trim(), "You will pay by COD");
        checkout.clickPaymentInfoContinue();
    }

    @Test(dependsOnMethods = {"checkoutProduct"})
    public void confirmOrder() {

        checkout = new CheckoutPage(driver);
        checkout.confirmOrder();

        waitFor().until(ExpectedConditions.urlContains("/checkout/completed/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/checkout/completed/"));

        waitFor().until(ExpectedConditions.visibilityOf(checkout.successMessage));
        orderId = checkout.getOrderID();
        Assert.assertEquals(checkout.successMessage.getText().trim(), "Your order has been successfully processed!");
    }
}
