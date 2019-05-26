package com.devguide.jfx.view.components.main;

import io.vavr.Function3;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static com.devguide.jfx.utils.BasicUtils.addOne;
import static com.devguide.jfx.utils.BasicUtils.minusOne;
import static com.devguide.jfx.utils.KeyBoardUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.addStyle;
import static com.devguide.jfx.view.shared.SharedUtils.createStyleOpacity;

public interface MainViewUtils {

    // Size
    int WIDTH = 700;
    int HEIGHT = 700;


    /***
     * Handle key Board output
     */
    Function3<KeyEvent, Pane, Stage, Stage> handleKeyboard = (event, pane, window) -> {
        final double MAX_OPACITY = 1.0;
        final double MIN_OPACITY = 0.0;
        final double SPEED = 5.0;

        // Ctrl + Down
        if (CTRL_DOWN.match(event)) {
            if (pane.getOpacity() > MIN_OPACITY)
                addStyle
                        .accept(pane, createStyleOpacity.apply(
                                minusOne.apply(pane.getOpacity())));
        }

        // Ctrl + Up
        else if (CTRL_UP.match(event)) {
            if (pane.getOpacity() < MAX_OPACITY)
                addStyle
                        .accept(pane, createStyleOpacity.apply(
                                addOne.apply(pane.getOpacity())));
        }

        // Alt + Down
        else if (ALT_DOWN.match(event)) window.setIconified(true);

        // Up
        else if (isThisKeyIs.apply(event, KeyCode.UP)) {
            window.setY(window.getY() - SPEED);
        }

        // Down
        else if (isThisKeyIs.apply(event, KeyCode.DOWN)) {
            window.setY(window.getY() + SPEED);
        }

        // Left
        else if (isThisKeyIs.apply(event, KeyCode.LEFT)) {
            window.setX(window.getX() - SPEED);
        }
        // Right
        else if (isThisKeyIs.apply(event, KeyCode.RIGHT)) {
            window.setX(window.getX() + SPEED);
        }
        // Esc
        else if (isThisKeyIs.apply(event, KeyCode.ESCAPE)) {
            window.close();
            System.exit(0);
        }

        return window;
    };

}
