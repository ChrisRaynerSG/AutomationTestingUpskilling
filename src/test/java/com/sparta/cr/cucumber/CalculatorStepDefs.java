package com.sparta.cr.cucumber;

import com.sparta.cr.Calculator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class CalculatorStepDefs {

    private Calculator calculator;
    private Integer actual;
    private Exception exception;

    @Given("I have a calculator")
    public void iHaveACalculator() {
        calculator = new Calculator();
    }

    @And("I enter {int} and {int} into the calculator")
    public void iEnterAndIntoTheCalculator(int num1, int num2) {
        calculator.setNum1(num1);
        calculator.setNum2(num2);
    }
    @And("the exception should have the message {string}")
    public void theExceptionShouldHaveTheMessage(String message) {
        Assertions.assertEquals(message, exception.getMessage());
    }

    @And("I enter the numbers below to a list")
    public void iEnterTheNumbersBelowToAList(ArrayList<Integer> numbers) {
        calculator.setNumbers(numbers);
    }

    @When("I press add")
    public void iPressAdd() {
        actual = calculator.add();
    }

    @When("I press subtract")
    public void iPressSubtract() {
        actual = calculator.subtract();
    }

    @When("I press multiply")
    public void iPressMultiply() {
        actual = calculator.multiply();
    }

    @Then("the result should be {int}")
    public void theResultShouldBe(int expected) {
        Assertions.assertEquals(expected, actual);
    }

    @When("I press divide")
    public void iPressDivide() {
        try{
            actual = calculator.divide();
        }
        catch(Exception e) {
            exception = e;
        }
    }

    @Then("a DivideByZeroException should be thrown")
    public void aDivideByZeroExceptionShouldBeThrown() {
        Assertions.assertInstanceOf(ArithmeticException.class, exception);
    }

    @When("I iterate through the list to add all the even numbers")
    public void iIterateThroughTheListToAddAllTheEvenNumbers() {
        actual = calculator.sumEvens();
    }
}
