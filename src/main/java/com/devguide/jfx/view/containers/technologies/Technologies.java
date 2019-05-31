package com.devguide.jfx.view.containers.technologies;

import com.devguide.jfx.view.UI.PaneTypes;
import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;

import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.containers.technologies.TechnologiesUtils.*;

/***
 * Technologies section
 * Front & Back
 */
public class Technologies {

    /**
     *Create View
     * @return VBox contains Front List & Back List
     */
    public final VBox createTechContainer() {
        // Container
        VBox container = (VBox) createPane.apply(PaneTypes.VBOX);

        // Front List
        VBox frontView = createView.apply(

                FXCollections.observableArrayList(
                        frontTechnologies.asJava()
                ),
                FRONT
        );


        // Back List
        VBox backView = createView.apply(

                FXCollections.observableArrayList(
                        backTechnologies.asJava()
                ),
                BACK
        );

        // Adding Components
        container.getChildren().addAll(
                frontView,
                backView
        );

        return container;
    }
}
