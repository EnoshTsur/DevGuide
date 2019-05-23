package com.devguide.jfx.view.main;

import com.devguide.jfx.view.TitleBar.TitleBar;
import com.devguide.jfx.view.UI.PaneAlignment;
import com.devguide.jfx.view.UI.PaneAPI;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

import static com.devguide.jfx.view.main.MainViewUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.APP_NAME;

/***
 * Main Component for all UI
 */
public class MainView {

    // Singleton
    private static MainView instance = null;

    // Components
    private final TitleBar titleBar;

    /***
     * Private CTOR - Singleton
     */
    private MainView() {
        titleBar = new TitleBar(APP_NAME);
    }


    public final Stage getView() {
        // Window
        Stage window = buildWindow();

        // Title Bar
        GridPane titleView = titleBar.createView();

        // Main Pane
        BorderPane mainPane = PaneAPI.createBorderPane.apply(
                new HashMap<PaneAlignment, Pane>(){{
                    put(PaneAlignment.TOP, titleView);
                }}
        );


        Scene scene = new Scene(mainPane);
        window.setScene(scene);
        return window;
    }

    /**
     * Get Instance - Singleton
     * @return Main View
     */
    public static final synchronized MainView getInstance() {
        return instance == null ? new MainView() : instance;
    }


    /***
     *  Initialize Stage Window
     * @return Stage Window
     */
    private Stage buildWindow() {
        Stage window = new Stage();
        window.setWidth(WIDTH);
        window.setHeight(HEIGHT);
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);
        return window;
    }
}
