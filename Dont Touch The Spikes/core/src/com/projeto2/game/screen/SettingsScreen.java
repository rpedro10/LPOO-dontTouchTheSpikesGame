package com.projeto2.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projeto2.game.DontTouchTheSpikes;
import com.sun.glass.ui.Menu;

/**
 * Created by Catarina on 07/06/2016.
 */
public class SettingsScreen implements Screen{
    // setup the dimensions of the menu buttons
    public static final int BUTTON_WIDTH = 270;
    public static final int BUTTON_HEIGHT = 70;

    private DontTouchTheSpikes game;
    private Texture background;
    public Stage stage;
    public Skin skin;

    TextButton soundsButton;
    TextButton backButton;

    public SettingsScreen(DontTouchTheSpikes game){
        this.game = game;
        background = new Texture("menu.png");
        create();
        handleInput();
    }

    public void create(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        // Generate a white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Pixmap.Format.RGB888);
        pixmap.setColor(Color.PURPLE);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        BitmapFont bfont=new BitmapFont();
        skin.add("default",bfont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        //Define play button
        if (DontTouchTheSpikes.SOUND)
            soundsButton = new TextButton("SOUND: ON",textButtonStyle);
        else
            soundsButton = new TextButton("SOUND: OFF",textButtonStyle);
        soundsButton.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2, 450);
        stage.addActor(soundsButton);


        //Define Exit button
        backButton = new TextButton("BACK TO MENU",textButtonStyle);
        backButton.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2, 350);
        stage.addActor(backButton);

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
        if (MenuScreen.music == null)
            MenuScreen.music.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public void handleInput(){

        soundsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                if (DontTouchTheSpikes.SOUND){
                    soundsButton.setText("SOUND: OFF");
                    DontTouchTheSpikes.SOUND=false;
                }
                else{
                    soundsButton.setText("SOUND: ON");
                    DontTouchTheSpikes.SOUND=true;
                }
            }
        });

        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                    game.setScreen(new MenuScreen(game));
            }
        });
    }
}
