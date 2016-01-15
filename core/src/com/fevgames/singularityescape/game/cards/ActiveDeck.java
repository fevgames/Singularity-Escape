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

        //Debug cards

        BaseCard tmpCard=new BaseCard("Go to: NAVIGATION", GameState.ShipSections.NAVIGATION) {
            @Override
            public void run() {

            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);

        tmpCard=new BaseCard("Go to: LIVING", GameState.ShipSections.LIVING) {
            @Override
            public void run() {

            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);

        tmpCard=new BaseCard("Go to: TACTICAL", GameState.ShipSections.TACTICAL) {
            @Override
            public void run() {

            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);

        tmpCard=new BaseCard("Go to: CARGO", GameState.ShipSections.CARGO) {
            @Override
            public void run() {

            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);

        tmpCard=new BaseCard("Go to: ENGINEERING", GameState.ShipSections.ENGINEERING) {
            @Override
            public void run() {

            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);

        /*tmpCard=new BaseCard("[DEBUG] Set velocity to -3") {
            @Override
            public void run() {
                gameState.velocity=-3;
            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);
        tmpCard=new BaseCard("[DEBUG] Set velocity to 4") {
            @Override
            public void run() {
                gameState.velocity=4;
            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);
        tmpCard=new BaseCard("[DEBUG] Set velocity to 5") {
            @Override
            public void run() {
                gameState.velocity=5;
            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);
        tmpCard=new BaseCard("[DEBUG] Set velocity to 6") {
            @Override
            public void run() {
                gameState.velocity=6;
            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);
        tmpCard=new BaseCard("[DEBUG] Set velocity to 20") {
            @Override
            public void run() {
                gameState.velocity=20;
            }
        };
        tmpCard.persistent=true;
        cards.add(tmpCard);*/


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
