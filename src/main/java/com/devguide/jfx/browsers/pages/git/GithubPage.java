package com.devguide.jfx.browsers.pages.git;

import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.function.Consumer;

import static com.devguide.jfx.browsers.BotFn.setUp;
import static com.devguide.jfx.browsers.pages.git.GithubUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.components.console.ConsoleView.*;

public interface GithubPage {

    /***
     * Download GitWorkshop by Operation system
     */
    Consumer<TextArea> downloadGit = output -> {
        File location = consoleState.getLocation.get();
        if (isEmpty.apply(DOWNLOAD_PATH)){
            output.appendText(setOutputContent.apply(location, OPERATION_SYSTEM_ERROR));
            return;
        }
        Task<Void> download = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                setUp.apply(DOWNLOAD_PATH);
                return null;
            }
        };
        new Thread(download).start();
        download.setOnSucceeded(event -> output.appendText(setOutputContent.apply(location, DOWNLOAD_MESSAGE)));
        download.setOnFailed(event -> output.appendText(setOutputContent.apply(location, "Error...")));
    };
}
