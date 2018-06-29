package com.projeto2.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.projeto2.game.DontTouchTheSpikes;
import com.projeto2.game.screen.PlayScreen;

/**
 * Created by up201403263 on 01-06-2016.
 */
public class Candy extends Sprite {

    public World world;
    public Body body;

    private TextureRegion candy;
    private Vector2 position;

    private Fixture fixture;


    public Candy(World world, PlayScreen screen, Vector2 position){
        super(screen.getAtlas().findRegion("candy"));
        this.position=position;
        this.world=world;

        defineCandy();
        candy=new TextureRegion(getTexture(),0,63,719,360);

        setBounds(0, 0, 30 / DontTouchTheSpikes.PPM, 30 / DontTouchTheSpikes.PPM);

        setRegion(candy);
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getWidth() / 2);
        setRegion(candy);
    }

    public void defineCandy(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(position.x/ DontTouchTheSpikes.PPM,position.y/DontTouchTheSpikes.PPM);
        bdef.type=BodyDef.BodyType.StaticBody;
        body=world.createBody(bdef); // create in world

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(10 / DontTouchTheSpikes.PPM);
        fdef.isSensor=true;
        fdef.shape=shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
        setCategoryFilter(DontTouchTheSpikes.CANDY_BIT);
    }

    public void setCategoryFilter(short filterBit){
        Filter filter=new Filter();
        filter.categoryBits=filterBit;
        fixture.setFilterData(filter);
    }


    public void destroyBody(){
        if(body.getFixtureList().size != 0)
            body.destroyFixture(fixture);
    }
}

