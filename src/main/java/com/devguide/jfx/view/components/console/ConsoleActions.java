package com.devguide.jfx.view.components.console;

import com.devguide.jfx.browsers.pages.frontend.FrontendPage;
import com.devguide.jfx.execute.Execute;
import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.utils.Consumer3;
import com.devguide.jfx.utils.FileSystem;
import com.devguide.jfx.utils.OperationSystem;
import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Try;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.devguide.jfx.execute.Execute.run;
import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.utils.StringUtils.trimAndLower;
import static com.devguide.jfx.view.components.console.Commands.*;
import static com.devguide.jfx.view.components.console.Commands.LOGIN;
import static com.devguide.jfx.view.components.console.Console.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.allDrivers;
import static com.devguide.jfx.view.shared.SharedUtils.setTextColor;
import static io.vavr.Tuple.*;

public interface ConsoleActions {

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!! ACTIONS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    /***
     * Move Backwards
     */
    Consumer3<ComboBox<String>, TextArea, String> moveBackwards =
            (input, output, command) -> {
                consoleState.navigate.apply(BACKWARDS, input);
                setOutputAfterExecution.accept(output, command);
            };

    /***
     * Move Backwards
     */
    Consumer3<ComboBox<String>, TextArea, String> changeDirectory =
            (input, output, command) -> {
                String[] cdAndPath = command.split(SPACE);
                String path = (cdAndPath.length > 1 && isNotNull.apply(cdAndPath[1]))
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

    /***
     * Opens directory in console state location
     */
    Consumer3<ComboBox<String>, TextArea, String> openDirectory =
            (input, output, command) -> {
                File location = consoleState.getLocation.get();
                FileSystem.openLocation.accept(location);
            };

    /***
     * Writing help message to console
     */
    Consumer3<ComboBox<String>, TextArea, String> getHelp =
            (input, output, commands) -> setMultiLinesOutput.accept(List.of(
                    "~~~~~~~~~~~~~~~~~~~~~~~~~",
                    "~~ Console Application ~~",
                    "~~~~~~~~~~~~~~~~~~~~~~~~~",
                    EMPTY_STRING,
                    "Console app - contains common console methods.",
                    " few custom methods such as:",
                    EMPTY_STRING,
                    "1) open - Opens directory location",
                    "   Directory's location is next to the console output )",
                    EMPTY_STRING,
                    "2) color - Change console text color.",
                    "   Example: color red.",
                    EMPTY_STRING,
                    "3) login - Performs login to Frontend Masters",
                    "   In order to watch courses online.",
                    EMPTY_STRING,
                    "4) exit - Quit program",
                    EMPTY_STRING,
                    "5) help - Provides informative help message ( this )",
                    EMPTY_STRING,
                    EMPTY_STRING,
                    "*************************************************************************",
                    "*** Important! The original commands depends on your operation system ***",
                    "*** Example: On Linux: 'pwd', on Windows: 'echo %cd%'                 ***",
                    "**************************************************************************"
                    ),
                    output
            );


    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!! PREDICATES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


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
     * Returns True if command is equals to open
     */
    Predicate<String> isOpen = command -> doesItEqualsTo.apply(trimAndLower.apply(command), OPEN);

    /***
     * Returns True if command is equals to help
     */
    Predicate<String> isHelp = command -> doesItEqualsTo.apply(trimAndLower.apply(command), HELP);


    // Actions
    List<Tuple2<Predicate<String>, Consumer3<ComboBox<String>, TextArea, String>>> actions = List.of(
            of(isItBackwards, moveBackwards),
            of(isStartsWithCD, changeDirectory),
            of(isOneOfDrivers, changeDrive),
            of(isClearOrCls, clearScreen),
            of(isColorChange, changeColor),
            of(isExit, closeProgram),
            of(isLogin, doLogin),
            of(isOpen, openDirectory),
            of(isHelp, getHelp)
    );


    /***
     * Returns true if its one of the actions
     */
    Function3<String, ComboBox<String>, TextArea, Boolean> isOneOfActions
            = (command, input, output) ->
            actions.map(pair -> ifTrueThan.apply(pair._1, pair._2, command, input, output))
                    .collect(Collectors.toList()).stream().anyMatch(condition -> isItTrue.test(condition));

}
