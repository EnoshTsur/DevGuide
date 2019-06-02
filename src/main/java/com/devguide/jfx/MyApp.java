package com.devguide.jfx;

import com.devguide.jfx.ioutils.FileSystem;
import com.devguide.jfx.view.UI.AlertAPI;
import com.devguide.jfx.view.UI.ButtonAPI;
import com.devguide.jfx.view.components.main.MainView;
import io.vavr.collection.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
        Stage alert = createAlertWithButtons.apply(
                "Operation System:",
                FileSystem.getOperationSystem(),

                new HashMap<Button, Consumer<Event>>() {{
                    put(new Button("Close"), handleCloseRequest);
                    put(new Button("Hide"), handleMinimizeRequest);
                }}
        );

        alert.showAndWait();


        Stage window = mainView.getView();
        window.show();
        System.out.println(javafx.scene.text.Font.getFamilies());
        System.out.println(FileSystem.getOperationSystem());
//        Execute.run.apply("git s", new File("/home/ibo1.com/enosh.tsur"), ShellType.BASH);
    }

    @Override
    public void stop() {
        springContext.stop();
    }

}