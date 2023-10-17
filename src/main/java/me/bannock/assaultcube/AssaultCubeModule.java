package me.bannock.assaultcube;

import com.google.inject.AbstractModule;
import com.sun.jna.Native;
import me.bannock.assaultcube.game.GameApi;
import me.bannock.assaultcube.game.GameApiImpl;
import me.bannock.assaultcube.jna.Kernal32;
import me.bannock.assaultcube.jna.Psapi;

public class AssaultCubeModule extends AbstractModule {

    @Override
    protected void configure() {
        // JNA stuffs
        bind(Kernal32.class).toInstance(Native.load("kernel32", Kernal32.class));
        bind(Psapi.class).toInstance(Native.load("psapi", Psapi.class));

        // Actual application
        bind(GameApi.class).to(GameApiImpl.class).asEagerSingleton();
    }
}
