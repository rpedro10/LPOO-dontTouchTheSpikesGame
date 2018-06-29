package com.projeto2.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projeto2.game.DontTouchTheSpikes;

/**
 * Created by Catarina on 07/06/2016.
 */
public class PauseScreen implements Screen {

    // setup the dimensions of the menu buttons
    private static final int BUTTON_WIDTH = 270;
    private static final int BUTTON_HEIGHT = 70;

    private DontTouchTheSpikes game;

    private OrthographicCamera gamecam;
    private Viewport viewport;

    private TextButton resume_button;
    private TextButton exit_button;

    private Texture background;

    private Stage stage;
    private Skin skin;

    public PauseScreen(DontTouchTheSpikes game){
        this.game=game;
        background = new Texture("pause.png");
        create();
        handleInput();
    }

    public void create(){
        viewport= new FitViewport(DontTouchTheSpikes.V_WIDTH,DontTouchTheSpikes.V_HEIGHT,new OrthographicCamera());
        stage =new Stage(viewport,((DontTouchTheSpikes) game).batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        Label.LabelStyle font=new Label.LabelStyle(new BitmapFont(), Color.WHITE);

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

        //Define play again button
        resume_button = new TextButton("CONTINUE",textButtonStyle);
        resume_button.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2, 450);
        stage.addActor(resume_button);


        //Define Exit button
        exit_button = new TextButton("EXIT",textButtonStyle);
        exit_button.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2, 350);
        stage.addActor(exit_button);
    }

    public void handleInput(){
        resume_button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new PlayScreen(game));
            }
        });


        exit_button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.exitGame();
            }
        });
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

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
    public void resume() {

    }

    @Override
    public void show() {

    }
}
