package com.devguide.jfx.view.containers.main;

import com.devguide.jfx.view.UI.BorderPaneAlignment;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.components.choice.ChoiceView;
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
    private final ChoiceView choice;

    /***
     * Private CTOR
     * Singleton
     */
    private MainContainer(){
        technologies = new Technologies();
        choice = ChoiceView.getInstance();
    }

    /***
     * Create Main Container View
     * @return Border Pane
     */
    public BorderPane createView() {
        // Top
        GridPane searchView = SearchBar.createSearchBar();

        VBox techContainer = technologies.createTechContainer();

        GridPane choiceView = choice.createView();

        // Container
        BorderPane mainContainer = createBorderPane.apply(
                new HashMap<BorderPaneAlignment, Pane>() {{
                    put(BorderPaneAlignment.TOP, searchView);
                    put(BorderPaneAlignment.LEFT, techContainer);
                    put(BorderPaneAlignment.RIGHT, choiceView);
                    put(BorderPaneAlignment.CENTER, createPane.apply(PaneTypes.HBOX));
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
