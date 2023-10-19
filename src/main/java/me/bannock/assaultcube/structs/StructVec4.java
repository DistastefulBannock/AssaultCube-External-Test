package me.bannock.assaultcube.structs;

import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.MappingAssistant;
import me.bannock.memory.struct.StructMapper;

public class StructVec4 extends StructMapper {

    private float x, y, z, w;

    public StructVec4(MemoryApi memoryApi, long address){
        startMapping(memoryApi, address, 16);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        x = assistant.readFloat();
        y = assistant.readFloat();
        z = assistant.readFloat();
        w = assistant.readFloat();
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

    public float getW() {
        return w;
    }

    @Override
    public String toString() {
        return "StructVec4{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }

}
