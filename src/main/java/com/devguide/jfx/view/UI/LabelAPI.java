package com.devguide.jfx.view.UI;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple4;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.BasicUtils.isNull;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

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
                if (isNotEmpty.apply(text)) label.setText(text);
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


    /***
     * Set default padding for label
     */
    Function1<Label, Label> setDefaultLabelPadding = label ->  {
        label.setPadding(DEFAULT_INSETS);
        return label;
    };

    /**
     * Set padding by size
     */
    Function2<Label,
            Tuple4<Double, Double, Double, Double>,
            Label> setLabelPadding = (label, insets) -> {
        double up = doubleOrZero.apply(insets._1);
        double right = doubleOrZero.apply(insets._2);
        double down = doubleOrZero.apply(insets._3);
        double left = doubleOrZero.apply(insets._4);
        label.setPadding(new Insets(up, right, down, left));
        return label;
    };


}


