package com.projeto2.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projeto2.game.DontTouchTheSpikes;
import com.sun.scenario.Settings;


/**
 * Created by Catarina on 04/06/2016.
 */
public class MenuScreen implements Screen{

    // setup the dimensions of the menu buttons
    public static final int BUTTON_WIDTH = 270;
    public static final int BUTTON_HEIGHT = 70;

    private DontTouchTheSpikes game;
    private Texture background;
    public Stage stage;
    public Skin skin;

    TextButton playButton;
    TextButton exitButton;
    TextButton settingsButton;

    public static Music music;


    public MenuScreen(DontTouchTheSpikes game){
        this.game = game;
        background = new Texture("menu.png");
        create();
        handleInput();

        if (DontTouchTheSpikes.SOUND) {
            music = DontTouchTheSpikes.manager.get("game_music.mp3", Music.class);
            music.setLooping(true);
            music.play();
        }
        else{
            if (music != null)
                music.stop();
        }
    }

    public void create(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        // Generate a white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Format.RGB888);
        pixmap.setColor(Color.PURPLE);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        BitmapFont bfont=new BitmapFont();
        skin.add("default",bfont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        //Define play button
        playButton = new TextButton("PLAY",textButtonStyle);
        playButton.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2, 450);
        stage.addActor(playButton);


        //Define Settings button
        settingsButton = new TextButton("SETTINGS",textButtonStyle);
        settingsButton.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2,  350);
        stage.addActor(settingsButton);

        //Define Exit button
        exitButton = new TextButton("EXIT",textButtonStyle);
        exitButton.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2, 250);
        stage.addActor(exitButton);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        stage.dispose();
        skin.dispose();
    }

    public void handleInput(){

        playButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new PlayScreen(game));
            }
        });

        settingsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        exitButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.exitGame();
            }
        });
    }
}
