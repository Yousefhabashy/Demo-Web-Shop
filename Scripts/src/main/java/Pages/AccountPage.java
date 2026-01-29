package Pages;

import Base.PagesBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public class AccountPage extends PagesBase {
    public AccountPage(WebDriver driver) {
        super(driver);
    }

    // add address
    @FindBy(css = "a[href='/customer/addresses']")
    WebElement addresses;
    public void openAddresses() {
        waitFor().until(ExpectedConditions.elementToBeClickable(addresses));
        clickElementJS(addresses);
    }

    // order
    @FindBy(css = "div.order-list")
    public WebElement ordersContainer;
    private List<WebElement> getOrders() {
        return ordersContainer.findElements(By.cssSelector("div.section.order-item"));
    }
    public UserOrder getOrder(int orderNumber) {
        int index = orderNumber - 1;
        List<WebElement> orders= getOrders();
        if(index >= 0 && index < orders.size()) {
            return  new UserOrder(orders.get(index));
        }
        throw  new IndexOutOfBoundsException("Order index " + index + " not found!");
    }

    public static class UserOrder {

        private WebElement order;
        public UserOrder(WebElement OrderElement) {
            this.order = OrderElement;
        }

        public String getOrderID() {
            return order.findElement(By.cssSelector("div.title")).getText().replaceAll("[^0-9]", "");
        }

        public void openOrderDetails() {
            WebElement orderDetails = order.findElement(By.cssSelector("input.button-2.order-details-button"));
            waitFor().until(ExpectedConditions.elementToBeClickable(orderDetails));
            clickElementJS(orderDetails);
        }
    }

    // Change password
    @FindBy(css = "a[href='/customer/changepassword']")
    WebElement changePassword;
    public void openChangePassword() {
        waitFor().until(ExpectedConditions.elementToBeClickable(changePassword));
        clickElementJS(changePassword);
    }
    @FindBy(id = "OldPassword")
    WebElement oldPasswordBox;
    @FindBy(id = "NewPassword")
    WebElement newPasswordBox;
    @FindBy(id = "ConfirmNewPassword")
    WebElement confirmNewPasswordBox;
    @FindBy(css = "input.button-1.change-password-button")
    WebElement changePasswordButton;
    public void changePassword(String oldPassword, String newPassword) {
        setElementText(oldPasswordBox, oldPassword);
        setElementText(newPasswordBox, newPassword);
        setElementText(confirmNewPasswordBox, newPassword);
        waitFor().until(ExpectedConditions.elementToBeClickable(changePasswordButton));
        clickElementJS(changePasswordButton);
    }
}
