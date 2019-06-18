package com.cookie.game.blue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cookie.game.blue.screens.PlayScreen;

public class Game extends com.badlogic.gdx.Game {

    public Skin skin;

    public static Preferences preferences;

    @Override
    public void create() {

        skin = new Skin(Gdx.files.internal("ui/ui.skin"));

        preferences = Gdx.app.getPreferences("My Preferences");

        setScreen(new PlayScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
