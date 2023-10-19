package me.bannock.assaultcube.structs;

import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.MappingAssistant;
import me.bannock.memory.struct.StructMapper;

import javax.vecmath.Matrix4f;

public class StructMatrix4x4 extends StructMapper {

    private Matrix4f matrix;

    public StructMatrix4x4(MemoryApi memoryApi, long address){
        startMapping(memoryApi, address, 64);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        float[] tmp = new float[16];
        for (int i = 0; i < 16; i++){
            tmp[i] = assistant.readFloat();
        }
        matrix = new Matrix4f(tmp);
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        return "StructMatrix4x4{" +
                "matrix=" + matrix +
                '}';
    }
}
