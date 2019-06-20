package com.devguide.jfx.view.components.console;

import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.utils.*;
import com.devguide.jfx.view.UI.PaneTypes;
import io.vavr.*;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;
import java.util.function.*;

import static com.devguide.jfx.execute.Execute.*;
import static com.devguide.jfx.execute.ShellType.*;
import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.FileSystem.getLastFolder;
import static com.devguide.jfx.utils.KeyBoardUtils.*;
import static com.devguide.jfx.utils.OperationSystem.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.ComboBoxAPI.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.UI.TextAreaAPI.*;
import static com.devguide.jfx.view.components.console.ConsoleActions.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.*;
import static com.devguide.jfx.view.components.search.SearchBarUtils.*;
import static com.devguide.jfx.view.shared.Colors.COMBO_DARK_PURPLE;
import static com.devguide.jfx.view.shared.SharedUtils.*;

public interface ConsoleView {

    // ConsoleView State
    ConsoleState consoleState = isMyOperationSystem.test(WINDOWS10) ?
            new ConsoleState(WINDOWS10, CMD, gitCommands.asJava()) :
            new ConsoleState(LINUX, BASH, gitCommands.asJava());



    /***
     * Set Output content
     */
    Function2<File, String, String> setOutputContent = (path, command) ->
            f("{0}> {1}\n", getLastFolder.apply(path), command);

    /***
     * Set Multiple lines content
     */
    BiConsumer<io.vavr.collection.List<String>, TextArea> setMultiLinesOutput =
            (lines, output) -> lines.forEach(line -> output.appendText(
                    setOutputContent.apply(consoleState.getLocation.get(), line))
            );

    /***
     * Set output after execution
     */
    BiConsumer<TextArea, String> setOutputAfterExecution =
            (output, command) -> {
                File directory = consoleState.getLocation.get();
                ShellType shellType = consoleState.getShellType.get();
                Task<List<String>> task = new Task<List<String>>() {
                    @Override
                    protected List<String> call() {
                        return run.apply(
                                command,
                                directory,
                                shellType
                        );
                    }
                };
                Thread exe = new Thread(task);
                task.setOnSucceeded(t -> task.getValue().forEach(line ->
                        output.appendText(setOutputContent.apply(directory, line)))
                );
                task.setOnFailed(t -> output.appendText(task.getException().getMessage()));
                exe.start();
            };

    /***
     * Set output after execution
     */
    Consumer3<TextArea, String, Runnable> setOutputAndRunAfterExecution =
            (output, command, action) -> {
                File directory = consoleState.getLocation.get();
                ShellType shellType = consoleState.getShellType.get();
                Task<List<String>> task = new Task<List<String>>() {
                    @Override
                    protected List<String> call() {
                        return run.apply(
                                command,
                                directory,
                                shellType
                        );
                    }
                };
                Thread exe = new Thread(task);

                task.setOnSucceeded(event -> {
                            task.getValue().forEach(line -> output.appendText(setOutputContent.apply(directory, line)));
                            action.run();
                        });

                task.setOnFailed(t -> output.appendText(task.getException().getMessage()));
                exe.start();
            };

    /***
     * Run Command
     */
    Function1<String, List<String>> runCommand = command -> {
        File currentPath = consoleState.getLocation.get();
        ShellType shellType = consoleState.getShellType.get();

        return run.apply(
                command,
                currentPath,
                shellType
        );
    };

    /***
     * Print Output to the Text Area
     */
    BiConsumer<String, TextArea> printOutput = (command, output) -> {
        runCommand.apply(command).forEach(
                line -> output.appendText(setOutputContent.apply(
                        consoleState
                                .getLocation
                                .get(),
                        line))
        );
    };


    /***
     * Checks if condition returns true by command and act
     */
    Function5<Predicate<String>,
            Consumer3<ComboBox<String>,
                    TextArea,
                    String
                    >,
            String,
            ComboBox<String>,
            TextArea,
            Boolean
            > ifTrueThan =
            (condition, handler, command, input, output) -> {
                boolean pass = condition.test(command);
                if (pass) handler.accept(input, output, command);
                return pass;
            };


    /***
     * On Send
     */
    BiConsumer<ComboBox<String>, TextArea> onEnter =
            (input, output) -> {
                // Attributes
                String command = input.getEditor().getText();
                File directory = consoleState.getLocation.get();

                consoleState.updateState.accept(command);

                // Appending text
                output.appendText(setOutputContent.apply(directory, command));

                // Checking command exits
                if (doesItNullOrEmpty.test(command)) return;

                if (isOneOfActions.apply(command, input, output)) return;

                setOutputAfterExecution.accept(output, command);
            };


    /***
     * On Space
     */
    Consumer<ComboBox<String>> onUp = input -> {
        input.show();
    };

    /***
     * Key Released Event handler
     */
    Consumer3<ComboBox<String>, TextArea, KeyEvent> handleEvent =
            (input, output, keyEvent) -> {

                if (isThisKeyIs.apply(keyEvent, KeyCode.ENTER)) {
                    onEnter.accept(input, output);
                    input.getEditor().clear();
                    return;
                } else if (isThisKeyIs.apply(keyEvent, KeyCode.UP)) {
                    onUp.accept(input);
                    return;
                } else {

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
     * Create ConsoleView Output ( Text Area )
     */
    Supplier<TextArea> createConsoleOutput = () -> {
        // Output
        TextArea output = createConsoleTextArea.apply(
                textArea -> {
                    textArea.setEditable(false);
                    return textArea;
                },
                OUTPUT_WIDTH,
                OUTPUT_HEIGHT

        );
        output.appendText(setOutputContent.apply(
                consoleState
                        .getLocation
                        .get(),
                greetings.apply(null)
        ));
        output.selectPositionCaret(output.getLength());
        return output;
    };

    /***
     * Create ConsoleView Input
     */
    Function1<TextArea, ComboBox<String>> createConsoleInput = output -> {
        File dir = new File(USER_HOME);
        String initialMessage = f("{0}>", dir.getPath());
        ComboBox<String> input = createComboBoxWithRule
                .apply(
                        combobox -> setComboBoxStyles.apply(
                                combobox,
                                createToolTip.apply(
                                        ConsoleUtils.INPUT_MESSAGE
                                )
                        ),
                        gitCommands,
                        Tuple.of(
                                INPUT_WIDTH,
                                INPUT_HEIGHT
                        ),
                        null
                );
        setBackgroundColor.accept(input.getEditor(), COMBO_DARK_PURPLE);
        // ConsoleView State
        consoleState.initState.accept(input, output);
        input.setPromptText(initialMessage);
        input.setOnKeyPressed(event -> input.hide());
        input.setOnKeyReleased(
                event -> handleEvent.accept(
                        input,
                        output,
                        event
                )
        );
        return input;
    };

    /***
     * Create ConsoleView Container
     */
    Function2<ComboBox<String>, TextArea, VBox> createConsoleContainer =
            (input, output) -> {
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
                mainPane.setEffect(createShadow.apply(COMBO_DARK_PURPLE));
                return mainPane;
            };

    /***
     * View
     */
    Supplier<VBox> view = () -> {

        // Output
        TextArea output = createConsoleOutput.get();

        // Input
        ComboBox<String> input = createConsoleInput.apply(output);

        // Container
        VBox mainPane = createConsoleContainer.apply(input, output);

        return mainPane;
    };


}