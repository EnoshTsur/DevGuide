package com.devguide.jfx.view.UI;

import io.vavr.*;
import io.vavr.control.Option;
import javafx.event.Event;
import javafx.scene.control.TextField;

import java.util.function.Consumer;
import static com.devguide.jfx.utils.StringUtils.*;

/***
 *
 * Text Field API
 */
public interface TextFieldAPI {

    /**
     * Takes :
     * Option Function Text Filed = Rule
     * Tuple2 Integer = Size ( width, height )
     * String = Prompt Text
     * Consumer Event = On key pressed
     * Returns:
     * New Text Field
     */
    Function4<Option<Function1<TextField, TextField>>, Tuple2<Integer, Integer>, String, Consumer<Event>, TextField>
        createTextFieldWithRule = (ruleOption, size, promptText, eventHandler) -> {
        TextField textField = new TextField();
        if (!isEmpty.apply(promptText)) textField.setPromptText(promptText);
        textField.setMaxSize(size._1, size._2);
        textField.setOnKeyPressed( event -> eventHandler.accept(event));
        if (ruleOption.isEmpty()) return textField;
        return ruleOption.get().apply(textField);
    };

    /**
     * Takes :
     * Tuple2 Integer = Size ( width, height )
     * String = Prompt Text
     * Consumer Event = On key pressed
     * Returns:
     * New Text Field
     */
    Function3<Tuple2<Integer, Integer>, String, Consumer<Event>, TextField> createTextField =
            createTextFieldWithRule.apply(Option.none());

}
