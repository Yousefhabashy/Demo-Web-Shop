package Pages;

import Base.PagesBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public class AddressesPAge extends PagesBase {
    public AddressesPAge(WebDriver driver) {
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
        String stateName = getRandomChoice(stateSelect);
        setElementText(cityBox, city);
        setElementText(address1Box, address1);
        setElementText(address2Box, address2);
        setElementText(postalCodeBox, postalCode);
        setElementText(phoneNumberBox, phoneNumber);
        setElementText(faxBox, fax);
        waitFor().until(ExpectedConditions.elementToBeClickable(saveButton));
        clickElementJS(saveButton);
        return new String[]{countryName, stateName};
    }

    @FindBy(css = "input.button-2.edit-address-button")
    WebElement editAddress;
    public void openEditAddress() {
        waitFor().until(ExpectedConditions.elementToBeClickable(editAddress));
        clickElementJS(editAddress);
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

    @FindBy(css = "input.button-2.delete-address-button")
    WebElement deleteAddress;
    public void DeleteAddress() {
        waitFor().until(ExpectedConditions.elementToBeClickable(deleteAddress));
        clickElementJS(deleteAddress);

        waitFor().until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
