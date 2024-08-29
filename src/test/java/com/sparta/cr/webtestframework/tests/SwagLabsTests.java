package com.sparta.cr.webtestframework.tests;
import com.sparta.cr.webtestframework.pages.InventoryPage;
import com.sparta.cr.webtestframework.pages.Website;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;

public class SwagLabsTests extends TestSetup {
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private Website website;

    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should land on the inventory page")
    public void successfulLogin(){
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUserName("standard_user");
        website.getHomePage().enterPassword("secret_sauce");
        website.getHomePage().clickLoginButton();
        Assertions.assertEquals(BASE_URL+"inventory.html", website.getCurrentUrl());
    }

    @Test
    @DisplayName("Given I enter a valid username AND invalid password, when I click login, then I should get epic sadface")
    public void failedLogin(){
        website = getWebsite(BASE_URL);
        website.getHomePage().login("standard_user", "bad_password");
        Assertions.assertTrue(website.getHomePage().getErrorMessage().contains("Epic sadface"));
    }

    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should land on the inventory page, and should see the right number of items")
    public void checkNumberOfInventoryItems(){
        website = getWebsite(BASE_URL);
        website.getHomePage().successfulUserLogin();
        Assertions.assertEquals(6, website.getInventoryPage().getInventoryItems().size());
    }

    @Test
    @DisplayName("Given that I am on the inventory page, when I click add item to cart, then my cart is updated")
    public void addItemToCart(){
        website = getWebsite(BASE_URL);
        website.getHomePage().successfulUserLogin();
        Assertions.assertEquals("", website.getInventoryPage().getShoppingCartLink());
        website.getInventoryPage().addItemToShoppingCart();
        Assertions.assertEquals("1", website.getInventoryPage().getShoppingCartLink());
    }

