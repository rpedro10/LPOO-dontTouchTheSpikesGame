package com.projeto2.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.projeto2.game.DontTouchTheSpikes;
import com.projeto2.game.logic.Bird;
import com.projeto2.game.logic.InteractiveTileObject;
import com.projeto2.game.logic.LeftSpike;
import com.projeto2.game.logic.Top_bot;
import com.projeto2.game.screen.PlayScreen;


/**
 * Created by up201403263 on 03-06-2016.
 */
public class WorldContactListener implements ContactListener{
    private PlayScreen screen;

    public WorldContactListener(PlayScreen screen){
       this.screen = screen;
    }

    @Override
    public void beginContact(Contact contact) {
       // Gdx.app.log("Begin Contact","");

        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        int col = fixA.getFilterData().categoryBits |fixB.getFilterData().categoryBits;

        System.out.println(col);

        switch(col){

            //Collision with left/right Spikes
            case 3:
                if (fixA.getFilterData().categoryBits == DontTouchTheSpikes.BIRD_BIT){
                    ((Bird) fixA.getUserData()).setAlive(false);
                }
                if (fixB.getFilterData().categoryBits == DontTouchTheSpikes.BIRD_BIT){
                    ((Bird) fixB.getUserData()).setAlive(false);
                }
                break;


            //Collision with Spikes
            case 6:
                if (fixA.getFilterData().categoryBits == DontTouchTheSpikes.BIRD_BIT) {
                    ((Bird) fixA.getUserData()).setAlive(false);
                } else if (fixB.getFilterData().categoryBits == DontTouchTheSpikes.BIRD_BIT) {
                    ((Bird) fixB.getUserData()).setAlive(false);
                } else
                    break;
                break;


            //Collision with Candy
            case 7:
                if ((fixA.getFilterData().categoryBits == DontTouchTheSpikes.BIRD_BIT) || (fixB.getFilterData().categoryBits == DontTouchTheSpikes.BIRD_BIT)) {
                    screen.setCaught(true);
                } else
                    break;
                screen.getGame().updateCandyNumber();
                break;

            //Collision with walls
            case 10:
                if (fixA.getFilterData().categoryBits == DontTouchTheSpikes.BIRD_BIT){
                    if(((Bird) fixA.getUserData()).isFlyingRight()==true) {
                        ((Bird) fixA.getUserData()).setFlyingRight(false);
                        if (screen.getBeginning())
                            screen.setBeginningFalse();
                        screen.setCollisionRight();
                    }
                    else {
                        ((Bird) fixA.getUserData()).setFlyingRight(true);
                        screen.setCollisionLeft();
                    }
                }
                else if (fixB.getFilterData().categoryBits == DontTouchTheSpikes.BIRD_BIT) {
                    if(((Bird) fixB.getUserData()).isFlyingRight()==true) {
                        ((Bird) fixB.getUserData()).setFlyingRight(false);
                        if (screen.getBeginning())
                            screen.setBeginningFalse();
                        screen.setCollisionRight();
                    }
                    else {
                        ((Bird) fixB.getUserData()).setFlyingRight(true);
                        screen.setCollisionLeft();
                    }
                }
                screen.getGame().updateScore();
                screen.getGame().updateBestScore();
                screen.setCanDraw(true);
                break;

        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
