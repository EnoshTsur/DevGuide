package com.devguide.jfx.view.containers.technologies;

import com.devguide.jfx.view.UI.PaneTypes;
import io.vavr.*;
import io.vavr.collection.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import static com.devguide.jfx.view.UI.LabelAPI.createLabelWithRule;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.containers.technologies.TechnologiesUtils.*;
import static com.devguide.jfx.view.containers.technologies.TechnologyInitCell.*;
import static javafx.collections.FXCollections.*;

/***
 * Technologies section
 * Front & Back
 */
public interface Technologies {

    /***
     * Takes - List Data & Label's title
     * Create VBox contains list view & label header ( VBox  )
     * @return VBox
     */
    Function2<ObservableList<String>, String, VBox> createView =
            (data, title) -> {

                // Container
                VBox container = (VBox) createPaneWithRule.apply(
                        setContainerStyles,
                        PaneTypes.VBOX
                );

                // Title
                Label header = createLabelWithRule.apply(
                        setLabelStyles,
                        title
                );

                // List
                ListView<String> techList = seListViewIcons.apply(observableArrayList(data));

                setListViewStyles.apply(techList);

                // Adding stuff
                container.getChildren().addAll(header, techList);

                return container;
            };


    /***
     * Create Component View
     * Takes name ( front / back )
     * Returns VBox
     */
    Function2<String, List<String>, VBox> createComponentView = (name, data) ->
            createView.apply(
                    observableArrayList(
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
