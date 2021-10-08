package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.io.IOException;

public class Start extends Game {
    @Override
    public void create() {
        try {
            setScreen(new GameScreen(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
