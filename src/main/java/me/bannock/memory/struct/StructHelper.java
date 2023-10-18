package me.bannock.memory.struct;

import com.sun.jna.Memory;
import me.bannock.memory.MemoryApi;

import java.io.IOException;

public abstract class StructHelper {

    /**
     * Creates a new struct helper
     * @param memoryApi The memory api to use
     * @param address The address of the struct
     * @param byteSize The size of the struct in bytes
     */
    public StructHelper(MemoryApi memoryApi, long address, int byteSize){
        // Read bytes from the application's memory, then the user can jam those bytes into their fields
        try(Memory unmappedStruct = memoryApi.readMemory(address, byteSize)){

            // Put inside input stream to read from
            byte[] bytes = unmappedStruct.getByteArray(0, byteSize);
            try(ImprovedByteArrayInputStream bais = new ImprovedByteArrayInputStream(bytes)){
                // Have user initialize all their shit
                initialize(bais);
            } catch (IOException e){
                throw new RuntimeException("Something went wrong.", e);
            }

        }

    }

    /**
     * Initializes the fields used in your mapped struct
     * @param helper A stream that assists you in mapping the bytes to values
     */
    protected abstract void initialize(ImprovedByteArrayInputStream helper) throws IOException;

}
