package com.devguide.jfx.view.UI;

import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Option;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;

import java.util.function.Consumer;


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
    Function4<Option<Function1<ComboBox<String>, ComboBox<String>>>,
            List<String>,
                    Tuple2<Integer, Integer>,
                    Consumer<Event>,
                    ComboBox<String>> createComboBoxWithRule =

            (rule,list, widthAndHeight, eventHandler ) -> {

                ObservableList<String> data = FXCollections.observableArrayList(list.asJava());
                ComboBox<String> comboBox = new ComboBox<>(data);
                comboBox.setMinWidth(widthAndHeight._1);
                comboBox.setMinHeight(widthAndHeight._2);
                comboBox.setOnKeyPressed( event -> eventHandler.accept(event));
                if (!rule.isEmpty()) return rule.get().apply(comboBox);
                return comboBox;
            };

}
