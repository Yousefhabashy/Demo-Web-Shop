package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends PagesBase {
    public CartPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(css = "table.cart")
    public WebElement productsContainer;
    private List<WebElement> getProducts() {
        return productsContainer.findElements(By.cssSelector("tr.cart-item-row"));
    }
    public CartPage.CartProduct getProduct(int productNumber) {
        int index = productNumber - 1;
        List<WebElement> products = getProducts();
        if(index >= 0 && index < products.size()) {
            return  new CartPage.CartProduct(products.get(index));
        }
        throw  new IndexOutOfBoundsException("Products index " + index + " not found!");
    }

    public static class CartProduct {

        private WebElement product;
        public CartProduct(WebElement OrderElement) {
            this.product = OrderElement;
        }

        public String getProductTitle() {
            return product.findElement(By.cssSelector("a.product-name")).getText();
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
            WebElement cartUpdate = driver.findElement(By.cssSelector("input.button-2.update-cart-button"));
            clickElementJS(cartUpdate);
        }
        public void removeProduct() {
            WebElement remove = product.findElement(By.cssSelector("input[name='removefromcart']"));
            waitFor().until(ExpectedConditions.elementToBeClickable(remove));
            clickElementJS(remove);
            WebElement cartUpdate = driver.findElement(By.cssSelector("input.button-2.update-cart-button"));
            clickElementJS(cartUpdate);
        }
        public String getProductSize() {
            return product.findElement(By.cssSelector("div.attributes")).getText().replace("Size: ", "").trim();
        }
    }
    @FindBy(css = "input.button-2.continue-shopping-button")
    WebElement continueShopping;
    public void continueShopping() {
        waitFor().until(ExpectedConditions.elementToBeClickable(continueShopping));
        clickElementJS(continueShopping);
    }

    @FindBy(id = "termsofservice")
    WebElement termsOfService;
    @FindBy(id = "checkout")
    WebElement checkout;
    public void openCheckout() {
        waitFor().until(ExpectedConditions.elementToBeClickable(termsOfService));
        clickElementJS(termsOfService);
        waitFor().until(ExpectedConditions.elementToBeClickable(checkout));
        clickElementJS(checkout);
    }
}
