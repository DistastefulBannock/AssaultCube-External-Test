package me.bannock.assaultcube.structs;

import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.MappingAssistant;
import me.bannock.memory.struct.StructMapper;

public class StructVec3 extends StructMapper {

    private float x, y, z;

    public StructVec3(MemoryApi memoryApi, long address){
        startMapping(memoryApi, address, 12);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        x = assistant.readFloat();
        y = assistant.readFloat();
        z = assistant.readFloat();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "StructVec3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}
