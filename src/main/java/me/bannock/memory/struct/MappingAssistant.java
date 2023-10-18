package me.bannock.memory.struct;

import me.bannock.memory.MemoryApi;

public class MappingAssistant {

    private final MemoryApi memoryApi;
    private final long address, structSize;
    private long offset;

    /**
     * Creates a new mapping assistant
     * @param address The address of the struct
     * @param structSize The size of the struct in bytes
     */
    public MappingAssistant(MemoryApi memoryApi, long address, int structSize){
        this.memoryApi = memoryApi;
        this.address = address;
        this.structSize = structSize;
        offset = 0;
    }

    /**
     * Offsets the peer address in our memory object to get a new address
     * @param offset The offset to add
     * @return The old address
     */
    private long offsetAddress(long offset){
        this.offset += offset;
        if (this.offset > structSize)
            throw new RuntimeException("Offset is greater than struct size. " +
                    "Is the struct size large enough?");
        return address + this.offset - offset;
    }

    /**
     * Skips a number of bytes. Used if you don't know what some bytes are for in your remapping
     * @param bytes The number of bytes to skip
     */
    public void skip(int bytes){
        offsetAddress(bytes);
    }

    /**
     * Reads the next byte
     * @return The byte read
     */
    public byte readByte(){
        return memoryApi.readByte(offsetAddress(1));
    }

    /**
     * Reads a number of bytes from the struct
     * @param size The number of bytes to read
     * @return The bytes read
     */
    public byte[] readBytes(int size){
        return memoryApi.readBytes(offsetAddress(size), size);
    }

    /**
     * Reads a pointer
     * @return The pointer read
     */
    public long readPointer(){
        return memoryApi.readPointer(offsetAddress(memoryApi.getPointerSize()));
    }

    /**
     * Similar to readPointer, but does not dereference the address
     * @param offsetSize The size of the offset. Used so it skips the correct number of bytes
     * @return The current address
     */
    public long getAddress(long offsetSize){
        return offsetAddress(offsetSize);
    }

    /**
     * Reads the next 2 bytes as a short
     * @return The short read
     */
    public short readShort(){
        return memoryApi.readShort(offsetAddress(2));
    }

    /**
     * Reads the next 4 bytes as an int
     * @return The int read
     */
    public int readInt(){
        return memoryApi.readInt(offsetAddress(4));
    }

    /**
     * Reads the next 8 bytes as a long
     * @return The long read
     */
    public long readLong() {
        return memoryApi.readLong(offsetAddress(8));
    }

    /**
     * Reads the next 4 bytes as a float
     * @return The float read
     */
    public float readFloat(){
        return memoryApi.readFloat(offsetAddress(4));
    }

    /**
     * Reads the next 8 bytes as a double
     * @return The double read
     */
    public double readDouble(){
        return memoryApi.readDouble(offsetAddress(8));
    }

    /**
     * Reads the next bytes as a string
     * @param length The length of the string
     * @param encoding The encoding of the string
     * @return The string read
     */
    public String readString(int length, String encoding){
        return memoryApi.readString(offsetAddress(length), length);
    }

    /**
     * Reads the next bytes as a UTF-8 string
     * @param length The length of the string
     * @return The string read
     */
    public String readString(int length){
        return memoryApi.readString(offsetAddress(length), length);
    }

    /**
     * Reads the next bytes as a wide string
     * @param length The amount of bytes to read for the string
     * @return The string read
     */
    public String readWideString(int length){
        return memoryApi.readWideString(offsetAddress(length), length);
    }

    /**
     * Reads the next byte as a boolean
     * @return The boolean read
     */
    public boolean readBoolean(){
        return memoryApi.readBoolean(offsetAddress(1));
    }

    public MemoryApi getMemoryApi() {
        return memoryApi;
    }

    public long getStructSize() {
        return structSize;
    }

}
