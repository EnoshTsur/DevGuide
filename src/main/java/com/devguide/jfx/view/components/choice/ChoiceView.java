package com.devguide.jfx.view.components.choice;

import com.devguide.jfx.utils.BasicUtils;
import com.devguide.jfx.view.UI.PaneAPI;
import com.devguide.jfx.view.UI.PaneTypes;
import javafx.scene.layout.GridPane;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.components.choice.ChoiceUtils.setContainerStyles;

/**
 * Choice Component
 */
public class ChoiceView {

    // Singleton
    private static ChoiceView instance = null;

    /**
     * Private CTOR
     * Singleton
     */
    private ChoiceView() {}

    /***
     *
     * @return
     */
    public static final synchronized ChoiceView getInstance() {
        return isNull.apply(instance) ? new ChoiceView() : instance;
    }

    /***
     * Create Choice Component View
     * @return
     */
    public GridPane createView() {
        GridPane container = (GridPane) createPaneWithRule.apply(
                setContainerStyles,
                PaneTypes.GRID_PANE
        );
        return container;
    }
}
