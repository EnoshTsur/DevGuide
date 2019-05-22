package com.devguide.jfx.view.search;

import com.devguide.jfx.view.shared.SharedUtils;
import javafx.scene.layout.GridPane;

public class SearchBar {

    public static final GridPane getBar() {
        GridPane searchPane = new GridPane();
        searchPane.setPadding(SharedUtils.DEFAULT_INSETS);
        return  searchPane;
    }
}
