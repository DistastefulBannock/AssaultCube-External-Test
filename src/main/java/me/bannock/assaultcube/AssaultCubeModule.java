package me.bannock.assaultcube;

import com.google.inject.AbstractModule;
import me.bannock.memory.MemoryApi;
import me.bannock.memory.MemoryApiImpl;

public class AssaultCubeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MemoryApi.class).to(MemoryApiImpl.class).asEagerSingleton();
    }
}
