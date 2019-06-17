package com.devguide.jfx.view.UI;

import com.devguide.jfx.utils.BasicUtils;
import io.vavr.Function1;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

import static com.devguide.jfx.utils.StringUtils.*;

public interface PopupsAPI {


    /**
     * Get Directory as a String by choosing it!
     */
    Function1<Stage, String> createDirectoryChooser = window -> {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(window);
        if (BasicUtils.isNull.apply(selectedDirectory)) return EMPTY_STRING;
        return selectedDirectory.getAbsolutePath();
    };
}
