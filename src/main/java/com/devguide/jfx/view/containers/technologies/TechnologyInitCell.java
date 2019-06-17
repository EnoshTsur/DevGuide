package com.devguide.jfx.view.containers.technologies;

import com.devguide.jfx.browsers.pages.intellij.IntellijPage;
import com.devguide.jfx.browsers.pages.webstorm.WebStormPage;
import com.devguide.jfx.utils.Consumer3;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static com.devguide.jfx.browsers.pages.git.GithubPage.*;
import static com.devguide.jfx.browsers.pages.intellij.IntellijPage.*;
import static com.devguide.jfx.browsers.pages.vscode.VSCodePage.*;
import static com.devguide.jfx.browsers.pages.webstorm.WebStormPage.*;
import static com.devguide.jfx.utils.ErrorMessages.*;
import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.PopupsAPI.*;
import static com.devguide.jfx.view.components.console.Console.*;
import static com.devguide.jfx.view.components.main.MainView.*;
import static com.devguide.jfx.view.containers.technologies.TechnologiesCommands.*;

/***
 * Tool to initial List View with Images
 * @author 'Enosh Tsur'
 */
public interface TechnologyInitCell {

    int ICON_WIDTH = 20;
    int ICON_HEIGHT = 20;

    /***
     * Creates Image View by Path
     */
    Function1<String, Image> createImageByPath =
            path ->  new Image(path);

    /***
     * Creates Image Under Assets folder
     */
    Function1<String, Image> createImageUnderAssets =
            image -> createImageByPath.apply(f("assets/{0}", image));

    /***
     * Is File Chosen - Returns true if they picked a file
     */
    Predicate<File> isFileChosen = file -> {
        if (isItNullOrNotExists.test(file)) {
            File consoleLocation = consoleState.getLocation.get();
            TextArea consoleOutput = consoleState.consoleOutPut.get();
            consoleOutput.appendText(setOutputContent.apply(consoleLocation, NO_FILE_CHOSEN));
            return false;
        }
        return true;
    };

    Consumer3<File, String, TextArea> handleFolderPick = (file, command, output) -> {
        consoleState.setLocation.accept(file);
        Runnable openFile = () -> openLocation.accept(file);
        setOutputAndRunAfterExecution.accept(output, command, openFile);
    };

    /***
     * No file Chosen - Returns true if they didnt picked up any file
     */
    Predicate<File> noFileChosen = isFileChosen.negate();


    /***
     * Set Image by Technology type
     */
    Function2<ImageView, String, ImageView> setImageByTechnology
            = (icon, item) -> {

        switch (item) {
            case "CSS":
                icon.setImage(createImageUnderAssets.apply("css.png"));
                break;
            case "Fela":
                icon.setImage(createImageUnderAssets.apply("fela.png"));
                break;
            case "HTML5":
                icon.setImage(createImageUnderAssets.apply("html5.png"));
                break;
            case "React":
                icon.setImage(createImageUnderAssets.apply("react.png"));
                break;
            case "Java Script":
                icon.setImage(createImageUnderAssets.apply("js.jpg"));
                break;
            case "Apache Velocity":
                icon.setImage(createImageUnderAssets.apply("vm.png"));
                break;
            case "Node JS":
                icon.setImage(createImageUnderAssets.apply("nodejs.png"));
                break;
            case "Polopoly":
                icon.setImage(createImageUnderAssets.apply("polopoly.jpg"));
                break;
            case "Vavr":
                icon.setImage(createImageUnderAssets.apply("vavr.png"));
                break;
            case "Spring Boot":
                icon.setImage(createImageUnderAssets.apply("spring.png"));
                break;
            case "GraphQL":
                icon.setImage(createImageUnderAssets.apply("graphql.png"));
                break;
            case "Intellij":
                icon.setImage(createImageUnderAssets.apply("intellij.png"));
                break;
            case "Git":
            case "Git Shortcuts":
                icon.setImage(createImageUnderAssets.apply("git.png"));
                break;
            case "Yarn Shortcuts":
                icon.setImage(createImageUnderAssets.apply("shortcut.png"));
                break;
            case "Java 8":
            case "Java 11":
                icon.setImage(createImageUnderAssets.apply("java.png"));
                break;
            case "VS Code":
                icon.setImage(createImageUnderAssets.apply("vscode.png"));
                break;
            case "Web Storm":
                icon.setImage(createImageUnderAssets.apply("webstorm.png"));
                break;
        }

        icon.setFitHeight(ICON_HEIGHT);
        icon.setFitWidth(ICON_WIDTH);
        return icon;
    };

    /**
     * Set Action listener to cell by its name
     */
    Function2<ListCell,String, ListCell> setActionListener = (cell, name) -> {
        final String fixedName = trimAndLower.apply(name);
        final TextArea output = consoleState.consoleOutPut.get();

        switch (fixedName) {

            // Intellij
            case INTELLIJ:
                cell.setOnMouseClicked(event -> downloadIntellij.accept(output));
                break;

            // Web Storm
            case WEBSTORM:
                cell.setOnMouseClicked(event -> downloadWebStorm.accept(output));
                break;

            // Git
            case GIT:
                cell.setOnMouseClicked( event -> downloadGit.accept(output));
                break;

            // Visual Studio Code
            case VS_CODE:
                cell.setOnMouseClicked( event ->  downloadVSCode.accept(output));
                break;

            // Git Shortcuts
            case GIT_SHORTCUTS:
                cell.setOnMouseClicked( event -> {
                    File location = new File(createDirectoryChooser.apply(window));

                    // Validate null & existence
                    if (noFileChosen.test(location)) return;
                    handleFolderPick.accept(location, CLONE_GIT_SHORTCUTS, output);
                });
                break;

            // Yarn Shortcuts
            case YARN_SHORTCUTS:
                cell.setOnMouseClicked( event -> {

                    File location = new File(createDirectoryChooser.apply(window));

                    // Validate null & existence
                    if (noFileChosen.test(location)) return;
                    handleFolderPick.accept(location, CLONE_YARN_SHORTCUTS, output);
                });
                break;

        }
        return cell;
    };

    /***
     * Init Cell
     */
    Function3<ListCell, ImageView, String, ListCell> initialCell
            = (cell, icon, name) -> {
        setImageByTechnology.apply(icon, name);
        setActionListener.apply(cell, name);
        cell.setText(name);
        cell.setGraphic(icon);
        return cell;
    };

    /**
     * Set List View Icons
     * @param items
     * @return List View with Icons
     */
    Function1<ObservableList<String>, ListView<String>> seListViewIcons
            = items -> {

        ListView<String> listView = new ListView<>();
        listView.setItems(items);

        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    initialCell.apply(this, imageView, name);
                }
            }
        });
        return listView;
    };

}

