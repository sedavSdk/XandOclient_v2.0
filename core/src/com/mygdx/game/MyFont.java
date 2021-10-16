package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MyFont {
    static BitmapFont font;
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Polybius1981.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();

        p.color = new Color(246, 255, 0, 1);
        p.size = 30;
        p.borderWidth = 2f;
        p.borderColor = new Color(255, 0, 0, 1);
        font = generator.generateFont(p);
    }

}
