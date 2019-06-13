package com.devguide.jfx.browsers;

import com.devguide.jfx.utils.FileSystem;
import io.vavr.Function2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.function.Supplier;

import static com.devguide.jfx.browsers.Browser.*;
import static com.devguide.jfx.browsers.DriverFactory.*;


public interface WebActions {

    /***
     * Set Driver in order to work with
     */
    Supplier<WebDriver> setDriver = () -> {
        // Setting properties
        System.setProperty(
                "webdriver.chrome.driver",
                "/home/ibo1.com/enosh.tsur/realDevBible/DevGuide/src/main/java/com/devguide/jfx/browsers/chromedriver"
        );
        // Setting Driver
        WebDriver driver = new ChromeDriver();
        return driver;
    };

    /**
     * Navigate To
     */
    Function2<String, WebDriver, WebDriver> navigateTo = (path, driver) -> {
        driver.get(path);
        return driver;
    };
}
