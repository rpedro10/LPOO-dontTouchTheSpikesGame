package com.projeto2.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projeto2.game.DontTouchTheSpikes;
import com.projeto2.game.logic.Bird;
import com.projeto2.game.logic.Candy;
import com.projeto2.game.logic.InteractiveTileObject;
import com.projeto2.game.logic.LeftSpike;
import com.projeto2.game.logic.RightSpike;
import com.projeto2.game.tools.B2WorldCreator;
import com.projeto2.game.tools.WorldContactListener;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import javafx.scene.layout.TilePane;

/**
 * Created by Rui on 20-05-2016.
 */
public class PlayScreen implements Screen {

    private int numberSpikes;
    private Bird player;
    private Stack<Candy> candy;
    private boolean beginning;
    private boolean caught;
    private boolean canDraw;
    private boolean collisionRight;
    private boolean collisionLeft;
    private Hud hud;

    private DontTouchTheSpikes game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private ArrayList<LeftSpike> leftSpikeWall;
    private ArrayList<RightSpike> rightSpikeWall;

    private ArrayList<BodyDef> leftBodyDef;
    private ArrayList<BodyDef> rightBodyDef;

    private ArrayList<FixtureDef> leftFixtureDef;
    private ArrayList<FixtureDef> rightFixtureDef;

    private ArrayList<TiledMapTile> leftTile;
    private ArrayList<TiledMapTile> rightTile;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private TextureAtlas atlas;

    //box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(DontTouchTheSpikes game){

        this.game=game;
        numberSpikes = 1;
        beginning = true;
        collisionLeft = false;
        collisionRight = false;
        canDraw=true;
        caught=false;

        atlas = new TextureAtlas("bird_all.pack");

        leftSpikeWall = new ArrayList<LeftSpike>();
        rightSpikeWall = new ArrayList<RightSpike>();

        leftBodyDef = new ArrayList<BodyDef>();
        rightBodyDef = new ArrayList<BodyDef>();

        leftFixtureDef = new ArrayList<FixtureDef>();
        rightFixtureDef = new ArrayList<FixtureDef>();

        leftTile = new ArrayList<TiledMapTile>();
        rightTile = new ArrayList<TiledMapTile>();

        //create cam used in the game;
        gamecam=new OrthographicCamera();
        // create a viewPort to stretch the screen
        gamePort=new FitViewport(DontTouchTheSpikes.V_WIDTH/DontTouchTheSpikes.PPM,DontTouchTheSpikes.V_HEIGHT/DontTouchTheSpikes.PPM, gamecam);

        maploader= new TmxMapLoader();
        map = maploader.load("level.tmx");
        renderer =new OrthogonalTiledMapRenderer(map,1/DontTouchTheSpikes.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        hud = new Hud(game.batch, game);

        // box2d
        world=new World(new Vector2(0,-9.8f),true);
        b2dr= new Box2DDebugRenderer();
        new B2WorldCreator(world,map,this);

        //create bird
         player=new Bird(world,this);

        //create candy
        candy = new Stack<Candy>();

        world.setContactListener(new WorldContactListener(this));


    }

    public void setCanDraw(boolean state){
        canDraw = state;
    }

    public void setCaught(boolean state){
        caught = state;
    }

    public void addLeftSpike(LeftSpike spike){
        leftSpikeWall.add(spike);
    }

    public void addRightSpike(RightSpike spike){
        rightSpikeWall.add(spike);
    }

    public DontTouchTheSpikes getGame(){
        return game;
    }

    public void hideLeftWall(){
        for (int i=0; i<leftSpikeWall.size(); i++){
            leftSpikeWall.get(i).destroyBody();
            leftSpikeWall.get(i).getCell().setTile(null);
        }
    }

    public void hideRightWall(){
        for (int i=0; i<rightSpikeWall.size(); i++){
            rightSpikeWall.get(i).destroyBody();
            rightSpikeWall.get(i).getCell().setTile(null);
        }
    }

    public void saveLeftSpikes(){
        for (int i=0; i<leftSpikeWall.size(); i++){
            leftTile.add(leftSpikeWall.get(i).getCell().getTile());
            leftBodyDef.add(leftSpikeWall.get(i).getBdef());
            leftFixtureDef.add(leftSpikeWall.get(i).getFdef());
        }
    }

    public void saveRightSpikes(){
        for (int i=0; i<rightSpikeWall.size(); i++){
            rightTile.add(rightSpikeWall.get(i).getCell().getTile());
            rightBodyDef.add(rightSpikeWall.get(i).getBdef());
            rightFixtureDef.add(rightSpikeWall.get(i).getFdef());
        }
    }

    public void showLeftSpike(int num){
        leftSpikeWall.get(num).setBody(world.createBody(leftBodyDef.get(num)));
        leftSpikeWall.get(num).setFixture(leftSpikeWall.get(num).getBody().createFixture(leftFixtureDef.get(num)));
        leftSpikeWall.get(num).getCell().setTile(leftTile.get(num));
    }

    public void showRightSpike(int num){
        rightSpikeWall.get(num).setBody(world.createBody(rightBodyDef.get(num)));
        rightSpikeWall.get(num).setFixture(rightSpikeWall.get(num).getBody().createFixture(rightFixtureDef.get(num)));
        rightSpikeWall.get(num).getCell().setTile(rightTile.get(num));
    }

