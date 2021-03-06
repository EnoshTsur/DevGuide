package com.devguide.jfx.view.UI;

import io.vavr.*;
import io.vavr.collection.List;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.devguide.jfx.utils.KeyBoardUtils.*;
import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.DEFAULT_INSETS;

/***
 * Alert & Pop Ups API
 */
public interface AlertAPI {



    /**
     * Header Label Styles
     */
    Function2<Label, Font, Label> setHeaderLabelStyles = (label, font) -> {
        label.setFont(font);
        setDefaultLabelPadding.apply(label);
        setLabelTextColor.apply(label, LIGHT_PINK_PURPLE);

        return label;
    };

    /***
     * Set Alert Container Styles
     */
    Function3<BorderPane, Integer, Integer, BorderPane> setContainerStyles =
            (pane, width, height) -> {
                pane.setMinWidth(width);
                pane.setMinHeight(height);
                setBackgroundColor.accept(pane, PRIMARY_DARK);
                String oldStyle = pane.getStyle();
                pane.setOnMouseEntered(event -> addStyle.accept(pane, CURSOR_MOVE));
                pane.setOnMouseExited(event -> pane.setStyle(oldStyle));
                return pane;
            };

    /**
     * Set Buttons Styles
     */
    Function1<List<Button>, List<Button>> setButtonsStyles =
            buttons -> buttons.map( button -> {
                setBackgroundColor.accept(button, DARK_PURPLE_BLUE);
                setButtonTextColor.accept(button, LIGHT_PURPLE_BLUE);
                setButtonDefaultPadding.apply(button);
                setCursorPointer.accept(button);
                return button;
            });

    /***
     * Create Message Area for alert
     */
    Function4<String, Integer, Integer, Font, HBox> createHeader =
            (text, width, height, font) -> {

                Label header = createLabelWithRule.apply(
                        label -> setHeaderLabelStyles.apply(label, font),
                        text
                );

                HBox pane = (HBox) createPane
                        // Initialize pane
                        .apply(
                                PaneTypes.HBOX
                        );

                pane.getChildren().add(header);
                pane.setAlignment(Pos.TOP_CENTER);
                return pane;
            };


    /***
     * Set Event Listeners to map of buttons and handlers
     * Takes -
     * Map ( Buttons , Handlers [ Consumer <Event> ] )
     * Returns List of Buttons
     */
    Function1<Map<Button, Consumer<Event>>, List<Button>>
            setEventsListeners = buttonAndHandler -> {
        List<Button> fixedButtons = List.ofAll(buttonAndHandler.entrySet().stream().map(
                entry -> {
                    Button current = entry.getKey();
                    Consumer<Event> handler = entry.getValue();
                    current.setOnAction(event -> handler.accept(event));
                    return current;
                }
        ).collect(Collectors.toList()));
        return fixedButtons;
    };

    /***
     * Create Buttons Container
     */
    Function3<Integer, Integer, Map<Button, Consumer<Event>>, GridPane> createButtonsContainer =
            (width, height, options) -> {

                // Container
                GridPane container = (GridPane) createPaneWithRule.apply(
                        pane -> {
                            pane.setPadding(DEFAULT_INSETS);
                            return pane;
                        },
                        PaneTypes.GRID_PANE
                );

                container.setAlignment(Pos.CENTER);

                // Buttons
                List<Button> buttons = setEventsListeners.apply(options);
                setButtonsStyles.apply(buttons);

                final int BUTTONS_START = 0;
                final int ROW_COUNT = 0;
                final int BUTTONS_END = buttons.length();

                IntStream.range(
                        BUTTONS_START,
                        BUTTONS_END
                ).forEach(index -> {
                    Button current = buttons.get(index);
                    container.getChildren().add(current);
                    GridPane.setConstraints(current, index, ROW_COUNT);
                });

                container.setHgap(10);
                container.setVgap(0);
                return container;
            };

    /***
     * Ser Alert Default Stage
     */
    Function3<Integer, Integer, BorderPane, Stage> setAlertDefaultStage =
            (width, height, container) -> {
                Stage stage;
                final double[] offsets = new double[]{width, height};
                stage = new Stage();
                stage.setWidth(width);
                stage.setHeight(height);
                stage.setResizable(false);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                setStageDraggable(container, stage, offsets, true);
                return stage;
            };

    /***
     * Set Container Components Map
     */
    Function5<Integer,
            Integer,
            String,
            String,
            Map<Button, Consumer<Event>>,
            HashMap<BorderPaneAlignment, Pane>> setComponentsMap
            = (width, height, title, text, options) ->
            new HashMap<BorderPaneAlignment, Pane>() {{

                // Top
                put(BorderPaneAlignment.TOP,
                        createHeader.apply(
                                title,
                                width,
                                height,
                                HAARETZ_HEADER_FONT
                        )
                );

                // Center
                put(BorderPaneAlignment.CENTER,
                        createHeader.apply(
                                text,
                                width,
                                height,
                                HAARETZ_HEADER_FONT
                        )
                );

                // Bottom
                put(BorderPaneAlignment.BOTTOM,
                        createButtonsContainer.apply(
                                width,
                                height,
                                options
                        )
                );

            }};




    /***************************
     * **************************
     * ** ALERT _ WITH _ BUTTONS *
     * **************************
     * *************************
     */
    Function3<String,
            String,
            Map<Button, Consumer<Event>>,
            Stage
            > createAlertWithButtons =
            (title, text, options) -> {

                // Size
                final int WIDTH = 600, HEIGHT = 300;

                // Rectangle - for radius bounds
                Rectangle rect = new Rectangle(WIDTH, HEIGHT);
                rect.setArcHeight(15);
                rect.setArcWidth(15);

                BorderPane container = createBorderPane.apply(
                        setComponentsMap.apply(
                                WIDTH, HEIGHT,
                                title, text,
                                options
                        ));


                Stage window = setAlertDefaultStage.apply(WIDTH, HEIGHT, container);
                setContainerStyles.apply(container, WIDTH, HEIGHT);
                container.setOnKeyPressed(event -> handleKeyboard.apply(event, container, window));
                container.setClip(rect);
                Scene scene = new Scene(container);
                scene.setFill(Color.TRANSPARENT);
                window.setScene(scene);

                return window;
            };

}
