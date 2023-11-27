package me.bannock.assaultcube;


import com.google.inject.Guice;
import com.google.inject.Inject;
import me.bannock.assaultcube.structs.StructMatrix;
import me.bannock.assaultcube.structs.StructVector;
import me.bannock.assaultcube.structs.entity.StructEntity;
import me.bannock.memory.MemoryApi;
import me.bannock.memory.MemoryGuiceModule;

public class AssaultCube implements Runnable {

    private final MemoryApi memoryApi;

    @Inject
    public AssaultCube(MemoryApi memoryApi){
        this.memoryApi = memoryApi;
    }

    @Override
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
                StructMatrix viewMatrix = new StructMatrix(memoryApi,
                        memoryApi.processOffsets("ac_client.exe", 0x000FDEC8, 0x0),
                        4, 4);
                System.out.println("\n" + viewMatrix);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a world position to a screen position
     * <strong>Warning: Changes the memory size of the worldPos vector (4 extra bytes)</strong>
     * @param worldPos
     * @param viewMatrix
     * @param localPlayer
     * @param screenSize
     * @return
     */
    private int[] worldToScreen(StructVector worldPos, StructMatrix viewMatrix,
                                StructEntity localPlayer, int[] screenSize){
        if (worldPos.getSize() < 3)
            throw new IllegalArgumentException("worldPos must have at least 3 elements");
        if (viewMatrix.getSizeX() != 4 || viewMatrix.getSizeY() != 4)
            throw new IllegalArgumentException("viewMatrix must be 4x4");
        if (screenSize.length != 2)
            throw new IllegalArgumentException("screenSize must have 2 elements");
        if (worldPos.getSize() == 3)
            worldPos.addNewElement(1);

        // TODO: implement

        return null;
    }

    public static void main(String[] args) {
        Guice.createInjector(new MemoryGuiceModule(), new AssaultCubeModule()).getInstance(AssaultCube.class).run();
    }

}
