package com.devguide.jfx.view.UI;

import io.vavr.*;
import javafx.scene.control.TextField;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;

/***
 *
 * Text Field API
 */
public interface TextFieldAPI {

    /**
     * Takes :
     * Function Text Filed = Rule
     * Tuple2 Double = Size ( width, height )
     * String = Prompt Text
     * Returns:
     * New Text Field
     */
    Function3<Function1<TextField, TextField>, Tuple2<Double, Double>, String, TextField> createTextFieldWithRule
            = (rule, size, promptText) -> {

        TextField textField = new TextField();

        // Validate null || empty
        if (!doesItNullOrEmpty.test(promptText)) textField.setPromptText(promptText);

        // Set size
        textField.setMaxSize(size._1, size._2);

        if (isNull.apply(rule)) return textField;
        return rule.apply(textField);
    };

    /**
     * Takes :
     * Tuple2 Double = Size ( width, height )
     * String = Prompt Text
     * Consumer Event = On key pressed
     * Returns:
     * New Text Field
     */
    Function2<Tuple2<Double, Double>, String, TextField> createTextField = createTextFieldWithRule.apply(null);

    /***
     * Get Text by Text Field
     */
    Function1<TextField, String> getTextFieldText = input -> input.getText();

}
