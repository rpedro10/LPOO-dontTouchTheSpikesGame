package com.projeto2.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.projeto2.game.DontTouchTheSpikes;
import com.projeto2.game.logic.LeftSpike;
import com.projeto2.game.logic.RightSpike;
import com.projeto2.game.logic.Top_bot;
import com.projeto2.game.logic.Wall;
import com.projeto2.game.screen.PlayScreen;

/**
 * Created by up201403263 on 01-06-2016.
 */
public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map, PlayScreen screen){

        //local variables
        BodyDef bdef=   new BodyDef();
        PolygonShape shape= new PolygonShape();
        FixtureDef fdef =new FixtureDef();
        Body body;


        //create spikes ground and top bodies/fixtures
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){

            com.badlogic.gdx.math.Rectangle rect= ((RectangleMapObject)object).getRectangle();
            new Top_bot(world,map,rect);
        }

        // create walls fixture
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){

            com.badlogic.gdx.math.Rectangle rect= ((RectangleMapObject)object).getRectangle();
            new Wall(world,map,rect);
        }

        //create left wall fixture
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

            com.badlogic.gdx.math.Rectangle rect= ((RectangleMapObject)object).getRectangle();
           // new LeftSpike(world, map, rect);
            screen.addLeftSpike(new LeftSpike(world,map,rect));

        }

        //create right wall fixture
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){

            com.badlogic.gdx.math.Rectangle rect= ((RectangleMapObject)object).getRectangle();
            //new RightSpike(world,map,rect);
            screen.addRightSpike(new RightSpike(world,map,rect));
        }

    }
}
