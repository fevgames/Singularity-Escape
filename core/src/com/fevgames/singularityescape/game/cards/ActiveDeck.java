package com.fevgames.singularityescape.game.cards;

import com.badlogic.gdx.Gdx;
import com.fevgames.singularityescape.game.GameState;

import java.util.ArrayList;

/**
 * Created by Roby on 30/12/2015.
 */
public class ActiveDeck {
    ArrayList<BaseCard> cards;
    GameState gameState;

    public void init()
    {
        cards = new ArrayList<BaseCard>();
    }

    public void addCard(BaseCard c)
    {
        Gdx.app.log("SE","ActiveDeck - Card added: "+c.description);
        cards.add(c);
    }

    public void setGameState(GameState _g)
    {
        gameState=_g;
    }

    public ArrayList<BaseCard> getCards()
    {
        return cards;
    }
}
