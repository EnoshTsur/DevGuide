package com.devguide.jfx.view.UI;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple2;
import javafx.scene.control.TextArea;

import static com.devguide.jfx.utils.BasicUtils.*;

/***
 * Text Area API
 */
public interface TextAreaAPI {


    /***
     * Create Text Area with rule
     */
    Function2<Function1<TextArea, TextArea>, Tuple2<Double, Double>, TextArea> createTextAreaWithRule
            = (rule, size) -> {
        TextArea textArea = new TextArea();

        // Size
        textArea.setPrefSize(size._1, size._2);

        // Rule
        if (isNull.apply(rule)) return textArea;
        return rule.apply(textArea);
    };

    /***
     * Create Text Area with rule
     */
    Function1<Tuple2<Double, Double>, TextArea> createTextArea = createTextAreaWithRule.curried().apply(null);
}
