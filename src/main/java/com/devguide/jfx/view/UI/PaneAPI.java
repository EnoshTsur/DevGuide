package com.devguide.jfx.view.UI;

import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Option;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.devguide.jfx.view.UI.PaneFactory.*;

/***
 * Pane Utils - Utils Functions for all kinds of panes
 */
public interface PaneAPI {

    /**
     * Takes :
     * PaneType  HBox | VBox | Border | Pane | Stack | Pane | Grid Pane
     * Function = Option of Function: Pane -> Pane ( Edit Pane dynamically )
     * Returns:
     * New Edited Pane
     */
    Function2<Option<Function1<Pane, ? extends Pane>>, PaneTypes, ? extends Pane> createPaneWithRule =
            (paneRuleOption, paneType) -> {
                if (paneRuleOption.isEmpty()) return getPane.apply(paneType);
                return paneRuleOption.get().apply(getPane.apply(paneType));
            };

    /***
     * Create Simple Pane by Type
     * Takes :
     * PaneType  HBox | VBox | Border | Pane | Stack | Pane | Grid Pane
     */
    Function1<PaneTypes, ? extends Pane> createPane =
            createPaneWithRule
                    .curried()
                    .apply(Option.none());

    /**
     * Add Pane to Border Pane by alignment
     */
    BiConsumer<Map.Entry<PaneAlignment, ? extends Pane>, BorderPane> addPaneToBorderPane =
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
            };

    /***
     * Create Simple Border Pane
     * Takes: Map ( Pane Alignment, Option Function [ Border Pane rule ] )
     * Returns: Border Pane
     */
    Function2<Option<Function1<Pane, ? extends Pane>>, Map<PaneAlignment, Pane>, BorderPane> createBorderPaneWithRule =
            (paneRule, innerPanes) -> {
                BorderPane pane = (BorderPane) createPaneWithRule.apply(paneRule, PaneTypes.BORDER_PANE);
                if (innerPanes.isEmpty()) return pane;
                innerPanes.entrySet().forEach(entry -> addPaneToBorderPane.accept(entry, pane));
                return pane;
            };

    /***
     * Create Simple Border Pane
     */
    Function1<Map<PaneAlignment, Pane>, BorderPane> createBorderPane =
            createBorderPaneWithRule.curried().apply(Option.none());

    /***
     * Build Panes
     * Takes: List of  Tuple3 ( Pane Type, Pane Alignment, Function rule )
     * Returns Map of < Pane Alignment, Pane >
     */
    Function1<List<Tuple3<PaneTypes, PaneAlignment, Function1<Pane, Pane>>>, Map<PaneAlignment, Pane>>
            buildPanes = list -> {

        Map<PaneAlignment, Pane> panesMap = new HashMap<>();
        list.forEach(
                tuple3 -> panesMap.put(
                        tuple3._2,
                        createPaneWithRule.apply(Option.of(tuple3._3), tuple3._1)
                )
        );
        return panesMap;
    };

}
