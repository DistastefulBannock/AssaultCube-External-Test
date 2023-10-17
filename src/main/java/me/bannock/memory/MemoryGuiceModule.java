package me.bannock.memory;

import com.google.inject.AbstractModule;
import com.sun.jna.Native;
import me.bannock.memory.jna.Kernal32;
import me.bannock.memory.jna.Psapi;

public class MemoryGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Kernal32.class).toInstance(Native.load("kernel32", Kernal32.class));
        bind(Psapi.class).toInstance(Native.load("psapi", Psapi.class));
    }

}
