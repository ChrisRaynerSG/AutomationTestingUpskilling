package com.sparta.cr.webtestframework.pages;

import org.openqa.selenium.WebDriver;

public class Website {

    private WebDriver webDriver;
    private HomePage homePage;

    public Website(WebDriver webDriver) {
        this.webDriver = webDriver;
        homePage = new HomePage(webDriver);
    }

    public HomePage getHomePage(){
        return homePage;
    }
    public String getCurrentUrl(){
        return webDriver.getCurrentUrl();
    }
    public String getPageTitle(){
        return webDriver.getTitle();
    }

}
