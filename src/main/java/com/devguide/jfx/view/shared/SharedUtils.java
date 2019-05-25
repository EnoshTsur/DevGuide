package com.devguide.jfx.view.shared;

import io.vavr.*;
import io.vavr.collection.List;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.devguide.jfx.utils.StringUtils.*;

/***
 * Shared Utils for App UI
 */
public interface SharedUtils {

    String APP_NAME = "Dev Bible";

    int HEADER_FONT_SIZE = 23;

    int DEFAULT_SPACING = 10;

    String SHADOW_STYLE = "-fx-effect: dropshadow(three-pass-box, " +
            "rgba(0,0,0,0.8), 10, 0, 0, 0);";


    Insets DEFAULT_INSETS = new Insets(10, 10, 10, 10);


    Function1<String, String> createBgColorStyle = color ->
            f("-fx-background-color: {0}", color);


    /***
     * Get BackgroundImage ( Object ) by javafx Object sizes
     */
    Function2<Region, Image, BackgroundImage> createBackgroundImageByRegionSize =
            (javafxObject, image) -> new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(javafxObject.getWidth(), javafxObject.getHeight(),
                            true, true,
                            true, false));

    /***
     * Get Image ( Object ) by javafx Object sizes
     */
    Function2<Region, String, Image> createImageByRegionSize = (javafxObject, imagePath) ->
            new Image(imagePath, javafxObject.getWidth(), javafxObject.getHeight(),
                    false, true, true);

    /***
     * Set Background to JavaFX Object
     */
    BiConsumer<Region, String> setBackground = (javafxObject, imagePath) -> {
        if (isEmpty.apply(imagePath)) return;
        Image image = createImageByRegionSize.apply(javafxObject, imagePath);
        BackgroundImage backgroundImage = createBackgroundImageByRegionSize.apply(javafxObject, image);
        javafxObject.setBackground(new Background(backgroundImage));
    };

    /***
     * Set style if exist
     */
    BiConsumer<Region, String> addStyle = (javafxObject, style) -> {
        if (isEmpty.apply(style)) return;
        javafxObject.setStyle(f("{0};{1};", javafxObject.getStyle(), style));
    };

    /***
     * Add  many styles to JavaFX Object
     * If Empty ignored
     */
    BiConsumer<Region, List<String>> addManyStyles = (javafxObjects, styles) -> {
        if (styles.isEmpty()) return;
        styles.forEach(style -> addStyle.accept(javafxObjects, style));
    };

    /***
     * Create Font
     */
    Function3<String, FontWeight, Integer, Font> createFont =
            (fontName, fontWeight, fontSize) -> Font.font(fontName, fontWeight, fontSize);

    /***
     * Set Background color to JavaFX Element
     */
    BiConsumer<Region, String> setBackgroundColor = (javafxObject, color) ->
            addStyle.accept(javafxObject, createBgColorStyle.apply(color));


    /***
     * Add Shadow to JavaFX Objects
     */
    Consumer<Region> addShadow = javafxObject ->
            addStyle.accept(javafxObject, SHADOW_STYLE);

    /**
     * Takes :
     * Region = JavaFX Object such as Border Pane.. invoked mouse hold
     * Tuple2 = _1 Scale x, _2 Scale y
     * Boolean = Set Draggable true || false
     * Returns:
     * Draggable Object
     */
    static void setStageDraggable(Region clickInvoker,
                                  Stage window,
                                  double[] offsets,
                                  boolean draggable) {
        if (!draggable) {
            clickInvoker.setOnMousePressed(event -> {});
            clickInvoker.setOnMouseDragged(event -> {});
            return;
        }

        clickInvoker.setOnMousePressed(event -> {
            offsets[0] = window.getX() - event.getScreenX();
            offsets[1] = window.getY() - event.getScreenY();
        });
        clickInvoker.setOnMouseDragged(event -> {
            window.setX(event.getScreenX() + offsets[0]);
            window.setY(event.getScreenY() + offsets[1]);
        });
    }

}
