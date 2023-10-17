package me.bannock.assaultcube.jna;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;

public interface Psapi extends com.sun.jna.platform.win32.Psapi {

    /**
     * Retrieves a handle for each module in the specified process.
     * @param hProcess A handle to the process.
     * @param lphModule An array that receives the list of module handles.
     * @param cb The size of the lphModule array, in bytes.
     * @param lpcbNeeded The number of bytes required to store all module handles in the lphModule array.
     * @return If the function succeeds, the return value is nonzero.
     */
    boolean EnumProcessModules(WinNT.HANDLE hProcess, WinDef.HMODULE[] lphModule, int cb, IntByReference lpcbNeeded);

    /**
     * @param hProcess A handle to the process that contains the module.
     *                 The handle must have the PROCESS_QUERY_INFORMATION and PROCESS_VM_READ access rights. For more information, see Process Security and Access Rights.
     * @param hModule A handle to the module. If this parameter is NULL, this function returns the name of the file used to create the calling process.
     * @param lpImageFileName A pointer to the buffer that receives the base name of the module. If the base name is longer than maximum number of characters specified by the nSize parameter, the base name is truncated.
     * @param nSize The size of the lpBaseName buffer, in characters.
     * @return If the function succeeds, the return value specifies the length of the string copied to the buffer, in characters.
     *         If the function fails, the return value is zero. To get extended error information, call GetLastError.
     */
    int GetModuleBaseNameA(WinNT.HANDLE hProcess, WinDef.HMODULE hModule, byte[] lpImageFileName, int nSize);

    /**
     * @param hProcess A handle to the process that contains the module.
     *                 The handle must have the PROCESS_QUERY_INFORMATION and PROCESS_VM_READ access rights. For more information, see Process Security and Access Rights.
     * @param hModule A handle to the module. If this parameter is NULL, this function returns the name of the file used to create the calling process.
     * @param lpImageFileName A pointer to the buffer that receives the base name of the module. If the base name is longer than maximum number of characters specified by the nSize parameter, the base name is truncated.
     * @param nSize The size of the lpBaseName buffer, in characters.
     * @return If the function succeeds, the return value specifies the length of the string copied to the buffer, in characters.
     *         If the function fails, the return value is zero. To get extended error information, call GetLastError.
     */
    int GetModuleBaseNameW(WinNT.HANDLE hProcess, WinDef.HMODULE hModule, char[] lpImageFileName, int nSize);

}
