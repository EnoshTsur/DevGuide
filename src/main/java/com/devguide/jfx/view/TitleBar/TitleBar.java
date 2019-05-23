package com.devguide.jfx.view.TitleBar;

import com.devguide.jfx.utils.StringUtils;
import com.devguide.jfx.view.UI.LabelAPI;
import com.devguide.jfx.view.UI.PaneAlignment;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.shared.SharedUtils;
import io.vavr.collection.List;
import io.vavr.control.Option;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.FontWeight;

import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.TitleBar.TitleBarUtils.*;
import static com.devguide.jfx.view.UI.LabelAPI.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

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
        return createContainer( createHaaretzLogo(), createHeaderLabel());
    }

    /***
     * Create Header Label
     * @return Styled Label with text
     */
    private final Label createHeaderLabel() {
        return LabelAPI.createLabelWithRule.apply(
                Option.of(label -> {

                    // font
                    setLabelFont.accept(
                            label,
                            createFont.apply(
                                    FONT_TYPE,
                                    FontWeight.SEMI_BOLD,
                                    HEADER_FONT_SIZE
                            )
                    );

                    // alignment
                    label.setAlignment(Pos.CENTER);

                    // text color
                    setTextColor.accept(label, WHITE);
                    return label;
                }),
                // title
                title);
    }

    /***
     * Create Haaretz Logo ( Label )
     * @return Label - haaretz logo
     */
    private final Label createHaaretzLogo() {
        return createLabelWithRule.apply(
                Option.of(label -> {
                    setBackground.accept(label, HAARETZ_LOGO_PATH);
                    label.setMinWidth(50);
                    label.setMinHeight(50);
                    return label;
                }),
                EMPTY_STRING
        );
    }

    public static final HBox createCloseAndHidePanel() {
        HBox pane = (HBox) createPane.apply(PaneTypes.HBOX);
        return pane;
    }

    /***
     * Create Grid Pane Container
     * @param logo
     * @param title
     * @return Grid pane with all inside elements
     */
    private final GridPane createContainer(Label logo, Label title) {
        GridPane pane = (GridPane) createPaneWithRule.apply(

                Option.of(bar -> {
                    setBackgroundColor.accept(bar, HAARETZ_BLUE);
                    bar.setPadding(DEFAULT_INSETS);
                    bar.getChildren().addAll(logo, title);
                    return bar;
                }),

                PaneTypes.GRID_PANE
        );

        GridPane.setConstraints(logo, 0, 0);
        GridPane.setConstraints(title, 2, 0);
        return pane;
    }

}
