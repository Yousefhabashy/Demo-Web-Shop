package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class OrderPage extends PagesBase {
    public OrderPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.order-number")
    WebElement orderID;
    public String getOrderID() {
        return orderID.getText().replace("Order #", "").trim();
    }

    @FindBy(css = "ul.billing-info")
    WebElement billingInfo;

    public String getName() {
        return billingInfo.findElement(By.cssSelector("li.name")).getText().trim();
    }
    public String getEmail() {
        return billingInfo.findElement(By.cssSelector("li.email")).getText().replace("Email: ","").trim();
    }
    public String getPhoneNumber() {
        return billingInfo.findElement(By.cssSelector("li.phone")).getText().replace("Phone: ","").trim();
    }
    public String getFax() {
        return billingInfo.findElement(By.cssSelector("li.fax")).getText().replace("Fax: ","").trim();
    }
    public String getAddress1() {
        return billingInfo.findElement(By.cssSelector("li.address1")).getText().trim();
    }
    public String getAddress2() {
        return billingInfo.findElement(By.cssSelector("li.address2")).getText().trim();
    }
    public String getCity() {
        WebElement cityZibCode = billingInfo.findElement(By.cssSelector("li.city-state-zip"));
        List<String> cityZib = List.of(cityZibCode.getText().split(","));
        return cityZib.getFirst().trim();
    }
    public String getPostalCode() {
        WebElement cityZibCode = billingInfo.findElement(By.cssSelector("li.city-state-zip"));
        List<String> cityZib = List.of(cityZibCode.getText().split(","));
        return cityZib.get(1).trim();
    }
    public String getCountry() {
        return billingInfo.findElement(By.cssSelector("li.country")).getText().trim();
    }
    public String getPaymentMethod() {
        return billingInfo.findElement(By.cssSelector("li.payment-method")).getText().trim();
    }

    @FindBy(css = "ul.shipping-info")
    WebElement shippingContainer;
    public String getShippingMethod() {
        return shippingContainer.findElement(By.cssSelector("li.shipping-method")).getText().trim();
    }

    @FindBy(css = "table.data-table")
    WebElement productsContainer;
    private List<WebElement> getProducts() {
        return productsContainer.findElements(By.tagName("tr"));
    }
    public OrderPage.OrderProduct getProduct(int productNumber) {
        int index = productNumber - 1;
        List<WebElement> products = getProducts();
        if(index >= 0 && index < products.size()) {
            return  new OrderPage.OrderProduct(products.get(index));
        }
        throw  new IndexOutOfBoundsException("Products index " + index + " not found!");
    }

    public static class OrderProduct {

        private WebElement product;
        public OrderProduct(WebElement OrderElement) {
            this.product = OrderElement;
        }

        public String getProductTitle() {
            return product.findElement(By.cssSelector("td.a-left.name")).getText();
        }
        public String getProductUnitPrice() {
            return product.findElement(By.cssSelector("td.a-right.price")).getText().replace("Price:", "").trim();
        }
        public String getProductTotalPrice() {
            return product.findElement(By.cssSelector("td.a-right.total")).getText().replace("Total:", "").trim();
        }
        public int getProductQuantity() {
            String qtyText =  product.findElement(By.cssSelector("td.a-center.quantity")).getText().replace("Quantity:", "").trim();
            return Integer.parseInt(qtyText);
        }
    }

    @FindBy(css = "div.order-total")
    WebElement totalPrice;
    public String getTotalPrice() {
        return totalPrice.findElement(By.tagName("strong")).getText();
    }
}
