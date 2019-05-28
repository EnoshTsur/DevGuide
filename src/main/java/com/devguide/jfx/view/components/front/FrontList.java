package com.devguide.jfx.view.components.front;

import com.devguide.jfx.view.UI.PaneAPI;
import com.devguide.jfx.view.UI.PaneTypes;
import io.vavr.control.Option;
import javafx.scene.layout.StackPane;

import javax.swing.plaf.PanelUI;

import static com.devguide.jfx.view.UI.PaneAPI.*;

public class FrontList {

    /***
     * Create Front list view ( Stack Pane )
     * @return Stack Pane
     */
    public final StackPane createFrontList() {


        // Container
        StackPane frontView = (StackPane) createPaneWithRule.apply(
                Option.none(),
                PaneTypes.STACK_PANE
        );

        return frontView;
    }
}
