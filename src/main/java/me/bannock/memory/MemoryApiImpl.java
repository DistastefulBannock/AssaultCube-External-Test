package me.bannock.memory;

import com.google.inject.Inject;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import me.bannock.memory.jna.Kernal32;
import me.bannock.memory.jna.Psapi;
import me.bannock.memory.utils.ProcessUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.StringTemplate.STR;

public class MemoryApiImpl implements MemoryApi {

    private static final int ARRAY_SIZE = 2048;
    private static final String INVALID_POINTER_SIZE = "Invalid pointer size! Must be 2, 4, or 8.";
    private static final String NOT_BOUND_TO_EXECUTABLE_ERROR = "Must bind to executable first! Please run the bindToExecutable method";
    private static final String ALREADY_BOUND_TO_EXECUTABLE_ERROR = "Already bound to executable!";

    private final Kernal32 kernal32;
    private final Psapi psapi;
    private final Map<String, WinDef.HMODULE> moduleHandlePool;

    private int pointerSize = 4;
    private WinNT.HANDLE executableHandle = null;

    @Inject
    public MemoryApiImpl(Kernal32 kernal32, Psapi psapi){
        this.kernal32 = kernal32;
        this.psapi = psapi;

        moduleHandlePool = new HashMap<>();
    }

    @Override
    public void setPointerSize(int size) {
        if (size != 2 && size != 4 && size != 8)
            throw new IllegalArgumentException(INVALID_POINTER_SIZE);
        pointerSize = size;
    }

    @Override
    public void bindToExecutable(String executableName) throws RuntimeException {
        if (executableHandle != null)
            throw new IllegalStateException(ALREADY_BOUND_TO_EXECUTABLE_ERROR);

        // First, get the running processes
        Map<String, List<Integer>> processes;
        try{
            processes = ProcessUtils.getRunningProcesses();
        } catch (IOException e) {
            throw new RuntimeException(STR."Something went wrong while searching for \{executableName}.", e);
        }

        // Check if the running processes contains the one we're looking for, if not throw exception
        if (!processes.containsKey(executableName)) {
            throw new RuntimeException(STR."Could not find \{executableName} running!");
        }

        // Multiple instances of the executable may be running; if so we first inform the user that we'll be choosing the first one
        if (processes.get(executableName).size() > 1){
            System.out.println(STR."Multiple instances of \{executableName} are running, choosing first one.");
        }
        int pid = processes.get(executableName).get(0);

        // Get and store the handle
        executableHandle = kernal32.OpenProcess(Kernal32.PROCESS_ALL_ACCESS, false, pid);
    }

    @Override
    public WinNT.HANDLE getExecutableHandle() {
        if (executableHandle == null)
            throw new IllegalStateException(NOT_BOUND_TO_EXECUTABLE_ERROR);
        return executableHandle;
    }

    @Override
    public WinDef.HMODULE getModuleHandle(String moduleName) throws RuntimeException {
        if (executableHandle == null)
            throw new IllegalStateException(NOT_BOUND_TO_EXECUTABLE_ERROR);

        // Get handle if already found
        if (moduleHandlePool.containsKey(moduleName))
            return moduleHandlePool.get(moduleName);

        // Find handle
        WinDef.HMODULE moduleHandle = null; // This variable is where we store the desired handle
        WinDef.HMODULE[] modules = new WinDef.HMODULE[ARRAY_SIZE]; // This will be populated with all modules loaded by the executable
        psapi.EnumProcessModules(executableHandle, modules, modules.length, new IntByReference()); // Grab loaded modules
        // Search through all loaded modules for the one we want
        for (WinDef.HMODULE module : modules){
            // We use this char array as a buffer to load the module base name
            char[] name = new char[ARRAY_SIZE];
            int lengthInBuffer = psapi.GetModuleBaseNameW(executableHandle, module, name, 2048);
            if (lengthInBuffer == 0) // 0 means something went wrong
                continue;

            // Check if it's a match
            if (new String(name).substring(0, lengthInBuffer).equals(moduleName)){
                moduleHandle = module; // Matched; write and break!
                break;
            }
        }

        // Should still be null if the handle was not found
        if (moduleHandle == null)
            throw new RuntimeException(STR."Could not find module handle for \{moduleName}");

        // Add to pool and return
        moduleHandlePool.put(moduleName, moduleHandle); // We pool because bad devs don't know to store the handle in a variable
        return moduleHandle;
    }

    @Override
    public long getModuleBaseAddress(String moduleName) throws RuntimeException {
        return Pointer.nativeValue(getModuleHandle(moduleName).getPointer());
    }

    @Override
    public long readPointer(long address) {
        // We read different amounts depending on the pointer size used for the program
        return switch (pointerSize){
            case 2 -> readShort(address);
            case 4 -> readInt(address);
            case 8 -> readLong(address);
            default -> throw new IllegalStateException(INVALID_POINTER_SIZE);
        };
    }

    @Override
    public Memory readMemory(long address, int size) {
        if (executableHandle == null)
            throw new IllegalStateException(NOT_BOUND_TO_EXECUTABLE_ERROR);
        Memory memory = new Memory(size);
        kernal32.ReadProcessMemory(executableHandle, new com.sun.jna.Pointer(address), memory, size);
        return memory;
    }

    @Override
    public short readShort(long address) {
        Memory buffer = readMemory(address, 2);
        short readValue = buffer.getShort(0);
        buffer.close();
        return readValue;
    }

    @Override
    public int readInt(long address) {
        Memory buffer = readMemory(address, 4);
        int readValue = buffer.getInt(0);
        buffer.close();
        return readValue;
    }

    @Override
    public long readLong(long address) {
        Memory buffer = readMemory(address, 8);
        long readValue = buffer.getLong(0);
        buffer.close();
        return readValue;
    }

    @Override
    public String readString(long address, int length) {
        return readString(address, length, "UTF-8");
    }

    @Override
    public String readString(long address, int length, String encoding){
        Memory buffer = readMemory(address, length);
        String readValue = buffer.getString(0, encoding);
        buffer.close();
        return readValue.intern();
    }

    @Override
    public String readWideString(long address, int length) {
        Memory buffer = readMemory(address, length);
        String readValue = buffer.getWideString(0);
        buffer.close();
        return readValue.intern();
    }

    @Override
    public long processOffsets(String moduleName, long firstAddress, long... offsets) throws RuntimeException {
        return processOffsets(getModuleBaseAddress(moduleName) + firstAddress, offsets); // Just makes it take slightly less work
    }

    @Override
    public long processOffsets(long address, long... offsets) {

        // Go through every offset to find the final address
        for (long offset : offsets){
            // Dereference pointer
            address = readPointer(address);
            // Add next offset
            address += offset;
        }

        // Return the final offset
        return address;
    }

}
