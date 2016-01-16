package com.fevgames.singularityescape.game.cards;

import com.badlogic.gdx.Gdx;
import com.fevgames.singularityescape.game.GameState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Roby on 30/12/2015.
 */
public class ActionsDeck {
    ArrayList<BaseCard> cards;
    ArrayList<BaseCard> discardedCards;
    GameState gameState;

    public void init()
    {
        cards = new ArrayList<BaseCard>();
        discardedCards = new ArrayList<BaseCard>();

        cards.add(new BaseCard("[SL71b] Burn cargo for fuel", GameState.ShipSections.CARGO) {
            @Override
            public void run() {

                if(gameState.cratesNumber==0)
                {
                    gameState.showAlert("You have no more crates to use!");
                    return;
                }
                if(gameState.sL71bStatus.health==0||gameState.sL71bStatus.oxygen==0)
                {
                    gameState.showAlert("SL71b is dead!");
                    return;
                }

                gameState.cratesNumber--;
                gameState.distance+=70;

                gameState.showAlert("You moved forward 70 units!");
            }
        });
        cards.add(new BaseCard("[SL71b] Burn cargo for fuel", GameState.ShipSections.CARGO) {
            @Override
            public void run() {

                if(gameState.cratesNumber==0)
                {
                    gameState.showAlert("You have no more crates to use!");
                    return;
                }
                if(gameState.sL71bStatus.health==0||gameState.sL71bStatus.oxygen==0)
                {
                    gameState.showAlert("SL71b is dead!");
                    return;
                }

                gameState.cratesNumber--;
                gameState.distance+=70;

                gameState.showAlert("You moved forward 70 units!");
            }
        });

        cards.add(new BaseCard("[SL71b] Affinity to fusion core", GameState.ShipSections.CARGO) {
            @Override
            public void run() {

                if(gameState.sL71bStatus.health==0||gameState.sL71bStatus.oxygen==0)
                {
                    gameState.showAlert("SL71b is dead!");
                    return;
                }
                gameState.sL71bStatus.shipSection=GameState.ShipSections.ENGINEERING;
                gameState.showAlert("SL71b moved to Engineering. You gain +0.25 speed while SL71b is there.");
            }
        }.noFirstDraw());

        cards.add(new BaseCard("[SL71b] Afterburners", GameState.ShipSections.CARGO) {
            @Override
            public void run() {

                if(gameState.sL71bStatus.health==0||gameState.sL71bStatus.oxygen==0)
                {
                    gameState.showAlert("SL71b is dead!");
                    return;
                }
                gameState.distance+=80;
                gameState.sL71bStatus.shipSection=GameState.ShipSections.ENGINEERING;
                gameState.showAlert("SL71b activated afterburners and you gain 80 units of distance from the event horizon.");
            }
        });
        cards.add(new BaseCard("[SL71b] Afterburners", GameState.ShipSections.CARGO) {
            @Override
            public void run() {

                if(gameState.sL71bStatus.health==0||gameState.sL71bStatus.oxygen==0)
                {
                    gameState.showAlert("SL71b is dead!");
                    return;
                }
                gameState.distance+=80;
                gameState.sL71bStatus.shipSection=GameState.ShipSections.ENGINEERING;
                gameState.showAlert("SL71b activated afterburners and you gained 80 units of distance from the event horizon.");
            }
        });

        cards.add(new BaseCard("[Cindy] Convert ships food reserves for fuel boost", GameState.ShipSections.LIVING) {
            @Override
            public void run() {

                if(gameState.cindyStatus.health==0||gameState.cindyStatus.oxygen==0)
                {
                    gameState.showAlert("Cindy is dead!");
                    return;
                }
                gameState.distance+=70;
                gameState.cindyStatus.shipSection=GameState.ShipSections.LIVING;
                gameState.showAlert("Cindy moved to LIVING and you gained 100 units of distance from the event horizon burning food reserves.");
            }
        });
        cards.add(new BaseCard("[Cindy] Convert ships food reserves for fuel boost", GameState.ShipSections.LIVING) {
            @Override
            public void run() {

                if(gameState.cindyStatus.health==0||gameState.cindyStatus.oxygen==0)
                {
                    gameState.showAlert("Cindy is dead!");
                    return;
                }
                gameState.distance+=70;
                gameState.cindyStatus.shipSection=GameState.ShipSections.LIVING;
                gameState.showAlert("Cindy moved to LIVING and you gained 70 units of distance from the event horizon burning food reserves.");
            }
        });

        cards.add(new BaseCard("[Cindy] Tractor beam to nearby moon", GameState.ShipSections.TACTICAL) {
            @Override
            public void run() {

                if(gameState.cindyStatus.health==0||gameState.cindyStatus.oxygen==0)
                {
                    gameState.showAlert("Cindy is dead!");
                    return;
                }
                gameState.distance+=60;
                gameState.cindyStatus.shipSection=GameState.ShipSections.TACTICAL;
                gameState.showAlert("Cindy used the tractor beam and you gained 60 units of distance from the event horizon.");
            }
        });
        cards.add(new BaseCard("[Cindy] Tractor beam to nearby moon", GameState.ShipSections.TACTICAL) {
            @Override
            public void run() {

                if(gameState.cindyStatus.health==0||gameState.cindyStatus.oxygen==0)
                {
                    gameState.showAlert("Cindy is dead!");
                    return;
                }
                gameState.distance+=60;
                gameState.cindyStatus.shipSection=GameState.ShipSections.TACTICAL;
                gameState.showAlert("Cindy used the tractor beam and you gained 60 units of distance from the event horizon.");
            }
        });

        cards.add(new BaseCard("[Cindy] Minimal life support", GameState.ShipSections.TACTICAL) {
            @Override
            public void run() {

                if(gameState.cindyStatus.health==0||gameState.cindyStatus.oxygen==0)
                {
                    gameState.showAlert("Cindy is dead!");
                    return;
                }
                gameState.oxygenRegenerationRatio=0.5f;
                gameState.velocity+=1;
                gameState.cindyStatus.shipSection=GameState.ShipSections.TACTICAL;
                gameState.showAlert("Cindy redirected some life support systems to the main engine and you gain +1 to speed.");
            }
        }.noFirstDraw());

        cards.add(new BaseCard("Eject Navigation Pod", GameState.ShipSections.NAVIGATION) {
            @Override
            public void run() {
                gameState.distance+=100;
                gameState.texStatus.shipSection=GameState.ShipSections.NAVIGATION;
                gameState.velocity=0;
                gameState.disableActionWheel=true;
                gameState.showAlert("You ejected navigation pod.");
            }
        }.noFirstDraw());

        cards.add(new BaseCard("Sacrifice SL71b into fusion core", GameState.ShipSections.ENGINEERING) {
            @Override
            public void run() {

                if(gameState.sL71bStatus.health==0||gameState.sL71bStatus.oxygen==0)
                {
                    gameState.showAlert("SL71b is dead!");
                    return;
                }
                gameState.distance+=150 ;
                //gameState.sL71bStatus.shipSection=GameState.ShipSections.ENGINEERING;
                gameState.sL71bStatus.oxygen=gameState.sL71bStatus.health=0;
                gameState.showAlert("You sacrificed SL71b and you gained 150  units of distance from the event horizon.");
            }
        }.noFirstDraw());

        cards.add(new BaseCard("Trade Cindy to nearby pirates for assistance (tractor beam)", GameState.ShipSections.NAVIGATION) {
            @Override
            public void run() {

                if(gameState.cindyStatus.health==0||gameState.cindyStatus.oxygen==0)
                {
                    gameState.showAlert("Cindy is dead!");
                    return;
                }
                gameState.distance+=100 ;
                //gameState.sL71bStatus.shipSection=GameState.ShipSections.NAVIGATION;
                gameState.cindyStatus.oxygen=gameState.cindyStatus.health=0;
                gameState.showAlert("You sacrificed Cindy to the pirates and you gained 100  units of distance from the event horizon.");
            }
        }.noFirstDraw());

        cards.add(new BaseCard("Engage overheated FTL drive (warning: ship could explode!)", GameState.ShipSections.NAVIGATION) {
            @Override
            public void run() {

                if(Math.random() < 0.5)
                {
                    gameState.distance+=600;
                }
                else
                {
                    gameState.texStatus.health=0;
                }
                //gameState.sL71bStatus.shipSection=GameState.ShipSections.NAVIGATION;
                //gameState.cindyStatus.oxygen=gameState.cindyStatus.health=0;
                //gameState.showAlert("You sacrificed Cindy to the pirates and you gained 240 units of distance from the event horizon.");
            }
        }.noFirstDraw());





        /*cards.add(new BaseCard("Open Airlocks") {
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
        });*/
    }

    public BaseCard getRandom()
    {
        /*if(cards.size()==0&&discardedCards.size()>0)
        {
            cards = discardedCards;
            discardedCards = new ArrayList<BaseCard>();
            Gdx.app.log("SE","ActionsDeck reshuffled");
        }*/

        if(cards.size()==0)
            return null;

        Random randomGenerator;
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(cards.size());

        BaseCard tmp=cards.get(index);
        cards.remove(index);
        discardedCards.add(tmp);
        return tmp;
    }

    public BaseCard getRandomFirstDraw()
    {
        /*if(cards.size()==0&&discardedCards.size()>0)
        {
            cards = discardedCards;
            discardedCards = new ArrayList<BaseCard>();
            Gdx.app.log("SE","ActionsDeck reshuffled");
        }*/

        if(cards.size()==0)
            return null;

        Random randomGenerator;
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(cards.size());
        BaseCard tmp=cards.get(index);

        while(tmp.firstDraw==false)
        {
            index = randomGenerator.nextInt(cards.size());
            tmp=cards.get(index);
        }

        cards.remove(index);
        discardedCards.add(tmp);
        return tmp;
    }

    public void setGameState(GameState _g)
    {
        gameState=_g;
    }
}
