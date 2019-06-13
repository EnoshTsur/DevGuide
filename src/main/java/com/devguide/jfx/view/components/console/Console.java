package com.devguide.jfx.view.components.console;

import com.devguide.jfx.browsers.LoginPage;
import com.devguide.jfx.browsers.WebActions;
import com.devguide.jfx.execute.Execute;
import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.utils.*;
import com.devguide.jfx.view.UI.ButtonAPI;
import com.devguide.jfx.view.UI.PaneTypes;
import com.devguide.jfx.view.shared.Colors;
import com.sun.org.apache.xpath.internal.compiler.Keywords;
import io.vavr.*;
import io.vavr.control.Try;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.devguide.jfx.browsers.WebActions.*;
import static com.devguide.jfx.execute.Execute.*;
import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.FileSystem.getLastFolder;
import static com.devguide.jfx.utils.KeyBoardUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.ComboBoxAPI.*;
import static com.devguide.jfx.view.UI.PaneAPI.*;
import static com.devguide.jfx.view.UI.TextAreaAPI.*;
import static com.devguide.jfx.view.components.console.Commands.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.*;
import static com.devguide.jfx.view.components.search.SearchBarUtils.*;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.Colors.COMBO_DARK_PURPLE;
import static com.devguide.jfx.view.shared.SharedUtils.*;

public interface Console {


    /***
     * Set Out Put
     */
    Function2<File, String, String> setOutput = (path, command) ->
            f("{0}> {1}\n", getLastFolder.apply(path), command);

    // Console State
    ConsoleState consoleState =
            new ConsoleState(OperationSystem.LINUX, ShellType.BASH);

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
                consoleState.navigate.apply(BACKWARDS, input);
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
                output.appendText(setOutput.apply(
                        dir,
                        EMPTY_STRING
                ));

                // Run
                run.apply(
                        command,
                        dir,
                        consoleState
                                .getShellType
                                .get()
                );
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
                output.appendText(setOutput.apply(
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
                    setOutput.apply(consoleState.getLocation.get(), EMPTY_STRING)
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
            (input, output, command) -> {
                WebDriver driver = setDriver.get();
                navigateTo.apply("https://frontendmasters.com/login/", driver);
            };

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

                // Clear Screen
                boolean clearOutput = ifTrueThan.apply(
                        isClearOrCls,
                        clearScreen,
                        command,
                        input,
                        output
                );
                if (clearOutput) return;


                // Color Change
                boolean colorChange = ifTrueThan.apply(
                        isColorChange,
                        changeColor,
                        command,
                        input,
                        output
                );
                if (colorChange) return;

                // Exit
                boolean exit = ifTrueThan.apply(
                        isExit,
                        closeProgram,
                        command,
                        input,
                        output
                );
                if (exit) return;

                // Login
                boolean login = ifTrueThan.apply(
                        isLogin,
                        doLogin,
                        command,
                        input,
                        output
                );
                if (login) return;


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
        output.appendText(setOutput.apply(
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
                        basicCommands,
                        Tuple.of(
                                INPUT_WIDTH,
                                INPUT_HEIGHT
                        ),
                        null
                );
        setBackgroundColor.accept(input.getEditor(), COMBO_DARK_PURPLE);
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