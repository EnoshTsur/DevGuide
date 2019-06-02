package com.devguide.jfx.view.components.titlebar;

import com.devguide.jfx.view.UI.BorderPaneAlignment;
import com.devguide.jfx.view.UI.PaneTypes;
import io.vavr.*;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.HashMap;
import java.util.function.Consumer;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.components.titlebar.TitleBarUtils.*;
import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.ButtonAPI.handleCloseRequest;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

/***
 * Title Bar Component
 */
public interface TitleBar {

    /***
     * Create Button by Background Image and clickHandler
     */
    Function2<String, Consumer<Event>, Button> createTitleBarButton =
            (backgroundPath, clickHandler) -> {
                Button button = createButtonWithBackground.apply(
                        backgroundPath,
                        EMPTY_STRING,
                        setButtonStyles,
                        event -> clickHandler.accept(event)
                );
                return button;
            };


    /***
     * Create Title bar view:
     * Grid Pane Container
     * @return Grid Pane
     */
    Function3<Function2<HBox, HBox, BorderPane>,
            Function1<Function1<Label, Label>, HBox>,
            HBox, BorderPane> buildComponent =

            (createContainer, createLogo, buttons) ->
                    createContainer.apply(
                            createLogo.apply(setHaaretzLogoStyles),
                            buttons

                    );


    /***
     * Create Haaretz Logo ( Label )
     * @return Label - haaretz logo
     */
    Function1<Function1<Label, Label>, HBox> createHaaretzLogo =
            styleFunction -> {

                // Container
                HBox container = (HBox) createPane.apply(PaneTypes.HBOX);

                // Logo
                Label logo = createLabelWithRule.apply(
                        styleFunction,
                        EMPTY_STRING
                );

                // Add Stuff And Return HA HA :)
                container.getChildren().add(logo);
                return container;
            };


    /***
     * Create a HBox with Close & Hide Buttons
     * @return HBox
     */
    Function1<Function2<String, Consumer<Event>, Button>,
            Function2<String, String, HBox>> createCloseAndHidePanel =
            creator -> (closePath, hidePath) -> {

                // Close Button
                Button close = creator.apply(closePath, handleCloseRequest);

                // Hide Button
                Button hide = creator.apply(hidePath, handleMinimizeRequest);

                // Pane
                HBox pane = (HBox) createPane.apply(PaneTypes.HBOX);
                pane.getChildren().addAll(hide, close);
                pane.setSpacing(DEFAULT_SPACING);

                return pane;
            };

    /***
     * Create Buttons Pane
     */
    Function2<String, String, HBox> createButtons =
            createCloseAndHidePanel.apply(
                    createTitleBarButton
            );

    /***
     * Buttons Pane
     */
    HBox buttons = createButtons.apply(
            CLOSE_LOGO_PATH,
            HIDE_LOGO_PATH
    );

    /***
     * Create Border Pane Container
     */
    Function2<HBox, HBox, BorderPane> createContainer =
            (logo, buttons) -> {
                BorderPane pane = createBorderPaneWithRule.apply(

                        container -> setContainerStyles.apply((BorderPane) container),
                        new HashMap<BorderPaneAlignment, Pane>() {{
                            put(BorderPaneAlignment.LEFT, logo);
                            put(BorderPaneAlignment.RIGHT, buttons);
                        }}
                );
                return pane;
            };


    /***
     * Title Bar View
     */
    BorderPane view = buildComponent.apply(
            createContainer,
            createHaaretzLogo,
            buttons
    );

}
