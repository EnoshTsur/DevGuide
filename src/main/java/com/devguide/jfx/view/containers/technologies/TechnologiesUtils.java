package com.devguide.jfx.view.containers.technologies;

import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.shared.Colors;
import com.devguide.jfx.view.shared.ListViewCellImageFix;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.stream.Stream;

import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.UI.PaneAPI.createPaneWithRule;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.Colors.HAARETS_LIGHTEST;
import static com.devguide.jfx.view.shared.SharedUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.DEFAULT_INSETS;
import static com.devguide.jfx.view.shared.SharedUtils.setBackgroundColor;

public interface TechnologiesUtils {


    /***
     * Front list
     */
    List<String> backTechnologies = List.of(
            "Apache Velocity",
            "Node JS",
            "Polopoly",
            "Spring Boot",
            "Vavr",
            "GraphQL"
    );

    /***
     * Front list
     */
    List<String> frontTechnologies = List.of(
            "CSS",
            "Fela",
            "Java Script",
            "HTML5",
            "React"
    );

    /***
     * All data
     */
    List<String> frontAndBack = List.ofAll(
            Stream.concat(
                    frontTechnologies.toJavaStream(),
                    backTechnologies.toJavaStream()
            )
    );

    /***
     * Fixed data by Front & Back
     */
    Tuple2<List<String>, List<String>> frontAndBackTuple =
            Tuple.of(frontTechnologies, backTechnologies);


    String FRONT = "Frontend";
    String BACK = "Backend";

    /***
     * Header Label Styles
     */
    Function1<Label, Label> setLabelStyles = label -> {
        setLabelFont.apply(label, HAARETZ_TITLE_FONT);
        setLabelTextColor.apply(label, HAARETZ_TEXT_COLOR);
        return label;
    };

    /**
     * Set List View Styles
     */
    Function1<ListView<String>, ListView<String>> setListViewStyles = list -> list;

    /***
     * Set Front Pane Styles
     */
    Function1<Pane, ? extends Pane> setContainerStyles =
            pane -> {
                pane.setPadding(DEFAULT_INSETS);
                setBackgroundColor.accept(pane, HAARETS_LIGHTEST);

                return pane;
            };

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
                ListView<String> techList = ListViewCellImageFix
                        .seListViewtIcons
                        .apply(
                                FXCollections.observableArrayList(data)
                        );
                setListViewStyles.apply(techList);

                // Adding stuff
                container.getChildren().addAll(header, techList);

                return container;
            };

}
