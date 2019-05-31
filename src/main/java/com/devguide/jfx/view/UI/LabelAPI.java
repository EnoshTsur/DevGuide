package com.devguide.jfx.view.UI;

import io.vavr.Function1;
import io.vavr.Function2;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import static com.devguide.jfx.utils.BasicUtils.isNull;
import static com.devguide.jfx.utils.StringUtils.*;

/***
 * Label API - Functions for all kinds of Labels
 */
public interface LabelAPI {

    /***
     * Set text if not empty
     */
    Function2<Label, String, Label> setLabelText = (label, text) -> {
        if (isEmpty.apply(text)) return label;
        label.setText(text);
        return label;
    };

    /***
     * Set Font if not Null
     */
    Function2<Label, Font, Label> setLabelFont = (label, font) -> {
        if (isNull.apply(font)) return label;
        label.setFont(font);
        return label;
    };

    /***
     * Create Label with rule
     */
    Function2<Function1<Label, Label>, String, Label> createLabelWithRule =
            (ruleOption, text) -> {
                Label label = new Label();
                if (!isEmpty.apply(text)) label.setText(text);
                if (isNull.apply(ruleOption)) return label;
                return ruleOption.apply(label);
            };

    /***
     * Create Label with no rule
     */
    Function1<String, Label> createLabel =
            createLabelWithRule
                    .curried()
                    .apply(null);


    /***
     * Set Text Color to Label
     */
    Function2<Label, String, Label> setLabelTextColor =
            (label, color) -> {
                label.setTextFill(Color.web(color));
                return label;
            };


}


