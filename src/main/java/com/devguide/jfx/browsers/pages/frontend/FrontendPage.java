package com.devguide.jfx.browsers.pages.frontend;


import com.devguide.jfx.utils.Consumer3;
import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import javafx.concurrent.Task;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

import static com.devguide.jfx.browsers.BotFn.*;
import static com.devguide.jfx.browsers.pages.frontend.FrontendUtils.*;
import static com.devguide.jfx.browsers.pages.frontend.FrontendUtils.LOGIN_BUTTON;

/***
 * Login Page to front end masters
 */
public interface FrontendPage {

    /***
     * Login to Frontend masters
     */
    Consumer3<String, String, String> login = (url, username, password) -> {
        Task<Void> login = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
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
                                        password),
                                REMEMBER_ME
                        ),
                        LOGIN_BUTTON
                );
                return null;
            }
        };
        new Thread(login).start();
    };
}
