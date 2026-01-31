package Tests.Regression.User;

import Base.TestBase;
import Components.HeaderComponents;
import Data.TestData;
import Pages.AccountPage;
import Pages.AddressesPage;
import Pages.RegisterPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class AddNewAddressTest extends TestBase {

    HeaderComponents header;
    RegisterPage register;
    AccountPage account;
    AddressesPage addresses;

    String gender = "female";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    String fullName = firstName + " " + lastName;
    String company = TestData.generateCompany();
    String country;
    String state;
    String city = TestData.generateCity();
    String address1 = TestData.generateAddress();
    String address2 = TestData.generateAddress();
    String postalCode = TestData.generatePostalCode();
    String phone = TestData.generateTelephoneNumber();
    String fax = TestData.generateFax();

    @Test(priority = 1)
    public void  registerUser() {

        header = new HeaderComponents(driver);
        header.openRegister();
        waitFor().until(ExpectedConditions.urlContains("/register"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/register"));

        register = new RegisterPage(driver);
        register.registerUser(gender, firstName, lastName, email, password);

        waitFor().until(ExpectedConditions.urlContains("registerresult/1"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("registerresult/1"));

        waitFor().until(ExpectedConditions.visibilityOf(register.registerResult));
        Assert.assertEquals(register.registerResult.getText().trim(), "Your registration completed");
        waitFor().until(ExpectedConditions.visibilityOf(header.account));
        Assert.assertEquals(header.account.getText(), email);

        isLoggedIn = true;
    }

    @Test(dependsOnMethods = {"registerUser"})
    public void addNewAddress() {

        header = new HeaderComponents(driver);
        header.openAccount();
        waitFor().until(ExpectedConditions.urlContains("/customer/info"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/customer/info"));

        account = new AccountPage(driver);
        account.openAddresses();
        waitFor().until(ExpectedConditions.urlContains("/customer/addresses"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/customer/addresses"));

        addresses = new AddressesPage(driver);
        addresses.openAddNewAddress();
        waitFor().until(ExpectedConditions.urlContains("/customer/addressadd"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/customer/addressadd"));

        String[] countryState = addresses.addNewAddress(firstName, lastName, email, company,
                city, address1, address2, postalCode, phone, fax);
        country = countryState[0];
        state = countryState[1];
    }

    @Test(dependsOnMethods = {"addNewAddress"})
    public void checkAddedAddress() {

        addresses = new AddressesPage(driver);
        AddressesPage.Address address = addresses.getAddress(1);

        String addressName = address.getName();
        String addressEmail = address.getEmail();
        String addressPhoneNumber = address.getPhoneNumber();
        String addressCompany = address.getCompany();
        String addressCity = address.getCity();
        String addressAddress1 = address.getAddress1();
        String addressAddress2 = address.getAddress2();
        String addressPostalCode = address.getPostalCode();
        String addressFax = address.getFaxNumber();
        String addressCountry = address.getCountry();

        Assert.assertEquals(addressName, fullName);
        Assert.assertEquals(addressEmail, email);
        Assert.assertEquals(addressPhoneNumber, phone);
        Assert.assertEquals(addressCompany, company);
        Assert.assertEquals(addressCity, city);
        Assert.assertEquals(addressAddress1, address1);
        Assert.assertEquals(addressAddress2, address2);
        Assert.assertEquals(addressPostalCode, postalCode);
        Assert.assertEquals(addressFax, fax);
        Assert.assertEquals(addressCountry, country);
    }
}
