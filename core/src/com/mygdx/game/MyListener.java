package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.io.IOException;

public class MyListener implements Input.TextInputListener {
    String name;
    LobbyScreen lobbyScreen;

    public MyListener(LobbyScreen lobbyScreen) {
        this.lobbyScreen = lobbyScreen;
    }

    @Override
    public void input(String text) {
        name = text;
        try {
            lobbyScreen.goNext(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void canceled() {
        System.out.println(2);
    }
}
