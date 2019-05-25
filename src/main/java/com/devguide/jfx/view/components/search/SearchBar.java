package com.devguide.jfx.view.components.search;

import com.devguide.jfx.view.UI.*;
import io.vavr.Tuple;
import io.vavr.control.Option;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static com.devguide.jfx.utils.StringUtils.*;
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
                        Option.of(setInputLabelStyles::apply),
                        HEADER_TEXT
                );

        // Text Field
        TextField input = TextFieldAPI.createTextFieldWithRule
                .apply(
                        Option.of(setInputTextSytles::apply),

                        Tuple.of(
                                TEXT_FIELD_INPUT_WIDTH,
                                TEXT_FIELD_INPUT_HEIGHT
                        ),
                        EMPTY_STRING,
                        event -> System.out.println(event)
                );

        // Button
        Button submit = ButtonAPI.createButtonWithBackground
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
                        Option.of(
                                container -> setContainerStyles
                                        .apply((GridPane) container)
                        ),
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
