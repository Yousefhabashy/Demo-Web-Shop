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

    @FindBy(id = "addtocart_5_EnteredQuantity")
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

    @FindBy(id = "add-to-cart-button-5")
    WebElement addToCartButton;
    public void addToCart() {
        waitFor().until(ExpectedConditions.elementToBeClickable(addToCartButton));
        clickElementJS(addToCartButton);
    }


    @FindBy(id = "product_attribute_5_7_1")
    WebElement productSizeSelect;

    // random select
    private static String getRandomChoice(WebElement selectElement) {
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        options.removeIf( option ->
                option.getText().trim().isEmpty()
                        || option.getText().toLowerCase().contains("select country")
        );
        Random random = new Random();
        WebElement randomOption = options.get(random.nextInt(options.size()));

        select.selectByVisibleText(randomOption.getText());
        return randomOption.getText();
    }
    public String selectRandomSize() {
        return getRandomChoice(productSizeSelect);
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
