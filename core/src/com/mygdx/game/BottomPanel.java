package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BottomPanel extends Actor {
    Sprite sprite;
    MyButtonNo no;
    MyButtonYes yes;
    String name;
    static boolean flag_visible = false, flag_is_invited = false;
    BitmapFont font = MyFont.ruFont;
    LobbyScreen lobby;

    public BottomPanel(LobbyScreen lobby) {
        sprite = new Sprite(new Texture("utility_board.png"));
        sprite.setBounds(0, 0, 300, 200);
        no = new MyButtonNo(190, 50, 100, 60);
        yes = new MyButtonYes(10, 50, 100, 60);
        this.lobby = lobby;
    }

    public void createButtons(){
        this.getStage().addActor(no);
        this.getStage().addActor(yes);
    }

    public void invite(String name){
        if(!flag_is_invited) {
            this.name = name;
            flag_visible = true;
        }
    }

    public void access(String name){
        System.out.println(name);
        this.name = name;
        flag_visible = false;
        flag_is_invited = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        if(flag_visible) {
            font.draw(batch, "Пригласить игрока " + name, getX() + 5, getY() + 180);
            font.draw(batch, "в лобби?", getX() + 5, getY() + 150);
            no.mydraw(batch, parentAlpha);
            yes.mydraw(batch, parentAlpha);
        }
        if(flag_is_invited) {
            font.draw(batch, "Перейти в лобби", getX() + 5, getY() + 180);
            font.draw(batch, "игрока " + name + "?", getX() + 5, getY() + 150);
            no.mydraw(batch, parentAlpha);
            yes.mydraw(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        if(flag_visible) {
            if (no.flag_use) {
                flag_visible = false;
                no.flag_use = false;
            }
            if (yes.flag_use) {
                flag_visible = false;
                yes.flag_use = false;
                lobby.invite(name);
            }
        }
        if(flag_is_invited){
            if (no.flag_use) {
                flag_is_invited = false;
                no.flag_use = false;
                lobby.out.println("no");
                lobby.out.flush();
            }
            if (yes.flag_use) {
                flag_is_invited = false;
                yes.flag_use = false;
                lobby.out.println("yes");
                lobby.out.flush();
            }
        }
        super.act(delta);
    }

    @Override
    public boolean setZIndex(int index) {
        no.setZIndex(index + 1);
        yes.setZIndex(index + 1);
        return super.setZIndex(index);
    }
}
