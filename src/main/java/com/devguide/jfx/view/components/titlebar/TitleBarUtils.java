package com.devguide.jfx.view.components.titlebar;


import com.devguide.jfx.view.UI.PaneAPI;
import io.vavr.Function1;
import io.vavr.Tuple;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.function.Consumer;

import static com.devguide.jfx.view.UI.ButtonAPI.*;
import static com.devguide.jfx.view.UI.ButtonAPI.CURSOR_MOVE;
import static com.devguide.jfx.view.UI.ButtonAPI.CURSOR_POINTER;
import static com.devguide.jfx.view.UI.LabelAPI.setLabelFont;
import static com.devguide.jfx.view.UI.LabelAPI.setLabelTextColor;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.DEFAULT_INSETS;
import static com.devguide.jfx.view.shared.SharedUtils.HAARETZ_HEADER_FONT;

public interface TitleBarUtils {

    String HAARETZ_LOGO_PATH = "assets/Haaretz.svg.png";
    int HAARETZ_LOGO_WIDTH = 50;
    int HAARETZ_LOGO_HEIGHT = 50;

    String CLOSE_LOGO_PATH = "assets/close_button.png";
    String HIDE_LOGO_PATH = "assets/hide_button.png";

    int HEADER_MIN_WIDTH = 560;
    int BUTTON_MAX_WIDTH = 40;
    int BUTTON_MAX_HEIGHT = 40;

    /****
     * Title bar Utils
     * Header label styles
     */
    Function1<Label, Label> setHeaderStyles = label ->  {
        // Font
        setLabelFont.apply(
                label,
                HAARETZ_HEADER_FONT
        );
        // alignment
        label.setAlignment(Pos.CENTER);

        // text color
        setLabelTextColor.apply(label, LIGHT_PINK_PURPLE);
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
    Function1<BorderPane, BorderPane> setContainerStyles = container -> {
        setBackgroundLinearGradient.apply(container, PRIMARY, PRIMARY_DARK);
        addShadow.accept(container);
        PaneAPI.setPanePadding.apply(container, Tuple.of(0.0,5.0, 0.0, 5.0));
        String oldStyle = container.getStyle();
        container.setOnMouseEntered(event -> addStyle.accept(container,CURSOR_MOVE ));
        container.setOnMouseExited(event -> container.setStyle(oldStyle));
        return container;
    };


    /**
     * Buttons Styles ( Size, Padding, Pointer,  )
     */
    Consumer<Button> setButtonStyles = button -> {
        button.setPadding(DEFAULT_INSETS);
        button.setMaxSize(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT);
        addStyle.accept(button, CURSOR_POINTER);
        final String OLD_STYLE = button.getStyle();
        button.setStyle(OLD_STYLE);
        setCursorPointer.accept(button);
    };
}
