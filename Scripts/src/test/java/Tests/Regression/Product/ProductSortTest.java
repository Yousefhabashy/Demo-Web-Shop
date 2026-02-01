package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponents;
import Pages.SortPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class ProductSortTest extends TestBase {

    HeaderComponents header;
    SortPage sort;

    @Test(priority = 1)
    public void openJewelry(){

        header = new HeaderComponents(driver);
        header.openJewelry();
        waitFor().until(ExpectedConditions.urlContains("/jewelry"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/jewelry"));
    }

    @Test(priority = 2)
    public void sortByNameAToZ() {

        sort = new SortPage(driver);
        waitFor().until(ExpectedConditions.visibilityOf(sort.sortDropDown));
        waitFor().until(ExpectedConditions.elementToBeClickable(sort.sortDropDown));
        sort.selectAToZ();
        boolean isSorted = sort.isSortedAToZ();
        Assert.assertTrue(isSorted, "Products not sorted alphabetically from A to Z");
    }

    @Test(priority =3)
    public void sortByNameZToA() {

        sort = new SortPage(driver);
        waitFor().until(ExpectedConditions.visibilityOf(sort.sortDropDown));
        waitFor().until(ExpectedConditions.elementToBeClickable(sort.sortDropDown));
        sort.selectZToA();
        boolean isSorted = sort.isSortedZToA();
        Assert.assertTrue(isSorted, "Products not sorted alphabetically from Z to A");
    }

    @Test(priority = 4)
    public void sortByPriceLowToHigh() {

        sort = new SortPage(driver);
        waitFor().until(ExpectedConditions.visibilityOf(sort.sortDropDown));
        waitFor().until(ExpectedConditions.elementToBeClickable(sort.sortDropDown));
        sort.selectPriceLowToHigh();
        boolean isSorted = sort.isSortedLowToHigh();
        Assert.assertTrue(isSorted, "Products not sorted from price low to high");
    }

    @Test(priority = 5)
    public void sortByPriceHighToLow() {

        sort = new SortPage(driver);
        waitFor().until(ExpectedConditions.visibilityOf(sort.sortDropDown));
        waitFor().until(ExpectedConditions.elementToBeClickable(sort.sortDropDown));
        sort.selectPriceHighToLow();
        boolean isSorted = sort.isSortedHighToLow();
        Assert.assertTrue(isSorted, "Products not sorted from price high to low");
    }
}
