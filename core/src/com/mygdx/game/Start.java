package com.mygdx.game;

import com.badlogic.gdx.Game;

public class Start extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
