package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameScreen implements Screen {
    OrthographicCamera camera;
    Texture fiel, fieldActive, X, O;
    final static int _FIELD_SIZE = 30;
    final static int _FIELD_NUM = 20;
    static Stage stage;
    Start game;
    static Field.Fig test;
    static boolean flagWin, flagDispose, flagInput, flag_update;
    static Vector<Field> fields;
    static PrintWriter out;
    Timer timer = new Timer();
    public enum Turn{
        X,
        O
    }
    static BufferedReader input;
    Socket socket;
    static Turn turn, nowTurn;
    public GameScreen(Start game, Socket socket, BufferedReader in, PrintWriter p) throws IOException {
        this.socket = socket;
        input = in;
        out = p;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag_update = true;
            }
        }, 500, 1000);
            String s = input.readLine();
            System.out.print(s);
            if (s.equals("X")){
                turn = Turn.X;
            }
            else if (s.equals("O")){
                turn = Turn.O;
            }
        nowTurn = Turn.X;

        this.game = game;
        flagWin = false;
        flagDispose = false;
        flagInput = false;
        flag_update = false;

        fiel = new Texture("field.png");
        fieldActive = new Texture("field_active.png");
        X = new Texture("X.jpg");
        
        camera = new OrthographicCamera();
        stage = new Stage(new ExtendViewport(600, 700, camera));
        camera.position.set(new Vector3(300, 300,3));
        fields = new Vector<>(_FIELD_NUM * _FIELD_NUM);

        for(int i = 0; i < _FIELD_NUM * _FIELD_NUM; ++i){
            final Field f = new Field((i % _FIELD_NUM) * _FIELD_SIZE, (i / _FIELD_NUM) * _FIELD_SIZE, i, fiel, fieldActive, X);
            stage.addActor(f);
            fields.add(f);
        }
        Gdx.input.setInputProcessor(stage);
    }

    static void send(int i) {
        System.out.println(i);
        out.println(i);
        out.flush();
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
                flagWin = true;
                test = fig;
            }
        }, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flagDispose = true;
            }
        }, 2000);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        try {
            if(nowTurn != turn && input.ready()) resume();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(flagWin){
            if(test == Field.Fig.X){
            WinX winX;
            winX = new WinX();
            stage.addActor(winX);
        }else{
            WinO winO;
            winO = new WinO();
            stage.addActor(winO);
        }
        }
        if(flagDispose) dispose();
        if(flag_update){
            flag_update = false;
            send(-2);
        }
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
            try {
                    int u = Integer.parseInt(input.readLine());
                    System.out.print(u);
                    if(u > -1) fields.get(u).addFigureFromServer();
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        flagDispose = false;
        stage.dispose();
        game.setScreen(new LobbyScreen(game));
        fiel.dispose();
        fieldActive.dispose();
        X.dispose();
    }
}
