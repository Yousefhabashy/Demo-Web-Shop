package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortPage extends PagesBase {
    public SortPage(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> getAllProducts() {
        return driver.findElements(By.cssSelector("div.product-item"));
    }

    @FindBy(id = "products-orderby")
    public WebElement sortDropDown;
    public String getSelectedOption() {
        Select select = new Select(sortDropDown);
        return select.getFirstSelectedOption().getText();
    }

    // Sort A To Z
    public void selectAToZ() {
        Select select = new Select(sortDropDown);
        select.selectByVisibleText("Name: A to Z");
    }
    public boolean isSortedAToZ() {
        List<WebElement> products  = getAllProducts();
        List<String> productsNames = new ArrayList<>();
        for (WebElement product : products) {
            productsNames.add(product.getText().toLowerCase());
        }

        List<String> sortedNames = new ArrayList<>(productsNames);
        Collections.sort(sortedNames);

        return productsNames.equals(sortedNames);
    }

    // Sort Z To A
    public void selectZToA() {
        Select select = new Select(sortDropDown);
        select.selectByVisibleText("Name: Z to A");
    }

    public boolean isSortedZToA() {
        List<WebElement> products  = getAllProducts();
        List<String> productsNames = new ArrayList<>();
        for (WebElement product : products) {
            productsNames.add(product.getText().toLowerCase());
        }

        List<String> sortedNames = new ArrayList<>(productsNames);
        sortedNames.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));

        return productsNames.equals(sortedNames);
    }

    private List<Double> getAllProductPrices() {
        List<WebElement> prices = driver.findElements(By.cssSelector("span.price.actual-price"));
        List<Double> doublePrices =  new ArrayList<>();
        for (WebElement price : prices) {
            doublePrices.add(Double.parseDouble(price.getText().replaceAll("[^0-9]", "").trim()));
        }
        return doublePrices;
    }

    // Sort High To Low
    public void selectPriceHighToLow() {
        Select select = new Select(sortDropDown);
        select.selectByVisibleText("Price: High to Low");
    }
    public boolean isSortedHighToLow() {
        List<Double> prices = getAllProductPrices();

        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        return prices.equals(sortedPrices);
    }

    // Sort Low To High
    public void selectPriceLowToHigh() {
        Select select = new Select(sortDropDown);
        select.selectByVisibleText("Price: Low to High");
    }
    public boolean isSortedLowToHigh() {
        List<Double> prices = getAllProductPrices();

        List<Double> reversedPrices = new ArrayList<>(prices);
        reversedPrices.sort(Collections.reverseOrder());
        return prices.equals(reversedPrices);
    }

}
