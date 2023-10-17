package me.bannock.assaultcube.game;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;

public interface GameApi {

    /**
     * Attaches to an executable with the given name.
     * @param executableName The name of the executable;
     * @return True if the attach was successful, false otherwise.
     * @throws RuntimeException If the attach failed.
     */
    boolean connectToGame(String executableName) throws Exception;

    /**
     * Gets the handle for a specific module
     * @param moduleName The name of the module
     * @return The handle for the module
     * @throws RuntimeException If the module handle could not be found.
     *                   Call GetLastError() for more information.
     */
    WinDef.HMODULE getModuleHandle(String moduleName) throws Exception;

}
