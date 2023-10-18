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
     * Attaches to an executable with the given name.
     * @param executableName The name of the executable;
     * @throws RuntimeException If the attachment failed.
     */
    void bindToExecutable(String executableName) throws RuntimeException;

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
     * Reads a String from the executable
     * @param address The address to read from
     * @param length The length of the String to read
     * @return The String read
     */
    String readString(long address, int length);

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
     * @param length The length of the String to read
     * @return The String read
     */
    String readWideString(long address, int length);

    /**
     * Reads an offset and returns the resulting address
     * @param moduleName The name of the module
     * @param firstAddress The first address appended to the module base address
     * @param offsets The offsets to read. Dereferences the last offset before reading the next
     * @return The resulting address, not dereferenced
     * @throws RuntimeException If the module handle could not be found. Call GetLastError() for more information.
     */
    long processOffsets(String moduleName, long firstAddress, long... offsets) throws RuntimeException;

    /**
     * Reads an offset and returns the resulting address
     * @param address The address to read from
     * @param offsets The offsets to read. Dereferences the last offset before reading the next
     * @return The resulting address, not dereferenced
     */
    long processOffsets(long address, long... offsets);

}
