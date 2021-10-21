package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class LobbyScreen implements Screen {

    Start game;
    Socket socket;
    BufferedReader input;
    PrintWriter out;
    String name = "unnamed";
    MyListener listener;
    Timer timer;
    Timer listUpdater;
    static Vector<Player> players;
    String[] players_names;
    Texture texture;
    boolean flag_update;
    Stage stage;
    OrthographicCamera camera;
    Header header = new Header();
    BottomPanel bottomPanel;
    static float top = 550, bottom = 0, now_y = 550;


    public LobbyScreen(Start game) {
        new MyFont();
        texture = new Texture("player.png");
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(300, 700, camera));
        camera.position.set(new Vector3(150, 350,3));
        stage.addActor(header);
        bottomPanel = new BottomPanel(this);
        stage.addActor(bottomPanel);
        bottomPanel.createButtons();
        System.out.println(Gdx.graphics.getHeight());

        flag_update = false;

        timer = new Timer();
        listUpdater = new Timer();
        this.game = game;
        players = new Vector<>();
        listener = new MyListener(this);
        Gdx.input.getTextInput(listener, "Введите имя", " ", name);
    }

    void goNext(String name) throws IOException {
        Gdx.input.setInputProcessor(stage);
        this.name = name;
        socket = new Socket("m-cortex.com", 1321);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
        out.println(name);
        out.flush();
        listUpdater.schedule(new TimerTask() {
            @Override
            public void run() {
                flag_update = true;
            }
        }, 500, 1000);
    }

    public void listUpdate() {
        out.println("list");
        out.flush();
        String s;
        try {
                s = input.readLine();
                if(s != null) {
                    if (s.startsWith("invite")) {
                        access(s.substring(6));
                        return;
                    }
                    if (s.equals("start")) {
                        game.setScreen(new GameScreen(game, socket, input, out));
                        dispose();
                        return;
                    }
                    if (!s.equals("{!?"))
                        players_names = s.split(" ");
                }
        } catch (IOException e){

        }
        for(int i = 0; i < players.size(); ++i) players.get(i).updated = false;
        for (int i = 0; i < players_names.length; ++i){
            if(players_names[i].equals(" ") || players_names[i].equals("\n") || players_names[i].equals("") || players_names[i].equals(name.substring(1))) continue;
            boolean flag = false;
            for(int j = 0; j < players.size(); ++j){
                if(players_names[i].equals(players.get(j).name)){
                    players.get(j).updated = true;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                players.add(new Player(players_names[i], players.size() - 1, texture, this));
                bottom += 30;
                stage.addActor(players.lastElement());
                if(players.lastElement().getZIndex() > header.getZIndex()){
                    header.setZIndex(players.lastElement().getZIndex() + 1);
                    bottomPanel.setZIndex(players.lastElement().getZIndex() + 1);
                }
            }
        }
        for(int i = 0; i < players.size(); ++i)
            if(!players.get(i).updated) {
                for(int j = i + 1; j < players.size(); ++j){
                    players.get(j).move();
                }
                players.get(i).remove();
                bottom -= 30;
                players.remove(i);
            }
    }

    void invite(String name){
        System.out.println("inv" + name);
        out.println("inv" + name);
        out.flush();
    }

    @Override
    public void show() {

    }

    void access(String s){
        bottomPanel.access(s);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(88/255f, 37/255f, 46/255f, 1);
        if(flag_update) {
                flag_update = false;
                listUpdate();
            }
        stage.act();
        stage.draw();
    }

    static void move(float y){
        if(Math.abs(y) > 1) {
            if(y < 0){
                if(top > now_y + y){
                    y = top - now_y;
                }
            } else {
                if(now_y - bottom + y > 200) y = 200 - now_y + bottom;
                if(y < 0) y = 0;
            }
            now_y += y;
            for (int i = 0; i < players.size(); ++i)
                players.get(i).scroll(y);
        }
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
        timer.cancel();
        texture.dispose();
    }
}
