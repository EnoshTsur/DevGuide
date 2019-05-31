package com.devguide.jfx.view.components.footer;

import com.devguide.jfx.view.UI.PaneAPI;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.components.main.MainViewUtils;
import com.devguide.jfx.view.shared.Colors;
import com.devguide.jfx.view.shared.SharedUtils;
import javafx.scene.layout.HBox;

import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.components.main.MainViewUtils.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

/**
 * Footer Component
 */
public class FooterView {

    /***
     * Create A HBox for footer components
     * @return HBox
     */
    public HBox createFooter() {
        final int MIN_HEIGHT = 70;

        return (HBox) createPaneWithRule.apply(
                // Styles
                pane -> {
                    pane.setMinHeight(MIN_HEIGHT);

                    // Background
                    setBackgroundColor.accept(
                            pane,
                            HAARETS_LIGHTEST
                    );

                    return pane;
                },
                PaneTypes.HBOX
        );
    }
}
