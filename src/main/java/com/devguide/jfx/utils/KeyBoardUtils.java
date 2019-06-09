package com.devguide.jfx.utils;

import com.devguide.jfx.view.UI.ButtonAPI;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.collection.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.plaf.synth.Region;
import java.security.Key;

import static com.devguide.jfx.utils.BasicUtils.addOne;
import static com.devguide.jfx.utils.BasicUtils.minusOne;
import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.shared.SharedUtils.addStyle;
import static com.devguide.jfx.view.shared.SharedUtils.createStyleOpacity;


/**
 * Utils For Keyboard out put
 */
public interface KeyBoardUtils {

    /***
     * Key Combination - Control + up
     */
    KeyCombination CTRL_UP = new KeyCodeCombination(
            KeyCode.UP,
            KeyCombination.CONTROL_DOWN
    );

    /***
     * Key Combination - Control + down
     */
    KeyCombination CTRL_DOWN = new KeyCodeCombination(
            KeyCode.DOWN,
            KeyCombination.CONTROL_DOWN
    );

    /***
     * Key Combination - Alt + down
     */
    KeyCodeCombination ALT_DOWN = new KeyCodeCombination(
            KeyCode.DOWN,
            KeyCodeCombination.ALT_DOWN
    );


    /***
     * Returns true if key event equals to a specific key
     */
    Function2<KeyEvent, KeyCode, Boolean> isThisKeyIs =
            ((keyEvent, keyCode) -> keyEvent.getCode().equals(keyCode));

    /***
     * Returns true if key is in list of key codes
     */
    Function2<KeyEvent, List<KeyCode>, Boolean> isThisKeyIn =
            (key, listOf) -> listOf.contains(key.getCode());
    /***
     * Handle key Board output
     */
    Function3<KeyEvent, Pane, Stage, Stage> handleKeyboard = (event, pane, window) -> {
        final double MAX_OPACITY = 1.0;
        final double MIN_OPACITY = 0.0;
        final double SPEED = 10.0;

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
        }

        return window;
    };
}
