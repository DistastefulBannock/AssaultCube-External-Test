package me.bannock.assaultcube.structs.entity;

import me.bannock.assaultcube.structs.StructVec3;
import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.StructMapper;
import me.bannock.memory.struct.MappingAssistant;

public class StructEntity extends StructMapper {

    private StructVec3 motion, position, rotation;
    private int health;

    public StructEntity(MemoryApi memoryApi, long address) {
        startMapping(memoryApi, address, 0xF0);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        assistant.skip(12);
        motion = new StructVec3(assistant.getMemoryApi(),
                assistant.getAddress(12));
        assistant.skip(24);
        position = new StructVec3(assistant.getMemoryApi(),
                assistant.getAddress(12));
        rotation = new StructVec3(assistant.getMemoryApi(),
                assistant.getAddress(12));
        assistant.skip(0xA4);
        health = assistant.readInt();
    }

    public StructVec3 getMotion() {
        return motion;
    }

    public StructVec3 getPosition() {
        return position;
    }

    public StructVec3 getRotation() {
        return rotation;
    }

    public int getHealth() {
        return health;
    }

}
