package me.bannock.assaultcube.structs;

import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.MappingAssistant;
import me.bannock.memory.struct.StructMapper;

import java.util.Arrays;

public class StructVector extends StructMapper {

    private final int size;
    private float[] vector;

    public StructVector(MemoryApi memoryApi, long address, int size){
        this.size = size;
        startMapping(memoryApi, address, size * 4);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        vector = new float[size];
        for (int i = 0; i < size; i++){
            vector[i] = assistant.readFloat();
        }
    }

    @Override
    public String toString() {
        return "StructVector{" +
                "size=" + size +
                ", vector=" + Arrays.toString(vector) +
                '}';
    }

    public int getSize() {
        return size;
    }

    public float[] getVector() {
        return vector;
    }

    /**
     * Appends a new element to this vector
     * <strong>Warning: Will change the amount of bytes this array takes up</strong>
     * @param value The value to append
     */
    public void addNewElement(float value){
        vector = Arrays.copyOf(vector, vector.length + 1);
        vector[vector.length - 1] = value;
    }

    /**
     * Removes the final element from this vector
     * <strong>Warning: Will change the amount of bytes this array takes up</strong>
     */
    public void removeLastElement(){
        if (vector.length == 0)
            throw new IllegalStateException("Cannot remove element from empty vector");
        vector = Arrays.copyOf(vector, vector.length - 1);
    }

}
