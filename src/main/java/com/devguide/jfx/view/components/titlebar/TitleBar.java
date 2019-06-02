package com.devguide.jfx.view.components.titlebar;

import com.devguide.jfx.view.UI.PaneTypes;
import io.vavr.*;
import io.vavr.control.Option;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


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
    Function4<Function3<Label, Label, HBox, GridPane>,
            Function1<Function1<Label, Label>, Label>,
            Function2<Function1<Label, Label>, String, Label>,
            HBox, GridPane> buildComponent =

            (createContainer, createLogo, createHeader, buttons) ->
                    createContainer.apply(
                            createLogo.apply(setHaaretzLogoStyles),
                            createHeader.apply(setHeaderStyles, APP_NAME),
                            buttons

                    );

    // title
    /***
     * Create Header Label
     * @return Styled Label with text
     */
    Function2<Function1<Label, Label>, String, Label> createHeaderLabel =
            createLabelWithRule;

    /***
     * Create Haaretz Logo ( Label )
     * @return Label - haaretz logo
     */
    Function1<Function1<Label, Label>, Label> createHaaretzLogo =
            styleFunction -> createLabelWithRule.apply(
                    styleFunction,
                    EMPTY_STRING
            );


    /***
     * Creator for button
     */
    Function2<String, Consumer<Event>, Button> createFunction =
            createTitleBarButton;

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
                    createFunction
            );

    /***
     * Buttons Pane
     */
    HBox buttons = createButtons.apply(
            CLOSE_LOGO_PATH,
            HIDE_LOGO_PATH
    );

    /***
     * Create Grid Pane Container
     */
    Function3<Label, Label, HBox, GridPane> createContainer =
            (logo, title, buttons) -> {
                GridPane pane = (GridPane) createPaneWithRule.apply(

                        container -> setContainerStyles.apply((GridPane) container),
                        PaneTypes.GRID_PANE
                );
                pane.getChildren().addAll(logo, title, buttons);
                GridPane.setConstraints(logo, 0, 0);
                GridPane.setConstraints(title, 2, 0);
                GridPane.setConstraints(buttons, 3, 0);
                return pane;
            };


    /***
     * Title Bar View
     */
    GridPane view = buildComponent.apply(
            createContainer,
            createHaaretzLogo,
            createHeaderLabel,
            buttons
    );

}
