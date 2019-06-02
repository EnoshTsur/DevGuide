package com.devguide.jfx.view.UI;

import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Option;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;
/***
 * Pane API - Functions for all kinds of panes
 */
public interface ButtonAPI {

    String CURSOR_POINTER = "-fx-cursor: hand;";
    String CURSOR_MOVE = "-fx-cursor: move;";

    /**
     * Takes :
     * Option String = Background
     * String = Button's Text
     * Consumer Button = Rule ( for style and more... )
     * Consumer Event = Click handler
     * Returns:
     * New Button
     */
    Function4<String, String, Consumer<Button>, Consumer<Event>, Button>
            createButtonWithBackground = (backgroundPath, text, rule, clickHandler) -> {
        Button button = new Button();
        if (isNotEmpty.apply(text)) button.setText(text);
        if (isNotNull.apply(rule)) rule.accept(button);
        button.setOnAction(event -> clickHandler.accept(event));
        if (isEmpty.apply(backgroundPath) || isNull.apply(backgroundPath)) return button;
        setBackground.accept(button, backgroundPath);
        return button;
    };

    /**
     * Takes :
     * String = Button's Text
     * Consumer Button = Rule ( for style and more... )
     * Consumer Event = Click handler
     * Returns:
     * New Button
     */
    Function3<String, Consumer<Button>, Consumer<Event>, Button> createButton =
            createButtonWithBackground
                    .apply(EMPTY_STRING);

    /***
     * Minimize the window
     * @param event
     */
    Consumer<Event> handleMinimizeRequest = event ->
            ((Stage) ((Button) event
                    .getSource())
                    .getScene()
                    .getWindow())
                    .setIconified(true);

    /***
     * Close the window
     * @param event
     */
    Consumer<Event> handleCloseRequest = event ->
            ((Stage) ((Button) event
                    .getSource())
                    .getScene()
                    .getWindow())
                    .close();

    /***
     * Set Text Color to Button
     */
    BiConsumer<Button, String> setButtonTextColor =
            (button, color) -> button.setTextFill(Color.web(color));

    /***
     * Set Font if not Null
     */
    BiConsumer<Button, Font> setButtonFont = (button, font) -> {
        if (isNull.apply(font)) return;
        button.setFont(font);
    };

    /***
     * Sets Cursor to be pointer and add shadow
     * while mouse over givven button
     */
    Consumer<Button> setCursorPointer = button -> {
        final String OLD_STYLE = button.getStyle();
        button.setOnMouseEntered(e -> addManyStyles
                .accept(button, List.of(SHADOW_STYLE, CURSOR_POINTER)));
        button.setOnMouseExited(e -> button.setStyle(OLD_STYLE));
    };

    /***
     * Set Button default padding
     */
    Function1<Button, Button> setButtonDefaultPadding = button -> {
        button.setPadding(DEFAULT_INSETS);
        return button;
    };

    /***
     * Set Button Padding bu size
     */
    Function2<Button, Tuple4<Double, Double, Double, Double>, Button>
            setButtonPadding = (button , insets) -> {
        double up = doubleOrZero.apply(insets._1);
        double right = doubleOrZero.apply(insets._2);
        double down = doubleOrZero.apply(insets._3);
        double left = doubleOrZero.apply(insets._4);
        button.setPadding(new Insets(up, right, down, left));
        return button;
    };



}
