package com.devguide.jfx.view.shared;

import com.devguide.jfx.view.shared.SharedUtils;
import io.vavr.Function1;
import io.vavr.collection.List;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.devguide.jfx.view.UI.ButtonAPI.CURSOR_POINTER;
import static com.devguide.jfx.view.shared.SharedUtils.*;

/***
 * Shared Styles
 */
public interface AppStylesUtils {

    int HEADER_FONT_SIZE = 23;
    String FONT_TYPE = "Verdana";

    /***
     * Font by Size
     */
    Function1<Integer, Font> haaretzFontBySize = size ->
            createFont.apply(
                    FONT_TYPE,
                    FontWeight.SEMI_BOLD,
                    size
            );

    /***
     * Haaretz header font
     */
    Font HAARETZ_HEADER_FONT = haaretzFontBySize
            .apply(HEADER_FONT_SIZE);


}
