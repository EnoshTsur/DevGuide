package com.devguide.jfx.browsers.pages.vscode;

import io.vavr.Tuple3;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.function.Consumer;

import static com.devguide.jfx.browsers.BotFn.*;
import static com.devguide.jfx.browsers.pages.vscode.VSCodeUtils.*;
import static com.devguide.jfx.view.components.console.Console.*;

public interface VSCodePage {

    Consumer<TextArea> downloadVSCode = output -> {
        File location = consoleState.getLocation.get();

        Task<Void> download = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Tuple3<WebDriver, WebDriverWait, Actions> tools = setUp.apply(DOWNLOAD_PATH);
                clickOn.apply(tools, DOWNLOAD_BUTTON);
                return null;
            }
        };
        new Thread(download).start();
        download.setOnSucceeded(event -> output.appendText(setOutputContent.apply(location, "Downloading...")));
        download.setOnFailed(event -> output.appendText(setOutputContent.apply(location, "Error...")));


    };
}
