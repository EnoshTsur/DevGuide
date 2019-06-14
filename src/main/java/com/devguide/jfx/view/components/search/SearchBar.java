package com.devguide.jfx.view.components.search;

import com.devguide.jfx.browsers.FrontendPage;
import com.devguide.jfx.view.containers.technologies.TechnologiesUtils;
import com.devguide.jfx.view.shared.AutoCompleteComboBoxListener;
import io.vavr.Tuple;
import javafx.collections.FXCollections;
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

        // Combo Box
        ComboBox<String> input = createComboBoxWithRule
                .apply(
                        setInputTextStyles,
                        TechnologiesUtils.frontAndBack,

                        Tuple.of(
                                COMBOBOX_INPUT_WIDTH,
                                COMBOBOX_INPUT_HEIGHT
                        ),
                        System.out::println
                );
        input.setItems(
                FXCollections.observableArrayList(
                        TechnologiesUtils
                                .frontAndBack
                                .asJava()
                )
        );
        new AutoCompleteComboBoxListener(input);

        // Button
        Button submit = createButtonWithBackground
                .apply(
                        SEARCH_ICON_PATH,
                        EMPTY_STRING,
                        setSearchButtonStyles,
                        event -> {
                            // TODO://
                        }
                );

        Button frontEndMasters = createButtonWithBackground
                .apply(
                        FRONTEND_ICON_PATH,
                        EMPTY_STRING,
                        setFrontendButtonStyles,
                        event ->FrontendPage.login.apply(
                                "https://frontendmasters.com/login/",
                                "Eran.Meshulam",
                                "EM1234"
                        )

                );

        // Pane
        GridPane pane = (GridPane) createPaneWithRule
                .apply(
                        container -> setContainerStyles.apply((GridPane) container),
                        GRID_PANE
                );

        pane.getChildren().addAll(searchText, input, submit, frontEndMasters);
        GridPane.setConstraints(input, 0, 0);
        GridPane.setConstraints(submit, 1, 0);
        GridPane.setConstraints(frontEndMasters, 2, 0);
        pane.setHgap(25);
        return pane;
    }
}
