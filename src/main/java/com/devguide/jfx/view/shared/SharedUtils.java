package com.devguide.jfx.view.shared;

import io.vavr.*;
import io.vavr.collection.List;
import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.devguide.jfx.utils.StringUtils.*;

/***
 * Shared Utils for App UI
 */
public interface SharedUtils {


    String APP_NAME = "Dev Bible";
    String DEFAULT_FONT_TYPE = "-apple-system";
    String HEADER_FONT_TYPE = "-apple-system";


    int DEFAULT_SPACING = 10;
    int HEADER_FONT_SIZE = 23;
    int TITLE_FONT_SIZE = 18;

    String SHADOW_STYLE = "-fx-effect: dropshadow(three-pass-box, " +
            "rgba(0,0,0,0.8), 10, 0, 0, 0);";


    Insets DEFAULT_INSETS = new Insets(10, 10, 10, 10);


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


    Function1<String, String> createBgColorStyle = color ->
            f("-fx-background-color: {0}", color);


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
     * Set Background with linear gradient
     */
    Function3<Region, String, String, Region> setBackgroundLinearGradient = (javafxObject, colorA, colorB) -> {
        addStyle.accept(
                javafxObject,
                f("-fx-background-color: linear-gradient({0}, {1});", colorA, colorB)
        );
        return javafxObject;
    };


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

    /***
     * Create Style Opacity
     */
    Function1<Double, String> createStyleOpacity =
            amount -> f("-fx-opacity: {0}", amount);

    /***
     * Font by Size
     */
    Function1<Integer, Font> haaretzFontBySize = size ->
            createFont.apply(
                    DEFAULT_FONT_TYPE,
                    FontWeight.SEMI_BOLD,
                    size
            );

    /***
     * Haaretz header font ( 23 )
     */
    Font HAARETZ_HEADER_FONT = createFont.apply(
            HEADER_FONT_TYPE,
            FontWeight.LIGHT,
            HEADER_FONT_SIZE
    );


    /**
     * Haaretz title font ( 18 )
     */
    Font HAARETZ_TITLE_FONT = haaretzFontBySize
            .apply(TITLE_FONT_SIZE);

    /***
     * Get Shadow effect
     * @return Shadow
     */
    Function1<String, DropShadow>  createShadow = color -> {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.web(color));
        return dropShadow;
    };

    /***
     * Create Tool Tip
     * @param message
     * @return Tool Rip
     */
    Function1<String, Tooltip> createToolTip = message  -> {
        Tooltip tooltip = new Tooltip(message);
        tooltip.setStyle("-fx-font-size: 16px;" +
                "-fx-shape: M24 1h-24v16.981h4v5.019l7-5.019h13;");
        return tooltip;
    };

}
