package com.devguide.jfx.view.components.console;

import com.devguide.jfx.execute.Execute;
import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.utils.*;
import com.devguide.jfx.view.UI.PaneTypes;
import com.sun.org.apache.xpath.internal.compiler.Keywords;
import io.vavr.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.devguide.jfx.execute.Execute.*;
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
import static com.devguide.jfx.view.shared.SharedUtils.*;

public interface Console {


    /***
     * Set Out Put
     */
    Function2<File, String, String> setOutput = (path, command) ->
            f("{0}> {1}\n", path.getPath(), command);

    // Console State
    ConsoleState consoleState =
            new ConsoleState(OperationSystem.WINDOWS10, ShellType.CMD);

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
                line -> output.appendText(setOutput.apply(
                        consoleState
                                .getLocation
                                .get(),
                        line))
        );
    };

    /***
     *  Check out if given path is cd ../
     */
    Predicate<String> isItCDBackwards = path -> doesItEqualTo.apply(
            trimAndLower.apply(path),
            f("{0} {1}", CD, BACKWARDS)
    );

    /***
     * Check out if given path is ../
     */
    Predicate<String> isItBackwards = path -> doesItEqualTo.apply(
            trimAndLower.apply(path),
            BACKWARDS
    );

    /***
     *  Returns true if String starts with cd
     */
    Predicate<String> isStartsWithCD = command -> trimAndLower.
            apply(command)
            .startsWith(CD);

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
     * Move Backwards
     */
    Consumer3<ComboBox<String>, TextArea, String> moveBackwards =
            (input, output, command) -> {
                consoleState.navigate.accept(BACKWARDS, input);
                output.appendText(setOutput.apply(
                        consoleState
                                .getLocation
                                .get(),
                        EMPTY_STRING
                ));
            };

    /***
     * Move Backwards
     */
    Consumer3<ComboBox<String>, TextArea, String> changeDirectory =
            (input, output, command) -> {
                final String SPACE = " ";
                String[] cdAndPath = command.split(SPACE);
                String path = isNotNull.apply(cdAndPath[1])
                        ? cdAndPath[1] : EMPTY_STRING;
                // Run
                run.apply(
                        command,
                        consoleState
                                .getLocation
                                .get(),
                        consoleState
                                .getShellType
                                .get()
                );

                consoleState.navigate.accept(path, input);
                output.appendText(setOutput.apply(
                        consoleState
                                .getLocation
                                .get(),
                        EMPTY_STRING
                ));
            };


    /***
     * Change Drive
     */
    Consumer3<ComboBox<String>, TextArea, String> changeDrive =
            (input, output, command) -> {
                File drive = new File(trimAndUpper.apply(command));

                // Check existence
                if (!drive.exists()) {
                    printOutput.accept(command, output);
                    return;
                }
                // Navigate
                consoleState.navigate.accept(
                        drive.getPath(),
                        input
                );
                // Run
                run.apply(command, drive, consoleState.getShellType.get());

                // Output
                output.appendText(setOutput.apply(
                        drive,
                        EMPTY_STRING
                ));
                return;
            };


    /**
     * Returns True if commands starts with C: / D: ...
     */
    Predicate<String> isOneOfDrivers = command ->
            allDrivers.contains(trimAndLower.apply(command));


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
                if (doesItNullOrEmpty.test(command)) return;

                // Navigating Backwards
                boolean backwards = ifTrueThan.apply(
                        isItBackwards,
                        moveBackwards,
                        command,
                        input,
                        output
                );
                if (backwards) return;

                // Starts with CD
                boolean startsWithCD = ifTrueThan.apply(
                        isStartsWithCD,
                        changeDirectory,
                        command,
                        input,
                        output
                );
                if (startsWithCD) return;

                // Check if Command is Drive change
                boolean oneOfDrivers = ifTrueThan.apply(
                        isOneOfDrivers,
                        changeDrive,
                        command,
                        input,
                        output
                );
                if (oneOfDrivers) return;


                List<String> ans = run.apply(
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
                    input.getEditor().clear();
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
        output.appendText(setOutput.apply(
                consoleState
                        .getLocation
                        .get(),
                "Welcome Back!"
        ));

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
        setBackgroundColor.accept(input.getEditor(), "#0f0614");
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
        mainPane.setEffect(createShadow.apply("#0d001a"));

        return mainPane;
    };


}