package com.devguide.jfx.view.components.search;

import com.devguide.jfx.view.UI.ButtonAPI;
import com.devguide.jfx.view.shared.AppStylesUtils;
import com.devguide.jfx.view.shared.SharedUtils;
import io.vavr.Function1;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Consumer;

import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.shared.AppStylesUtils.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

public interface SearchBarUtils {

    int CONTAINER_MIN_HEIGHT = 40;

    String SEARCH_ICON_PATH = "assets/search.png";

    String HEADER_TEXT = "Search";
    String BUTTON_TEXT = "Submit";

    int TEXT_FIELD_INPUT_WIDTH = 200;
    int TEXT_FIELD_INPUT_HEIGHT = 40;

    Font buttonFont = createFont.apply(FONT_TYPE, FontWeight.MEDIUM, 16);


    Function1<Label, Label> setInputLabelStyles = label -> {
        setLabelFont
                .accept(
                        label,
                        haaretzFontBySize
                                .apply(20)
                );
        setLabelTextColor.accept(label, WHITE);
        addShadow.accept(label);
        return label;
    };

    Function1<TextField, TextField> setInputTextSytles = input -> {

        return input;
    };

    Consumer<Button> setButtonStyles = button -> {
        button.setMaxSize(30, 30);
        setOnMouseEntered.accept(button);
    };

    Function1<GridPane, GridPane> setContainerStyles = container -> {
        container.setPadding(DEFAULT_INSETS);
        container.setMinHeight(CONTAINER_MIN_HEIGHT);
        setBackgroundColor.accept(container, HAARETZ_LIGHTBLUE);
        return container;
    };
}
