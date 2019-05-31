package com.devguide.jfx.view.UI;

import io.vavr.*;
import io.vavr.collection.List;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

import static com.devguide.jfx.utils.BasicUtils.*;

/***
 * Pane API - Functions for all kinds of panes
 */
public interface PaneAPI {

    /**
     * Takes :
     * PaneType  HBox | VBox | Border | Pane | Stack | Pane | Grid Pane
     * Function = Function: Pane -> Pane ( Edit Pane dynamically )
     * Returns:
     * New Edited Pane
     */
    Function2<Function1<Pane, ? extends Pane>, PaneTypes, ? extends Pane> createPaneWithRule =
            (paneRuleOption, paneType) -> {
                if (isNull.apply(paneRuleOption)) return PaneFactory.getPane.apply(paneType);
                return paneRuleOption.apply(PaneFactory.getPane.apply(paneType));
            };

    /***
     * Create Simple Pane by Type
     * Takes :
     * PaneType  HBox | VBox | Border | Pane | Stack | Pane | Grid Pane
     */
    Function1<PaneTypes, ? extends Pane> createPane =
            createPaneWithRule
                    .curried()
                    .apply(null);

    /**
     * Add Pane to Border Pane by alignment
     */
    Function2<Map.Entry<BorderPaneAlignment, ? extends Pane>, BorderPane, BorderPane> addPaneToBorderPane =
            (entry, borderPane) -> {
                switch (entry.getKey()) {
                    case TOP:
                        borderPane.setTop(entry.getValue());
                        break;
                    case LEFT:
                        borderPane.setLeft(entry.getValue());
                        break;
                    case RIGHT:
                        borderPane.setRight(entry.getValue());
                        break;
                    case BOTTOM:
                        borderPane.setBottom(entry.getValue());
                        break;
                    case CENTER:
                        borderPane.setCenter(entry.getValue());
                        break;
                }
                return borderPane;
            };

    /***
     * Create Simple Border Pane
     * Takes: Map ( Pane Alignment, Option Function [ Border Pane rule ] )
     * Returns: Border Pane
     */
    Function2<Function1<Pane, ? extends Pane>, Map<BorderPaneAlignment, Pane>, BorderPane> createBorderPaneWithRule =
            (paneRule, innerPanes) -> {
                BorderPane pane = (BorderPane) createPaneWithRule.apply(paneRule, PaneTypes.BORDER_PANE);
                if (innerPanes.isEmpty()) return pane;
                innerPanes.entrySet().forEach(entry -> addPaneToBorderPane.apply(entry, pane));
                return pane;
            };


    /***
     * Create Simple Border Pane
     */
    Function1<Map<BorderPaneAlignment, Pane>, BorderPane> createBorderPane =
            createBorderPaneWithRule.curried().apply(null);

    /***
     * Build Panes
     * Takes: List of  Tuple3 ( Pane Type, Pane Alignment, Function rule )
     * Returns Map of < Pane Alignment, Pane >
     */
    Function1<List<Tuple3<PaneTypes, BorderPaneAlignment, Function1<Pane, Pane>>>, Map<BorderPaneAlignment, Pane>>
            buildPanes = list -> {

        Map<BorderPaneAlignment, Pane> panesMap = new HashMap<>();
        list.forEach(
                tuple3 -> panesMap.put(
                        tuple3._2,
                        createPaneWithRule.apply(tuple3._3, tuple3._1)
                )
        );
        return panesMap;
    };

}
