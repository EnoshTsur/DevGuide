package com.devguide.jfx.browsers;

import com.devguide.jfx.utils.Consumer3;
import com.devguide.jfx.utils.StringUtils;
import com.devguide.jfx.view.components.console.Console;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import javafx.scene.control.TextArea;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.devguide.jfx.browsers.BotFn.clickOn;
import static com.devguide.jfx.browsers.BotFn.setUp;
import static com.devguide.jfx.browsers.GithubUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.components.console.Console.*;

public interface GithubPage {

    /***
     * Download Git by Operation system
     */
    Consumer<TextArea> downloadGit = output -> {
        File location = consoleState.getLocation.get();
        if (isEmpty.apply(DOWNLOAD_PATH)){
            output.appendText(setOutputContent.apply(location, OPERATION_SYSTEM_ERROR));
            return;
        }
        Tuple3<WebDriver, WebDriverWait, Actions> tools = setUp.apply(DOWNLOAD_PATH);
        output.appendText(setOutputContent.apply(location, DOWNLOAD_MESSAGE));
        return;
    };
}
