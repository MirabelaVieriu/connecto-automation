package ro.usv.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ro.usv.pages.LoginPage;

import java.time.Duration;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @Given("I open the page {string}")
    public void i_open_the_page(String url) {
        driver.get(url);
    }

    @When("I fill the input {string} with {string}")
    public void i_fill_the_input_with(String input, String value) {
        switch (input) {
            case "email":
                loginPage.enterEmail(value);
                break;
            case "password":
                loginPage.enterPassword(value);
                break;
            default:
                throw new IllegalArgumentException("Unknown input: " + input);
        }
    }

    @When("I click on the {string} button")
    public void i_click_on_the_button(String buttonText) {
        if (buttonText.equalsIgnoreCase("Autentificare")) {
            loginPage.clickLogin();
        }
    }

    @Then("I should be redirected to {string}")
    public void i_should_be_redirected_to(String expectedPath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean redirected = wait.until(driver -> driver.getCurrentUrl().contains(expectedPath));

        Assert.assertTrue("User was not redirected correctly", redirected);
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String message) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'" + message + "')]")
        ));
    }

    @Then("the login form should not be submitted")
    public void the_login_form_should_not_be_submitted() {
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/sign-in")
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}