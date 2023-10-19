package me.bannock.assaultcube;


import com.google.inject.Guice;
import com.google.inject.Inject;
import me.bannock.assaultcube.structs.StructMatrix4x4;
import me.bannock.assaultcube.structs.StructVec3;
import me.bannock.assaultcube.structs.entity.StructEntity;
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
            memoryApi.openHandle("ac_client.exe");
            memoryApi.setPointerSize(4);

            // print health
            long pollUntil = System.currentTimeMillis() + 120000;
            while (System.currentTimeMillis() < pollUntil){
//                System.out.println("Health: " + memoryApi.readInt(
//                        memoryApi.processOffsets("ac_client.exe", 0x0017F110, 0x0, 0xEC)
//                ));
//                StructEntityVector entities = new StructEntityVector(memoryApi,
//                        memoryApi.processOffsets("ac_client.exe", 0x18AC00));
//                System.out.println(entities.getLocalPlayer().getHealth() + "HP\n" +
//                        entities.getLocalPlayer().getPos());
                StructMatrix4x4 viewMatrix = new StructMatrix4x4(memoryApi,
                        memoryApi.processOffsets("ac_client.exe", 0x000FDEC8, 0x0));
                System.out.println("\n" + viewMatrix);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] worldToScreen(StructVec3 worldPos, StructMatrix4x4 viewMatrix, StructEntity localPlayer, int[] screenSize){
        // TODO: implement
//        viewMatrix.getMatrix().mul
        return null;
    }

    public static void main(String[] args) {
        Guice.createInjector(new MemoryGuiceModule(), new AssaultCubeModule()).getInstance(AssaultCube.class).run();
    }

}
