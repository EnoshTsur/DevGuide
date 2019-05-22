package com.devguide.jfx.view.main;

import com.devguide.jfx.view.UI.PaneAlignment;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.UI.PaneUtils;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.control.Option;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/***
 * Main Component for all UI
 */
public class MainView {

    // Singleton
    private static MainView instance = null;

    /***
     * Private CTOR - Singleton
     */
    private MainView() {}


    public final Stage getView() {
        Stage window = new Stage();

        Map<PaneAlignment, Pane> myMap = PaneUtils.buildPanes.apply(
                List.of(
                        Tuple.of(
                                PaneTypes.HBOX,
                                PaneAlignment.TOP,
                                pane -> {
                                    pane.setStyle("-fx-background-color: blue");
                                    pane.setMinWidth(500);
                                    pane.setMinHeight(500);
                                    return pane;
                                }),
                        Tuple.of(
                                PaneTypes.VBOX,
                                PaneAlignment.CENTER,
                                pane -> {
                                    pane.setStyle("-fx-background-color: yellow");
                                    pane.setMinWidth(500);
                                    pane.setMinHeight(500);
                                    return pane;
                                })
                )
        );

        BorderPane mainPane = PaneUtils.createBorderPane.apply(myMap);

        Scene scene = new Scene(mainPane);
        window.setScene(scene);
        return window;
    }

    /**
     * Get Instance - Singleton
     * @return Main View
     */
    public static final synchronized MainView getInstance() {
        return instance == null ? new MainView() : instance;
    }

}
