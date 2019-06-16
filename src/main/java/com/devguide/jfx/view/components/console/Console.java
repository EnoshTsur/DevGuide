package com.devguide.jfx.view.components.console;

import com.devguide.jfx.browsers.pages.frontend.FrontendPage;
import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.utils.*;
import com.devguide.jfx.view.UI.PaneTypes;
import com.google.common.primitives.Booleans;
import io.vavr.*;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import static com.devguide.jfx.view.components.console.Commands.*;
import static com.devguide.jfx.view.components.console.ConsoleActions.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.*;
import static com.devguide.jfx.view.components.search.SearchBarUtils.*;
import static com.devguide.jfx.view.shared.Colors.COMBO_DARK_PURPLE;
import static com.devguide.jfx.view.shared.SharedUtils.*;

public interface Console {

    // Console State
    ConsoleState consoleState = isMyOperationSystem.test(WINDOWS10) ?
            new ConsoleState(WINDOWS10, CMD, gitCommands.asJava()) :
            new ConsoleState(LINUX, BASH, gitCommands.asJava());

    /***
     * Set Output content
     */
    Function2<File, String, String> setOutputContent = (path, command) ->
            f("{0}> {1}\n", getLastFolder.apply(path), command);

    /***
     * Set output after execution
     */
    BiConsumer<TextArea, String> setOutPutAfterExecution =
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
                consoleState.navigate.apply(BACKWARDS, input);
                setOutPutAfterExecution.accept(output, command);
            };

    /***
     * Move Backwards
     */
    Consumer3<ComboBox<String>, TextArea, String> changeDirectory =
            (input, output, command) -> {
                String[] cdAndPath = command.split(SPACE);
                String path = isNotNull.apply(cdAndPath[1])
                        ? cdAndPath[1] : EMPTY_STRING;

                // Get a new location
                String location = consoleState
                        .navigate
                        .apply(path, input);

                // Assign location to variable
                File dir = new File(location);

                // Append output
                output.appendText(setOutputContent.apply(dir, EMPTY_STRING));

                // Run
                run.apply(command, dir, consoleState.getShellType.get());
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
                consoleState.navigate.apply(
                        drive.getPath(),
                        input
                );
                // Run
                run.apply(command, drive, consoleState.getShellType.get());
                // Output
                output.appendText(setOutputContent.apply(
                        drive,
                        EMPTY_STRING
                ));
                return;
            };

    /***
     * Change Drive
     */
    Consumer3<ComboBox<String>, TextArea, String> clearScreen =
            (input, output, command) -> output.setText(
                    setOutputContent.apply(consoleState.getLocation.get(), EMPTY_STRING)
            );

    /***
     * Change console colors
     */
    Consumer3<ComboBox<String>, TextArea, String> changeColor =
            (input, output, command) -> {
                String[] commandAndColor = command.split(SPACE);

                // Getting text color
                Try<String> textColor = Try.of(() -> commandAndColor[1]);

                // Painting text color
                if (!textColor.isEmpty()) setTextColor.accept(output, textColor.get());

            };

    /***
     * Closing the app
     */
    Consumer3<ComboBox<String>, TextArea, String> closeProgram =
            (input, output, command) -> output.getScene().getWindow().hide();

    /***
     * Do Login -> navigate to site and send keys in order to login
     */
    Consumer3<ComboBox<String>, TextArea, String> doLogin =
            (input, output, command) -> FrontendPage.login.accept(
                    "https://frontendmasters.com/login/",
                    "Eran.Meshulam",
                    "EM1234"
            );


    /**
     * Returns True if commands starts with C: / D: ...
     */
    Predicate<String> isOneOfDrivers = command ->
            allDrivers.contains(trimAndLower.apply(command));

    /***
     * Returns True if command equals to cls / clear
     */
    Predicate<String> isClearOrCls = command ->
            doesItEqualTo.apply(trimAndLower.apply(command), CLEAR) ||
                    doesItEqualTo.apply(trimAndLower.apply(command), CLS);

    /***
     * Returns True if command starts with color
     */
    Predicate<String> isColorChange = command ->
            trimAndLower.apply(command).startsWith("color");

    /***
     * Returns True if command is equals to exit
     */
    Predicate<String> isExit = command -> doesItEqualTo.apply(trimAndLower.apply(command), EXIT);

    /***
     * Returns True if command is equals to login
     */
    Predicate<String> isLogin = command -> doesItEqualTo.apply(trimAndLower.apply(command), LOGIN);


    /***
     * On Send
     */
    BiConsumer<ComboBox<String>, TextArea> onEnter =
            (input, output) -> {
                // Attributes
                String command = input.getEditor().getText();
                File directory = consoleState.getLocation.get();
                ShellType shellType = consoleState.getShellType.get();

                consoleState.updateState.accept(command);

                // Appending text
                output.appendText(setOutputContent.apply(directory, command));

                // Checking command exits
                if (doesItNullOrEmpty.test(command)) return;

                if (isOneOfActions.apply(command, input, output)) return;

                setOutPutAfterExecution.accept(output, command);
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
     * Create Console Output ( Text Area )
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
        return output;
    };

    /***
     * Create Console Input
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
        // Console State
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
     * Create Console Container
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