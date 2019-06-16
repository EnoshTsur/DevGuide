package com.devguide.jfx.browsers.pages.frontend;

import org.openqa.selenium.By;

public interface FrontendUtils {

    By USER_NAME = By.name("username");
    By USER_PASSWORD = By.name("password");
    By REMEMBER_ME = By.id("remember");
    By LOGIN_BUTTON = By.xpath("//*[@id=\"loginForm\"]/button");
}
