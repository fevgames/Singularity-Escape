package com.fevgames.singularityescape.game.cards;

import com.badlogic.gdx.Gdx;
import com.fevgames.singularityescape.game.GameState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Roby on 30/12/2015.
 */
public class EventsDeck {
    ArrayList<BaseCard> cards;
    ArrayList<BaseCard> discardedCards;
    GameState gameState;

    public void init()
    {
        cards = new ArrayList<BaseCard>();
        discardedCards = new ArrayList<BaseCard>();

        /*cards.add(new BaseCard("An asteroid damages the ship. You lose 30% structural integrity") {
            @Override
            public void run() {
                gameState.integrity-=30;
                gameState.showAlert("The following event occurred: "+this.description);
            }
        });
        cards.add(new BaseCard("An asteroid damages the ship. You lose 20% structural integrity") {
            @Override
            public void run() {
                gameState.integrity-=20;
                gameState.showAlert("The following event occurred: "+this.description);
            }
        });
        cards.add(new BaseCard("An asteroid damages the ship. You lose 10% structural integrity") {
            @Override
            public void run() {
                gameState.integrity-=10;
                gameState.showAlert("The following event occurred: "+this.description);
            }
        });*/
        cards.add(new BaseCard("Air Leak") {
            @Override
            public void run() {
                gameState.activeDeck.addCard(new BaseCard("Repair air leak") {
                    @Override
                    public void run() {
                        gameState.leakingAir=false;
                    }
                });

                gameState.leakingAir=true;
                gameState.showAlert("There is an air leak in the ship and oxygen is leaking out into space.");
            }
        });

        cards.add(new BaseCard("Navigation Error") {
            @Override
            public void run() {
                gameState.activeDeck.addCard(new BaseCard("Repair Ship Navigation", GameState.ShipSections.NAVIGATION) {
                    @Override
                    public void run() {
                        gameState.velocity+=2;
                    }
                });

                gameState.velocity-=2;
                gameState.showAlert("A navigation error has been detected and has significantly thrown off escape trajectory.");
            }
        });

        cards.add(new BaseCard("Mix of Chemicals") {
            @Override
            public void run() {
                gameState.activeDeck.addCard(new BaseCard("Clear chemicals from the ship") {
                    @Override
                    public void run() {
                        gameState.poisonGas=false;
                    }
                });

                gameState.poisonGas=true;
                gameState.showAlert("Gravity has crushed containers in Tactical and there is now poisonous gas in the entire ship. ");
            }
        });

        cards.add(new BaseCard("Fuel line crushed") {
            @Override
            public void run() {
                gameState.activeDeck.addCard(new BaseCard("[SL71b] Repair fuel line") {
                    @Override
                    public void run() {
                        if(gameState.sL71bStatus.health==0)
                        {
                            gameState.showAlert("SL71b is dead!");
                            return;
                        }
                        gameState.velocity=4;
                    }
                });

                gameState.velocity=0;
                gameState.showAlert("The fusion core fuel lines connecting to the power invert have been crushed and have stopped ship propulsion.");
            }
        });

        cards.add(new BaseCard("Cargo bay door malfunction", GameState.ShipSections.CARGO) {
            @Override
            public void run() {
                gameState.activeDeck.addCard(new BaseCard("Hit the emergency close bay door button", GameState.ShipSections.TACTICAL) {
                    @Override
                    public void run() {
                        gameState.leakingAir=false;
                    }
                });

                gameState.leakingAir=true;
                gameState.showAlert("The cargo bay doors have opened unexpectedly.");
            }
        });
    }

    public BaseCard getRandom()
    {
        if(cards.size()==0&&discardedCards.size()>0)
        {
            cards = discardedCards;
            discardedCards = new ArrayList<BaseCard>();
            Gdx.app.log("SE","EventsDeck reshuffled");
        }

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

    public void setGameState(GameState _g)
    {
        gameState=_g;
    }
}
