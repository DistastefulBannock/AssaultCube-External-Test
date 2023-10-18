package me.bannock.memory.struct;

import me.bannock.memory.MemoryApi;

public abstract class StructMapper {

    /**
     * Call when you want to start mapping the struct. Will read the memory and pass an assistant to initialize.
     * @param memoryApi The memory api to use
     * @param address The address of the struct
     * @param structSize The size of the struct in bytes
     */
    protected void startMapping(MemoryApi memoryApi, long address, int structSize){
        initialize(new MappingAssistant(memoryApi, address, structSize));
    }

    protected abstract void initialize(MappingAssistant assistant);

}
