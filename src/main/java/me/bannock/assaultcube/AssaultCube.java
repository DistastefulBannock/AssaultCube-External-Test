package me.bannock.assaultcube;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.sun.jna.Pointer;
import me.bannock.assaultcube.game.GameApi;

import java.io.IOException;

public class AssaultCube {

    private final GameApi gameApi;

    @Inject
    public AssaultCube(GameApi gameApi){
        this.gameApi = gameApi;
    }

    public void run() {
        try {
            gameApi.connectToGame("ac_client.exe");
            System.out.println(Long.toHexString(Pointer.nativeValue(gameApi.getModuleHandle("ac_client.exe").getPointer())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Guice.createInjector(new AssaultCubeModule()).getInstance(AssaultCube.class).run();
    }

}
