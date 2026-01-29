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

public class CheckoutPage extends PagesBase {
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

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

    @FindBy(css = "input.button-1.add-address-button")
    WebElement addNewAddress;
    public void openAddNewAddress() {
        waitFor().until(ExpectedConditions.elementToBeClickable(addNewAddress));
        clickElementJS(addNewAddress);
    }
    @FindBy(id = "BillingNewAddress_FirstName")
    WebElement firstNameBox;
    @FindBy(id = "BillingNewAddress_LastName")
    WebElement lastNameBox;
    @FindBy(id = "BillingNewAddress_Email")
    WebElement emailBox;
    @FindBy(id = "BillingNewAddress_Company")
    WebElement companyBox;
    @FindBy(id = "BillingNewAddress_CountryId")
    WebElement countrySelect;
    @FindBy(id = "BillingNewAddress_StateProvinceId")
    WebElement stateSelect;
    @FindBy(id = "BillingNewAddress_City")
    WebElement cityBox;
    @FindBy(id = "BillingNewAddress_Address1")
    WebElement address1Box;
    @FindBy(id = "BillingNewAddress_Address2")
    WebElement address2Box;
    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    WebElement postalCodeBox;
    @FindBy(id = "BillingNewAddress_PhoneNumber")
    WebElement phoneNumberBox;
    @FindBy(id = "BillingNewAddress_FaxNumber")
    WebElement faxBox;
    @FindBy(css = "input.button-1.new-address-next-step-button")
    WebElement continueButton;
    public String[] addNewAddress(String firstname, String lastName, String email,
                                  String company, String city, String address1, String address2,
                                  String postalCode, String phoneNumber, String fax) {
        firstNameBox.clear();
        setElementText(firstNameBox, firstname);
        lastNameBox.clear();
        setElementText(lastNameBox, lastName);
        emailBox.clear();
        setElementText(emailBox, email);
        setElementText(companyBox, company);
        String countryName = getRandomChoice(countrySelect);
        String stateName = getRandomChoice(stateSelect);
        setElementText(cityBox, city);
        setElementText(address1Box, address1);
        setElementText(address2Box, address2);
        setElementText(postalCodeBox, postalCode);
        setElementText(phoneNumberBox, phoneNumber);
        setElementText(faxBox, fax);
        waitFor().until(ExpectedConditions.elementToBeClickable(continueButton));
        clickElementJS(continueButton);
        return new String[]{countryName, stateName};
    }

    public void clickContinue() {
        waitFor().until(ExpectedConditions.elementToBeClickable(continueButton));
        clickElementJS(continueButton);
    }

    @FindBy(id = "shippingoption_0")
    WebElement ground;
    public void selectground() {
        waitFor().until(ExpectedConditions.elementToBeClickable(ground));
        clickElementJS(ground);
        clickContinue();
    }

    @FindBy(id = "shippingoption_1")
    WebElement nextDay;
    public void selectNextDay() {
        waitFor().until(ExpectedConditions.elementToBeClickable(nextDay));
        clickElementJS(nextDay);
        clickContinue();
    }

    @FindBy(id = "shippingoption_2")
    WebElement secondDay;
    public void selectSecondDay() {
        waitFor().until(ExpectedConditions.elementToBeClickable(secondDay));
        clickElementJS(secondDay);
        clickContinue();
    }

    @FindBy(id = "paymentmethod_0")
    WebElement COD;
    public void selectCOD() {
        waitFor().until(ExpectedConditions.elementToBeClickable(COD));
        clickElementJS(COD);
        clickContinue();
    }

