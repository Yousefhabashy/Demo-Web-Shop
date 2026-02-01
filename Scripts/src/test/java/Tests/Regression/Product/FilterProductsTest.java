package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponents;
import Pages.FilterPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class FilterProductsTest extends TestBase {


    HeaderComponents header;
    FilterPage filter;

    @Test(priority = 1)
    public void openJewelry(){

        header = new HeaderComponents(driver);
        header.openJewelry();
        waitFor().until(ExpectedConditions.urlContains("/jewelry"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/jewelry"));
    }

    @Test(priority = 2)
    public void filter0To500() {

        filter = new FilterPage(driver);
        waitFor().until(ExpectedConditions.visibilityOf(filter.filterContainer));
        waitFor().until(ExpectedConditions.elementToBeClickable(filter.filterContainer));
        filter.select0_500();

        waitFor().until(ExpectedConditions.urlContains("/jewelry?price=0-500"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/jewelry?price=0-500"));

        boolean isFiltered = filter.isFilteredBetween(0, 500);
        Assert.assertTrue(isFiltered, "Products not filtered from 0 to 500");
    }

    @Test(priority = 3)
    public void filter500To700() {

        filter = new FilterPage(driver);

        filter.removePriceFilter();
        waitFor().until(ExpectedConditions.urlContains("/jewelry"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/jewelry"));

        waitFor().until(ExpectedConditions.visibilityOf(filter.filterContainer));
        waitFor().until(ExpectedConditions.elementToBeClickable(filter.filterContainer));
        filter.select500_700();

        waitFor().until(ExpectedConditions.urlContains("/jewelry?price=500-700"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/jewelry?price=500-700"));

        boolean isFiltered = filter.isFilteredBetween(500, 700);
        Assert.assertTrue(isFiltered, "Products not filtered from 500 to 700");
    }

    @Test(priority = 4)
    public void filterOver700() {

        filter = new FilterPage(driver);

        filter.removePriceFilter();
        waitFor().until(ExpectedConditions.urlContains("/jewelry"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/jewelry"));

        waitFor().until(ExpectedConditions.visibilityOf(filter.filterContainer));
        waitFor().until(ExpectedConditions.elementToBeClickable(filter.filterContainer));
        filter.select700_3000();

        waitFor().until(ExpectedConditions.urlContains("/jewelry?price=700-3000"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/jewelry?price=700-3000"));

        boolean isFiltered = filter.isFilteredBetween(700, 3000);
        Assert.assertTrue(isFiltered, "Products not filtered from 700 to 3000");

        filter.removePriceFilter();
        waitFor().until(ExpectedConditions.urlContains("/jewelry"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/jewelry"));
    }
}
