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
    WebDriver filterContainer;

    public void select0_500() {
        WebElement filterUnder25 = filterContainer.findElements(By.tagName("li")).getFirst();
        WebElement filterLink = filterUnder25.findElement(By.tagName("a"));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterLink));
        clickElementJS(filterLink);
    }
    public void select500_700() {
        WebElement filter25_50 = filterContainer.findElements(By.tagName("li")).get(2);
        WebElement filterLink = filter25_50.findElement(By.tagName("a"));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterLink));
        clickElementJS(filterLink);
    }
    public void select700_3000() {
        WebElement filterOver50 = filterContainer.findElements(By.tagName("li")).get(3);
        WebElement filterLink = filterOver50.findElement(By.tagName("a"));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterLink));
        clickElementJS(filterLink);
    }

    private List<WebElement> getAllProducts() {
        return driver.findElements(By.cssSelector("div.product-item"));
    }
    private List<Double> getAllPrices() {
        List<WebElement> prices = getAllProducts();
        List<Double> doublePrices = new ArrayList<>();
        for (WebElement price : prices) {
            String piceText = price.findElement(By.cssSelector("span.price.actual-price")).getText().trim();
            doublePrices.add(Double.parseDouble(piceText));
        }
        return doublePrices;
    }

    public Boolean isFiltered0_500(){
        List<Double> prices = getAllPrices();
        for (Double price : prices) {
            if (price > 500) return  false;
        }
        return true;
    }
    public Boolean isFiltered500_700(){
        List<Double> prices = getAllPrices();
        for (Double price : prices) {
            if (price<500 || price > 700) return  false;
        }
        return true;
    }
    public Boolean isFilteredOver700(){
        List<Double> prices = getAllPrices();
        for (Double price : prices) {
            if (price < 700 || price > 3000) return false;
        }
        return true;
    }
}
