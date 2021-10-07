package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameScreen implements Screen {
    OrthographicCamera camera;
    Texture fiel, fieldActive, X, O;
    final static int _FIELD_SIZE = 25;
    final static int _FIELD_NUM = 27;
    static Stage stage;
    static Vector<Field> fields;
    public static enum Turn{
        X,
        O
    }
    static Turn turn = Turn.X;
    public GameScreen() {
        fiel = new Texture("field.png");
        fieldActive = new Texture("field_active.png");
        X = new Texture("X.jpg");
        
        camera = new OrthographicCamera();
        stage = new Stage(new ExtendViewport(600, 700, camera));
        camera.position.set(new Vector3(300, 350,3));
        fields = new Vector<>(_FIELD_NUM * _FIELD_NUM);

        for(int i = 0; i < _FIELD_NUM * _FIELD_NUM; ++i){
            final Field f = new Field((i % _FIELD_NUM) * _FIELD_SIZE, (i / _FIELD_NUM) * _FIELD_SIZE, i, fiel, fieldActive, X);
            stage.addActor(f);
            fields.add(f);
        }

        Gdx.input.setInputProcessor(stage);
    }

    public static void findWin(int i){
        int x = i % _FIELD_NUM, y = i / _FIELD_NUM;
        int left_up_to_right_down = 1, left_down_to_right_up = 1, left_to_right = 1, down_to_up = 1;
        while(--x > 0 && ++y < _FIELD_NUM && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) left_up_to_right_down++;
        x = i % _FIELD_NUM; y = i / _FIELD_NUM;
        while(++x < _FIELD_NUM && --y > 0 && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) left_up_to_right_down++;
        x = i % _FIELD_NUM; y = i / _FIELD_NUM;
        while(++x < _FIELD_NUM && ++y < _FIELD_NUM && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) left_down_to_right_up++;
        x = i % _FIELD_NUM; y = i / _FIELD_NUM;
        while(--x > 0 && --y > 0 && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) left_down_to_right_up++;
        x = i % _FIELD_NUM; y = i / _FIELD_NUM;
        while(--x > 0 && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) left_to_right++;
        x = i % _FIELD_NUM; y = i / _FIELD_NUM;
        while(++x < _FIELD_NUM && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) left_to_right++;
        x = i % _FIELD_NUM; y = i / _FIELD_NUM;
        while(++y < _FIELD_NUM && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) down_to_up++;
        x = i % _FIELD_NUM; y = i / _FIELD_NUM;
        while(--y > 0 && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) down_to_up++;
        if(left_down_to_right_up >= 5){
            fields.get(i).condition = Field.Condition.ACTIVE;
            x = i % _FIELD_NUM; y = i / _FIELD_NUM;
            while(++x < _FIELD_NUM && ++y < _FIELD_NUM && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) fields.get(x + y * _FIELD_NUM).condition = Field.Condition.ACTIVE;
            x = i % _FIELD_NUM; y = i / _FIELD_NUM;
            while(--x > 0 && --y > 0 && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) fields.get(x + y * _FIELD_NUM).condition = Field.Condition.ACTIVE;
            win(fields.get(i).fig);
        }
        if(left_up_to_right_down >= 5){
            fields.get(i).condition = Field.Condition.ACTIVE;
            x = i % _FIELD_NUM; y = i / _FIELD_NUM;
            while(--x > 0 && ++y < _FIELD_NUM && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) fields.get(x + y * _FIELD_NUM).condition = Field.Condition.ACTIVE;
            x = i % _FIELD_NUM; y = i / _FIELD_NUM;
            while(++x < _FIELD_NUM && --y > 0 && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) fields.get(x + y * _FIELD_NUM).condition = Field.Condition.ACTIVE;
            win(fields.get(i).fig);
        }
        if(left_to_right >= 5){
            fields.get(i).condition = Field.Condition.ACTIVE;
            x = i % _FIELD_NUM; y = i / _FIELD_NUM;
            while(--x > 0 && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) fields.get(x + y * _FIELD_NUM).condition = Field.Condition.ACTIVE;
            x = i % _FIELD_NUM; y = i / _FIELD_NUM;
            while(++x < _FIELD_NUM && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) fields.get(x + y * _FIELD_NUM).condition = Field.Condition.ACTIVE;
            win(fields.get(i).fig);
        }
        if(down_to_up >= 5){
            fields.get(i).condition = Field.Condition.ACTIVE;
            x = i % _FIELD_NUM; y = i / _FIELD_NUM;
            while(++y < _FIELD_NUM && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) fields.get(x + y * _FIELD_NUM).condition = Field.Condition.ACTIVE;
            x = i % _FIELD_NUM; y = i / _FIELD_NUM;
            while(--y > 0 && fields.get(x + y * _FIELD_NUM).fig == fields.get(i).fig) fields.get(x + y * _FIELD_NUM).condition = Field.Condition.ACTIVE;
            win(fields.get(i).fig);
        }
    }

    static void win(final Field.Fig fig){
        Gdx.input.setInputProcessor(null);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(fig == Field.Fig.X){
                    WinX winX;
                    winX = new WinX();
                    stage.addActor(winX);
                }else{
                    WinO winO;
                    winO = new WinO();
                    stage.addActor(winO);
                }
            }
        }, 1000);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
