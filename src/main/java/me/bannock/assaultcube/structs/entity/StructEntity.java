package me.bannock.assaultcube.structs.entity;

import me.bannock.assaultcube.structs.StructVec3;
import me.bannock.memory.MemoryApi;
import me.bannock.memory.struct.StructMapper;
import me.bannock.memory.struct.MappingAssistant;

public class StructEntity extends StructMapper {

    private StructVec3 eyePos, motion, pos, rot;
    private float recoil, fat, height, weight;
    private int health;

    public StructEntity(MemoryApi memoryApi, long address) {
        startMapping(memoryApi, address, 0xBC);
    }

    @Override
    protected void initialize(MappingAssistant assistant) {
        /*
        From ReClass.net:
        char pad_0000[4]; //0x0000
        Vector3 eyePosition; //0x0004
        Vector3 motion; //0x0010
        char pad_001C[12]; //0x001C
        Vector3 position; //0x0028
        Vector3 rotation; //0x0034
        float recoil; //0x0040
        char pad_0044[8]; //0x0044
        float fat; //0x004C
        float height; //0x0050
        float weight; //0x0054
        char pad_0058[100]; //0x0058
        int32_t health; //0x00BC
        */
        assistant.skip(4);
        eyePos = new StructVec3(assistant.getMemoryApi(), assistant.readPointer());
        motion = new StructVec3(assistant.getMemoryApi(), assistant.readPointer());
        assistant.skip(12);
        pos = new StructVec3(assistant.getMemoryApi(), assistant.readPointer());
        rot = new StructVec3(assistant.getMemoryApi(), assistant.readPointer());
        recoil = assistant.readFloat();
        assistant.skip(8);
        fat = assistant.readFloat();
        height = assistant.readFloat();
        weight = assistant.readFloat();
        assistant.skip(100);
        health = assistant.readInt();
    }

    public StructVec3 getEyePos() {
        return eyePos;
    }

    public StructVec3 getMotion() {
        return motion;
    }

    public StructVec3 getPos() {
        return pos;
    }

    public StructVec3 getRot() {
        return rot;
    }

    public float getRecoil() {
        return recoil;
    }

    public float getFat() {
        return fat;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public int getHealth() {
        return health;
    }
}
