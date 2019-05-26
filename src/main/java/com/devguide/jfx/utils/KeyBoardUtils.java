package com.devguide.jfx.utils;

import io.vavr.Function2;
import io.vavr.Function3;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.security.Key;


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
}
