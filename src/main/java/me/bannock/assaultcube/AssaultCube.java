package me.bannock.assaultcube;


import com.google.inject.Guice;
import com.google.inject.Inject;
import me.bannock.memory.MemoryApi;
import me.bannock.memory.MemoryGuiceModule;

public class AssaultCube {

    private final MemoryApi memoryApi;

    @Inject
    public AssaultCube(MemoryApi memoryApi){
        this.memoryApi = memoryApi;
    }

    @SuppressWarnings("")
    public void run() {
        try {
            memoryApi.bindToExecutable("ac_client.exe");

            // print health
            long pollUntil = System.currentTimeMillis() + 120000;
            while (System.currentTimeMillis() < pollUntil){
                System.out.println("Health: " + memoryApi.readInt(
                        memoryApi.processOffsets("ac_client.exe", 0x0017F110, 0x0, 0xEC)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Guice.createInjector(new MemoryGuiceModule(), new AssaultCubeModule()).getInstance(AssaultCube.class).run();
    }

}
