package com.devguide.jfx.browsers.pages.webstorm;

import com.devguide.jfx.utils.FileSystem;
import org.openqa.selenium.By;

import static com.devguide.jfx.utils.OperationSystem.WINDOWS10;

public interface WebStormUtils {
    // Page Address
    String PAGE_ADDRESS = FileSystem.isMyOperationSystem.test(WINDOWS10) ?
            "https://www.jetbrains.com/webstorm/download/#section=windows" :
            "https://www.jetbrains.com/webstorm/download/#section=linux";

    // Download Button
    By DOWNLOAD_BUTTON = By.xpath("//*[@id=\"section\"]/div/div[3]/div[1]/div/div/div/a");
}
