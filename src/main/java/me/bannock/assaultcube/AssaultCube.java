package me.bannock.assaultcube;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.sun.jna.platform.win32.WinNT;
import me.bannock.memory.ExecutableApi;
import me.bannock.memory.jna.Kernal32;

import java.io.IOException;

public class AssaultCube {

    private final ExecutableApi executableApi;

    @Inject
    public AssaultCube(ExecutableApi executableApi){
        this.executableApi = executableApi;
    }

    public void run() {
        try {
            executableApi.connectToExecutable("ac_client.exe");
//            System.out.println(Long.toHexString(Pointer.nativeValue(executableApi.getModuleHandle("ac_client.exe").getPointer())));
            WinNT.HANDLE handle = executableApi.getExecutableHandle();

            // print health
            long pollUntil = System.currentTimeMillis() + 120000;
            while (System.currentTimeMillis() < pollUntil){
                System.out.println(executableApi.readInt(0x00840C04));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Guice.createInjector(new AssaultCubeModule()).getInstance(AssaultCube.class).run();
    }

}
