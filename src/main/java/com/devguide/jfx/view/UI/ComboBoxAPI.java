package com.devguide.jfx.view.UI;

import com.devguide.jfx.utils.BasicUtils;
import io.vavr.*;
import io.vavr.collection.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;

import java.util.function.Consumer;

import static com.devguide.jfx.utils.BasicUtils.*;


/***
 * ComboBox API - Functions for all kinds of combo box
 */
public interface ComboBoxAPI {

    /**
     * Takes :
     * Option Function Combo Box = Rule
     * Tuple2 Integer = Size ( width, height )
     * Consumer Event = On key pressed
     * Returns:
     * New Combo Box
     */
    Function4<Function1<ComboBox<String>, ComboBox<String>>,
            List<String>,
                    Tuple2<Double, Double>,
                    Consumer<KeyEvent>,
                    ComboBox<String>> createComboBoxWithRule =

            (rule,list, widthAndHeight, eventHandler ) -> {

                ObservableList<String> data = FXCollections.observableArrayList(list.asJava());
                ComboBox<String> comboBox = new ComboBox<>(data);
                comboBox.setMinWidth(widthAndHeight._1);
                comboBox.setMinHeight(widthAndHeight._2);
                if (isNotNull.apply(eventHandler))comboBox
                        .setOnKeyPressed( event -> eventHandler.accept(event));
                if (isNotNull.apply(rule)) return rule.apply(comboBox);
                return comboBox;
            };

    /***
     * Get Combo box editor text
     */
    Function1<ComboBox, String> getComboEditorText = comboBox ->
            comboBox.getEditor().getText();

    /**
     * Get Caret Position
     */
    Function1<ComboBox<String>, Integer> getCaretPoisition =
            input -> input.getEditor().getCaretPosition();

}
