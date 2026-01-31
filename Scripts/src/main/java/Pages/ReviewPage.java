package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ReviewPage extends PagesBase {
    public ReviewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "AddProductReview_Title")
    WebElement reviewTitle;
    @FindBy(id = "AddProductReview_ReviewText")
    WebElement reviewText;

    @FindBy(id = "addproductrating_1")
    WebElement badReviewRate;
    @FindBy(id = "addproductrating_2")
    WebElement acceptableReviewRate;
    @FindBy(id = "addproductrating_3")
    WebElement goodReviewRate;
    @FindBy(id = "addproductrating_4")
    WebElement veryGoodReviewRate;
    @FindBy(id = "addproductrating_5")
    WebElement excellentReviewRate;
    @FindBy(css = "input.button-1.write-product-review-button")
    WebElement submitReview;

    public void addReview(String title, String review, int rate) {

        setElementText(reviewTitle, title);
        setElementText(reviewText , review);
        if (rate <= 1) {
            waitFor().until(ExpectedConditions.elementToBeClickable(badReviewRate));
            clickElementJS(badReviewRate);
        } else if (rate == 2) {
            waitFor().until(ExpectedConditions.elementToBeClickable(acceptableReviewRate));
            clickElementJS(acceptableReviewRate);
        }else if (rate == 3) {
            waitFor().until(ExpectedConditions.elementToBeClickable(goodReviewRate));
            clickElementJS(goodReviewRate);
        }else if (rate == 4) {
            waitFor().until(ExpectedConditions.elementToBeClickable(veryGoodReviewRate));
            clickElementJS(veryGoodReviewRate);
        }else {
            waitFor().until(ExpectedConditions.elementToBeClickable(excellentReviewRate));
            clickElementJS(excellentReviewRate);
        }
        waitFor().until(ExpectedConditions.elementToBeClickable(submitReview));
        clickElementJS(submitReview);
    }

    @FindBy(css = "div.page-title")
    WebElement productTitle;
    public String getProductTitle() {
        return productTitle.findElement(By.tagName("a")).getText().trim();
    }

    @FindBy(css = "div.result")
    public WebElement successMessage;
}
