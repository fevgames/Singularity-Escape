package com.fevgames.singularityescape.game.cards;

/**
 * Created by Roby on 30/12/2015.
 */
public abstract class BaseCard {

    public String description;

    public BaseCard(String _d)
    {
        this.description=_d;
    }

    public abstract void run();

}
