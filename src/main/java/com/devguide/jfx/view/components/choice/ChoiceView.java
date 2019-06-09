package com.devguide.jfx.view.components.choice;

import com.devguide.jfx.utils.BasicUtils;
import com.devguide.jfx.utils.StringUtils;
import com.devguide.jfx.view.UI.PaneAPI;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.components.console.Console;
import io.vavr.Function1;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.components.choice.ChoiceUtils.setContainerStyles;

/**
 * Choice Component
 */
public interface ChoiceView {

    /***
     * View Function
     */
    VBox console = Console.view.get();

    Function1<String, GridPane> buildComponent = str -> {

        GridPane container = (GridPane) createPaneWithRule.apply(
                setContainerStyles,
                PaneTypes.GRID_PANE
        );
        container.getChildren().addAll(console);
        GridPane.setConstraints(console, 0 , 0);
        return container;
    };

    /***
     * View
     */
    GridPane view = buildComponent.apply(EMPTY_STRING);
}
