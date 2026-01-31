package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public class ProductPage extends PagesBase {
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.product-name")
    WebElement productName;
    public String getProductTitle() {
        return productName.getText().trim();
    }

    @FindBy(linkText = "Add your review")
    WebElement addReview;
    public void openAddReview() {
        waitFor().until(ExpectedConditions.elementToBeClickable(addReview));
        clickElementJS(addReview);
    }

    @FindBy(css = "input.qty-input")
    WebElement quantityBox;

    public int getProductQuantity() {
        String qtyText =  quantityBox.getAttribute("value");
        assert qtyText != null;
        return Integer.parseInt(qtyText);
    }
    public void updateQuantity(int newQuantity) {
        quantityBox.clear();
        setElementText(quantityBox, String.valueOf(newQuantity));
    }

    @FindBy(css = "input.button-1.add-to-cart-button")
    WebElement addToCartButton;
    public void addToCart() {
        waitFor().until(ExpectedConditions.elementToBeClickable(addToCartButton));
        clickElementJS(addToCartButton);
    }

    @FindBy(css = "div.product-price")
    WebElement productPrice;
    public String getProductPrice() {
        return productPrice.findElement(By.tagName("span")).getText().trim();
    }

    @FindBy(id = "product_attribute_5_7_1")
    List<WebElement> productSizeSelect;

    public boolean hasSize() {
        return !productSizeSelect.isEmpty() && productSizeSelect.get(0).isDisplayed();
    }
    // random select
    private static String getRandomChoice(WebElement selectElement) {
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        options.removeIf(option ->
                option.getText().trim().isEmpty()
                        || option.getText().toLowerCase().contains("select")
        );
        if (options.isEmpty()) {
            throw new RuntimeException("No selectable size options available");
        }
        Random random = new Random();
        WebElement randomOption = options.get(random.nextInt(options.size()));

        select.selectByVisibleText(randomOption.getText());
        return randomOption.getText();
    }

    public String selectRandomSize() {
        if (!hasSize()) {
            return null;
        }
        return getRandomChoice(productSizeSelect.getFirst());
    }

    @FindBy(id = "add-to-wishlist-button-5")
    WebElement addToWishlistButton;
    public void addToWishlist() {
        waitFor().until(ExpectedConditions.elementToBeClickable(addToWishlistButton));
        clickElementJS(addToWishlistButton);
    }

    @FindBy(css = "input.button-2.email-a-friend-button")
    WebElement emailFriendButton;
    public void openEmailFriend() {
        waitFor().until(ExpectedConditions.elementToBeClickable(emailFriendButton));
        clickElementJS(emailFriendButton);
    }

}
