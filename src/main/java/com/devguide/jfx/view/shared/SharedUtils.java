package com.devguide.jfx.view.shared;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.utils.BasicUtils.*;

/***
 * Shared Utils for App UI
 */
public interface SharedUtils {

    String APP_NAME = "Dev Bible";
    String FONT_TYPE = "Verdana";
    int HEADER_FONT_SIZE = 23;


    Insets DEFAULT_INSETS = new Insets(10, 10, 10, 10);


    Function1<String, String> createBgColorStyle = color ->
            f("-fx-background-color: {0}",color);

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
    BiConsumer<Region, String> setStyle = (javafxObject, style) -> {
        if (isEmpty.apply(style)) return;
        javafxObject.setStyle(f("{0}{1}", javafxObject.getStyle(), style));
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
        setStyle.accept(javafxObject, createBgColorStyle.apply(color));


}
