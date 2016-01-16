package com.fevgames.singularityescape.game.cards;

import com.fevgames.singularityescape.game.GameState;

/**
 * Created by Roby on 30/12/2015.
 */
public abstract class BaseCard {

    public String description;
    public GameState.ShipSections shipSection;
    public GameState.ShipCharacters character;
    public boolean firstDraw;

    public boolean persistent;

    public BaseCard(String _d)
    {
        this.shipSection=GameState.ShipSections.ALL;
        this.description=_d;
        this.character=GameState.ShipCharacters.ALL;

        persistent=false;
        firstDraw=true;
    }
    public BaseCard(String _d,GameState.ShipSections _s)
    {
        this.shipSection=_s;
        this.description=_d;
        this.character=GameState.ShipCharacters.ALL;

        persistent=false;
        firstDraw=true;
    }
    public BaseCard(String _d,GameState.ShipSections _s, GameState.ShipCharacters _c)
    {
        this.shipSection=_s;
        this.description=_d;
        this.character=_c;

        persistent=false;
        firstDraw=true;
    }

    public abstract void run();

    public BaseCard noFirstDraw()
    {
        this.firstDraw=false;
        return this;
    }

}
