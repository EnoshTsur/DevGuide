package com.devguide.jfx.view.components.main;

import com.devguide.jfx.view.components.footer.FooterView;
import com.devguide.jfx.view.components.titlebar.TitleBar;
import com.devguide.jfx.view.UI.BorderPaneAlignment;
import com.devguide.jfx.view.containers.main.MainContainer;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.components.main.MainViewUtils.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

/***
 * Main Component for all UI
 */
public class MainView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    private final static double[] offsets = {xOffset, yOffset};

    // Singleton
    private static MainView instance = null;

    // Components
    private final FooterView footerBar;

    /***
     * Private CTOR - Singleton
     */
    private MainView() {
        footerBar = new FooterView();
    }


    /***
     * Application Main View
     * @return Stage
     */
    public final Stage getView() {
        // Window
        Stage window = buildWindow();

        // Title Bar
        GridPane titleView = TitleBar.view;

        // Main Container
        BorderPane mainView = MainContainer.view;

        // Footer
        HBox footerView = footerBar.createFooter();

        // Main Pane
        BorderPane mainPane = createBorderPane.apply(

                new HashMap<BorderPaneAlignment, Pane>() {{
                    put(BorderPaneAlignment.TOP, titleView);
                    put(BorderPaneAlignment.CENTER, mainView);
                    put(BorderPaneAlignment.BOTTOM,footerView);
                }}
        );

        addStyle.accept(mainPane, "-fx-background-color: transparent;");
        addStyle.accept(mainPane, f("-fx-border-color: {0}", HAARETZ_BLUE));
        mainPane.setOnKeyPressed(e -> handleKeyboard.apply(e, mainPane, window));
        Scene scene = new Scene(mainPane);
        scene.setFill(Color.TRANSPARENT);
        setStageDraggable(mainPane, window, offsets, true);
        window.setScene(scene);
         return window;
    }

    /**
     * Get Instance - Singleton
     *
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
        window.initStyle(StageStyle.TRANSPARENT);
        return window;
    }
}
