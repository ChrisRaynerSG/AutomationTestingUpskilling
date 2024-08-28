package com.sparta.cr.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InventoryPage {

    private WebDriver driver;
    private By inventoryItem = new By.ByClassName("inventory_item");
    private By shoppingCartLink = new By.ByClassName("shopping_cart_link");
    private By addItemToCart = new  By.ByClassName("btn_inventory");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getInventoryItems(){
        return driver.findElements(inventoryItem);
    }
    public String getShoppingCartLink(){
        return driver.findElement(shoppingCartLink).getText();
    }

    public void addItemToShoppingCart(){
        driver.findElement(addItemToCart).click();
    }

    public void clickShoppingCartLink(){
        driver.findElement(shoppingCartLink).click();
    }
}
