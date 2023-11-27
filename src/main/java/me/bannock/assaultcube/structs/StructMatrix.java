package me.bannock.assaultcube.structs;

import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.MappingAssistant;
import me.bannock.memory.struct.StructMapper;

import java.util.Arrays;

public class StructMatrix extends StructMapper {

    private final int sizeX, sizeY;
    private float[][] matrix;

    public StructMatrix(MemoryApi memoryApi, long address, int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        startMapping(memoryApi, address, sizeX * sizeY * 4);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        matrix = new float[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++){
            for (int y = 0; y < sizeY; y++){
                matrix[x][y] = assistant.readFloat();
            }
        }
    }

    @Override
    public String toString() {
        return "StructMatrix{" +
                "sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", matrix=" + Arrays.toString(matrix) +
                '}';
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public float[][] getMatrix() {
        return matrix;
    }
}
