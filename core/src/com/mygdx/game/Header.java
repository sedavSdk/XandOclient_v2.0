package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Header extends Actor {
    Sprite sprite = new Sprite(new Texture("header-01.png"));

    public Header() {
        sprite.setBounds(0, 580, 300, 120);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
