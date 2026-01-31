package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponents;
import Pages.CartPage;
import Pages.HomePage;
import Pages.ProductPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class AddProductToCartTest extends TestBase {

    HeaderComponents header;
    HomePage home;
    ProductPage product;
    CartPage cart;

    String productTitle;
    boolean hasSize;
    String productSize;
    String productPrice;
    int productQuantity;

    @Test(priority = 1)
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
        if (hasSize) {
            String cartProductSize = cartProduct.getProductSize();
            Assert.assertEquals(cartProductSize , productSize);
        }

        Assert.assertEquals(cartProductTitle, productTitle);
        Assert.assertEquals(cartProductPrice, productPrice);
        Assert.assertEquals(cartProductQuantity, productQuantity);
    }
}
