package com.projeto2.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.projeto2.game.DontTouchTheSpikes;
import com.projeto2.game.screen.PlayScreen;

/**
 * Created by up201403263 on 03-06-2016.
 */
public class Top_bot extends InteractiveTileObject {
    public Top_bot(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);
        fixture.setUserData(this);//fixture =name of fixture;
        setCategoryFilter(DontTouchTheSpikes.TOP_BOT_BIT);
    }

}
