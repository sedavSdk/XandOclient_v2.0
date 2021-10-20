package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyButtonYes extends Actor {
    Sprite sprite;
    ClickListener listener;
    Texture up, down;
    String s = "no";
    BitmapFont font;
    int x, y, wight, height;
    boolean flag_use = false;

    public MyButtonYes(final int xx, final int yy, final int wight, final int height) {
        up = new Texture("button_yes_up.png");
        down = new Texture("button_yes_down.png");
        sprite = new Sprite(up);
        this.x = xx;
        this.y = yy;
        this.height = height;
        this.wight = wight;
        this.setBounds(xx, yy, wight, height);
        sprite.setBounds(xx, yy - 30, wight, height);
        font = MyFont.ruFont;
        listener = new ClickListener(){


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setTexture(down);
                sprite.setBounds(xx + 5, yy - 25, wight - 5, height - 5);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setTexture(up);
                sprite.setBounds(xx, yy - 30, wight, height);
                flag_use = true;
                super.touchUp(event, x, y, pointer, button);
            }
        };
        this.addListener(listener);
    }

    public void mydraw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        font.draw(batch, "да",  sprite.getX() + 10,  sprite.getY() + 30);
    }
}
