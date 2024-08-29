package com.sparta.cr.webtestframework.stepdefs;

import com.sparta.cr.webtestframework.pages.InventoryPage;
import com.sparta.cr.webtestframework.pages.Website;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class InventoryStepdefs {

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

    @Given("I am on the inventory page")
    public void iAmOnTheInventoryPage() {
        website = TestSetup.getWebsite(BASE_URL);
        website.getHomePage().successfulUserLogin();
    }

    @When("I add an item to my cart")
    public void iAddAnItemToMyCart() {
        website.getInventoryPage().addItemToShoppingCart();
    }

    @Then("I should see {int} items")
    public void iShouldSeeItems(int expected) {
        Assertions.assertEquals(expected, website.getInventoryPage().getInventoryItems().size());
    }

    @Then("I should see the cart updated")
    public void iShouldSeeTheCartUpdated() {
        Assertions.assertEquals("1", website.getInventoryPage().getShoppingCartLink());
    }
}
