package ro.usv;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

public class TestDefinitions {


    private int first;
    private int second;
    private int result;

    @Given("I have entered {int} into the calculator")
    public void i_have_entered(int number) {
        if (first == 0) first = number;
        else second = number;
    }

    @When("I press add")
    public void i_press_add() {
        result = first + second;
    }

    @Then("the result should be {int}")
    public void the_result_should_be(int expected) {
        assertEquals(expected, result);
    }
}
