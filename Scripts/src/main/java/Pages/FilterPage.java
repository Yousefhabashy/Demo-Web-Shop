package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class FilterPage extends PagesBase {
    public FilterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "ul.price-range-selector")
    public WebElement filterContainer;

    private void selectPriceRangeByIndex(int index) {
        List<WebElement> filters = filterContainer.findElements(By.tagName("li"));
        WebElement filterLink = filters.get(index).findElement(By.tagName("a"));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterLink));
        clickElementJS(filterLink);
    }

    public void select0_500() {
        selectPriceRangeByIndex(0);
    }
    public void select500_700() {
        selectPriceRangeByIndex(1);
    }
    public void select700_3000() {
        selectPriceRangeByIndex(2);
    }

    @FindBy(css = "a.remove-price-range-filter")
    WebElement removeFilter;
    public void removePriceFilter() {
        waitFor().until(ExpectedConditions.elementToBeClickable(removeFilter));
        clickElementJS(removeFilter);
    }

    private List<WebElement> getAllProducts() {
        return driver.findElements(By.cssSelector("div.product-item"));
    }

    private List<Double> getAllPrices() {
        List<WebElement> prices = driver.findElements(By.cssSelector("span.price.actual-price"));
        List<Double> doublePrices =  new ArrayList<>();
        for (WebElement price : prices) {
            doublePrices.add(parsePrice(price.getText()));
        }
        return doublePrices;
    }

    private double parsePrice(String priceText) {
        return Double.parseDouble(
                priceText.replaceAll("[^0-9.]", "")
        );
    }

    public boolean isFilteredBetween(double min, double max) {
        for (Double price : getAllPrices()) {
            if (price < min || price > max) {
                return false;
            }
        }
        return true;
    }
}