    /**
     *
     * @param numSpikes Number of spikes that left / right wall should have
     * @return Returns boolean vector to know where to draw spikes
     */
    public Vector generateSpikesVector(int numSpikes){

        //fill empty vector and count number of spikes in vector
        int num = 0;
        Vector spikes = new Vector();
        Random rand = new Random();
        boolean push;
        int aux;
        for (int i = 0; i<leftSpikeWall.size(); i++){
            //push = rand.nextBoolean();
            push = false;
            aux = rand.nextInt(4);
            if (aux == 0) {
                num++;
                push = true;
            }
            spikes.add(push);
        }

        if (num>numSpikes){
            for (int i = 0; i<spikes.size(); i++){
                if (num == numSpikes)
                    break;
                if (spikes.elementAt(i).equals(true)){
                    spikes.set(i,false);
                    num--;
                }
            }
        }

        if (num<numSpikes){
            for (int i = 0; i<spikes.size(); i++){
                if (num == numSpikes)
                    break;
                if (spikes.elementAt(i).equals(false)){
                    spikes.set(i,true);
                    num++;
                }
            }
        }
        return spikes;
    }


    public void generateLeftWall(){
        Vector spikes = generateSpikesVector(numberSpikes);
        for (int i = 0; i<leftSpikeWall.size(); i++){
            if (spikes.get(i).equals(true))
                showLeftSpike(i);
        }
    }

    public void generateRightWall(){
        Vector spikes = generateSpikesVector(numberSpikes);
        for (int i = 0; i<rightSpikeWall.size(); i++){
            if (spikes.get(i).equals(true))
                showRightSpike(i);
        }
    }

    public TextureAtlas getAtlas(){   // to get  textures
        return atlas;
    }

    public void handleInput(float dt){
        if(Gdx.input.justTouched()){
            player.b2body.applyLinearImpulse(new Vector2(0,7f),player.b2body.getWorldCenter(),true);
        }
    }

    public void setBeginningFalse(){
        beginning=false;
    }

    public boolean getBeginning(){
        return beginning;
    }

    public void setCollisionRight(){
        collisionRight = true;
    }

    public void setCollisionLeft(){
        collisionLeft = true;
    }

    public void updateNumberSpikes(){
        if (game.getScore() == 1)
            numberSpikes++;
        if (game.getScore() == 2)
            numberSpikes++;
        if (game.getScore()%5 == 0) {
            if (numberSpikes<=20)
                numberSpikes++;
        }
    }

    public void update(float  dt){
        if (!player.isAlive()) {
            game.saveGame();
            game.setScreen(new GameOverScreen(game));
        }

        if (beginning){
            //Saves spikes and hides them
            saveRightSpikes();
            saveLeftSpikes();
            hideLeftWall();
            hideRightWall();
        }

        if (collisionRight){
            hideRightWall();
            generateLeftWall();
            updateNumberSpikes();
        }
        collisionRight = false;

        if (collisionLeft){
            hideLeftWall();
            generateRightWall();
            updateNumberSpikes();
        }
        collisionLeft = false;

        if (canDraw){
            candyRandomlyAppears();
            canDraw=false;
        }
        if (caught){
            hideCandy();
            caught=false;
        }

        //handle user input
        handleInput(dt);

        player.direction(dt);

        if (!candy.empty())
            candy.peek().update(dt);

        world.step(1 / 60f, 1, 2);

        player.update(dt);

        gamecam.update();

        //tell our renderer to draw only what camera can see in the world
        renderer.setView(gamecam);

    }


    //CANDY
    public Stack<Candy> getCandy(){
        return candy;
    }

    public void hideCandy(){
        if (!candy.empty()) {
            candy.peek().destroyBody();
            candy.pop();
        }
    }

    public void candyRandomlyAppears(){
        if (candy.empty()){
            Random rand = new Random();
            int aux = rand.nextInt(3);
            if (aux == 1) {
                System.out.println("gerou");
                Vector2 pos = generateRandomPosition();
                Candy default_candy = new Candy(world, this, pos);
                candy.push(default_candy);
                System.out.println("oi");
            }
        }
    }

    public Vector2 generateRandomPosition(){
        Random rand = new Random();
        Vector2 pos = new Vector2();
        pos.x = rand.nextInt(DontTouchTheSpikes.V_WIDTH/4)+DontTouchTheSpikes.V_WIDTH/4;
        pos.y = rand.nextInt(DontTouchTheSpikes.V_HEIGHT/4)+DontTouchTheSpikes.V_HEIGHT/4;
        return pos;
    }


    //OVERRIDE METHODS
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render game map;
        renderer.render();


        //render box2d debuglines
        b2dr.render(world, gamecam.combined);
////////sprite of bird
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        if (!candy.empty())
            candy.peek().draw(game.batch);

        if(player.isAlive()) {
            player.draw(game.batch);
        }

        game.batch.end();

        //display Score
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.update();
        hud.stage.draw();

////////////////////////
        //set the batch to draw what camera sees
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
       gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
