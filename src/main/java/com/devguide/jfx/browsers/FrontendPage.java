package com.devguide.jfx.browsers;


import io.vavr.Function3;
import io.vavr.Tuple3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.devguide.jfx.browsers.BotFn.*;
import static com.devguide.jfx.browsers.FrontendUtils.*;
import static com.devguide.jfx.browsers.FrontendUtils.LOGIN_BUTTON;

/***
 * Login Page to front end masters
 */
public interface FrontendPage {

    /***
     * Login to Frontend masters
     */
    Function3<String, String, String, Runnable> login = (url, username, password) -> {
        Tuple3<WebDriver, WebDriverWait, Actions> tools = setUp.apply(url);
        clickOn.apply(
                clickOn.apply(
                        setInputText.apply(
                                setInputText.apply(
                                        tools,
                                        USER_NAME,
                                        username
                                ),
                                USER_PASSWORD,
                                password
                        ), REMEMBER_ME
                ), LOGIN_BUTTON
        );

        return () -> System.out.println("run");
    };
}
