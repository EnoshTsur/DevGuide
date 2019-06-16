package com.devguide.jfx;

import com.devguide.jfx.utils.FileSystem;
import com.devguide.jfx.view.components.main.MainView;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.function.Consumer;

import static com.devguide.jfx.view.UI.AlertAPI.*;
import static com.devguide.jfx.view.UI.ButtonAPI.*;

/***
 * Main
 */
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