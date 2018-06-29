package com.projeto2.game.logic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.projeto2.game.DontTouchTheSpikes;

/**
 * Created by Catarina on 05/06/2016.
 */
public class LeftSpike extends InteractiveTileObject {

    public LeftSpike(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);
        fixture.setUserData(this);//fixture =name of fixture;
        setCategoryFilter(DontTouchTheSpikes.LEFT_SPIKE_BIT);
    }


}
