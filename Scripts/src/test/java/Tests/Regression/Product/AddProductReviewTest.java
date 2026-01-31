package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponents;
import Data.TestData;
import Pages.HomePage;
import Pages.ProductPage;
import Pages.RegisterPage;
import Pages.ReviewPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class AddProductReviewTest extends TestBase{

    HeaderComponents header;
    RegisterPage register;
    HomePage home;
    ProductPage productPage;
    ReviewPage review;

    String gender = "female";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    String reviewTitle = TestData.generateReviewTitle();
    String productReview = TestData.generateReview();

    String productTitle;

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
        productPage.openAddReview();

        waitFor().until(ExpectedConditions.urlContains("/productreviews"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/productreviews"));
    }

    @Test(dependsOnMethods = {"openProductReview"})
    public void addProductReview() {

        review = new ReviewPage(driver);
        review.addReview(reviewTitle, productReview, 3);

        String title = review.getProductTitle();
        Assert.assertEquals(title, productTitle);

        waitFor().until(ExpectedConditions.visibilityOf(review.successMessage));
        Assert.assertEquals(review.successMessage.getText().trim(), "Product review is successfully added.");
    }
}
