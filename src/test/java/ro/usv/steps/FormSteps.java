package ro.usv.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ro.usv.pages.LoginPage;
import ro.usv.pages.SignUpPage;

import java.time.Duration;
import java.util.Random;

public class FormSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private SignUpPage signUpPage;
    private String currentPage;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @Given("I open the page {string}")
    public void i_open_the_page(String url) {
        driver.get(url);
        if (url.contains("/sign-in")) {
            loginPage = new LoginPage(driver);
            currentPage = "login";
        } else if (url.contains("/sign-up")) {
            signUpPage = new SignUpPage(driver);
            currentPage = "signup";
        }
    }

    @When("I fill the input {string} with {string}")
    public void i_fill_the_input_with(String input, String value) {
        if (value.equals("<random_email>")) {
            String randomString = generateRandomString(6); // 6 characters
            value = "user_" + randomString + "@student.usv.ro";
        }

        switch (currentPage) {
            case "login":
                switch (input) {
                    case "email" -> loginPage.enterEmail(value);
                    case "password" -> loginPage.enterPassword(value);
                    default -> throw new IllegalArgumentException("Unknown login input: " + input);
                }
                break;
            case "signup":
                switch (input) {
                    case "name" -> signUpPage.enterName(value);
                    case "email" -> signUpPage.enterEmail(value);
                    case "password" -> signUpPage.enterPassword(value);
                    case "confirmPassword" -> signUpPage.enterConfirmPassword(value);
                    default -> throw new IllegalArgumentException("Unknown signup input: " + input);
                }
                break;
        }
    }

    @When("I click on the {string} button")
    public void i_click_on_the_button(String buttonText) {
        switch (currentPage) {
            case "login":
                if (buttonText.equalsIgnoreCase("Autentificare")) loginPage.clickLogin();
                break;
            case "signup":
                if (buttonText.equalsIgnoreCase("Înregistrare")) signUpPage.clickSignUp();
                break;
        }
    }

    @Then("I should be redirected to {string}")
    public void i_should_be_redirected_to(String expectedPath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean redirected = wait.until(d -> d.getCurrentUrl().contains(expectedPath));
        Assert.assertTrue("User was not redirected correctly", redirected);
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String message) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isDisplayed = false;

        switch (currentPage) {
            case "login" -> {
                // Wait until the element is visible, then set isDisplayed
                isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'" + message + "')]")
                )) != null;
            }
            case "signup" -> {
                // Wait until your signup page reports the message is displayed
                isDisplayed = wait.until(d -> signUpPage.isErrorMessageDisplayed(message));
            }
        }

        Assert.assertTrue("Expected error message not displayed: " + message, isDisplayed);
    }

    @Then("the login form should not be submitted")
    public void the_login_form_should_not_be_submitted() {
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/sign-in")
        );
    }

    @Then("the register form should not be submitted")
    public void the_register_form_should_not_be_submitted() {
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/sign-up")
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    // Helper method to generate a short random alphanumeric string
    private String generateRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}