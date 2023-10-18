package me.bannock.assaultcube.structs.entity;

import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.StructMapper;
import me.bannock.memory.struct.MappingAssistant;

public class StructEntityVector extends StructMapper {

    private StructEntityLinkedList entities;
    private int maxEntityCount, entityCount;

    public StructEntityVector(MemoryApi memoryApi, long address) {
        startMapping(memoryApi, address, 12);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        long listPointer = assistant.readPointer();
        maxEntityCount = assistant.readInt();
        entityCount = assistant.readInt();
        entities = new StructEntityLinkedList(assistant.getMemoryApi(), listPointer,
                this);
    }

    public int getMaxEntityCount() {
        return maxEntityCount;
    }

    public int getEntityCount() {
        return entityCount;
    }

    public StructEntity[] getEntities() {
        return entities.getEntities();
    }
}
