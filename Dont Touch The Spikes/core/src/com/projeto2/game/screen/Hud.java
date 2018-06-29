package com.projeto2.game.screen;

/**
 * Created by Catarina on 04/06/2016.
 */


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projeto2.game.DontTouchTheSpikes;


/**
 * Created by Catarina on 01/06/2016.
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;
    private DontTouchTheSpikes game;

    Label scoreLabel;
    Label candyLabel;
    Label bestScoreLabel;

    public Hud(SpriteBatch sb, DontTouchTheSpikes game){
        this.game = game;
        viewport = new FitViewport(DontTouchTheSpikes.V_WIDTH, DontTouchTheSpikes.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb); // kind of an empty box

        Table table = new Table();
        table.top();
        table.setFillParent(true); // table is the size of the stage

        bestScoreLabel = new Label(String.format("BEST: %4d", game.getBestScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        candyLabel = new Label(String.format("CANDY: %6d", game.getNumCandy()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(candyLabel).expandX().padTop(10);
        table.add(bestScoreLabel).expandX().padTop(10);

        //SCORE LABEL
        Table table2 = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%3d", game.getScore()), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel.setFontScale(2.2f); //redimensionar

        table2.add(scoreLabel).center();
        table2.setPosition(DontTouchTheSpikes.V_WIDTH/2, DontTouchTheSpikes.V_HEIGHT*0.48f);

        stage.addActor(table);
        stage.addActor(table2);
    }

    public void update(){
        bestScoreLabel.setText(String.format("BEST: %4d", game.getBestScore()));
        candyLabel.setText(String.format("CANDY: %6d", game.getNumCandy()));
        scoreLabel.setText(String.format("%3d", game.getScore()));
    }

}
