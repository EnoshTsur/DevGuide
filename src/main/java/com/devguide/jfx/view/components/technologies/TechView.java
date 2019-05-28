package com.devguide.jfx.view.components.technologies;

import com.devguide.jfx.view.UI.PaneTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import static com.devguide.jfx.view.UI.ListViewAPI.createListViewWithRule;
import static com.devguide.jfx.view.UI.PaneAPI.createPaneWithRule;
import static com.devguide.jfx.view.components.technologies.TechUtils.*;
import static com.devguide.jfx.view.containers.technologies.TechnologiesUtils.setContainerStyles;
import static com.devguide.jfx.view.containers.technologies.TechnologiesUtils.setListViewStyles;

public class TechView {

    /***
     * Takes - List Data
     * Create Stack Pane contains list view ( Stack Pane )
     * @return Stack Pane
     */
    public final StackPane createView(ObservableList<String> data) {

        // Container
        StackPane backView = (StackPane) createPaneWithRule.apply(
                setContainerStyles,
                PaneTypes.STACK_PANE
        );

        // List
        ListView<String> backList = createListViewWithRule.apply(
                setListViewStyles,
                FXCollections.observableArrayList(data)
        );

        // Adding stuff
        backView.getChildren().add(backList);

        return backView;
    }
}
