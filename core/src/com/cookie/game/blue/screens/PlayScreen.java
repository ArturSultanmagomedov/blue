package com.cookie.game.blue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cookie.game.blue.Game;

public class PlayScreen extends InputAdapter implements Screen {

    private Game game;

    private Viewport viewportUI;
    private Stage stage;
    private Label label;
    private Button bLeft, bRight, bJump;
    private OrthographicCamera camera, cameraUI;

    private Viewport viewport;
    private SpriteBatch batch;
    private Animation<TextureAtlas.AtlasRegion> animation;
    private float elapsedTime = 0f;

    private int time = 0, state = 0;
    private float velocity = 0f;

    private Color bColor = new Color(0.5f, 0.7f, 1.0f, 0.7f);

    public PlayScreen(Game game) {

        this.game = game;

        camera = new OrthographicCamera();
        cameraUI = new OrthographicCamera();

        viewport = new FitViewport(800, 450, camera);
        viewportUI = new FitViewport(800, 450, cameraUI);

        createUI();

        batch = new SpriteBatch();
        animation = new Animation<TextureAtlas.AtlasRegion>(1 / 15f, new TextureAtlas(Gdx.files.internal("animation/knightStayRight.atlas")).getRegions());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        ///////////        GAME CAMERA        ///////////

        batch.setProjectionMatrix(camera.projection);
        camera.update();

        animate();

        timer();

        ///////////        UI CAMERA        ///////////

        batch.setProjectionMatrix(cameraUI.combined);
        cameraUI.update();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        viewportUI.update(width, height);
        viewport.update(width, height);

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

    // создание элементов управления
    private void createUI() {

        stage = new Stage(viewportUI);
        Gdx.input.setInputProcessor(stage);

        float dbw = 75, brx = viewport.getWorldWidth() - 15 - dbw, blx = brx - 15 - dbw;

        label = new Label("TIME: " + 0, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setPosition(40, 400);
        label.setFontScale(2.3f);

        bJump = new Button(game.skin);
        bJump.setTransform(true);
        bJump.setSize(dbw, dbw);
        bJump.setColor(bColor);
        bJump.setPosition(15, 15);

        bLeft = new Button(game.skin);
        bLeft.setTransform(true);
        bLeft.setSize(dbw, dbw);
        bLeft.setColor(bColor);
        bLeft.setPosition(blx, 15);

        bRight = new Button(game.skin);
        bRight.setTransform(true);
        bRight.setSize(dbw, dbw);
        bRight.setColor(bColor);
        bRight.setPosition(brx, 15);

        stage.addActor(label);
        stage.addActor(bJump);
        stage.addActor(bLeft);
        stage.addActor(bRight);

    }

    // анимация
    private void animate() {

        elapsedTime += Gdx.graphics.getDeltaTime();

        if (click("right")) {

            state = 0;

            velocity += Gdx.graphics.getDeltaTime() * 200;

            animation = new Animation<TextureAtlas.AtlasRegion>(1 / 30f, new TextureAtlas(Gdx.files.internal("animation/knightRunRight.atlas")).getRegions());

        }
        else if (click("left")) {

            state = 1;

            velocity += Gdx.graphics.getDeltaTime() * -200;

            animation = new Animation<TextureAtlas.AtlasRegion>(1 / 30f, new TextureAtlas(Gdx.files.internal("animation/knightRunLeft.atlas")).getRegions());

        }
        else {

            if (state == 0) {

                animation = new Animation<TextureAtlas.AtlasRegion>(1 / 3f, new TextureAtlas(Gdx.files.internal("animation/knightStayRight.atlas")).getRegions());

            }

            if (state == 1) {

                animation = new Animation<TextureAtlas.AtlasRegion>(1 / 3f, new TextureAtlas(Gdx.files.internal("animation/knightStayLeft.atlas")).getRegions());

            }

        }


        camera.position.set(velocity, 0, 0);

        batch.begin();
        batch.draw(animation.getKeyFrame(elapsedTime, true), velocity, 0, 70, 70);
        batch.end();

    }

    // обратный отсчет - 300 секунд
    private void timer() {

        if (300 - time / 10 > 0) {

            time += (int) (Gdx.graphics.getDeltaTime() * 30);

            label.setText("TIME: " + (300 - time / 10));

        }

    }

    private void control() {

    }

    // проверка - нажата ли нужная кнопка
    private boolean click(String key) {

        if ((bLeft.isPressed() || Gdx.input.isKeyPressed(21) || Gdx.input.isKeyPressed(29)) && key.equals("left")) return true;
        if ((bRight.isPressed() || Gdx.input.isKeyPressed(22) || Gdx.input.isKeyPressed(32)) && key.equals("right")) return true;

        return ((bJump.isPressed() && Gdx.input.justTouched()) || Gdx.input.isKeyJustPressed(62) || Gdx.input.isKeyJustPressed(19) || Gdx.input.isKeyJustPressed(51)) && key.equals("jump");

    }

    @Override
    public void dispose() {

        batch.dispose();
        stage.dispose();

    }

}
