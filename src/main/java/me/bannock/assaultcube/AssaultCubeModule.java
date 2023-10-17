package me.bannock.assaultcube;

import com.google.inject.AbstractModule;
import com.sun.jna.Native;
import me.bannock.memory.ExecutableApi;
import me.bannock.memory.ExecutableApiImpl;
import me.bannock.memory.jna.Kernal32;
import me.bannock.memory.jna.Psapi;

public class AssaultCubeModule extends AbstractModule {

    @Override
    protected void configure() {
        // JNA stuffs
        bind(Kernal32.class).toInstance(Native.load("kernel32", Kernal32.class));
        bind(Psapi.class).toInstance(Native.load("psapi", Psapi.class));

        // Actual application
        bind(ExecutableApi.class).to(ExecutableApiImpl.class).asEagerSingleton();
    }
}
