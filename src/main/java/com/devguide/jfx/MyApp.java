package com.devguide.jfx;

import com.devguide.jfx.view.main.MainView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


import static com.devguide.jfx.utils.StringUtils.*;


@SpringBootApplication
public class MyApp extends Application {

    private String name = "enosh";

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