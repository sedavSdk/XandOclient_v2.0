package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyButtonNo extends Actor {
    Sprite sprite;
    ClickListener listener;
    Texture up, down;
    String s = "no";
    BitmapFont font;
    int x, y, wight, height;

    public MyButtonNo(final int xx, final int yy, final int wight, final int height) {
        up = new Texture("button_no_up.png");
        down = new Texture("button_no_down.png");
        sprite = new Sprite(up);
        this.x = xx;
        this.y = yy;
        this.height = height;
        this.wight = wight;
        this.setBounds(xx, yy, wight, height);
        sprite.setBounds(xx, yy - 30, wight, height);
        font = MyFont.font;
        if(MyFont.font == null) System.out.print("!!!!!!!!");
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
                super.touchUp(event, x, y, pointer, button);
            }
        };
        this.addListener(listener);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        //font.draw(batch, "s",  10,  10);
        super.draw(batch, parentAlpha);
    }
}
