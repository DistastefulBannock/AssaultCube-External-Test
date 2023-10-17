package me.bannock.assaultcube;

import com.google.inject.AbstractModule;
import me.bannock.memory.ExecutableApi;
import me.bannock.memory.ExecutableApiImpl;

public class AssaultCubeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExecutableApi.class).to(ExecutableApiImpl.class).asEagerSingleton();
    }
}
