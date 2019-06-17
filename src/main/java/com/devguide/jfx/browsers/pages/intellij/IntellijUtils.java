package com.devguide.jfx.browsers.pages.intellij;

import com.devguide.jfx.utils.FileSystem;
import com.devguide.jfx.utils.OperationSystem;
import org.openqa.selenium.By;

import static com.devguide.jfx.utils.OperationSystem.*;

public interface IntellijUtils {

    // Page Address
    String PAGE_ADDRESS = FileSystem.isMyOperationSystem.test(WINDOWS10) ?
            "https://www.jetbrains.com/idea/download/#section=windows" :
            "https://www.jetbrains.com/idea/download/#section=linux";

    // Download Button
    By DOWNLOAD_BUTTON = By.xpath("//*[@id=\"section\"]/div/div[3]/div[1]/div[1]/div[2]/div/div[1]/a");
}
