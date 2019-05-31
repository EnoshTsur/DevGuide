package com.devguide.jfx;

import com.devguide.jfx.view.components.main.MainView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
        System.out.println(javafx.scene.text.Font.getFamilies());
//        Execute.run.apply("git s", new File("/home/ibo1.com/enosh.tsur"), ShellType.BASH);
    }

    @Override
    public void stop() {
        springContext.stop();
    }

}