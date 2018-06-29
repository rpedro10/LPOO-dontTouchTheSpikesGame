package com.projeto2.game.logic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.projeto2.game.DontTouchTheSpikes;
import com.projeto2.game.screen.PlayScreen;

/**
 * Created by up201403263 on 26-05-2016.
 */
public class Bird extends Sprite {

    public enum State{FALLING,JUMPING }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion birdFly;
    private Animation birdJump;




    private boolean alive;
    private boolean flyingRight;
    private float stateTimer;

    public Bird(World world,PlayScreen screen){
        super(screen.getAtlas().findRegion("bird-sprite"));
        this.world=world;
        currentState=State.FALLING;
        previousState=State.FALLING;
        stateTimer=0;
        flyingRight=true;
        alive=true;

        Array<TextureRegion>frames=new Array<TextureRegion>();
        for(int i=1;i<5;i++){
            frames.add(new TextureRegion(getTexture(),i*180,430,180,150));
        }
        birdJump=new Animation(0.05f,frames);
        frames.clear();


        birdFly=new TextureRegion(getTexture(),0,430,180,150);

        defineBird();
        setBounds(0,0,44/DontTouchTheSpikes.PPM,44/DontTouchTheSpikes.PPM);
        setRegion(birdFly);

    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isFlyingRight() {
        return flyingRight;
    }

    public void setFlyingRight(boolean flyingRight) {
        this.flyingRight = flyingRight;
    }

    public void update(float dt){

        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getWidth()/2);

        setRegion(getFrame(dt));

    }

    public TextureRegion getFrame(float dt){
        currentState=getState();
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region=birdJump.getKeyFrame(stateTimer);
                break;
            case FALLING:
            default:
                region=birdFly;
        }

        if((b2body.getLinearVelocity().x<0|| !flyingRight)&&!region.isFlipX()){
            region.flip(true,false);
           flyingRight=false;
        }
        else if((b2body.getLinearVelocity().x>0|| flyingRight)&& region.isFlipX()){
            region.flip(true,false);
            flyingRight=true;
        }
        stateTimer=currentState==previousState? stateTimer+dt:0;
        previousState=currentState;
        return region;
    }

    public State getState(){
        if (b2body.getLinearVelocity().y>0){
            return State.JUMPING;
        }
        else
            return State.FALLING;
    }

    public void direction(float dt){
        if(isFlyingRight())
            b2body.applyLinearImpulse(new Vector2(0.05f,0),b2body.getWorldCenter(),true);
        else
            b2body.applyLinearImpulse(new Vector2(-0.05f,0),b2body.getWorldCenter(),true);
    }

    public void defineBird(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(100/DontTouchTheSpikes.PPM,700/DontTouchTheSpikes.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef); // create in world

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(13/DontTouchTheSpikes.PPM);

        fdef.filter.categoryBits=DontTouchTheSpikes.BIRD_BIT;
        //what bird can collide
        fdef.filter.maskBits=DontTouchTheSpikes.CANDY_BIT|DontTouchTheSpikes.WALL_BIT |DontTouchTheSpikes.TOP_BOT_BIT|DontTouchTheSpikes.LEFT_SPIKE_BIT|DontTouchTheSpikes.RIGHT_SPIKE_BIT;

        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);

    }
}
