package com.devguide.jfx.view.UI;
import javafx.scene.layout.*;

import java.util.function.Function;

/***
 * Get Specific Pane by Type
 */
public interface PaneFactory {

    /**
     * Pane Factory
     * Get a Pane by Type
     */
    Function<PaneTypes, ? extends Pane> getPane =
            (type) -> {
                switch (type) {
                    case STACK_PANE:
                        return new StackPane();
                    case GRID_PANE:
                        return new GridPane();
                    case HBOX:
                        return new HBox();
                    case VBOX:
                        return new VBox();
                    case BORDER_PANE:
                        return new BorderPane();
                    default:
                        return null;
                }
            };
}
