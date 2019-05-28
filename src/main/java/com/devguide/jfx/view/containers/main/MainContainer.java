package com.devguide.jfx.view.containers.main;

import com.devguide.jfx.view.UI.BorderPaneAlignment;
import com.devguide.jfx.view.components.search.SearchBar;
import com.devguide.jfx.view.containers.technologies.Technologies;
import javafx.scene.layout.*;

import java.util.HashMap;

import static com.devguide.jfx.view.UI.PaneAPI.*;

public class MainContainer {

    // Singleton
    private static MainContainer instance = null;

    // Components
    private final Technologies technologies;

    /***
     * Private CTOR
     * Singleton
     */
    private MainContainer(){
        technologies = new Technologies();
    }

    /***
     * Create Main Container View
     * @return Border Pane
     */
    public BorderPane createView() {
        // Top
        GridPane searchView = SearchBar.createSearchBar();

        VBox container = technologies.createTechContainer();


        // Container
        BorderPane mainContainer = createBorderPane.apply(
                new HashMap<BorderPaneAlignment, Pane>() {{
                    put(BorderPaneAlignment.TOP, searchView);
                    put(BorderPaneAlignment.LEFT, container);
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
