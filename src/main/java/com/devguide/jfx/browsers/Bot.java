package com.devguide.jfx.browsers;

import io.vavr.Function1;
import io.vavr.Function2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.function.Supplier;

/**
 * Bot in order to act on pages
 */
public class Bot {

    // Attributes
    private WebDriver driver;
    private Actions actions;

    /***
     * CTOR - Web driver
     * @param driver
     */
    public Bot(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    // Functions
    /**
     * Get Website's title
     * @return
     */
    Supplier<String> getTitle = () -> driver.getTitle();


    /***
     * Go to URL
     * @param url
     * @return ActionBot - for flow
     */
    Function1<String, Bot> goTo = url -> {
        driver.get(url);
        return this;
    };

    /***
     * Clicking on By element
     * @param locator
     * @return ActionBot - for flow
     */
    Function1<By, Bot> clickOn = locator -> {
        driver.findElement(locator).click();
        return this;
    };


    /***
     * Double Clicking on By element
     * @param locator
     * @return ActionBot - for flow
     */
    Function1<By, Bot> doubleClickOn =  locator -> {
        actions.doubleClick(driver.findElement(locator));
        return this;
    };

    /***
     * Pressing Enter
     * @return ActionBot - for flow
     */
    Function1<By, Bot> pressEnter = locator -> {
        actions.sendKeys(driver.findElement(locator), Keys.ENTER).perform();
        return this;
    };


    /***
     * Sending keys
     * @return ActionBot - for flow
     */
    Function2<By, String, Bot> sendKeys = (locator , text) -> {
        driver.findElement(locator).sendKeys(text);
        return this;
    };

    /***
     * Clearing input
     * @param locator
     * @return ActionBot for - flow
     */
     Function1<By, Bot> clearInput = locator ->{
        driver.findElement(locator).clear();
        return this;
    };


}
