package me.bannock.memory.jna;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface Kernal32 extends com.sun.jna.platform.win32.Kernel32{

    /**
     *
     * @param hProcess A handle to the process with memory that is being read. The handle
     *                 must have PROCESS_VM_READ access to the process.
     * @param lpBaseAddress A pointer to the base address in the specified process from which
     *                      to read. Before any data transfer occurs,
     *                      the system verifies that all data in the base address and
     *                      memory of the specified size is accessible for read access,
     *                      and if it is not accessible the function fails.
     * @param lpBuffer A pointer to a buffer that receives the contents from the
     *                 address space of the specified process.
     * @param nSize The number of bytes to be read from the specified process.
     * @param lpNumberOfBytesRead A pointer to a variable that receives the number of
     *                            bytes transferred into the specified buffer. If
     *                            lpNumberOfBytesRead is NULL, the parameter is ignored.
     * @return If the function succeeds, the return value is nonzero.
     *         If the function fails, the return value is 0 (zero). To get
     *         extended error information, call GetLastError.
     *         The function fails if the requested read operation crosses
     *         into an area of the process that is inaccessible.
     */
    boolean ReadProcessMemory(HANDLE hProcess, Pointer lpBaseAddress,
                              Pointer lpBuffer, int nSize, IntByReference lpNumberOfBytesRead);

    /**
     *
     * @param hProcess A handle to the process with memory that is being read. The handle
     *                 must have PROCESS_VM_READ access to the process.
     * @param lpBaseAddress A pointer to the base address in the specified process from which
     *                      to read. Before any data transfer occurs,
     *                      the system verifies that all data in the base address and
     *                      memory of the specified size is accessible for read access,
     *                      and if it is not accessible the function fails.
     * @param lpBuffer A pointer to a buffer that receives the contents from the
     *                 address space of the specified process.
     * @param nSize The number of bytes to be read from the specified process.
     * @return If the function succeeds, the return value is nonzero.
     *         If the function fails, the return value is 0 (zero). To get
     *         extended error information, call GetLastError.
     *         The function fails if the requested read operation crosses
     *         into an area of the process that is inaccessible.
     */
    @CanIgnoreReturnValue
    default boolean ReadProcessMemory(HANDLE hProcess, Pointer lpBaseAddress,
                                      Pointer lpBuffer, int nSize){
        return ReadProcessMemory(hProcess, lpBaseAddress, lpBuffer, nSize, null);
    }

}
