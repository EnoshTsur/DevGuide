package com.devguide.jfx.view.components.search;

import io.vavr.Function1;
import io.vavr.collection.List;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.util.function.Consumer;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.ListCellStyles.*;
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

    Font buttonFont = createFont.apply(DEFAULT_FONT_TYPE, FontWeight.MEDIUM, 16);


    /****
     * Search Bar Utils
     * Search Label styles
     */
    Function1<Label, Label> setInputLabelStyles = label -> {
        setLabelFont.apply(
                label,
                createFont.apply(
                        DEFAULT_FONT_TYPE,
                        FontWeight.LIGHT,
                        SEARCH_LABEL_FONT_SIZE
                )
        );
        label.setPadding(SEARCH_LABEL_PADDING);
        setLabelTextColor.apply(label, HAARETZ_LIGHT_TEXT_COLOR);
        return label;
    };


    /***
     * Set Combo Box Styles
     */
    Function1<ComboBox<String>, ComboBox<String>> setComboBoxStyles =
            combobox -> {
                combobox.setTooltip(createToolTip.apply(INPUT_MESSAGE));
                combobox.setFocusTraversable(false);
                combobox.setEditable(true);
                setBackgroundColor.accept(combobox.getEditor(), HAARETZ_DARKEST);
                combobox.getEditor().setFont(HAARETZ_TITLE_FONT);
                addManyStyles.accept(combobox.getEditor(), List.of("-fx-text-fill: #99d6ff;"));
                addStyle.accept(combobox, "-fx-font: Monospaced 30px;");

                return combobox;
            };

    /****
     * Search Bar Utils
     * Text Field styles
     */
    Function1<ComboBox<String>, ComboBox<String>> setInputTextStyles = input -> {
        setComboBoxStyles.apply(input);
        input.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                final ListCell<String> cell = new ListCell<String>() {
                    {
                        super.setPrefWidth(100);
                    }

                    @Override
                    public void updateItem(String item,
                                           boolean empty) {
                        super.updateItem(item, empty);

                        if (isNull.apply(item) && empty) setText(null);
                        else setText(item);

                        setText(item);
                        setListCellsStyles.apply(this);

                    }
                };
                return cell;
            }
        });
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
