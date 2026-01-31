package Components;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.swing.*;

public class HeaderComponents extends PagesBase {
    public HeaderComponents(WebDriver driver) {
        super(driver);
        PagesBase.driver = driver;
    }

    @FindBy(css = "div.content")
    public WebElement successMessage;

    @FindBy(css = "a.account")
    public WebElement account;
    public void openAccount() {
        waitFor().until(ExpectedConditions.elementToBeClickable(account));
        clickElementJS(account);
    }
    @FindBy(css = "a.ico-logout")
    WebElement logout;
    public void logoutUser() {
        waitFor().until(ExpectedConditions.elementToBeClickable(logout));
        clickElementJS(logout);
    }
    @FindBy(linkText = "Register")
    WebElement register;
    public void openRegister() {
        waitFor().until(ExpectedConditions.elementToBeClickable(register));
        clickElementJS(register);
    }

    @FindBy(linkText = "Log in")
    WebElement login;
    public void openLogin() {
        waitFor().until(ExpectedConditions.elementToBeClickable(login));
        clickElementJS(login);
    }

    @FindBy(id = "topcartlink")
    WebElement cart;
    public void openCart() {
        WebElement cartLink = cart.findElement(By.tagName("a"));
        waitFor().until(ExpectedConditions.elementToBeClickable(cartLink));
        clickElementJS(cartLink);
    }

    @FindBy(css = "span.cart-qty")
    public WebElement cartQty;

    @FindBy(id = "topcartlink")
    WebElement wishlist;
    public void openWishlist() {
        WebElement wishlistLink = wishlist.findElement(By.tagName("a"));
        waitFor().until(ExpectedConditions.elementToBeClickable(wishlistLink));
        clickElementJS(wishlistLink);
    }

    @FindBy(css = "span.wishlist-qty")
    public WebElement wishlistQty;

    // Search
    @FindBy(id = "small-searchterms")
    WebElement searchBar;
    @FindBy(css = "input.button-1.search-box-button")
    WebElement searchButton;

    public void searchProduct(String productName) {
        setElementText(searchBar, productName);
        waitFor().until(ExpectedConditions.elementToBeClickable(searchButton));
        clickElementJS(searchButton);
    }

    // links

    @FindBy(css = "a[href='/books']")
    WebElement books;
    public void openBooks() {
        waitFor().until(ExpectedConditions.elementToBeClickable(books));
        clickElementJS(books);
    }

    @FindBy(css = "a[href='/computers']")
    WebElement computers;
    public void openComputers() {
        waitFor().until(ExpectedConditions.elementToBeClickable(computers));
        clickElementJS(computers);
    }

    @FindBy(css = "a[href='/desktops']")
    WebElement desktops;
    public void openDesktops() {
        Actions actions = new Actions(driver);
        actions.moveToElement(computers).moveToElement(desktops).click().perform();
    }
    @FindBy(css = "a[href='/notebooks']")
    WebElement notebooks;
    public void openNotebooks() {
        Actions actions = new Actions(driver);
        actions.moveToElement(computers).moveToElement(notebooks).click().perform();
    }
    @FindBy(css = "a[href='/accessories']")
    WebElement accessories;
    public void openAccessories() {
        Actions actions = new Actions(driver);
        actions.moveToElement(computers).moveToElement(accessories).click().perform();
    }

    @FindBy(css = "a[href='/electronics']")
    WebElement electronics;
    public void openElectronics() {
        waitFor().until(ExpectedConditions.elementToBeClickable(electronics));
        clickElementJS(electronics);
    }

    @FindBy(css = "a[href='/camera-photo']")
    WebElement cameraPhotos;
    public void openCameraPhotos() {
        Actions actions = new Actions(driver);
        actions.moveToElement(electronics).moveToElement(cameraPhotos).click().perform();
    }
    @FindBy(css = "a[href='/cell-phones']")
    WebElement cellPhones;
    public void openCellPhones() {
        Actions actions = new Actions(driver);
        actions.moveToElement(electronics).moveToElement(cellPhones).click().perform();
    }

    @FindBy(css = "a[href='/apparel-shoes']")
    WebElement apparelAndShoes;
    public void openApparelAndShoes() {
        waitFor().until(ExpectedConditions.elementToBeClickable(apparelAndShoes));
        clickElementJS(apparelAndShoes);
    }

    @FindBy(css = "a[href='/digital-downloads']")
    WebElement digitalDownloads;
    public void openDigitalDownloads() {
        waitFor().until(ExpectedConditions.elementToBeClickable(digitalDownloads));
        clickElementJS(digitalDownloads);
    }

    @FindBy(css = "a[href='/jewelry']")
    WebElement jewelry;
    public void openJewelry() {
        waitFor().until(ExpectedConditions.elementToBeClickable(jewelry));
        clickElementJS(jewelry);
    }

    @FindBy(css = "a[href='/gift-cards']")
    WebElement giftCards;
    public void openGiftCards() {
        waitFor().until(ExpectedConditions.elementToBeClickable(giftCards));
        clickElementJS(giftCards);
    }
}
