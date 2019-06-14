package com.devguide.jfx.view.shared;


import com.devguide.jfx.utils.StringUtils;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.stream.Collectors;

import static com.devguide.jfx.utils.KeyBoardUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.ComboBoxAPI.*;
import static javafx.collections.FXCollections.*;

/**
 * Auto Complete ComboBox
 *
 * @author 'Enosh Tsur'
 */

public class AutoCompleteComboBoxListener implements EventHandler<KeyEvent> {

    private ComboBox input;
    private StringBuilder sb;
    private ObservableList<String> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    private List<KeyCode> keysToReturn = List.of(
            KeyCode.RIGHT, KeyCode.LEFT,
            KeyCode.HOME, KeyCode.END, KeyCode.TAB
    );

    /***
     * Returns Observable List of Strings
     * Who contains Combo Box Text
     */
    private Function2<ComboBox<String>,
            ObservableList<String>,
            ObservableList<String>> getMatchItems = (input, data) ->
            observableArrayList(data.stream().filter(
                    item -> doesItContains.apply(
                                    getComboEditorText.apply(input),
                                    item
                            )
                    ).collect(Collectors.toList()));


    public AutoCompleteComboBoxListener(final ComboBox comboBox) {
        this.input = comboBox;
        sb = new StringBuilder();
        data = comboBox.getItems();

        this.input.setEditable(true);
        this.input.setOnKeyPressed(t -> comboBox.hide());
        this.input.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {

        if (event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaretToPos = moveCaret.apply(
                    input
                            .getEditor()
                            .getText()
                            .length()
            );
            return;


        } else if (event.getCode() == KeyCode.DOWN) {
            if (!input.isShowing()) {
                input.show();
            }
            caretPos = -1;
            moveCaretToPos = moveCaret.apply(
                    input
                            .getEditor()
                            .getText()
                            .length()
            );
            return;

        } else if (event.getCode() == KeyCode.ENTER) {
            input.getEditor().setText(getComboEditorText.apply(input));
            input.getSelectionModel().select(getComboEditorText.apply(input));
            return;

        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = input.getEditor().getCaretPosition();

        } else if (event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = input.getEditor().getCaretPosition();
        }

        if (isThisKeyIn.apply(event, keysToReturn)) {
            return;
        }

        // Match Items
        ObservableList<String> matchItems = getMatchItems.apply(input, data);

        String t = input.getEditor().getText();

        input.setItems(matchItems);
        input.getEditor().setText(t);
        if (!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaretToPos = moveCaret.apply(t.length());
        if (!matchItems.isEmpty()) {
            input.show();
        }
    }

    private Function1<Integer, Boolean> moveCaret = textLength -> {
        if (caretPos == -1) {
            input.getEditor().positionCaret(textLength);
        } else {
            input.getEditor().positionCaret(caretPos);
        }
        return false;
    };

}

