package com.fevgames.singularityescape.game;

/**
 * Created by Roby on 28/12/2015.
 */
public class GameState {
    public int distance;
    public float gameTime;
    public float gameTimeSinceLastCard;
    public boolean paused;

    public enum ShipSections {
        NAVIGATION,LIVING,TACTICAL,CARGO,ENGINEERING
    }

    public void init(int difficulty)    //0-easy 1-normal 2-hard
    {
        if(difficulty==0)
        {
            this.distance=7;
        }
        else if(difficulty==1)
        {
            this.distance=5;
        }
        else if(difficulty==2)
        {
            this.distance=3;
        }

        this.gameTime=0;
        this.gameTimeSinceLastCard=0;
        this.paused=true;
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


}
