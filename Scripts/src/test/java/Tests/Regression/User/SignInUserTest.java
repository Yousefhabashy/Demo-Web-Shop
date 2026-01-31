package Tests.Regression.User;

import Base.TestBase;
import Components.HeaderComponents;
import Pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class SignInUserTest extends TestBase {

    HeaderComponents header;
    LoginPage login;

    String email = "steve.west@gmail.com";
    String pass = "6hyp26o3pb";

    @Test(priority = 1)
    public void loginUser() {

        header = new HeaderComponents(driver);
        header.openLogin();

        waitFor().until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/login"));

        login = new LoginPage(driver);
        login.loginUser(email, pass, true);

        waitFor().until(ExpectedConditions.urlMatches("https://demowebshop.tricentis.com/"));
        Assert.assertEquals(Objects.requireNonNull(driver.getCurrentUrl()), "https://demowebshop.tricentis.com/");

        waitFor().until(ExpectedConditions.visibilityOf(header.account));
        Assert.assertEquals(header.account.getText(), email);

        isLoggedIn = true;
    }
}
