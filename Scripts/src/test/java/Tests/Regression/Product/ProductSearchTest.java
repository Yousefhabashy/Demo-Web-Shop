package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponents;
import Pages.HomePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class ProductSearchTest extends TestBase {

    HeaderComponents header;
    HomePage home;

    String searchProduct = "Diamond Heart";


    @Test(priority = 1)
    public void searchProduct() {

        header = new HeaderComponents(driver);
        header.searchProduct(searchProduct);

        waitFor().until(ExpectedConditions.urlContains("search?q="));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("search?q="));

        home = new HomePage(driver);
        boolean isFound = home.isFound(searchProduct);
        Assert.assertTrue(isFound);
    }
}