    //Create a POM for the inventory page
    //Add a test for:
    //The number of items on the inventory page is 6
    //Adding an item to the basket increases the basket count by 1
    //Update Website so it instantiates and can return anInventoryPage instanceme

//    @Test
//    @DisplayName("GIVEN I enter a valid username AND an invalid password WHEN I click login THEN I should see epic sad face")
//    public void unsuccessfulLogin(){
//        webDriver.get(BASE_URL);
//        WebElement usernameField = webDriver.findElement(By.name("user-name"));
//        WebElement passwordField = webDriver.findElement(By.name("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//        WebElement errorField = webDriver.findElement(By.className("error-message-container"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("wrong_password");
//        loginButton.click();
//        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL));
//        MatcherAssert.assertThat(errorField.getText(), Matchers.startsWith("Epic sadface:"));
//
//    }
//
//    @Test
//    @DisplayName("GIVEN I am logged in WHEN I click logout THEN I should be logged out of the webpage")
//    public void successfulLogout() throws InterruptedException {
//        webDriver.get(BASE_URL);
//        WebElement username = webDriver.findElement(By.name("user-name"));
//        WebElement password = webDriver.findElement(By.name("password"));
//        WebElement login = webDriver.findElement(By.id("login-button"));
//        username.sendKeys("standard_user");
//        password.sendKeys("secret_sauce");
//        login.click();
//        WebElement hamburgerMenu = webDriver.findElement(By.id("react-burger-menu-btn"));
//        hamburgerMenu.click();
//        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
//        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
//        Assertions.assertEquals(BASE_URL + "inventory.html", webDriver.getCurrentUrl());
//        logoutButton.click();
//        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL));
//
//    }
//    @Test
//    @DisplayName("Given I enter a valid username and password, when I click login, then I should see the correct number of products")
//    public void checkNumberOfProductsOnInventoryPage() throws IOException {
//        webDriver.get(BASE_URL);
//        WebElement usernameField = webDriver.findElement(By.name("user-name"));
//        WebElement passwordField = webDriver.findElement(By.name("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce");
//        loginButton.click();
//        List<WebElement> inventory = webDriver.findElements(By.className("inventory_item"));
//        int inventoryCount = inventory.size();
//
//        try(PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
//            for(WebElement item : inventory){
//                WebElement nameElement = item.findElement(By.className("inventory_item_name"));
//                WebElement priceElement = item.findElement(By.className("inventory_item_price"));
//                String itemName = nameElement.getText() + ": " + priceElement.getText();
//                writer.println(itemName);
//
//            }
//        }
//        MatcherAssert.assertThat(inventoryCount, Matchers.is(6));
//    }
//
//    @Test
//    @DisplayName("GIVEN I am logged in WHEN I add an item to my cart THEN the item should be displayed in my cart")
//    public void successfulAddItemToCart() throws IOException {
//        webDriver.get(BASE_URL);
//        WebElement username = webDriver.findElement(By.name("user-name"));
//        WebElement password = webDriver.findElement(By.name("password"));
//        WebElement login = webDriver.findElement(By.id("login-button"));
//        username.sendKeys("standard_user");
//        password.sendKeys("secret_sauce");
//        login.click();
//        WebElement addItemButton = webDriver.findElement(By.className("btn_inventory"));
//        WebElement shoppingCartLink = webDriver.findElement(By.className("shopping_cart_link"));
//        addItemButton.click();
//        Assertions.assertEquals("1",shoppingCartLink.getText());
//        shoppingCartLink.click();
//        List<WebElement> cart = webDriver.findElements(By.className("cart_item"));
//        try(PrintWriter writer = new PrintWriter(new FileWriter("cart.txt"))) {
//            for(WebElement item : cart){
//                WebElement quantityElement = item.findElement(By.className("cart_quantity"));
//                WebElement nameElement = item.findElement(By.className("inventory_item_name"));
//                String itemName = nameElement.getText() + ": " + quantityElement.getText();
//                writer.println(itemName);
//                System.out.println(itemName);
//            }
//        }
//        MatcherAssert.assertThat(cart.size(), Matchers.is(1));
//    }
//
//    @Test
//    @DisplayName("GIVEN I am logged in WHEN I land on the homepage THEN no items should be displayed in my cart")
//    public void noItemsWhenFirstLogin(){
//        webDriver.get(BASE_URL);
//        WebElement username = webDriver.findElement(By.name("user-name"));
//        WebElement password = webDriver.findElement(By.name("password"));
//        WebElement login = webDriver.findElement(By.id("login-button"));
//        username.sendKeys("standard_user");
//        password.sendKeys("secret_sauce");
//        login.click();
//        WebElement shoppingCartLink = webDriver.findElement(By.className("shopping_cart_link"));
//        Assertions.assertEquals("",shoppingCartLink.getText());
//        shoppingCartLink.click();
//        List<WebElement> cart = webDriver.findElements(By.className("cart_item"));
//        MatcherAssert.assertThat(cart.size(), Matchers.is(0));
//    }
//
//    @Test
//    @DisplayName("GIVEN I am logged in AND I have an item in my cart WHEN I click remove item THEN the item is removed.")
//    public void successfulRemoveItemFromCart(){
//        webDriver.get(BASE_URL);
//        WebElement username = webDriver.findElement(By.name("user-name"));
//        WebElement password = webDriver.findElement(By.name("password"));
//        WebElement login = webDriver.findElement(By.id("login-button"));
//        username.sendKeys("standard_user");
//        password.sendKeys("secret_sauce");
//        login.click();
//        WebElement addItemButton = webDriver.findElement(By.className("btn_inventory"));
//        WebElement shoppingCartLink = webDriver.findElement(By.className("shopping_cart_link"));
//        addItemButton.click();
//        shoppingCartLink.click();
//        WebElement removeItemButton = webDriver.findElement(By.className("cart_button"));
//        removeItemButton.click();
//        List<WebElement> cart = webDriver.findElements(By.className("cart_item"));
//        MatcherAssert.assertThat(cart.size(), Matchers.is(0));
//    }
//
//    @Test
//    @DisplayName("GIVEN I am logged in AND I have Items in my cart WHEN I checkout my items AND input my details THEN I successfully purchase my items")
//    public void successfulCheckoutItemsInCart(){
//        webDriver.get(BASE_URL);
//        WebElement username = webDriver.findElement(By.name("user-name"));
//        WebElement password = webDriver.findElement(By.name("password"));
//        WebElement login = webDriver.findElement(By.id("login-button"));
//        username.sendKeys("standard_user");
//        password.sendKeys("secret_sauce");
//        login.click();
//        WebElement addItemButton = webDriver.findElement(By.className("btn_inventory"));
//        WebElement shoppingCartLink = webDriver.findElement(By.className("shopping_cart_link"));
//        addItemButton.click();
//        Assertions.assertEquals("1",shoppingCartLink.getText());
//        shoppingCartLink.click();
//        WebElement checkoutButton = webDriver.findElement(By.id("checkout"));
//        checkoutButton.click();
//        WebElement firstName = webDriver.findElement(By.id("first-name"));
//        WebElement lastName = webDriver.findElement(By.id("last-name"));
//        WebElement zipCode = webDriver.findElement(By.id("postal-code"));
//        WebElement continueButton = webDriver.findElement(By.id("continue"));
//        firstName.sendKeys("Mantis");
//        lastName.sendKeys("Toboggan");
//        zipCode.sendKeys("12345");
//        continueButton.click();
//        WebElement finishButton = webDriver.findElement(By.id("finish"));
//        finishButton.click();
//        Assertions.assertEquals(BASE_URL + "checkout-complete.html", webDriver.getCurrentUrl());
//    }

    //create a test for valid email, invalid password, assert error message contains EPIC SADFACE

    //create a test for dragged box to appear in dropped box, USE ACTIONS

    //create more test
}
