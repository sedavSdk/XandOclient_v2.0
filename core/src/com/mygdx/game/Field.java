package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.IOException;

public class Field extends Actor {
    Sprite fiel, figure, fiel_active;
    int x, y;
    Texture fieldT, fieldActive, X, O;
    boolean flag = false;
    final int _FIELD_SIZE = GameScreen._FIELD_SIZE;

    public enum Condition {
        ACTIVE,
        NON_ACTIVE
    }

    public enum Fig {
        X,
        O,
        NONE
    }

    Fig fig;
    Condition condition;
    ClickListener listener;
    int num;

    public Field(int x, int y, int num, Texture fieldT, Texture fieldActive, Texture X) {
        fig = Fig.NONE;
        condition = Condition.NON_ACTIVE;
        this.num = num;
        this.x = x;
        this.y = y;
        figure = new Sprite();

        listener = new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                condition = Condition.ACTIVE;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                condition = Condition.NON_ACTIVE;
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (condition == Condition.ACTIVE) {
                    condition = Condition.NON_ACTIVE;
                    if(GameScreen.nowTurn == GameScreen.turn) {
                            addFigure();
                    }
                }
                super.touchUp(event, x, y, pointer, button);
            }
        };

        fiel = new Sprite(fieldT);
        fiel_active = new Sprite(fieldActive);
        figure = new Sprite(X);
        fiel_active.setBounds(x, y, _FIELD_SIZE, _FIELD_SIZE);
        fiel.setBounds(x, y, _FIELD_SIZE, _FIELD_SIZE);
        this.setBounds(x, y, _FIELD_SIZE, _FIELD_SIZE);
        this.addListener(listener);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        switch (condition) {
            case NON_ACTIVE:
                fiel.draw(batch);
                break;
            case ACTIVE:
                fiel_active.draw(batch);
        }
        if (flag) figure.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void addFigure() {
        if (fig == Fig.NONE) {
            if (GameScreen.nowTurn == GameScreen.Turn.O) {
                figure.setTexture(new Texture("O.png"));
                figure.setBounds(x, y, _FIELD_SIZE, _FIELD_SIZE);
                GameScreen.nowTurn = GameScreen.Turn.X;
                fig = Fig.O;
            } else {
                figure.setBounds(x, y, _FIELD_SIZE, _FIELD_SIZE);
                GameScreen.nowTurn = GameScreen.Turn.O;
                fig = Fig.X;
            }
            flag = true;
            GameScreen.findWin(num);
            GameScreen.send(num);
        }
    }

    public void addFigureFromServer() {
            if (GameScreen.nowTurn == GameScreen.Turn.O) {
                figure.setTexture(new Texture("O.png"));
                figure.setBounds(x, y, _FIELD_SIZE, _FIELD_SIZE);
                GameScreen.nowTurn = GameScreen.Turn.X;
                fig = Fig.O;
            } else {
                figure.setBounds(x, y, _FIELD_SIZE, _FIELD_SIZE);
                GameScreen.nowTurn = GameScreen.Turn.O;
                fig = Fig.X;
            }
            flag = true;
            GameScreen.findWin(num);
        }
}
