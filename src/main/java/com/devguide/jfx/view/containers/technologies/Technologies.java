package com.devguide.jfx.view.containers.technologies;

import com.devguide.jfx.view.UI.PaneAPI;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.components.technologies.TechUtils;
import com.devguide.jfx.view.components.technologies.TechView;
import javafx.collections.FXCollections;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/***
 * Technologies section
 * Front & Back
 */
public class Technologies {

    // Components
    private final TechView techView;

    /***
     * CTOR - Initial Components
     */
    public Technologies() {
        techView = new TechView();
    }

    /**
     *Create View
     * @return VBox contains Front List & Back List
     */
    public final VBox createTechContainer() {
        // Container
        VBox container = (VBox) PaneAPI.createPane.apply(PaneTypes.VBOX);

        // Front List
        StackPane frontView = techView.createView(

                FXCollections.observableArrayList(
                        TechUtils.frontTechnologies.asJava()
                )
        );

        // Back List
        StackPane backView = techView.createView(

                FXCollections.observableArrayList(
                        TechUtils.backTechnologies.asJava()
                )
        );

        // Adding Components
        container.getChildren().addAll(
                frontView,
                backView
        );

        return container;
    }
}
