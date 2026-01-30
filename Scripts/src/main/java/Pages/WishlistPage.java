package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class WishlistPage extends PagesBase {
    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "table.cart")
    public WebElement productsContainer;
    private List<WebElement> getProducts() {
        return productsContainer.findElements(By.cssSelector("td.cart-item-row"));
    }
    public WishlistPage.WishlistProduct getProduct(int productNumber) {
        int index = productNumber - 1;
        List<WebElement> products = getProducts();
        if(index >= 0 && index < products.size()) {
            return  new WishlistPage.WishlistProduct(products.get(index));
        }
        throw  new IndexOutOfBoundsException("Products index " + index + " not found!");
    }

    public static class WishlistProduct {

        private WebElement product;
        public WishlistProduct(WebElement OrderElement) {
            this.product = OrderElement;
        }

        public String getProductTitle() {
            WebElement titleContainer = product.findElement(By.cssSelector("td.product"));
            return titleContainer.findElement(By.tagName("a")).getText();
        }
        public String getProductUnitPrice() {
            return product.findElement(By.cssSelector("span.product-unit-price")).getText();
        }
        public String getProductTotalPrice() {
            return product.findElement(By.cssSelector("span.product-subtotal")).getText();
        }
        public int getProductQuantity() {
            String qtyText =  product.findElement(By.cssSelector("input.qty-input")).getAttribute("value");
            assert qtyText != null;
            return Integer.parseInt(qtyText);
        }
        public void updateQuantity(int newQuantity) {
            WebElement quantityInput =  product.findElement(By.cssSelector("input.qty-input"));
            quantityInput.clear();
            setElementText(quantityInput, String.valueOf(newQuantity));
            WebElement wishlistUpdate = driver.findElement(By.cssSelector("input.button-2.update-wishlist-button"));
            clickElementJS(wishlistUpdate);
        }
        public void removeProduct() {
            WebElement removeContainer = product.findElement(By.cssSelector("td.remove-from-cart"));
            WebElement remove = removeContainer.findElement(By.cssSelector("input[name='removefromcart']"));
            waitFor().until(ExpectedConditions.elementToBeClickable(remove));
            clickElementJS(remove);
            WebElement wishlistUpdate = driver.findElement(By.cssSelector("input.button-2.update-cart-button"));
            clickElementJS(wishlistUpdate);
        }
        public void addProductToCart() {
            WebElement cartContainer = product.findElement(By.cssSelector("td.add-to-cart"));
            WebElement add = cartContainer.findElement(By.cssSelector("input[name='addtocart']"));
            waitFor().until(ExpectedConditions.elementToBeClickable(add));
            clickElementJS(add);
            WebElement wishlistUpdate = driver.findElement(By.cssSelector("input.button-2.update-wishlist-button"));
            clickElementJS(wishlistUpdate);
        }
    }
}
