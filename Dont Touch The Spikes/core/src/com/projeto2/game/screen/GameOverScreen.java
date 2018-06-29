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
import com.projeto2.game.DontTouchTheSpikes;

/**
 * Created by up201403263 on 04-06-2016.
 */
public class GameOverScreen implements Screen {

    // setup the dimensions of the menu buttons
    private static final int BUTTON_WIDTH = 270;
    private static final int BUTTON_HEIGHT = 70;

    private DontTouchTheSpikes game;
    private Texture background;
    public Stage stage;
    public Skin skin;
    private FitViewport viewport;

    TextButton playAgainButton;
    TextButton exitButton;
    TextButton backButton;

    Label scoreLabel;

    public GameOverScreen(DontTouchTheSpikes game){
        this.game = game;
        background = new Texture("gameOver.png");
        create();
        handleInput();
    }

    public void create(){
        viewport= new FitViewport(DontTouchTheSpikes.V_WIDTH,DontTouchTheSpikes.V_HEIGHT,new OrthographicCamera());
        //stage = new Stage();
        stage =new Stage(viewport,((DontTouchTheSpikes) game).batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        Label.LabelStyle font=new Label.LabelStyle(new BitmapFont(),Color.WHITE);

        Table table =new Table();
        table.setFillParent(true);
        scoreLabel = new Label(String.format("SCORE: %4d", game.getScore()), new Label.LabelStyle(new BitmapFont(), Color.PURPLE));
        table.add(scoreLabel).center();
        table.setPosition(0, DontTouchTheSpikes.V_HEIGHT/5);
        stage.addActor(table);

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
        playAgainButton = new TextButton("PLAY AGAIN",textButtonStyle);
        playAgainButton.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2, 400);
        stage.addActor(playAgainButton);


        //Define BAck button
        backButton = new TextButton("BACK TO MENU",textButtonStyle);
        backButton.setPosition(game.V_WIDTH / 2 - BUTTON_WIDTH / 2, 300);
        stage.addActor(backButton);

        //Define Exit button
        exitButton = new TextButton("EXIT",textButtonStyle);
        exitButton.setPosition(game.V_WIDTH/2-BUTTON_WIDTH/2, 200);
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
        playAgainButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.resetScore();
                game.setScreen(new PlayScreen(game));
            }
        });

        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        });

        exitButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.exitGame();
            }
        });
    }
}
