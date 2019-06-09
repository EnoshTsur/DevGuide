package com.devguide.jfx.view.components.console;

import com.devguide.jfx.execute.ShellType;
import com.devguide.jfx.utils.BasicUtils;
import com.devguide.jfx.utils.OperationSystem;
import com.devguide.jfx.utils.StringUtils;
import io.vavr.Function1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.OperationSystem.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.*;

public class ConsoleState {

    private ShellType shellType;

    private OperationSystem operationSystem;

    private File location;

    private List<String> history = new ArrayList<>( basicCommands.asJava() );


    /***
     * Console State
     * @param operationSystem
     */
    public ConsoleState(OperationSystem operationSystem, ShellType shellType) {
        this.operationSystem = operationSystem;
        this.shellType = shellType;
        this.location = new File(USER_HOME);
        setDefaultCommands.run();
    }

    /**
     * Set Default Commands by Operation System
     */
    private Runnable setDefaultCommands = () -> {
                if (doesItEqualTo.apply(operationSystem, LINUX)) {
                    history.addAll(linuxCommands.asJava());
                    return;
                }
                history.addAll(windowsCommands.asJava());
            };


    /***
     * Get History
     */
    public Supplier<List<String>> getHistory = () ->
            new ArrayList<>(history);

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
     * Checks if command exist in history
     */
    public Function1<String, Boolean> existInHistory = command ->
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
    public Runnable clearHistorySoft = () -> new ArrayList<>(
            history = basicCommands.push(
            "cd", "../"
    ).asJava());

    /***
     * Clear History include Git Commands
     */
    public Runnable clearHistoryHard = () -> history = new ArrayList<>();

    /***
     * Navigate to New Path if exists
     */
    public Consumer<String> navigate = path -> {
        if (isEmpty.apply(path)) return;

        if (doesItEqualTo.apply(path, BACKWARDS)) {
            File parent = new File(location.getParent());
            location = ( isNotNull.apply(parent) && parent.exists() )
                    ? parent : location;
            return;
        }

        if (path.contains(FORWARD_SLASH)) {
            String[] folders = path.split(FORWARD_SLASH);

            Stream.of(folders).forEach(folder -> {
                if (!isNotOneOfChildren.apply(folder))return;

                File next = new File(f("{0}/{1}",
                        location.getPath(),
                        folder)
                );
                if (!next.exists() || !next.isDirectory()) return;

                location = next;
                return;
            });

        }

        File destination = new File(path);
        if (!destination.exists()) return;

        location = destination;
    };



    @Override
    public String toString() {
        return "ConsoleState{" +
                "location=" + location +
                ", history=" + history +
                '}';
    }
}
