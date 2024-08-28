package com.sparta.cr.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public class ToolsQaTests {
    private static final String BASE_URL = "https://demoqa.com/droppable/";
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver.exe";
    private static ChromeDriverService service;
    private WebDriver webDriver;

    private static ChromeOptions getChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=");
//        options.setImplicitWaitTimeout(Duration.of(10));
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


}
