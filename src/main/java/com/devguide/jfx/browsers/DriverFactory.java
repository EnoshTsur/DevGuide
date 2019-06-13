package com.devguide.jfx.browsers;

import io.vavr.Function1;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Browser Factory
 */
public interface DriverFactory {

    /***
     * Get Web Driver by Browser type
     */
    static WebDriver getDriverByBrowser(Browser browser) {
            switch (browser) {
                case CHROME:
                    return new ChromeDriver();
                case FIRE_FOX:
                    return new FirefoxDriver();
                case EDGE:
                    return new EdgeDriver();
                default:
                    return null;

            }
    }
}
