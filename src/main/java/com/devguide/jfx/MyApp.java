package com.devguide.jfx;

import com.devguide.jfx.view.UI.PaneAlignment;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.UI.PaneUtils;
import com.devguide.jfx.view.main.MainView;
import io.vavr.control.Option;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


import java.util.HashMap;

@SpringBootApplication
public class MyApp extends Application {

    private final static MainView mainView = MainView.getInstance();

    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(MyApp.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage window = mainView.getView();
        window.show();

    }

    @Override
    public void stop() {
        springContext.stop();
    }

}