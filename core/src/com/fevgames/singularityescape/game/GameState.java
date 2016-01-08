package com.fevgames.singularityescape.game;

import com.badlogic.gdx.Gdx;
import com.fevgames.singularityescape.game.cards.ActionsDeck;
import com.fevgames.singularityescape.game.cards.ActiveDeck;
import com.fevgames.singularityescape.game.cards.BaseCard;
import com.fevgames.singularityescape.game.cards.EventsDeck;

/**
 * Created by Roby on 28/12/2015.
 */
public class GameState {
    public float velocity,gravity;

    public float distance;


    public float gameTime;
    public float gameTimeSinceLastCard;
    //public float gameTimeSinceLastMovement;
    public boolean paused;
    public float integrity;

    private EventsDeck eventsDeck;
    private ActionsDeck actionsDeck;
    public ActiveDeck activeDeck;

    public enum ShipSections {
        NAVIGATION,LIVING,TACTICAL,CARGO,ENGINEERING,
        ALL
    }

    public enum ShipCharacters
    {
        Tex,Cindy,SL71b,
        ALL
    }

    public void init(int difficulty)    //0-easy 1-normal 2-hard
    {
        this.velocity=4;
        this.gravity=5;

        if(difficulty==0)
        {
            this.distance=420;
        }
        else if(difficulty==1)
        {
            this.distance=300;
        }
        else if(difficulty==2)
        {
            this.distance=180;
        }

        this.gameTime=0;
        this.gameTimeSinceLastCard=0;
        //this.gameTimeSinceLastMovement=0;
        this.paused=false;
        this.integrity=100;

        eventsDeck=new EventsDeck();
        eventsDeck.init();
        eventsDeck.setGameState(this);
        actionsDeck=new ActionsDeck();
        actionsDeck.init();
        actionsDeck.setGameState(this);
        activeDeck=new ActiveDeck();
        activeDeck.init();
        activeDeck.setGameState(this);


        //Draw 3 cards
        for(int i=0;i<3;i++)
        {
            BaseCard tmp=actionsDeck.getRandom();
            if(tmp!=null)
            {
                activeDeck.addCard(tmp);
            }
        }
    }

    public static ShipSections[] getShipSections()
    {
        ShipSections[] ret = new ShipSections[5];
        ret[0]=ShipSections.NAVIGATION;
        ret[1]=ShipSections.LIVING;
        ret[2]=ShipSections.TACTICAL;
        ret[3]=ShipSections.CARGO;
        ret[4]=ShipSections.ENGINEERING;
        return ret;
    }
    public static String getShipSectionName(ShipSections sec)
    {
        if(sec==ShipSections.NAVIGATION)
            return "Navigation";
        if(sec==ShipSections.LIVING)
            return "Living";
        if(sec==ShipSections.TACTICAL)
            return "Tactical";
        if(sec==ShipSections.CARGO)
            return "Cargo";
        if(sec==ShipSections.ENGINEERING)
            return "Engineering";

        return "UNKNOWN";
    }

    public void update()
    {
        if(!paused)
        {
            this.gameTime+= Gdx.graphics.getDeltaTime();
            this.gameTimeSinceLastCard+= Gdx.graphics.getDeltaTime();
            //this.gameTimeSinceLastMovement+= Gdx.graphics.getDeltaTime();

            if(this.gameTimeSinceLastCard>=120)
            {
                this.gameTimeSinceLastCard=0;

                BaseCard tmp=eventsDeck.getRandom();
                if(tmp!=null)
                {
                    Gdx.app.log("SE","EventsDeck - Card drawed: "+tmp.description);
                    tmp.run();
                }
                tmp=actionsDeck.getRandom();
                if(tmp!=null)
                {
                    activeDeck.addCard(tmp);
                }
                tmp=actionsDeck.getRandom();
                if(tmp!=null)
                {
                    activeDeck.addCard(tmp);
                }
            }

            distance+=(this.velocity-this.gravity)*Gdx.graphics.getDeltaTime();

            if(this.distance>=600)
            {
                //Win!
            }
            if(this.distance<=1)
            {
                //Fail!
            }

            /*if(this.gameTimeSinceLastMovement>=60&&this.distance>2)
            {
                this.gameTimeSinceLastMovement=0;
                this.distance-=1;
            }
            if(this.gameTimeSinceLastMovement>=120&&this.distance==2)
            {
                this.gameTimeSinceLastMovement=0;
                this.distance-=1;
                //Game over!
            }*/
        }
    }


}