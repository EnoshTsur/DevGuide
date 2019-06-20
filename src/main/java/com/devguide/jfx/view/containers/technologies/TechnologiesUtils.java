package com.devguide.jfx.view.containers.technologies;

import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import java.util.stream.Stream;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.Colors.PRIMARY_MIDDLE;
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
     * Installations list
     */
    List<String> installations = List.of(
            "Intellij",
            "GitWorkshop",
            "GitWorkshop Shortcuts",
            "Yarn Shortcuts",
            "VS Code",
            "Web Storm",
            "Node JS",
            "Java 8",
            "Java 11"
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
    String INSTALL = "Install";

    /***
     * Header Label Styles
     */
    Function1<Label, Label> setLabelStyles = label -> {
        label.setPadding(DEFAULT_INSETS);
        setLabelFont.apply(label, HAARETZ_TITLE_FONT);
        setLabelTextColor.apply(label, TEXT_COLOR);
        return label;
    };

    /**
     * Set List View Styles
     */
    Function1<ListView<String>, ListView<String>> setListViewStyles = list -> {
        list.setMaxWidth(200);
        list.setMaxHeight(200);
        return list;
    };

    /***
     * Set Front Pane Styles
     */
    Function1<Pane, ? extends Pane> setContainerStyles =
            pane -> {
                pane.setMinWidth(280);
                setBackgroundColor.accept(pane, PRIMARY_MIDDLE);

                return pane;
            };


}