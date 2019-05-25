package com.devguide.jfx.view.containers;

import com.devguide.jfx.view.UI.BorderPaneAlignment;
import com.devguide.jfx.view.UI.PaneAPI;
import com.devguide.jfx.view.components.search.SearchBar;
import io.vavr.control.Option;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class MainContainer {

    // Singleton
    private static MainContainer instance = null;

    /***
     * Private CTOR
     * Singleton
     */
    private MainContainer(){}

    /***
     * Create Main Container View
     * @return Border Pane
     */
    public BorderPane createView() {
        // Top
        GridPane searchView = SearchBar.createSearchBar();

        // Container
        BorderPane mainContainer = PaneAPI.createBorderPaneWithRule.apply(
                Option.none(),
                new HashMap<BorderPaneAlignment, Pane>() {{
                    put(BorderPaneAlignment.TOP, searchView);
                }}
        );

        return mainContainer;
    }

    /***
     * Get the only instance
     * @return
     */
    public static final MainContainer getInstance() {
        return instance == null ? new MainContainer() : instance;
    }
}
