package com.devguide.jfx.view.shared;

import io.vavr.Function1;
import io.vavr.Function2;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/***
 * Tool to initial List View with Images
 * @author 'Enosh Tsur'
 */
public interface ListViewCellImageFix {


    // Images
    Image IMAGE_BRANCH = new Image("file:assets/branch_icon.png");
    Image IMAGE_FOLDER = new Image("file:assets/folder_icon.jpg");

    /**
     * Set List View Icons
     *
     * @param items
     * @return List View with Icons
     */
    Function2<ObservableList<String>, Image, ListView<String>> seListViewtIcons
    = (items, icon) -> {

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
                    imageView.setImage(icon);
                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
        return listView;
    };

}

