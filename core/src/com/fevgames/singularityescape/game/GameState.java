package com.fevgames.singularityescape.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.fevgames.singularityescape.common.SoundUtils;
import com.fevgames.singularityescape.game.cards.ActionsDeck;
import com.fevgames.singularityescape.game.cards.ActiveDeck;
import com.fevgames.singularityescape.game.cards.BaseCard;
import com.fevgames.singularityescape.game.cards.EventsDeck;
import com.fevgames.singularityescape.screens.EndScreen;

/**
 * Created by Roby on 28/12/2015.
 */
public class GameState {

    public enum ShipSections {
        NAVIGATION,LIVING,TACTICAL,CARGO,ENGINEERING,
        ALL,
        NONE
    }

    public enum ShipCharacters
    {
        Tex,Cindy,SL71b,
        ALL
    }

    public class CharacterStatus
    {
        public float health;
        public float oxygen;
        public ShipSections shipSection;

        public CharacterStatus()
        {
            this.health=100;
            this.oxygen=100;
            this.shipSection=ShipSections.NONE;
        }
    }

    public float velocity,gravity;
    public float distance;
    public ShipSections currentShipSection;
    public float oxygenRegenerationRatio;

    public boolean leakingAir;
    public boolean poisonGas;

    public String alertMessage;

    public float gameTime;
    public float gameTimeSinceLastCard;
    //public float gameTimeSinceLastMovement;
    public boolean paused;
    public float integrity;
    public int cratesNumber;
    public boolean disableActionWheel;

    private int discardedAlerts;

    private EventsDeck eventsDeck;
    private ActionsDeck actionsDeck;
    public ActiveDeck activeDeck;

    private Game game;

    public CharacterStatus texStatus,cindyStatus,sL71bStatus;


    public void init(Game _g,int difficulty)    //0-easy 1-normal 2-hard
    {
        this.game=_g;
        this.velocity=4;
        this.gravity=7;
        this.currentShipSection=ShipSections.NAVIGATION;
        this.cratesNumber=6;
        this.oxygenRegenerationRatio=1;
        this.leakingAir=false;
        this.poisonGas=false;
        this.disableActionWheel=false;

        this.texStatus=new CharacterStatus();
        this.texStatus.shipSection=ShipSections.NAVIGATION;
        this.cindyStatus=new CharacterStatus();
        this.cindyStatus.shipSection=ShipSections.TACTICAL;
        this.sL71bStatus=new CharacterStatus();
        this.sL71bStatus.shipSection=ShipSections.ENGINEERING;



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


        //Draw 2 cards
        for(int i=0;i<2;i++)
        {
            BaseCard tmp=actionsDeck.getRandomFirstDraw();
            if(tmp!=null)
            {
                activeDeck.addCard(tmp);
            }
        }

        this.alertMessage=("FTL jump completed! Cool down protocols engaged.");
        this.paused=true;
        this.discardedAlerts=0;
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

            if(this.leakingAir)
            {
                this.texStatus.oxygen-=(2*Gdx.graphics.getDeltaTime());
                if(this.texStatus.oxygen<=0)
                    this.texStatus.health=0;
                this.sL71bStatus.oxygen-=(2*Gdx.graphics.getDeltaTime());
                if(this.sL71bStatus.oxygen<=0)
                    this.sL71bStatus.health=0;
                this.cindyStatus.oxygen-=(2*Gdx.graphics.getDeltaTime());
                if(this.cindyStatus.oxygen<=0)
                    this.cindyStatus.health=0;
            }
            else
            {
                this.texStatus.oxygen+=(this.oxygenRegenerationRatio*Gdx.graphics.getDeltaTime());
                if(this.texStatus.oxygen>100)
                    this.texStatus.oxygen=100;
                this.sL71bStatus.oxygen+=(this.oxygenRegenerationRatio*Gdx.graphics.getDeltaTime());
                if(this.sL71bStatus.oxygen>100)
                    this.sL71bStatus.oxygen=100;
                this.cindyStatus.oxygen+=(this.oxygenRegenerationRatio*Gdx.graphics.getDeltaTime());
                if(this.cindyStatus.oxygen>100)
                    this.cindyStatus.oxygen=100;
            }

            if(this.poisonGas)
            {
                this.texStatus.health-=(2*Gdx.graphics.getDeltaTime());
                this.sL71bStatus.health-=(2*Gdx.graphics.getDeltaTime());
                this.cindyStatus.health-=(2*Gdx.graphics.getDeltaTime());
            }

            //this.gameTimeSinceLastMovement+= Gdx.graphics.getDeltaTime();

            if(this.gameTimeSinceLastCard>=30)
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

            distance+=(
                    (this.velocity+(sL71bStatus.shipSection==GameState.ShipSections.ENGINEERING&&sL71bStatus.health>0&&sL71bStatus.oxygen>0?0.25:0))
                            -this.gravity)*Gdx.graphics.getDeltaTime();

            if(this.distance>=600)
            {
                //Win!
                game.getScreen().dispose();
                game.setScreen(new EndScreen(game,true));
            }
            if(this.distance<=1||(texStatus.oxygen<=0||texStatus.health<=0))
            {
                //Fail!
                game.getScreen().dispose();
                game.setScreen(new EndScreen(game,false));
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

    public void showAlert(String _s)
    {
        this.alertMessage=_s + " Tap to continue.";
        paused=true;

        SoundUtils.alert.play();
    }

    public void discardAlert()
    {
        this.discardedAlerts++;
        if(this.discardedAlerts==1)
        {
            this.alertMessage = "Singularity detected.\r\nCollision with event horizon in "+((int)(this.distance/(60*(gravity-velocity))))+" minutes and "+((int)(this.distance-(((int)(this.distance/(60*(gravity-velocity))))*(60*(gravity-velocity)))))+" seconds.";
        }
        else if(this.discardedAlerts==2)
        {
            this.alertMessage = "You have one goal, escape from the gravity well of the black hole before you hit the event horizon (and die). Tap anywhere in a room to bring up the ACTION WHEEL. Blue indicates available actions involving that particular room. Tap on the room name to see the ACTION LIST, and tap actions from the list to execute them. Gray means no actions available (other than changing view to that room). Good luck!";
        }
        else {
            this.alertMessage = "";
            paused = false;
        }

    }


}
