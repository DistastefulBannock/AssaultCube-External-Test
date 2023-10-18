package me.bannock.memory;

import com.sun.jna.Memory;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

public interface MemoryApi {

    /**
     * Sets the pointer size to use for the system
     * @param size The amount of bytes to use for a pointer
     */
    void setPointerSize(int size);

    /**
     * Opens a handle to an executable with the given name.
     * @param executableName The name of the executable;
     * @param access The access level of the handle
     * @throws RuntimeException If the attachment failed.
     */
    void openHandle(String executableName, int access) throws RuntimeException;

    /**
     * Opens a handle to an executable with the given name. Uses WinNT.PROCESS_ALL_ACCESS
     * @param executableName The name of the executable;
     * @throws RuntimeException If the attachment failed.
     */
    default void openHandle(String executableName) throws RuntimeException{
        openHandle(executableName, WinNT.PROCESS_ALL_ACCESS);
    }

    /**
     * Gets the handle for the executable
     * @return The handle for the executable
     */
    WinNT.HANDLE getExecutableHandle();

    /**
     * Gets the handle for a specific module
     * @param moduleName The name of the module
     * @return The handle for the module
     * @throws RuntimeException If the module handle could not be found.
     *                   Call GetLastError() for more information.
     */
    WinDef.HMODULE getModuleHandle(String moduleName) throws RuntimeException;

    /**
     * Gets the base address for a specific module
     * @param moduleName The name of the module
     * @return The base address for the module
     * @throws RuntimeException If the module handle could not be found. Call GetLastError() for more information.
     */
    long getModuleBaseAddress(String moduleName) throws RuntimeException;

    /**
     * Reads memory from the executable.
     * <strong>IMPORTANT: Will cause memory leak if memory object isn't closed!</strong>
     * @param address The address to read from
     * @param size The amount of bytes to read into
     * @return The memory read
     */
    Memory readMemory(long address, int size);

    /**
     * Reads a byte from the executable.
     * @param address The address to read from
     * @return The byte read
     */
    byte readByte(long address);

    /**
     * Reads a number of bytes from the executable.
     * @param address The address to read from
     * @param size The amount of bytes to read
     * @return The bytes read
     */
    byte[] readBytes(long address, int size);

    /**
     * Reads a pointer from the given address, accounts for pointer size
     * @param address The address to read from
     * @return The pointer read
     */
    long readPointer(long address);

    /**
     * Reads a short from the executable (2 bytes)
     * @param address The address to read from
     * @return The short read
     */
    short readShort(long address);

    /**
     * Reads an integer from the executable  (4 bytes)
     * @param address The address to read from
     * @return The integer read
     */
    int readInt(long address);

    /**
     * Reads a long from the executable  (8 bytes)
     * @param address The address to read from
     * @return The long read
     */
    long readLong(long address);

    /**
     * Reads a float from the executable
     * @param address The address to read from
     * @return The float read
     */
    float readFloat(long address);

    /**
     * Reads a double from the executable
     * @param address The address to read from
     * @return The double read
     */
    double readDouble(long address);

    /**
     * Reads a String from the executable as UTF-8
     * @param address The address to read from
     * @param length The length of the String to read
     * @return The String read
     */
    default String readString(long address, int length){
        return readString(address, length, "UTF-8");
    }

    /**
     * Reads a String from the executable
     * @param address The address to read from
     * @param length The length of the String to read
     * @param encoding The encoding of the String
     * @return The String read
     */
    String readString(long address, int length, String encoding);

    /**
     * Reads a wide String from the executable
     * @param address The address to read from
     * @param length The length of bytes to read for the String
     * @return The String read
     */
    String readWideString(long address, int length);

    /**
     * Reads a boolean from the executable
     * @param address The address to read from
     * @return The boolean read
     */
    boolean readBoolean(long address);

    /**
     * Reads an offset and returns the resulting address
     * @param address The address to read from
     * @param offsets The offsets to read. Dereferences the last offset before reading the next
     * @return The resulting address, not dereferenced
     */
    long processOffsets(long address, long... offsets);

    /**
     * Reads an offset and returns the resulting address
     * @param moduleName The name of the module
     * @param firstAddress The first address appended to the module base address
     * @param offsets The offsets to read. Dereferences the last offset before reading the next
     * @return The resulting address, not dereferenced
     * @throws RuntimeException If the module handle could not be found. Call GetLastError() for more information.
     */
    default long processOffsets(String moduleName, long firstAddress, long... offsets) throws RuntimeException{
        return processOffsets(getModuleBaseAddress(moduleName) + firstAddress, offsets); // Just makes it take slightly less work
    }

    /**
     * @return true if we are able to read memory from the application, otherwise false
     */
    boolean hasReadPermission();

    /**
     * @return true if we are able to write memory to the application, otherwise false
     */
    boolean hasWritePermission();

    /**
     * @return The size of a pointer in bytes
     */
    int getPointerSize();

}
