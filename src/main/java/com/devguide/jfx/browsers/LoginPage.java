package com.devguide.jfx.browsers;

import io.vavr.Function1;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/***
 * Login Page to front end masters
 */
public class LoginPage extends AbstractPage {

    By USER_NAME = By.name("username");
    By USER_PASSWORD = By.name("password");
    By REMEMBER_ME = By.id("remember");
    By LOGIN_BUTTON = By.xpath("//*[@id=\"loginForm\"]/button");

    /***
     * CTR contains WebDriver , ActionBot & WebDriverWait
     * @param driver
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /***
     * Set Username
     */
    Function1<String, Bot> setUserName = username -> {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(USER_NAME)));
        bot.sendKeys.apply(USER_NAME, username);
        return bot;
    };

    /***
     * Set Password
     */
    Function1<String, Bot> setPassword = password -> {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(USER_PASSWORD)));
        bot.sendKeys.apply(USER_PASSWORD, password);
        return bot;
    };

    /***
     * Click on remember me
     */
    Runnable clickOnRememberMe = () -> {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(REMEMBER_ME)));
        bot.clickOn.apply(REMEMBER_ME);
    };

    /**
     * Click on login button
     */
    Runnable clickLogin = () -> {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(LOGIN_BUTTON)));
        bot.clickOn.apply(LOGIN_BUTTON);
    };
}
