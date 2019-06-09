package com.devguide.jfx.view.components.console;

import com.devguide.jfx.execute.Execute;
import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.utils.*;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.shared.AutoCompleteComboBoxListener;
import io.vavr.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.KeyBoardUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.ComboBoxAPI.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.UI.TextAreaAPI.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.OUTPUT_HEIGHT;
import static com.devguide.jfx.view.components.search.SearchBarUtils.*;
import static com.devguide.jfx.view.shared.SharedUtils.DEFAULT_INSETS;
import static com.devguide.jfx.view.shared.SharedUtils.createToolTip;

public interface Console {

    /***
     * Set Out Put
     */
    Function2<File, String, String> setOutput = (path, command) ->
            f("{0}>{1}\n", path.getPath(), command);

    // Console State
    ConsoleState consoleState =
            new ConsoleState(OperationSystem.WINDOWS10, ShellType.CMD);

    /***
     * On Send
     */
    BiConsumer<ComboBox<String>, TextArea> onSend =
            (input, output) -> {
                // Attributes
                String command = input.getEditor().getText();
                File directory = consoleState.getLocation.get();
                ShellType shellType = consoleState.getShellType.get();

                // Appending text
                output.appendText(setOutput.apply(directory, command));

                // Checking command exits
                if (isNull.apply(command) || isEmpty.apply(command)) return;

                // Navigating Backwards
                if (doesItEqualTo.apply(trimAndLower.apply(command),
                        f("{0} {1}", CD, BACKWARDS))) {

                    consoleState.navigate.accept(BACKWARDS);
                    output.appendText(setOutput.apply(
                            consoleState
                                    .getLocation
                                    .get(),
                            EMPTY_STRING
                    ));
                    return;
                }

                if (trimAndLower.apply(command).startsWith(CD)) {
                    final String SPACE = " ";
                    String[] cdAndPath = command.split(SPACE);
                    String path = isNotNull.apply(cdAndPath[1])
                            ? cdAndPath[1] : EMPTY_STRING;
                    consoleState.navigate.accept(path);
                    output.appendText(setOutput.apply(
                            consoleState
                                    .getLocation
                                    .get(),
                            EMPTY_STRING
                    ));
                    return;
                }

                List<String> ans = Execute.run.apply(
                        command,
                        directory,
                        shellType
                );
                ans.forEach(line -> output.appendText(
                        setOutput.apply(directory, line)
                ));
            };

    /***
     * On Space
     */
    Consumer<ComboBox<String>> onSpace = input -> System.out.println("Space");

    /***
     * Keu Released Event handler
     */
    Consumer3<ComboBox<String>, TextArea, KeyEvent> handleEvent =
            (input, output, keyEvent) -> {

                if (isThisKeyIs.apply(keyEvent, KeyCode.ENTER)) {
                    onSend.accept(input, output);
                    return;
                } else if (isThisKeyIs.apply(keyEvent, KeyCode.SPACE)) {
                    onSpace.accept(input);
                }
            };

    /***
     * Output
     * Create Text Area
     */
    Function3<Function1<TextArea, TextArea>,
            Double,
            Double,
            TextArea
            > createConsoleTextArea =
            (rule, width, height) ->
                    createTextAreaWithRule.apply(
                            rule,
                            Tuple.of(width, height)

                    );

    /***
     * View
     */
    Supplier<VBox> view = () -> {

        File dir = new File(USER_HOME);
        String initialMessage = f("{0}>", dir.getPath());

        // Output
        TextArea output = createConsoleTextArea.apply(
                textArea -> {
                    textArea.setEditable(false);
                    return textArea;
                },
                OUTPUT_WIDTH,
                OUTPUT_HEIGHT

        );

        // Input
        ComboBox<String> input = createComboBoxWithRule
                .apply(
                        combobox -> setComboBoxStyles.apply(
                                combobox,
                                createToolTip.apply(
                                        ConsoleUtils.INPUT_MESSAGE
                                )
                        ),
                        basicCommands,
                        Tuple.of(
                                INPUT_WIDTH,
                                INPUT_HEIGHT
                        ),
                        null
                );
        input.setPromptText(initialMessage);
        input.setOnKeyPressed(event -> input.hide());
        input.setOnKeyReleased(
                event -> handleEvent.accept(
                        input,
                        output,
                        event
                )
        );

        // Container
        VBox mainPane = (VBox) createPaneWithRule.apply(
                pane -> {
                    pane.setMaxSize(
                            OUTPUT_WIDTH,
                            OUTPUT_HEIGHT + INPUT_HEIGHT
                    );
                    pane.setPadding(DEFAULT_INSETS);
                    return pane;
                },
                PaneTypes.VBOX
        );
        mainPane.getChildren().addAll(output, input);

        return mainPane;
    };

}