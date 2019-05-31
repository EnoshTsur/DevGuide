package com.devguide.jfx.view.components.search;

import com.devguide.jfx.view.containers.technologies.Technologies;
import com.devguide.jfx.view.containers.technologies.TechnologiesUtils;
import com.devguide.jfx.view.shared.AutoCompleteComboBoxListener;
import io.vavr.Tuple;
import io.vavr.control.Option;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.ComboBoxAPI.*;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.UI.PaneTypes.*;
import static com.devguide.jfx.view.components.search.SearchBarUtils.*;

public class SearchBar {

    /***
     * Create Search Bar View
     * Label: Text = "Search"
     * Text Field: Input
     * Button Submit
     * @return
     */
    public static final GridPane createSearchBar() {

        // Text
        Label searchText = createLabelWithRule
                .apply(
                        setInputLabelStyles,
                        HEADER_TEXT
                );

        // Text Field
        ComboBox<String> input = createComboBoxWithRule
                .apply(
                        setInputTextStyles,
                        TechnologiesUtils.frontAndBack,

                        Tuple.of(
                                TEXT_FIELD_INPUT_WIDTH,
                                TEXT_FIELD_INPUT_HEIGHT
                        ),
                        System.out::println
                );
        new AutoCompleteComboBoxListener<>(input);

        // Button
        Button submit = createButtonWithBackground
                .apply(
                        Option.of(SEARCH_ICON_PATH),
                        EMPTY_STRING,
                        setButtonStyles,
                        event -> {
                            // TODO://
                        }
                );

        // Pane
        GridPane pane = (GridPane) createPaneWithRule
                .apply(
                        container -> setContainerStyles.apply((GridPane) container),
                        GRID_PANE
                );

        pane.getChildren().addAll(searchText, input, submit);
        GridPane.setConstraints(searchText, 0, 0);
        GridPane.setConstraints(input, 1, 0);
        GridPane.setConstraints(submit, 2, 0);
        pane.setHgap(10);
        return pane;
    }
}
