package com.devguide.jfx.browsers;

import io.vavr.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.devguide.jfx.browsers.FrontendUtils.*;

/**
 * Bot in order to act on pages
 */
public interface BotFn {

    /***
     * Set Driver in order to work with
     */
    Supplier<WebDriver> setDriver = () -> {
        // Setting properties
        System.setProperty(
                "webdriver.chrome.driver",
                "src/main/java/com/devguide/jfx/browsers/chromedriver3.exe"
        );
        // Setting Driver
        WebDriver driver = new ChromeDriver();
        return driver;
    };

    /**
     * Get Website's title
     *
     * @return
     */
    Function1<WebDriver, String> getTitle = driver -> driver.getTitle();


    /***
     * Go to URL
     */
    BiConsumer<WebDriver, String> goTo = (driver, url) -> driver.get(url);

    /***
     * Clicking on By element
     */
    Function2<Tuple3<WebDriver, WebDriverWait, Actions>, By,
            Tuple3<WebDriver, WebDriverWait, Actions>> clickOn = (tools, locator) -> {
        WebDriver driver = tools._1;
        driver.findElement(locator).click();
        return tools;
    };


    /***
     * Double Clicking on By element
     */
    Function2<WebDriver, By, WebDriver> doubleClickOn = (driver, locator) -> {
        Actions actions = new Actions(driver);
        actions.doubleClick(driver.findElement(locator));
        return driver;
    };

    /***
     * Pressing Enter
     */
    Function2<WebDriver, By, WebDriver> pressEnter = (driver, locator) -> {
        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(locator), Keys.ENTER).perform();
        return driver;
    };


    /***
     * Sending keys
     */
    Function3<Tuple3<WebDriver, WebDriverWait, Actions>, By, String,
            Tuple3<WebDriver, WebDriverWait, Actions>> sendKeys = (tools, locator, text) -> {
        WebDriver driver = tools._1;
        driver.findElement(locator).sendKeys(text);
        return tools;
    };

    /***
     * Clearing input
     */
    Function2<WebDriver, By, WebDriver> clearInput = (driver, locator) -> {
        driver.findElement(locator).clear();
        return driver;
    };

    /***
     * Set up - Driver , Wait, Actions
     */
    Function1<String, Tuple3<WebDriver, WebDriverWait, Actions>> setUp = url -> {
        WebDriver driver = setDriver.get();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
        driver.manage().window().maximize();
        goTo.accept(driver, url);
        return Tuple.of(driver, wait, actions);
    };

    /***
     * Set Input text
     */
    Function3<Tuple3<WebDriver, WebDriverWait, Actions>, By, String,
            Tuple3<WebDriver, WebDriverWait, Actions>> setInputText
            = (tools, locator, text) -> {
        WebDriver driver = tools._1;
        WebDriverWait wait = tools._2;
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(USER_NAME)));
        sendKeys.apply(tools, locator, text);
        return tools;
    };


}
