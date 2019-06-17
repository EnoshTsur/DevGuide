package com.devguide.jfx.browsers.pages.webstorm;

import io.vavr.Tuple3;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.function.Consumer;

import static com.devguide.jfx.browsers.BotFn.clickOn;
import static com.devguide.jfx.browsers.BotFn.setUp;
import static com.devguide.jfx.browsers.pages.webstorm.WebStormUtils.*;
import static com.devguide.jfx.view.components.console.Console.consoleState;
import static com.devguide.jfx.view.components.console.Console.setOutputContent;

public interface WebStormPage {

    Consumer<TextArea> downloadWebStorm = output -> {
        File location = consoleState.getLocation.get();

        Task<Void> download = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Tuple3<WebDriver, WebDriverWait, Actions> tools = setUp.apply(PAGE_ADDRESS);
                clickOn.apply(tools, DOWNLOAD_BUTTON);
                return null;
            }
        };
        new Thread(download).start();
        download.setOnSucceeded(event -> output.appendText(setOutputContent.apply(location, "Downloading...")));
        download.setOnFailed(event -> output.appendText(setOutputContent.apply(location, "Error...")));
    };
}
