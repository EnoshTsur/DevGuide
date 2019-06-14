package com.devguide.jfx.execute;

import io.vavr.*;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.devguide.jfx.utils.BasicUtils.*;
import static com.devguide.jfx.utils.StringUtils.*;

/***
 * Executing command by custom shell
 */
public interface Execute {

    /**
     * Building Process
     */
    Function3<String, ShellType, File, ProcessBuilder> buildProcess =
            (action, shellType, path) -> {
                // Decides on which shell to execute
                String[] command = ShellFactory.getShell.apply(shellType);

                // Insert the actual command
                command[2] = action;

                // Building a process and reading input from shell
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);
                processBuilder.directory(path);
                return processBuilder;
            };


    /***
     * Create Buffered Reader for process output
     */
    Function1<Process, BufferedReader> createBufferedReader =
            process -> {
                InputStream in = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(isr);
                return br;
            };


    /***
     * !!!!!!!!!!!!!!!!!!!!!!!
     * !! Executing command !!
     * !!!!!!!!!!!!!!!!!!!!!!!
     */
    Function3<String, File, ShellType, List<String>> run =
            (action, path, shell) -> {
                List<String> message = new ArrayList<>();
                try {
                    ProcessBuilder processBuilder = buildProcess.apply(action, shell, path);

                    // Process
                    Process process = processBuilder.start();

                    // Process IO
                    BufferedReader br = createBufferedReader.apply(process);

                    // Output line
                    String line;
                    while (!isNull.apply(line = br.readLine())) {
                        message.add(line);
                        System.out.println(line);
                    }
                    // Close
                    br.close();

                    // Exit value from shell & wait for process to finish
                    int exitValue = process.waitFor();
                    System.out.println(f("\n\nExit value: {0}", exitValue));
                    System.out.println(f("\n\nCommand: {0}", action));

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                return message;
            };

    /***
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * !! Executing command with thread !!
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    Function3<String, File, ShellType, List<String>> runOnThread =
            (action, path, shellType) -> {
                List<String> message = new ArrayList<>();
                Task<List<String>> task = new Task<List<String>>() {
                    @Override
                    protected List<String> call() {
                        return run.apply(action, path, shellType);
                    }
                };
                Thread exe = new Thread(task);
                task.setOnSucceeded(t -> task.getValue().forEach(message::add));
                task.setOnFailed(t -> System.out.println(task.getException()));
                exe.start();
                Try.run(() -> exe.join());
                return message;
            };


}




