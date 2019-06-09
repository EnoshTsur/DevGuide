package com.devguide.jfx.view.containers.technologies;

import com.devguide.jfx.view.UI.PaneTypes;
import io.vavr.*;
import io.vavr.collection.List;
import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.containers.technologies.TechnologiesUtils.*;

/***
 * Technologies section
 * Front & Back
 */
public interface Technologies {


    /***
     * Create Component View
     * Takes name ( front / back )
     * Returns VBox
     */
    Function2<String, List<String>, VBox> createComponentView = (name, data) ->
            createView.apply(
                    FXCollections.observableArrayList(
                    data.asJava()
            ),
            name
    );

    /***
     * View Builder
     */
    Function1<Function2<String, List<String>, VBox>,
            Function1<Tuple3<String, String, String>, VBox>> buildView

            = createFunction ->  frontAndBackText ->

    {
        VBox frontBox = createFunction.apply(
                frontAndBackText._1,
                frontTechnologies
        );
        VBox backBox = createFunction.apply(
                frontAndBackText._2,
                backTechnologies
        );

        VBox devBox = createFunction.apply(

                frontAndBackText._3,
                installations
        );

        VBox container = (VBox) createPane.apply(PaneTypes.VBOX);
        container.getChildren().addAll(frontBox, backBox, devBox);
        return container;
    };

    /***
     * Creates The Actual View
     */
    Function1<Tuple3<String, String, String>, VBox> createTechView =
            buildView.apply(createComponentView);

    /***
     * View
     */
    VBox view = createTechView.apply(Tuple.of(FRONT, BACK, INSTALL));
}
