package com.devguide.jfx.browsers;

import com.devguide.jfx.utils.Consumer3;
import io.vavr.Function1;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class AbstractPage {

    // Page attributes
    protected WebDriver driver;
    protected Bot bot;
    protected WebDriverWait wait;

    /***
     * CTR contains WebDriver , ActionBot & WebDriverWait
     * @param driver
     */
    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        driver.manage()
                .timeouts()
                .implicitlyWait(
                        10,
                        TimeUnit.MINUTES
                );

        this.bot = new Bot(driver);
        this.wait = new WebDriverWait(driver, 30);
    }




}
