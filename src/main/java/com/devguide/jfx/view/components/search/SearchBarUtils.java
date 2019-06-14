package com.devguide.jfx.view.components.search;

import com.devguide.jfx.utils.BasicUtils;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.util.function.Consumer;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

public interface SearchBarUtils {

    int CONTAINER_MIN_HEIGHT = 40;
    int SEARCH_LABEL_FONT_SIZE = 18;

    Insets SEARCH_LABEL_PADDING = new Insets(0, 10, 0, 10);


    String SEARCH_ICON_PATH = "assets/search.png";
    String FRONTEND_ICON_PATH = "assets/frontendmasterslogo.png";

    String HEADER_TEXT = "Search";
    String INPUT_MESSAGE = "Buzz Words\nSuch as React / VM...";

    double COMBOBOX_INPUT_WIDTH = 200;
    double COMBOBOX_INPUT_HEIGHT = 40;

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
        setLabelTextColor.apply(label, LIGHT_TEXT_COLOR);
        return label;
    };


    /***
     * Set Combo Box Styles
     */
    Function2<ComboBox<String>, Tooltip,  ComboBox<String>> setComboBoxStyles =
            (combobox, tooltip) -> {
                combobox.setTooltip(tooltip);
                combobox.setFocusTraversable(false);
                combobox.setEditable(true);
                setBackgroundColor.accept(combobox.getEditor(), DARKEST);
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
        setComboBoxStyles.apply(input, createToolTip.apply(INPUT_MESSAGE));
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
    Consumer<Button> setSearchButtonStyles = button -> {
        button.setMaxSize(30, 30);
        setCursorPointer.accept(button);
    };


    /****
     * Search Bar Utils
     * Button styles
     */
    Consumer<Button> setFrontendButtonStyles = button -> {
        button.setMinSize(120, 50);
        setCursorPointer.accept(button);
    };

    /****
     * Search Bar Utils
     * Container styles
     */
    Function1<GridPane, GridPane> setContainerStyles = container -> {
        container.setPadding(DEFAULT_INSETS);
        container.setMinHeight(CONTAINER_MIN_HEIGHT);
        setBackgroundColor.accept(container, PRIMARY_LIGHT);
        return container;
    };
}
