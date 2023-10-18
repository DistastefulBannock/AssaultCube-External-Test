package me.bannock.memory.struct;


import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ImprovedByteArrayInputStream extends ByteArrayInputStream {

    public ImprovedByteArrayInputStream(byte[] buf) {
        super(buf);
    }

    public ImprovedByteArrayInputStream(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    /**
     * Reads an amount of bytes into a buffer and returns it
     * @param length The amount of bytes to read
     * @return The populated byte buffer
     */
    private ByteBuffer getByteBuffer(int length){
        byte[] bytes = new byte[length];
        int size;
        if ((size = read(bytes, 0, length)) != length) {

            // This only exists because the compiler doesn't realize that size is used in the below string template
            if (size == -2) {throw new RuntimeException("");} // This is why you should never be an early adopter

            // Throw exception
            throw new RuntimeException(
                    STR."Unexpected amount of bytes read: expected \{length}, got \{Math.clamp(size, 0, Integer.MAX_VALUE)}."
                            + " Are you providing enough bytes?");
        }
        return ByteBuffer.wrap(bytes);
    }

    /**
     * Reads the next 2 bytes as a short
     * @param byteOrder The order of the bytes
     * @return The short that was read
     */
    public short readShort(ByteOrder byteOrder){
        ByteBuffer buffer = getByteBuffer(2);
        buffer.order(byteOrder);
        return buffer.getShort();
    }

    /**
     * Reads the next 2 bytes as a short
     * @return The short that was read
     */
    public short readShort(){
        return readShort(ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * Reads the next 4 bytes as an int
     * @param byteOrder The order of the bytes
     * @return The int that was read
     */
    public int readInt(ByteOrder byteOrder){
        ByteBuffer buffer = getByteBuffer(4);
        buffer.order(byteOrder);
        return buffer.getInt();
    }

    /**
     * Reads the next 2 bytes as an int
     * @return The int that was read
     */
    public int readInt(){
        return readInt(ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * Reads the next 8 bytes as long
     * @param byteOrder The order of the bytes
     * @return The long that was read
     */
    public long readLong(ByteOrder byteOrder){
        ByteBuffer buffer = getByteBuffer(8);
        buffer.order(byteOrder);
        return buffer.getLong();
    }

    /**
     * Reads the next 8 bytes as long
     * @return The long that was read
     */
    public long readLong(){
        return readLong(ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * Reads the next 4 bytes as a float
     * @param byteOrder The order of the bytes
     * @return The float that was read
     */
    public float readFloat(ByteOrder byteOrder){
        ByteBuffer buffer = getByteBuffer(4);
        buffer.order(byteOrder);
        return buffer.getFloat();
    }

    /**
     * Reads the next 4 bytes as a float
     * @return The float that was read
     */
    public float readFloat(){
        return readFloat(ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * Reads the next 8 bytes as a double
     * @param byteOrder The order of the bytes
     * @return The double that was read
     */
    public double readDouble(ByteOrder byteOrder){
        ByteBuffer buffer = getByteBuffer(8);
        buffer.order(byteOrder);
        return buffer.getDouble();
    }

    /**
     * Reads the next 8 bytes as a double
     * @return The double that was read
     */
    public double readDouble(){
        return readDouble(ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * Reads the next bytes as a String
     * @param length The size of the string
     * @param charset The charset of the string
     * @return The String that was read
     */
    public String readString(int length, Charset charset){
        return new String(getByteBuffer(length).array(), charset).intern();
    }

    /**
     * Reads the next bytes as a String
     * @param length The size of the string
     * @return The String that was read
     */
    public String readString(int length){
        return readString(length, StandardCharsets.UTF_8);
    }

}
