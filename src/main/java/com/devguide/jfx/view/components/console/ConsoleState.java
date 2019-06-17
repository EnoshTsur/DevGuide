package com.devguide.jfx.view.components.console;

import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.utils.*;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Try;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.ListUtils.*;
import static com.devguide.jfx.utils.OperationSystem.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.components.console.Console.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.*;
import static javafx.collections.FXCollections.*;

public class ConsoleState {

    private ShellType shellType;

    private OperationSystem operationSystem;

    private File location;

    private ComboBox<String> input;

    private ObservableList<String> history;

    List<String> historyList;

    private TextArea output;

    private List<String> commands;


    /***
     * Console State
     * @param operationSystem
     */
    public ConsoleState(
            OperationSystem operationSystem,
            ShellType shellType,
            List<String> commands
    ) {
        this.operationSystem = operationSystem;
        this.shellType = shellType;
        this.history = observableArrayList(commands);
        this.historyList = new ArrayList<>(history);
        this.location = new File(USER_HOME);

    }

    /**
     * Set Default Commands by Operation System
     */
    private Supplier<List<String>> setDefaultCommands = () -> {
        if (doesItEqualTo.apply(operationSystem, LINUX)) {
            history.addAll(linuxCommands.asJava());
            return history;
        }
        history.addAll(windowsCommands.asJava());
        return history;
    };


    /***
     * Get History
     */
    public Supplier<ListProperty<String>> getHistory = () -> {
        ListProperty property = new SimpleListProperty();
        property.set(observableArrayList(history));
        return property;
    };


    /***
     * Get Shell Type
     */
    public Supplier<ShellType> getShellType = () -> shellType;

    /***
     * Get Location Children
     */
    public Supplier<List<File>> locationChildren = () ->
            Stream.of(
                    Objects.requireNonNull(location.listFiles()))
                    .filter(File::isDirectory)
                    .collect(Collectors.toList());

    /***
     * Returns true if String path is a child of current location
     */
    public Function1<String, Boolean> isOneOfChildren = child ->
            locationChildren.get().contains(child);

    /***
     * Returns true if item is not one of current location children
     */
    public Function1<String, Boolean> isNotOneOfChildren =
            StringUtils.not.apply(isOneOfChildren);

    /***
     * Get Location
     */
    public Supplier<File> getLocation = () ->
            new File(location.getPath());

    /***
     * Set Location
     */
    public Consumer<File> setLocation = file -> location = file;

    /***
     * Checks if command exist in history
     */
    public Predicate<String> existInHistory = command ->
            history.contains(trimAndLower.apply(command));

    /***
     * Add Single Command to History
     */
    public Consumer<String> addSingle = command -> history.add(command);

    /***
     * Add Multiple Commands to History
     */
    public Consumer<List<String>> addMany = commands -> commands.forEach(newCommand -> {
        if (!history.contains(trimAndLower.apply(newCommand)))
            history.add(newCommand);
    });

    /***
     * Remove Item if exists
     */
    public Consumer<String> removeItem = item -> {
        if (history.contains(item)) history.remove(item);
    };

    /***
     * RemoveMany Items if they exists
     */
    public Consumer<List<String>> removeMany = items ->
            items.forEach(item -> {
                if (history.contains(item)) history.remove(item);
            });

    /***
     * Clear History exclude Git Commands
     */
    public Runnable clearHistorySoft = () ->
            history = FXCollections.observableArrayList(setDefaultCommands.get());


    /***
     * Clear History include Git Commands
     */
    public Runnable clearHistoryHard = () -> history = FXCollections.observableArrayList();

    /***
     * Navigate to New Path if exists
     */
    public Function2<String, ComboBox<String>, String> navigate = (path, input) -> {
        if (isEmpty.apply(path)) return location.getPath();

        // Is it Backwards
        if (isItBackwards.test(path)) {
            Try<File> parent = Try.of(() -> new File(location.getParent()));
            if (parent.isEmpty()) return location.getPath();
            location = (isNotNull.apply(parent) && parent.get().exists())
                    ? parent.get() : location;
            input.setPromptText(location.getPath());
            return location.getPath();
        }

        // Contains Forward Slash
        if (doesItContains.apply(FORWARD_SLASH, path)) {
            String[] folders = path.split(FORWARD_SLASH);

            Stream.of(folders).forEach(folder -> {
                Try<File> child = Try.of(() -> new File(location, folder));
                if (!child.isEmpty() && child.get().isDirectory() &&  !folder.equals(".")) {
                    location = child.get();
                    input.setPromptText(location.getPath());
                }
            });
            return location.getPath();
        }

        // No Forward slash
        String[] folders = path.split(FORWARD_SLASH);
        Stream.of(folders).forEach(folder -> {
            Try<File> child = Try.of(() -> new File(location, folder));
            if (!child.isEmpty() && child.get().isDirectory()) {
                location = child.get();
                input.setPromptText(location.getPath());
            }
        });
        return location.getPath();
    };


    /***
     * Init State
     */
    public BiConsumer<ComboBox<String>, TextArea> initState = (input, output) -> {
        this.input = input;
        this.output = output;
        this.commands = setDefaultCommands.get();
        history.addListener((ListChangeListener<Object>) change -> {
            input.getItems().clear();
            history.forEach(item -> input.getItems().add(item));
        });
    };

    /***
     * Get Console output
     */
    public Supplier<TextArea> consoleOutPut = () -> output;

    /***
     * Update State
     */
    public Consumer<String> updateState = command -> {
        if (isNotOneOfUtil.apply(command, historyList)) {
            historyList.add(command);
            history = FXCollections.observableArrayList(historyList);
            history.sort(String::compareTo);
        }
        historyList = new ArrayList<>(history);
    };



    @Override
    public String toString() {
        return "ConsoleState{" +
                "location=" + location +
                ", history=" + history +
                '}';
    }
}
