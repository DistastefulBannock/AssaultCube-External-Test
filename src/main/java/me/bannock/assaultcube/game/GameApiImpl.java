package me.bannock.assaultcube.game;

import com.google.inject.Inject;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import me.bannock.assaultcube.jna.Kernal32;
import me.bannock.assaultcube.jna.Psapi;
import me.bannock.assaultcube.utils.ProcessUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.StringTemplate.STR;

public class GameApiImpl implements GameApi {

    private static final int ARRAY_SIZE = 2048;

    private final Kernal32 kernal32;
    private final Psapi psapi;
    private final Map<String, WinDef.HMODULE> moduleHandles;
    private WinNT.HANDLE executableHandle = null;

    @Inject
    public GameApiImpl(Kernal32 kernal32, Psapi psapi){
        this.kernal32 = kernal32;
        this.psapi = psapi;

        moduleHandles = new HashMap<>();
    }

    @Override
    public boolean connectToGame(String executableName) throws Exception {
        Map<String, List<Integer>> processes = ProcessUtils.getRunningProcesses();
        if (!processes.containsKey(executableName)) {
            throw new RuntimeException(STR."Could not find \{executableName} running!");
        }
        if (processes.get(executableName).size() > 1){
            System.out.println(STR."Multiple instances of \{executableName} are running, choosing first one.");
        }
        int pid = processes.get(executableName).get(0);
        executableHandle = kernal32.OpenProcess(Kernal32.PROCESS_ALL_ACCESS, false, pid);
        return true;
    }

    @Override
    public WinDef.HMODULE getModuleHandle(String moduleName) throws Exception {
        if (executableHandle == null)
            throw new IllegalStateException("Must connect to executable before getting module handle!");

        // Get handle if already found
        if (moduleHandles.containsKey(moduleName))
            return moduleHandles.get(moduleName);

        // Find handle
        WinDef.HMODULE moduleHandle = null;
        WinDef.HMODULE[] modules = new WinDef.HMODULE[ARRAY_SIZE];
        IntByReference bytesNeededToStore = new IntByReference();
        psapi.EnumProcessModules(executableHandle, modules, modules.length, bytesNeededToStore);
        // Search through all loaded modules for the one we want
        for (WinDef.HMODULE module : modules){
            char[] name = new char[ARRAY_SIZE];
            int lengthInBuffer = psapi.GetModuleBaseNameW(executableHandle, module, name, 2048);
            if (lengthInBuffer == 0)
                continue;
            String tmpModuleName = new String(name).substring(0, lengthInBuffer);
            if (tmpModuleName.equals(moduleName)){
                moduleHandle = module;
                break;
            }
        }

        // Check if found
        if (moduleHandle == null)
            throw new RuntimeException(STR."Could not find module handle for \{moduleName}");

        // Cache and return
        moduleHandles.put(moduleName, moduleHandle);
        return moduleHandle;
    }
}
