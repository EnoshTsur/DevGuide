package com.devguide.jfx.view.components.titlebar;


import io.vavr.Function1;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.ButtonAPI.CURSOR_MOVE;
import static com.devguide.jfx.view.UI.ButtonAPI.CURSOR_POINTER;
import static com.devguide.jfx.view.UI.LabelAPI.setLabelFont;
import static com.devguide.jfx.view.UI.LabelAPI.setLabelTextColor;
import static com.devguide.jfx.view.shared.Colors.HAARETZ_BLUE;
import static com.devguide.jfx.view.shared.Colors.WHITE;
import static com.devguide.jfx.view.shared.SharedUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.DEFAULT_INSETS;
import static com.devguide.jfx.view.shared.AppStylesUtils.HAARETZ_HEADER_FONT;

public interface TitleBarUtils {

    String HAARETZ_LOGO_PATH = "assets/haaretz_logo.jpg";
    int HAARETZ_LOGO_WIDTH = 50;
    int HAARETZ_LOGO_HEIGHT = 50;

    String CLOSE_LOGO_PATH = "assets/close_button.png";
    String HIDE_LOGO_PATH = "assets/hide_button.png";

    int HEADER_MIN_WIDTH = 360;
    int BUTTON_MIN_WIDTH = 30;
    int BUTTON_MIN_HEIGHT = 30;

    /****
     * Title bar Utils
     * Header label styles
     */
    Function1<Label, Label> setHeaderStyles = label ->  {
        // Font
        setLabelFont.accept(
                label,
                HAARETZ_HEADER_FONT
        );
        // alignment
        label.setAlignment(Pos.CENTER);

        // text color
        setLabelTextColor.accept(label, WHITE);
        // text width
        label.setMinWidth(HEADER_MIN_WIDTH);
        return label;
    };

    /****
     * Title bar Utils
     * Haaretz logo label styles
     */
    Function1<Label, Label> setHaaretzLogoStyles = label -> {
        setBackground.accept(label, HAARETZ_LOGO_PATH);
        label.setMinWidth(HAARETZ_LOGO_WIDTH);
        label.setMinHeight(HAARETZ_LOGO_HEIGHT);
        return label;
    };

    /****
     * Title bar Utils
     * Header label styles
     */
    Function1<GridPane, GridPane> setContainerStyles = container -> {
        setBackgroundColor.accept(container, HAARETZ_BLUE);
        addShadow.accept(container);
        container.setPadding(DEFAULT_INSETS);
        String oldStyle = container.getStyle();
        container.setOnMouseEntered(event -> addStyle.accept(container,CURSOR_MOVE ));
        container.setOnMouseExited(event -> container.setStyle(oldStyle));
        return container;
    };



    Consumer<Button> setButtonStyles = button -> {
        button.setPadding(DEFAULT_INSETS);
        button.setMinSize(BUTTON_MIN_WIDTH, BUTTON_MIN_HEIGHT);
        addStyle.accept(button, CURSOR_POINTER);
        final String OLD_STYLE = button.getStyle();
        button.setStyle(OLD_STYLE);
        setOnMouseEntered.accept(button);
    };
}
