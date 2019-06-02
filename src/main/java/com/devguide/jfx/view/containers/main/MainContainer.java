package com.devguide.jfx.view.containers.main;

import com.devguide.jfx.view.UI.BorderPaneAlignment;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.components.choice.ChoiceView;
import com.devguide.jfx.view.components.search.SearchBar;
import com.devguide.jfx.view.containers.technologies.Technologies;
import io.vavr.Function3;
import javafx.scene.layout.*;

import java.util.HashMap;

import static com.devguide.jfx.view.UI.PaneAPI.*;

/***
 * Main Container for all insides components
 */
public interface MainContainer {

    /***
     * Build Main container Component
     */
    Function3<GridPane, VBox, GridPane, BorderPane> buildComponent =
            (searchView, techContainer, choiceView) -> {
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
            };

    /***
     * Component View !!!
     */
    BorderPane view = buildComponent.apply(
            SearchBar.createSearchBar(),
            Technologies.view,
            ChoiceView.getInstance().createView()
    );

}
