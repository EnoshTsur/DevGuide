package com.devguide.jfx.view.components.choice;

import io.vavr.Function1;
import io.vavr.collection.List;
import javafx.scene.layout.Pane;

import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

/***
 * Choice Component Utils
 */
public interface ChoiceUtils {

    /***
     * Set Container Styles
     */
    Function1<Pane, ? extends Pane> setContainerStyles = container -> {
        container.setPadding(DEFAULT_INSETS);
        container.setPrefSize(700, 500);
        addManyStyles.accept(container, List.of(
                createBgColorStyle.apply(DARKEST)
        ));
        return container;
    };
}
