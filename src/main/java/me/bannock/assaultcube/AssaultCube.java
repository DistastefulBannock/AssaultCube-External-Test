package me.bannock.assaultcube;


import com.google.inject.Guice;
import com.google.inject.Inject;
import me.bannock.memory.ExecutableApi;
import me.bannock.memory.jna.JnaGuiceModule;

public class AssaultCube {

    private final ExecutableApi executableApi;

    @Inject
    public AssaultCube(ExecutableApi executableApi){
        this.executableApi = executableApi;
    }

    @SuppressWarnings("")
    public void run() {
        try {
            executableApi.connectToExecutable("ac_client.exe");

            // print health
            long pollUntil = System.currentTimeMillis() + 120000;
            while (System.currentTimeMillis() < pollUntil){
                System.out.println(executableApi.readInt(0x00840C04));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Guice.createInjector(new JnaGuiceModule(), new AssaultCubeModule()).getInstance(AssaultCube.class).run();
    }

}
