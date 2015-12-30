package com.fevgames.singularityescape.game.cards;

import com.fevgames.singularityescape.game.GameState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Roby on 30/12/2015.
 */
public class EventsDeck {
    ArrayList<BaseCard> cards;
    GameState gameState;

    public void init()
    {
        cards = new ArrayList<BaseCard>();
        cards.add(new BaseCard("An asteroid damages the ship. You lose 30% structural integrity") {
            @Override
            public void run() {
                gameState.integrity-=30;
            }
        });
        cards.add(new BaseCard("An asteroid damages the ship. You lose 20% structural integrity") {
            @Override
            public void run() {
                gameState.integrity-=20;
            }
        });
        cards.add(new BaseCard("An asteroid damages the ship. You lose 10% structural integrity") {
            @Override
            public void run() {
                gameState.integrity-=10;
            }
        });
    }

    public BaseCard getRandom()
    {
        if(cards.size()==0)
            return null;

        Random randomGenerator;
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(cards.size());

        BaseCard tmp=cards.get(index);
        cards.remove(index);
        return tmp;
    }

    public void setGameState(GameState _g)
    {
        gameState=_g;
    }
}
