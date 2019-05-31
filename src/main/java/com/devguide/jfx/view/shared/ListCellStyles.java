package com.devguide.jfx.view.shared;

import io.vavr.Function1;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

import static com.devguide.jfx.view.UI.ButtonAPI.CURSOR_POINTER;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.Colors.HAARETZ_DARKEST;
import static com.devguide.jfx.view.shared.SharedUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.setBackgroundColor;

/***
 * List Cells Styles
 */
public interface ListCellStyles {

    /***
     * Set List Cell styles
     */
    Function1<ListCell<String>, ListCell<String>> setListCellsStyles =
            list -> {
                list.setFont(HAARETZ_TITLE_FONT);
                list.setTextFill(Color.web(HAARETZ_LIGHT_TEXT_COLOR));
                addStyle.accept(list, CURSOR_POINTER);
                setBackgroundColor.accept(list, HAARETZ_DARKEST);
                list.setOnMouseEntered(event -> setBackgroundColor.accept(list, HAARETZ_DARKBLUE));
                list.setOnMouseExited(event -> setBackgroundColor.accept(list, HAARETZ_DARKEST));
                return list;
            };
}
