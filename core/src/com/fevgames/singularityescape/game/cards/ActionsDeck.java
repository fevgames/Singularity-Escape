package com.fevgames.singularityescape.game.cards;

import com.fevgames.singularityescape.game.GameState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Roby on 30/12/2015.
 */
public class ActionsDeck {
    ArrayList<BaseCard> cards;
    GameState gameState;

    public void init()
    {
        cards = new ArrayList<BaseCard>();

        cards.add(new BaseCard("Open Airlocks") {
            @Override
            public void run() {
                gameState.velocity+=2;
            }
        });

        cards.add(new BaseCard("Toss Fuel Cell into Fusion Reactor") {
            @Override
            public void run() {
                gameState.distance+=100;
            }
        });

        cards.add(new BaseCard("Overload Fusion Reactor") {
            @Override
            public void run() {
                gameState.velocity+=2;
            }
        });

        cards.add(new BaseCard("Destroy Surplus Weight") {
            @Override
            public void run() {
                gameState.distance+=100;
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
