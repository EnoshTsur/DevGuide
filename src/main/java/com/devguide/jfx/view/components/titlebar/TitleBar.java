package com.devguide.jfx.view.components.titlebar;

import com.devguide.jfx.view.UI.PaneTypes;
import io.vavr.Function2;
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
public class TitleBar {

    // Title
    private String title;

    /**
     * CTOR - Initialize title
     *
     * @param title
     */
    public TitleBar(String title) {
        this.title = title;
    }


    /***
     * Create Title bar view:
     * Grid Pane Container
     * @return Grid Pane
     */
    public GridPane createView() {
        return createContainer(
                createHaaretzLogo(),
                createHeaderLabel(),
                createCloseAndHidePanel()
        );
    }

    /***
     * Create Header Label
     * @return Styled Label with text
     */
    private final Label createHeaderLabel() {
        return createLabelWithRule.apply(
                Option.of(setHeaderStyles::apply),
                // title
                title);
    }

    /***
     * Create Haaretz Logo ( Label )
     * @return Label - haaretz logo
     */
    private final Label createHaaretzLogo() {
        return createLabelWithRule.apply(
                Option.of(setHaaretzLogoStyles::apply),
                EMPTY_STRING
        );
    }

    /***
     * Create a HBox with Close & Hide Buttons
     * @return HBox
     */
    public static final HBox createCloseAndHidePanel() {

        // Close Button
        Button close = createTitleBarButton
                .apply(
                        CLOSE_LOGO_PATH,
                        event -> handleCloseRequest.accept(event)
        );
        addShadow.accept(close);

        // Hide Button
        Button hide = createTitleBarButton
                .apply(
                        HIDE_LOGO_PATH,
                        event -> handleMinimizeRequest.accept(event)
                );

        // Pane
        HBox pane = (HBox) createPane.apply(PaneTypes.HBOX);
        pane.getChildren().addAll(hide, close);
        pane.setSpacing(DEFAULT_SPACING);

        return pane;
    }

    /***
     * Create Grid Pane Container
     * @param logo
     * @param title
     * @return Grid pane with all inside elements
     */
    private final GridPane createContainer(Label logo, Label title, HBox buttons) {
        GridPane pane = (GridPane) createPaneWithRule.apply(
                Option.of(
                        container -> setContainerStyles
                                .apply((GridPane) container)
                ),
                PaneTypes.GRID_PANE
        );
        pane.getChildren().addAll(logo, title, buttons);
        GridPane.setConstraints(logo, 0, 0);
        GridPane.setConstraints(title, 2, 0);
        GridPane.setConstraints(buttons, 3, 0);
        return pane;
    }


    /***
     * Create Button by Background Image and clickHandler
     */
    private static Function2<String, Consumer<Event>, Button> createTitleBarButton =
            (backgroundPath, clickHandler) -> {
                Button button = createButtonWithBackground.apply(
                        Option.of(backgroundPath),
                        EMPTY_STRING,
                        setButtonStyles,
                        event -> clickHandler.accept(event)
                );
                return button;
            };

}
