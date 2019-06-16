package com.devguide.jfx.browsers.pages.vscode;

import org.openqa.selenium.By;

import static com.devguide.jfx.utils.FileSystem.isMyOperationSystem;
import static com.devguide.jfx.utils.OperationSystem.WINDOWS10;

public interface VSCodeUtils {

    /**
     * Get the right path to download depends on your operation system
     */
    String DOWNLOAD_PATH = "https://code.visualstudio.com/download";

    By DOWNLOAD_BUTTON = isMyOperationSystem.test(WINDOWS10) ?
           By.xpath("//*[@id=\"alt-downloads\"]/div/div[1]/button") :
            By.xpath("//*[@id=\"alt-downloads\"]/div/div[2]/button[1]");


}
