package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import sun.font.TrueTypeFont;
import sun.font.TrueTypeGlyphMapper;

public class Player extends Actor {
    boolean updated = true, inv;
    String name;
    int i;
    Texture texture;
    Sprite sprite;
    BitmapFont font;
    ClickListener listener;
    LobbyScreen lobby;
    float y, ny, want_y;

    public Player(final String name, int i, Texture texture, final LobbyScreen lobby) {
        this.lobby = lobby;
        this.texture = texture;
        this.name = name;
        this.i = i;
        this.setZIndex(1);
        y = 520 - i * 30 + lobby.now_y - 550;
        ny = 0;

        font = MyFont.font;

        sprite = new Sprite(texture);
        sprite.setBounds(0, y, 300, 30);
        this.setBounds(0, y - 30, 300, 30);
        listener = new ClickListener(){


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ny = y;
                want_y = y;
                inv = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                inv = false;
                LobbyScreen.move(y - ny);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(inv) lobby.invite(name);
                super.touchUp(event, x, y, pointer, button);
            }
        };
        this.addListener(listener);
    }

    public void move(){
        i--;
        y += 30;
        ny += 30;
        sprite.setBounds(0, y, 300, 30);
        this.setBounds(0, y - 30, 300, 30);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        font.draw(batch, name, 10, y + 23);
    }

    public void scroll(float y){
        want_y = y;
        this.y += want_y;
        sprite.setBounds(0, this.y, 300, 30);
        this.setBounds(0, this.y - 30, 300, 30);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
