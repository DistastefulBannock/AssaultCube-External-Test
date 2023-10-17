package me.bannock.memory;

import com.sun.jna.Memory;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

public interface ExecutableApi {

    /**
     * Attaches to an executable with the given name.
     * @param executableName The name of the executable;
     * @return True if the attach was successful, false otherwise.
     * @throws RuntimeException If the attach failed.
     */
    boolean connectToExecutable(String executableName) throws Exception;

    /**
     * Gets the handle for the executable
     * @return The handle for the executable
     * @throws Exception if the executable handle has not been found
     */
    WinNT.HANDLE getExecutableHandle() throws Exception;

    /**
     * Gets the handle for a specific module
     * @param moduleName The name of the module
     * @return The handle for the module
     * @throws RuntimeException If the module handle could not be found.
     *                   Call GetLastError() for more information.
     */
    WinDef.HMODULE getModuleHandle(String moduleName) throws Exception;

    /**
     * Reads memory from the executable.
     * <strong>IMPORTANT: Will cause memory leak if memory object isn't closed!</strong>
     * @param address The address to read from
     * @param size The amount of bytes to read into
     * @return The memory read
     * @throws Exception If something went wrong while reading the memory
     */
    Memory readMemory(long address, int size) throws Exception;

    /**
     * Reads a short from the executable (2 bytes)
     * @param address The address to read from
     * @return The short read
     * @throws Exception If something went wrong while reading the memory
     */
    short readShort(long address) throws Exception;

    /**
     * Reads an integer from the executable  (4 bytes)
     * @param address The address to read from
     * @return The integer read
     * @throws Exception If something went wrong while reading the memory
     */
    int readInt(long address) throws Exception;

    /**
     * Reads a long from the executable  (8 bytes)
     * @param address The address to read from
     * @return The long read
     * @throws Exception If something went wrong while reading the memory
     */
    long readLong(long address) throws Exception;

    /**
     * Reads a String from the executable
     * @param address The address to read from
     * @param length The length of the String to read
     * @return The String read
     * @throws Exception If something went wrong while reading the memory
     */
    String readString(long address, int length) throws Exception;

    /**
     * Reads a String from the executable
     * @param address The address to read from
     * @param length The length of the String to read
     * @param encoding The encoding of the String
     * @return The String read
     * @throws Exception If something went wrong while reading the memory
     */
    String readString(long address, int length, String encoding) throws Exception;

    /**
     * Reads a wide String from the executable
     * @param address The address to read from
     * @param length The length of the String to read
     * @return The String read
     * @throws Exception If something went wrong while reading the memory
     */
    String readWideString(long address, int length) throws Exception;

}
