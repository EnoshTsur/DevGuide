package com.devguide.jfx.view.UI;

import com.devguide.jfx.utils.Consumer3;
import com.devguide.jfx.utils.StringUtils;
import javafx.concurrent.Task;
import javafx.scene.layout.Region;


import javax.swing.text.html.ImageView;

import java.util.function.BiConsumer;

import static com.devguide.jfx.view.shared.SharedUtils.*;

public interface LoadingAPI {

    // Animation path
    String LOADING_LOGO_PATH = "assets/loading.gif";

    // Set Original Background again
    BiConsumer<Region, String> setOriginalBackgroundBack = (javafxObject, originalPath) ->
            setBackground.accept(javafxObject, StringUtils.f("assets/{0}", originalPath));

    // Animate while doing mission
    Consumer3<Region, String, Task<Void>> animateLoading = (javafxObject, originalPath, mission) -> {
        setBackground.accept(javafxObject, LOADING_LOGO_PATH);

        new Thread(mission).start();
        mission.setOnFailed( event -> setOriginalBackgroundBack.accept(javafxObject, originalPath));
        mission.setOnSucceeded( event -> setOriginalBackgroundBack.accept(javafxObject, originalPath));
    };



}
