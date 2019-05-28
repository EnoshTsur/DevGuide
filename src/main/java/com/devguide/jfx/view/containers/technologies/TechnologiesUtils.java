package com.devguide.jfx.view.containers.technologies;

import io.vavr.Function1;
import io.vavr.collection.List;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import static com.devguide.jfx.view.shared.Colors.HAARETS_LIGHTEST;
import static com.devguide.jfx.view.shared.SharedUtils.DEFAULT_INSETS;
import static com.devguide.jfx.view.shared.SharedUtils.setBackgroundColor;

public interface TechnologiesUtils {

    int CONTAINER_MAX_WIDTH = 180;
    int CONTAINER_MAX_HEIGHT = 200;

    /**
     * Set List View Styles
     */
    Function1<ListView<String>, ListView<String>> setListViewStyles = list -> {
        list.setPrefSize(300, 300);
        return list;
    };

    /***
     * Set Front Pane Styles
     */
    Function1<Pane, ? extends Pane> setContainerStyles =
            pane -> {
                pane.setPadding(DEFAULT_INSETS);
                setBackgroundColor.accept(pane, HAARETS_LIGHTEST);
                pane.setMaxHeight(CONTAINER_MAX_HEIGHT);
                pane.setMaxWidth(CONTAINER_MAX_WIDTH);
                return pane;
            };
}
