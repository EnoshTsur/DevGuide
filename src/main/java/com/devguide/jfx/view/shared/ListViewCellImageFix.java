package com.devguide.jfx.view.shared;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.*;

import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.UI.ButtonAPI.CURSOR_POINTER;
import static com.devguide.jfx.view.shared.Colors.*;
import static com.devguide.jfx.view.shared.ListCellStyles.*;
import static com.devguide.jfx.view.shared.SharedUtils.*;

/***
 * Tool to initial List View with Images
 * @author 'Enosh Tsur'
 */
public interface ListViewCellImageFix {

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
     * Set Image by Technology type
     */
    Function2<ImageView, String, ImageView> setImageByTechonology
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
        }

        icon.setFitHeight(ICON_HEIGHT);
        icon.setFitWidth(ICON_WIDTH);
        return icon;
    };

    /**
     * Set List View Icons
     *
     * @param items
     * @return List View with Icons
     */
    Function1<ObservableList<String>, ListView<String>> seListViewtIcons
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
                    setImageByTechonology.apply(imageView, name);
                    setText(name);
                    setGraphic(imageView);
                    setListCellsStyles.apply(this);

                }
            }
        });
        return listView;
    };

}

