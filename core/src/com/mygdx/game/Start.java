package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.io.IOException;

public class Start extends Game {
    @Override
    public void create() {
        setScreen(new LobbyScreen(this));
    }

}
