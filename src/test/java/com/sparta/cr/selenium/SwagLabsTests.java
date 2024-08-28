package com.sparta.cr.selenium;
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

public class SwagLabsTests {
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver.exe";
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static ChromeDriverService service;
    private WebDriver webDriver;

    private static ChromeOptions getChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=");
        return options;
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new ChromeDriverService
                .Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterAll
    static void afterEach(){
        service.stop();
    }

    @BeforeEach
    public void setUp(){
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions()); //RemoteWebDriver is an interface, that implements WebDriver, becomes implicit when provided with arguments
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }

    @Test
    @DisplayName("Check that the webDriver works")
    public void checkWebDriverWorks(){
        webDriver.get(BASE_URL);
        Assertions.assertEquals("https://www.saucedemo.com/", webDriver.getCurrentUrl());
        Assertions.assertEquals("Swag Labs", webDriver.getTitle());
    }

    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should land on the inventory page")
    public void successfulLogin(){
        webDriver.get(BASE_URL);
        WebElement username = webDriver.findElement(By.name("user-name"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement login = webDriver.findElement(By.id("login-button"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        login.click();
        Assertions.assertEquals(BASE_URL + "inventory.html", webDriver.getCurrentUrl());
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL + "inventory.html"));

    }


    @Test
    @DisplayName("GIVEN I enter a valid username AND an invalid password WHEN I click login THEN I should see epic sad face")
    public void unsuccessfulLogin(){
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        WebElement errorField = webDriver.findElement(By.className("error-message-container"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("wrong_password");
        loginButton.click();
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL));
        MatcherAssert.assertThat(errorField.getText(), Matchers.startsWith("Epic sadface:"));

    }

    @Test
    @DisplayName("GIVEN I am logged in WHEN I click logout THEN I should be logged out of the webpage")
    public void successfulLogout() throws InterruptedException {
        webDriver.get(BASE_URL);
        WebElement username = webDriver.findElement(By.name("user-name"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement login = webDriver.findElement(By.id("login-button"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        login.click();
        WebElement hamburgerMenu = webDriver.findElement(By.id("react-burger-menu-btn"));
        hamburgerMenu.click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        Assertions.assertEquals(BASE_URL + "inventory.html", webDriver.getCurrentUrl());
        logoutButton.click();
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL));

    }
    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should see the correct number of products")
    public void checkNumberOfProductsOnInventoryPage() throws IOException {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        List<WebElement> inventory = webDriver.findElements(By.className("inventory_item"));
        int inventoryCount = inventory.size();

        try(PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            for(WebElement item : inventory){
                WebElement nameElement = item.findElement(By.className("inventory_item_name"));
                WebElement priceElement = item.findElement(By.className("inventory_item_price"));
                String itemName = nameElement.getText() + ": " + priceElement.getText();
                writer.println(itemName);

            }
        }
        MatcherAssert.assertThat(inventoryCount, Matchers.is(6));
    }

    @Test
    @DisplayName("GIVEN I am logged in WHEN I add an item to my cart THEN the item should be displayed in my cart")
    public void successfulAddItemToCart() throws IOException {
        webDriver.get(BASE_URL);
        WebElement username = webDriver.findElement(By.name("user-name"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement login = webDriver.findElement(By.id("login-button"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        login.click();
        WebElement addItemButton = webDriver.findElement(By.className("btn_inventory"));
        WebElement shoppingCartLink = webDriver.findElement(By.className("shopping_cart_link"));
        addItemButton.click();
        Assertions.assertEquals("1",shoppingCartLink.getText());
        shoppingCartLink.click();
        List<WebElement> cart = webDriver.findElements(By.className("cart_item"));
        try(PrintWriter writer = new PrintWriter(new FileWriter("cart.txt"))) {
            for(WebElement item : cart){
                WebElement quantityElement = item.findElement(By.className("cart_quantity"));
                WebElement nameElement = item.findElement(By.className("inventory_item_name"));
                String itemName = nameElement.getText() + ": " + quantityElement.getText();
                writer.println(itemName);
                System.out.println(itemName);
            }
        }
        MatcherAssert.assertThat(cart.size(), Matchers.is(1));
    }

    @Test
    @DisplayName("GIVEN I am logged in WHEN I land on the homepage THEN no items should be displayed in my cart")
    public void noItemsWhenFirstLogin(){
        webDriver.get(BASE_URL);
        WebElement username = webDriver.findElement(By.name("user-name"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement login = webDriver.findElement(By.id("login-button"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        login.click();
        WebElement shoppingCartLink = webDriver.findElement(By.className("shopping_cart_link"));
        Assertions.assertEquals("",shoppingCartLink.getText());
        shoppingCartLink.click();
        List<WebElement> cart = webDriver.findElements(By.className("cart_item"));
        MatcherAssert.assertThat(cart.size(), Matchers.is(0));
    }

    @Test
    @DisplayName("GIVEN I am logged in AND I have an item in my cart WHEN I click remove item THEN the item is removed.")
    public void successfulRemoveItemFromCart(){
        webDriver.get(BASE_URL);
        WebElement username = webDriver.findElement(By.name("user-name"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement login = webDriver.findElement(By.id("login-button"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        login.click();
        WebElement addItemButton = webDriver.findElement(By.className("btn_inventory"));
        WebElement shoppingCartLink = webDriver.findElement(By.className("shopping_cart_link"));
        addItemButton.click();
        shoppingCartLink.click();
        WebElement removeItemButton = webDriver.findElement(By.className("cart_button"));
        removeItemButton.click();
        List<WebElement> cart = webDriver.findElements(By.className("cart_item"));
        MatcherAssert.assertThat(cart.size(), Matchers.is(0));
    }

    //create a test for valid email, invalid password, assert error message contains EPIC SADFACE

    //create a test for dragged box to appear in dropped box, USE ACTIONS

    //create more test
}
