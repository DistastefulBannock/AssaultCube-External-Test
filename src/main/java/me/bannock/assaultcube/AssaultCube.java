package me.bannock.assaultcube;


import com.google.inject.Guice;
import com.google.inject.Inject;
import me.bannock.assaultcube.structs.TestStruct;
import me.bannock.memory.MemoryApi;
import me.bannock.memory.MemoryGuiceModule;

public class AssaultCube {

    private final MemoryApi memoryApi;

    @Inject
    public AssaultCube(MemoryApi memoryApi){
        this.memoryApi = memoryApi;
    }

    public void run() {
        try {
            memoryApi.bindToExecutable("ac_client.exe");
            memoryApi.setPointerSize(4);

            // print health
            long pollUntil = System.currentTimeMillis() + 120000;
            while (System.currentTimeMillis() < pollUntil){
                System.out.println("Health: " + memoryApi.readInt(
                        memoryApi.processOffsets("ac_client.exe", 0x0017F110, 0x0, 0xEC)
                ));
                TestStruct testStruct = new TestStruct(memoryApi, memoryApi.processOffsets("ac_client.exe", 0x0017F110, 0x0, 0xEC));
                System.out.println(testStruct.getHealth());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Guice.createInjector(new MemoryGuiceModule(), new AssaultCubeModule()).getInstance(AssaultCube.class).run();
    }

}
