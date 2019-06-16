package com.devguide.jfx.browsers.pages.git;



import com.devguide.jfx.utils.OperationSystem;

import static com.devguide.jfx.utils.FileSystem.*;
import static com.devguide.jfx.utils.OperationSystem.*;
import static com.devguide.jfx.utils.StringUtils.*;
import static com.devguide.jfx.view.components.console.ConsoleUtils.*;

public interface GithubUtils {

    /**
     * Get the right path to download depends on your operation system
     */
    String DOWNLOAD_PATH = isMyOperationSystem.test(WINDOWS10) ?
            "https://git-scm.com/download/win" :
            isMyOperationSystem.test(LINUX) ?
            "https://git-scm.com/download/linux" :
                    EMPTY_STRING;

    /***
     * Returns an error message when operation system name is not one of
     * Windows 10 || Linux
     */
    String OPERATION_SYSTEM_ERROR = logError.apply(
            f("It seems like your operation system is not one  of {0}",
                    OperationSystem.values())
    );

    /***
     * Download Message
     */
    String DOWNLOAD_MESSAGE = "Downloading...";

}
