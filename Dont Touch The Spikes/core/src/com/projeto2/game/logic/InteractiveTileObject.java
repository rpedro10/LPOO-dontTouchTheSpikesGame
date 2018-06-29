package com.projeto2.game.logic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.projeto2.game.DontTouchTheSpikes;

import org.w3c.dom.css.Rect;

/**
 * Created by up201403263 on 01-06-2016.
 */
public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected FixtureDef fdef;
    protected BodyDef bdef;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){

        this.world=world;
        this.map=map;
        this.bounds=bounds;

        bdef=new BodyDef();
        fdef= new FixtureDef();
        PolygonShape shape=new PolygonShape();

        bdef.type=BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ DontTouchTheSpikes.PPM,(bounds.getY()+ bounds.getHeight()/2)/DontTouchTheSpikes.PPM);
        body=world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2/DontTouchTheSpikes.PPM,bounds.getHeight()/2/DontTouchTheSpikes.PPM);
        fdef.shape=shape;

        fixture= body.createFixture(fdef);
    }


    public void setCategoryFilter(short filterBit){
        Filter filter=new Filter();
        filter.categoryBits=filterBit;
        fixture.setFilterData(filter);
    }


    public TiledMapTileLayer.Cell getCell(){
        //get spikes layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x*DontTouchTheSpikes.PPM/32), (int)(body.getPosition().y*DontTouchTheSpikes.PPM/32));
    }


    public void destroyBody(){
        if(body.getFixtureList().size != 0)
            body.destroyFixture(fixture);
    }

    public void setBody(Body body){
        this.body = body;
    }

    public Body getBody(){
        return body;
    }

    public BodyDef getBdef(){
        return bdef;
    }

    public void setFixture(Fixture fixture){
        this.fixture = fixture;
    }

    public FixtureDef getFdef(){
        return fdef;
    }

}
