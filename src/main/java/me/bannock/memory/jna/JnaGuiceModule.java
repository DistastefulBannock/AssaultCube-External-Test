package me.bannock.memory.jna;

import com.google.inject.AbstractModule;
import com.sun.jna.Native;

public class JnaGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Kernal32.class).toInstance(Native.load("kernel32", Kernal32.class));
        bind(Psapi.class).toInstance(Native.load("psapi", Psapi.class));
    }

}
