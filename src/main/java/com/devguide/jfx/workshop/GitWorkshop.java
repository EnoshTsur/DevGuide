package com.devguide.jfx.workshop;

import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.function.BiConsumer;

import static com.devguide.jfx.view.components.console.ConsoleView.*;

public interface GitWorkshop {

    BiConsumer<ComboBox<String>, TextArea > runGitWorkshop = (input, output) -> {

        // Save Originals handler
        EventHandler<? super KeyEvent> keyPressed = input.getOnKeyPressed();
        EventHandler<? super KeyEvent> keyReleased = input.getOnKeyReleased();

        // ConsoleView location
        File location  = consoleState.getLocation.get();


        // Not working because i need to create an enter && exit
        input.setOnKeyPressed(null);
        input.setOnKeyReleased( event -> {
            if (event.equals(KeyCode.ENTER)) {
                output.appendText(setOutputContent.apply(location, input.getEditor().getText()));
            }
        });

        input.setOnKeyPressed(keyPressed);
        input.setOnKeyReleased(keyReleased);

    };
}