    @FindBy(id = "paymentmethod_1")
    WebElement moneyOrder;
    public void selectMoneyOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(moneyOrder));
        clickElementJS(moneyOrder);
        clickContinue();
    }

    @FindBy(id = "paymentmethod_2")
    WebElement creditCard;
    public void selectCreditCard() {
        waitFor().until(ExpectedConditions.elementToBeClickable(creditCard));
        clickElementJS(creditCard);
        clickContinue();
    }

    @FindBy(id = "paymentmethod_3")
    WebElement purchaseOrder;
    public void selectPurchaseOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(purchaseOrder));
        clickElementJS(purchaseOrder);
        clickContinue();
    }

    @FindBy(id = "CreditCardType")
    WebElement creditCardTypeSelect;
    public void SelectCardType(String cardType) {
        Select select = new Select(creditCardTypeSelect);
        if (cardType.equalsIgnoreCase("Visa")) {
            select.selectByVisibleText("Visa");
        } else if (cardType.equalsIgnoreCase("Master card")) {
            select.selectByVisibleText("Master card");
        }
        else if (cardType.equalsIgnoreCase("Discover")) {
            select.selectByVisibleText("Discover");
        }
        else if (cardType.equalsIgnoreCase("Amex")) {
            select.selectByVisibleText("Amex");
        }
    }

    @FindBy(id = "CardholderName")
    WebElement cardHolderName;
    @FindBy(id = "CardNumber")
    WebElement cardNumberBox;
    @FindBy(id = "ExpireMonth")
    WebElement cardExpiryMonth;
    @FindBy(id = "ExpireYear")
    WebElement cardExpiryYear;
    @FindBy(id = "CardCode")
    WebElement cardCCV;

    public void fillPaymentInfo(String cardHolder, String cardNumber,
                                String expiryMonth, String expiryYear, String CVV) {
        setElementText(cardHolderName, cardHolder);
        setElementText(cardNumberBox, cardNumber);
        setElementText(cardExpiryMonth, expiryMonth);
        setElementText(cardExpiryYear, expiryYear);
        setElementText(cardCCV, CVV);
        clickContinue();
    }

    public static class ConfirmOrder {

        @FindBy(css = "ul.billing-info")
        WebElement addressContainer;

        public String getName() {
            return addressContainer.findElement(By.cssSelector("li.name")).getText().trim();
        }
        public String getEmail() {
            return addressContainer.findElement(By.cssSelector("li.email")).getText().replace("Email: ","").trim();
        }
        public String getPhoneNumber() {
            return addressContainer.findElement(By.cssSelector("li.phone")).getText().replace("Phone: ","").trim();
        }
        public String getFax() {
            return addressContainer.findElement(By.cssSelector("li.fax")).getText().replace("Fax: ","").trim();
        }
        public String getAddress1() {
            return addressContainer.findElement(By.cssSelector("li.address1")).getText().trim();
        }
        public String getAddress2() {
            return addressContainer.findElement(By.cssSelector("li.address2")).getText().trim();
        }
        public String getCity() {
            WebElement cityZibCode = addressContainer.findElement(By.cssSelector("li.city-state-zip"));
            List<String> cityZib = List.of(cityZibCode.getText().split(","));
            return cityZib.getFirst().trim();
        }
        public String getPostalCode() {
            WebElement cityZibCode = addressContainer.findElement(By.cssSelector("li.city-state-zip"));
            List<String> cityZib = List.of(cityZibCode.getText().split(","));
            return cityZib.get(1).trim();
        }
        public String getCountry() {
            return addressContainer.findElement(By.cssSelector("li.country")).getText().trim();
        }
        public String getPaymentMethod() {
            return addressContainer.findElement(By.cssSelector("li.payment-method")).getText().trim();
        }

        @FindBy(css = "ul.shipping-info")
        WebElement shippingContainer;
        public String getShippingMethod() {
            return shippingContainer.findElement(By.cssSelector("li.shipping-method")).getText().trim();
        }
    }


    @FindBy(css = "table.cart")
    public WebElement productsContainer;
    private List<WebElement> getProducts() {
        return productsContainer.findElements(By.cssSelector("tr.cart-item-row"));
    }
    public CheckoutPage.CheckoutProduct getProduct(int productNumber) {
        int index = productNumber - 1;
        List<WebElement> products = getProducts();
        if(index >= 0 && index < products.size()) {
            return  new CheckoutPage.CheckoutProduct(products.get(index));
        }
        throw  new IndexOutOfBoundsException("Products index " + index + " not found!");
    }

    public static class CheckoutProduct {

        private WebElement product;
        public CheckoutProduct(WebElement OrderElement) {
            this.product = OrderElement;
        }

        public String getProductTitle() {
            WebElement productDetails = product.findElement(By.cssSelector("td.product"));
            return productDetails.findElement(By.cssSelector("a.product-name")).getText();
        }
        public String getProductUnitPrice() {
            return product.findElement(By.cssSelector("td.unit-price.nobr")).getText();
        }
        public String getProductTotalPrice() {
            WebElement totalPrice = product.findElement(By.cssSelector("td.subtotal.nobr.end"));
            return totalPrice.findElement(By.cssSelector("span.product-subtotal")).getText();
        }
        public int getProductQuantity() {
            String qtyText =  product.findElement(By.cssSelector("td.qty.nobr")).getText().replace("Qty.:", "").trim();
            return Integer.parseInt(qtyText);
        }


        public String getProductSize() {
            WebElement productDetails = product.findElement(By.cssSelector("td.product"));
            WebElement productSizeColor = productDetails.findElement(By.cssSelector("div.attributes"));
            List<String> sizeColor = List.of(productSizeColor.getText().split("\n"));
            return sizeColor.getFirst().replace("Size: ", "").trim();
        }
        public String getProductColor() {
            WebElement productDetails = product.findElement(By.cssSelector("td.product"));
            WebElement productSizeColor = productDetails.findElement(By.cssSelector("div.attributes"));
            List<String> sizeColor = List.of(productSizeColor.getText().split("\n"));
            return sizeColor.get(1).replace("Color: ", "").trim();
        }
    }

    @FindBy(css = "table.cart-total")
    WebElement totalPrices;
    public double getSubTotal() {
        WebElement subTotal = totalPrices.findElements(By.cssSelector("span.product-price")).getFirst();
        String subTotalText = subTotal.getText();
        return Double.parseDouble(subTotalText);
    }
    public double getShipping() {
        WebElement subTotal = totalPrices.findElements(By.cssSelector("span.product-price")).get(1);
        String shippingText = subTotal.getText();
        return Double.parseDouble(shippingText);
    }
    public double getTax() {
        WebElement subTotal = totalPrices.findElements(By.cssSelector("span.product-price")).get(2);
        String taxText = subTotal.getText();
        return Double.parseDouble(taxText);
    }
    public double getTotal() {
        WebElement subTotal = totalPrices.findElements(By.cssSelector("span.product-price")).get(3);
        String totalText = subTotal.getText();
        return Double.parseDouble(totalText);
    }

    public boolean isPriceCalculatedCorrectly() {
        double subTotal = getSubTotal();
        double shipping = getShipping();
        double tax = getTax();
        double total = getTotal();
        return  subTotal + shipping + tax == total;
    }

    @FindBy(css = "div.title")
    public WebElement successMessage;

    @FindBy(css = "ul.details")
    WebElement orderDetailsContainer;
    public String getOrderID() {
        return orderDetailsContainer.findElements(By.tagName("li")).getFirst().getText().replace("Order number: ", "").trim();
    }

    @FindBy(linkText = "Click here for order details.")
    WebElement orderDetailsPage;
    public void openOrderDetailsPage() {
        waitFor().until(ExpectedConditions.elementToBeClickable(orderDetailsPage));
        clickElementJS(orderDetailsPage);
    }

    @FindBy(css = "a.button-2.print-order-button")
    WebElement printOrder;
    public void printOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(printOrder));
        clickElementJS(printOrder);
    }
    @FindBy(css = "a.button-2.pdf-order-button")
    WebElement pdfInvoiceOrder;
    public void printPDFInvoice() {
        waitFor().until(ExpectedConditions.elementToBeClickable(pdfInvoiceOrder));
        clickElementJS(pdfInvoiceOrder);
    }
    @FindBy(css = "input.button-1.re-order-button")
    WebElement reOrder;
    public void reOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(reOrder));
        clickElementJS(reOrder);
    }
}
