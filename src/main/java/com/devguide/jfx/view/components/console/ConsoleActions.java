package com.devguide.jfx.view.components.console;

import com.devguide.jfx.utils.BasicUtils;
import io.vavr.Function3;
import io.vavr.Tuple;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.view.components.console.Console.*;
import static io.vavr.Tuple.*;

public interface ConsoleActions {


    /***
     * Returns true if its one of the actions
     */
    Function3<String, ComboBox<String>, TextArea, Boolean> isOneOfActions
            = (command, input, output) ->
            Arrays.asList(
                    of(isItBackwards, moveBackwards),
                    of(isStartsWithCD, changeDirectory),
                    of(isOneOfDrivers, changeDrive),
                    of(isClearOrCls, clearScreen),
                    of(isColorChange, changeColor),
                    of(isExit, closeProgram),
                    of(isLogin, doLogin))
                    .stream()
                    .map(pair -> ifTrueThan.apply(pair._1, pair._2, command, input, output))
                    .collect(Collectors.toList()).stream().anyMatch(condition -> isItTrue.test(condition));

}
