package com.sparta.cr.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;

    private By userNameField = new By.ByName("user-name");
    private By passwordField = new By.ByName("password");
    private By loginButton = new By.ById("login-button");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    public void enterUserName(String userName) {
        driver.findElement(userNameField).sendKeys(userName);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
