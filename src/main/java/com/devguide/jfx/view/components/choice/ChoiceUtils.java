package com.devguide.jfx.view.components.choice;

import com.devguide.jfx.view.shared.Colors;
import com.devguide.jfx.view.shared.SharedUtils;
import io.vavr.Function1;
import io.vavr.collection.List;
import javafx.scene.layout.GridPane;
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
        container.setPrefSize(500, 300);
        addManyStyles.accept(container, List.of(
                createBgColorStyle.apply(HAARETZ_DARKEST)
        ));
        return container;
    };
}
