package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

public class HomePage extends PagesBase {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> getAllProducts() {
        return driver.findElements(By.cssSelector("div.product-item"));
    }
    public WebElement getRandomProduct() {
        List<WebElement> products = getAllProducts();
        if(products.isEmpty()) {
            throw new RuntimeException("No products found!");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());
        return products.get(randomIndex);
    }

    public void openProductPage(WebElement productItem) {
        WebElement productTitle = productItem.findElement(By.cssSelector("h2.product-title"));
        WebElement productLink = productTitle.findElement(By.tagName("a"));
        waitFor().until(ExpectedConditions.elementToBeClickable(productLink));
        clickElementJS(productLink);
    }
    public String getProductTitle(WebElement productItem) {
        WebElement productTitle = productItem.findElement(By.cssSelector("h2.product-title"));
        return productTitle.findElement(By.tagName("a")).getText().trim();
    }

    public String getPrice(WebElement productItem) {
        return productItem.findElement(By.cssSelector("span.price.actual-price")).getText().trim();
    }

    public boolean isFound(String productTitle) {

        List<WebElement> products = getAllProducts();
        if(products.isEmpty()) {
            return false;
        }
        for (WebElement product : products) {
            String title = getProductTitle(product);
            if (title.contains(productTitle)) {
                return true;
            }
        }
        return false;
    }
}
