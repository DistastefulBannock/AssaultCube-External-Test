package me.bannock.assaultcube.structs;

import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.ImprovedByteArrayInputStream;
import me.bannock.memory.struct.StructHelper;

import java.io.IOException;

public class TestStruct extends StructHelper {

    private int health;

    /**
     * Creates a new test struct
     * @param memoryApi The memory api to use
     * @param address   The address of the struct
     */
    public TestStruct(MemoryApi memoryApi, long address) {
        super(memoryApi, address, 8);
    }

    @Override
    protected void initialize(ImprovedByteArrayInputStream helper) throws IOException {
        this.health = helper.readInt();
        helper.skipNBytes(4);
    }

    public int getHealth() {
        return health;
    }

}
