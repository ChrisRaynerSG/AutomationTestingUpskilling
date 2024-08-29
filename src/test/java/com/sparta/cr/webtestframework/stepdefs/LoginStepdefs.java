package com.sparta.cr.webtestframework.stepdefs;

import com.sparta.cr.webtestframework.pages.Website;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class LoginStepdefs {

    private Website website;
    private static final String BASE_URL = "https://www.saucedemo.com/";

    @After
    public void afterEach(){
        TestSetup.quitWebDriver();
        TestSetup.stopService();
    }

    @Before
    public static void setup() throws IOException {
        TestSetup.startChromeService();
        TestSetup.createWebDriver();
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        website = TestSetup.getWebsite(BASE_URL);
    }

    @And("I have entered the username {string}")
    public void iHaveEnteredTheUsername(String arg0) {
        website.getHomePage().enterUserName(arg0);
    }

    @And("I have entered the password {string}")
    public void iHaveEnteredThePassword(String arg0) {
        website.getHomePage().enterPassword(arg0);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        website.getHomePage().clickLoginButton();
    }

    @Then("I should land on the inventory page")
    public void iShouldLandOnTheInventoryPage() {
        Assertions.assertEquals(BASE_URL + "inventory.html", website.getCurrentUrl());
    }

    @Then("I should see an error message that contains {string}")
    public void iShouldSeeAnErrorMessageThatContains(String expected) {
        Assertions.assertTrue(website.getHomePage().getErrorMessage().contains("Epic sadface"));
    }

}
