package com.devguide.jfx.view.components.console;

import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.devguide.jfx.view.UI.TextFieldAPI.*;


public class Console extends BorderPane {
    protected final TextArea textArea = new TextArea();
    protected final TextField textField = new TextField();

    protected final List<String> history = new ArrayList<>();
    protected int historyPointer = 0;

    private Consumer<String> onMessageReceivedHandler;

    private Consumer<Runnable> runSafe = runnable -> {
        Objects.requireNonNull(runnable, "runnable");
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        }
        else {
            Platform.runLater(runnable);
        }
    };

    /***
     * History Attributes
     */
    private Supplier<Tuple2<Integer, List<String>>> historyAttributes = () ->
            Tuple.of(0, new ArrayList<>());

    Function1<TextField, TextField> setOnKeyPressed = (input) -> {
        input.setOnKeyPressed( event -> {


            // History attributes
            Tuple2<Integer, List<String>> history =
                    historyAttributes.get();

            // Get Text
            String text = getTextFieldText.apply(input);
            input.appendText(text + System.lineSeparator());


            switch (event.getCode()) {
                history.add(text);
                historyPointer++;
                if (onMessageReceivedHandler != null) {
                    onMessageReceivedHandler.accept(text);
                }
                textField.clear();
                break;
                case UP:
                    if (historyPointer == 0) {
                        break;
                    }
                    historyPointer--;
                    runSafe.accept(() -> {
                        textField.setText(history.get(historyPointer));
                        textField.selectAll();
                    });
                    break;
                case DOWN:
                    if (historyPointer == history.size() - 1) {
                        break;
                    }
                    historyPointer++;
                    runSafe.accept(() -> {
                        textField.setText(history.get(historyPointer));
                        textField.selectAll();
                    });
                    break;
                default:
                    break;
            }
        });
    };

    public Console() {
        textArea.setEditable(false);
        setCenter(textArea);
        setMinWidth(470);
        setMinHeight(340);
        setFocused(false);

        textField.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    String text = textField.getText();
                    textArea.appendText(text + System.lineSeparator());
                    history.add(text);
                    historyPointer++;
                    if (onMessageReceivedHandler != null) {
                        onMessageReceivedHandler.accept(text);
                    }
                    textField.clear();
                    break;
                case UP:
                    if (historyPointer == 0) {
                        break;
                    }
                    historyPointer--;
                    runSafe.accept(() -> {
                        textField.setText(history.get(historyPointer));
                        textField.selectAll();
                    });
                    break;
                case DOWN:
                    if (historyPointer == history.size() - 1) {
                        break;
                    }
                    historyPointer++;
                    runSafe.accept(() -> {
                        textField.setText(history.get(historyPointer));
                        textField.selectAll();
                    });
                    break;
                default:
                    break;
            }
        });
        setBottom(textField);
    }

}