package com.devguide.jfx.view.UI;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Option;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.function.BiConsumer;

import static com.devguide.jfx.utils.BasicUtils.isNull;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.utils.BasicUtils.*;
/***
 * Label API - Functions for all kinds of Labels
 */
public interface LabelAPI {

    /***
     * Set text if not empty
     */
    BiConsumer<Label, String> setLabelText = (label, text) -> {
        if (isEmpty.apply(text)) return;
        label.setText(text);
    };

    /***
     * Set Font if not Null
     */
    BiConsumer<Label, Font> setLabelFont = (label, font) -> {
        if (isNull.apply(font)) return;
        label.setFont(font);
    };

    /***
     * Create Label with rule
     */
    Function2<Option<Function1<Label, Label>>, String, Label> createLabelWithRule =
            ( ruleOption, text) -> {
                Label label = new Label();
                if(!isEmpty.apply(text)) label.setText(text);
                if(ruleOption.isEmpty()) return label;
                return ruleOption.get().apply(label);
            };

    /***
     * Create Label with no rule
     */
    Function1<String, Label> createLabel =
             createLabelWithRule
                    .curried()
                    .apply(Option.none());


    /***
     * Set Text Color to Label
     */
    BiConsumer<Label, String> setLabelTextColor =
            (label, color) -> label.setTextFill(Color.web(color));


}


