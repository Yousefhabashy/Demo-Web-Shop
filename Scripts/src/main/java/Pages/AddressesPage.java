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

public class AddressesPage extends PagesBase {
    public AddressesPage(WebDriver driver) {
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
    @FindBy(id = "Address_FirstName")
    WebElement firstNameBox;
    @FindBy(id = "Address_LastName")
    WebElement lastNameBox;
    @FindBy(id = "Address_Email")
    WebElement emailBox;
    @FindBy(id = "Address_Company")
    WebElement companyBox;
    @FindBy(id = "Address_CountryId")
    WebElement countrySelect;
    @FindBy(id = "Address_StateProvinceId")
    WebElement stateSelect;
    @FindBy(id = "Address_City")
    WebElement cityBox;
    @FindBy(id = "Address_Address1")
    WebElement address1Box;
    @FindBy(id = "Address_Address2")
    WebElement address2Box;
    @FindBy(id = "Address_ZipPostalCode")
    WebElement postalCodeBox;
    @FindBy(id = "Address_PhoneNumber")
    WebElement phoneNumberBox;
    @FindBy(id = "Address_FaxNumber")
    WebElement faxBox;
    @FindBy(css = "input.button-1.save-address-button")
    WebElement saveButton;
    public String[] addNewAddress(String firstname, String lastName, String email,
                                  String company, String city, String address1, String address2,
                                  String postalCode, String phoneNumber, String fax) {
        setElementText(firstNameBox, firstname);
        setElementText(lastNameBox, lastName);
        setElementText(emailBox, email);
        setElementText(companyBox, company);
        String countryName = getRandomChoice(countrySelect);
        setElementText(cityBox, city);
        setElementText(address1Box, address1);
        setElementText(address2Box, address2);
        String stateName = getRandomChoice(stateSelect);
        setElementText(postalCodeBox, postalCode);
        setElementText(phoneNumberBox, phoneNumber);
        setElementText(faxBox, fax);
        waitFor().until(ExpectedConditions.elementToBeClickable(saveButton));
        clickElementJS(saveButton);
        return new String[]{countryName, stateName};
    }

    public String[] editAddress(String firstname, String lastName, String email,
                                String company, String city, String address1, String address2,
                                String postalCode, String phoneNumber, String fax) {
        firstNameBox.clear();
        setElementText(firstNameBox, firstname);
        lastNameBox.clear();
        setElementText(lastNameBox, lastName);
        emailBox.clear();
        setElementText(emailBox, email);
        companyBox.clear();
        setElementText(companyBox, company);
        String countryName = getRandomChoice(countrySelect);
        String stateName = getRandomChoice(stateSelect);
        cityBox.clear();
        setElementText(cityBox, city);
        address1Box.clear();
        setElementText(address1Box, address1);
        address2Box.clear();
        setElementText(address2Box, address2);
        postalCodeBox.clear();
        setElementText(postalCodeBox, postalCode);
        phoneNumberBox.clear();
        setElementText(phoneNumberBox, phoneNumber);
        faxBox.clear();
        setElementText(faxBox, fax);
        waitFor().until(ExpectedConditions.elementToBeClickable(saveButton));
        clickElementJS(saveButton);
        return new String[]{countryName, stateName};
    }

    @FindBy(css = "div.address-list")
    WebElement addressesContainer;
    private List<WebElement> getAddresses() {
        return addressesContainer.findElements(By.cssSelector("div.section.address-item"));
    }
    public AddressesPage.Address getAddress(int addressNumber) {
        int index = addressNumber - 1;
        List<WebElement> addresses = getAddresses();
        if(index >= 0 && index < addresses.size()) {
            return  new AddressesPage.Address(addresses.get(index));
        }
        throw  new IndexOutOfBoundsException("Address index " + index + " not found!");
    }

    public static class Address {

        private WebElement address;
        public Address(WebElement OrderElement) {
            this.address = OrderElement;
        }

        public String getName() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            return addressContainer.findElement(By.cssSelector("li.name")).getText().trim();
        }
        public String getEmail() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            return addressContainer.findElement(By.cssSelector("li.email")).getText().replace("Email:", "").trim();
        }
        public String getPhoneNumber() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            return addressContainer.findElement(By.cssSelector("li.phone")).getText().replace("Phone number:", "").trim();
        }
        public String getFaxNumber() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            return addressContainer.findElement(By.cssSelector("li.fax")).getText().replace("Fax number:", "").trim();
        }
        public String getCompany() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            return addressContainer.findElement(By.cssSelector("li.company")).getText().trim();
        }
        public String getAddress1() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            return addressContainer.findElement(By.cssSelector("li.address1")).getText().trim();
        }
        public String getAddress2() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            return addressContainer.findElement(By.cssSelector("li.address2")).getText().trim();
        }
        public String getCity() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            WebElement cityZibCode = addressContainer.findElement(By.cssSelector("li.city-state-zip"));
            List<String> cityZib = List.of(cityZibCode.getText().split(","));
            return cityZib.getFirst().trim();
        }
        public String getPostalCode() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            WebElement cityZibCode = addressContainer.findElement(By.cssSelector("li.city-state-zip"));
            List<String> cityZib = List.of(cityZibCode.getText().split(","));
            return cityZib.get(1).trim();
        }
        public String getCountry() {
            WebElement addressContainer = address.findElement(By.cssSelector("ul.info"));
            return addressContainer.findElement(By.cssSelector("li.country")).getText().trim();
        }

        public void DeleteAddress() {

            WebElement buttonsContainer = address.findElement(By.cssSelector("div.buttons"));
            WebElement deleteAddress = buttonsContainer.findElement(By.cssSelector("input.button-2.delete-address-button"));
            waitFor().until(ExpectedConditions.elementToBeClickable(deleteAddress));
            clickElementJS(deleteAddress);

            waitFor().until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }

        public void editAddress() {

            WebElement buttonsContainer = address.findElement(By.cssSelector("div.buttons"));
            WebElement editAddress = buttonsContainer.findElement(By.cssSelector("input.button-2.edit-address-button"));
            waitFor().until(ExpectedConditions.elementToBeClickable(editAddress));
            clickElementJS(editAddress);
        }
    }
}
