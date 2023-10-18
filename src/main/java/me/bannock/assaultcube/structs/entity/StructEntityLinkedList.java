package me.bannock.assaultcube.structs.entity;

import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.StructMapper;
import me.bannock.memory.struct.MappingAssistant;

public class StructEntityLinkedList extends StructMapper {

    private final StructEntityVector vector;
    private final StructEntity[] entities;

    public StructEntityLinkedList(MemoryApi memoryApi, long address, StructEntityVector vector) {
        this.vector = vector;
        this.entities = new StructEntity[vector.getMaxEntityCount()];
        startMapping(memoryApi, address, 4 + vector.getMaxEntityCount() * 4);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        assistant.skip(4);
        for (int i = 0; i < vector.getEntityCount(); i++){
            entities[i] = new StructEntity(assistant.getMemoryApi(), assistant.readPointer());
        }
    }

    public StructEntity[] getEntities() {
        return entities;
    }

}
