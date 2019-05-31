package com.devguide.jfx.view.UI;

import io.vavr.Function1;
import io.vavr.Function2;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


import static com.devguide.jfx.utils.BasicUtils.*;

/***
 * List View API - Functions for all kinds of list view
 */
public interface ListViewAPI {

    /**
     * Create List View with rule
     */
    Function2<Function1<ListView<String>, ListView<String>>, ObservableList<String>, ListView<String>>
        createListViewWithRule = (rule, data) -> {

        if (isNull.apply(rule) && data.isEmpty()) return new ListView<>();
        if (isNull.apply(rule) && !data.isEmpty()) return new ListView<>(data);
        if (data.isEmpty()) return rule.apply(new ListView<>());
        return rule.apply(new ListView<>(data));
    };

    /***
     * Create Simple List View without any rules
     */
    Function1<ObservableList<String>, ListView<String>> createListView =
            createListViewWithRule.apply(null);
}
