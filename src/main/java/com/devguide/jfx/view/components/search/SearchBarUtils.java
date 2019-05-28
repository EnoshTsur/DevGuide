package com.devguide.jfx.view.components.search;

import io.vavr.Function1;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Consumer;

import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

public interface SearchBarUtils {

    int CONTAINER_MIN_HEIGHT = 40;
    int SEARCH_LABEL_FONT_SIZE = 25;

    Insets SEARCH_LABEL_PADDING = new Insets(0, 10, 0, 10);




    String SEARCH_ICON_PATH = "assets/search.png";

    String HEADER_TEXT = "Search";
    String BUTTON_TEXT = "Submit";
    String INPUT_MESSAGE = "Buzz Words\nSuch as React / VM...";

    int TEXT_FIELD_INPUT_WIDTH = 200;
    int TEXT_FIELD_INPUT_HEIGHT = 40;

    Font buttonFont = createFont.apply(HEADER_FONT_TYPE, FontWeight.MEDIUM, 16);


    /****
     * Search Bar Utils
     * Search Label styles
     */
    Function1<Label, Label> setInputLabelStyles = label -> {
        setLabelFont.apply(
                        label,
                        createFont.apply(
                                SEARCH_FONT_TYPE,
                                        FontWeight.EXTRA_BOLD,
                                        SEARCH_LABEL_FONT_SIZE
                                )
                );
        label.setPadding(SEARCH_LABEL_PADDING);
        setLabelTextColor.apply(label, WHITE);
        addShadow.accept(label);
        return label;
    };

    /****
     * Search Bar Utils
     * Text Field styles
     */
    Function1<ComboBox<String>, ComboBox<String>> setInputTextStyles = input -> {
        input.setTooltip(createToolTip.apply(INPUT_MESSAGE));
        input.setFocusTraversable(false);
        input.setEditable(true);
        addStyle.accept(input, "-fx-font: 30px Arial Rounded MT Bold;");
        addStyle.accept(input, createBgColorStyle.apply(WHITE));
        return input;
    };

    /****
     * Search Bar Utils
     * Button styles
     */
    Consumer<Button> setButtonStyles = button -> {
        button.setMaxSize(30, 30);
        setOnMouseEntered.accept(button);
    };

    /****
     * Search Bar Utils
     * Container styles
     */
    Function1<GridPane, GridPane> setContainerStyles = container -> {
        container.setPadding(DEFAULT_INSETS);
        container.setMinHeight(CONTAINER_MIN_HEIGHT);
        setBackgroundColor.accept(container, HAARETZ_LIGHTBLUE);
        return container;
    };
}
