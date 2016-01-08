package com.fevgames.singularityescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.fevgames.singularityescape.common.GraphicUtils;
import com.fevgames.singularityescape.game.cards.ActiveDeck;
import com.fevgames.singularityescape.game.cards.BaseCard;

import java.util.ArrayList;

/**
 * Created by Roby on 30/12/2015.
 */
public class ActionMenu implements InputProcessor {

    SpriteBatch batch;
    OrthographicCamera camera;
    GameState gameState;
    BitmapFont font;
    GlyphLayout layout;
    public boolean visible;

    private GlyphLayout tmpGL;

    private Texture bgTexture;
    //private String [] textLines;

    public GameState.ShipSections selectedShipSection;

    public ActionMenu()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout=new GlyphLayout();
        visible=false;

        /*textLines=new String[4];
        textLines[0]="######";
        textLines[1]=" - Entry #1";
        textLines[2]=" - Entry #2";
        textLines[3]=" - Entry #3";*/

        font.setColor(Color.BLACK);

        this.selectedShipSection=GameState.ShipSections.LIVING;

        //layout.setText(font,"Cancel");
    }

    public void setGameState(GameState _g)
    {
        gameState=_g;
    }

    public void render()
    {
        if(!visible)
            return;

        ArrayList<BaseCard> activeCards=gameState.activeDeck.getCards();

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(
                bgTexture,
                0-(bgTexture.getWidth()/2),
                0-(bgTexture.getHeight()/2)
        );

        float y=bgTexture.getHeight()/2-30;

        //tmpGL
        for(int i=0;i<activeCards.size();i++)
        {
            if(activeCards.get(i).shipSection==GameState.ShipSections.ALL||activeCards.get(i).shipSection==selectedShipSection)
            {
                tmpGL=font.draw(
                        batch,
                        activeCards.get(i).description,
                        -bgTexture.getWidth()/2+30,
                        y,
                        (bgTexture.getWidth()-60),
                        -1,
                        true
                );
                y-=(tmpGL.height*1.8);
            }
        }

        /*for(int i=0;i<textLines.length;i++)
        {
            layout.setText(font,textLines[i]);
            font.draw(
                    batch,
                    textLines[i],
                    -bgTexture.getWidth()/2+30,
                    +bgTexture.getHeight()/2-30-(20*i)
            );
        }*/



        batch.end();
    }

    public void update()
    {

        //textLines[0]=GameState.getShipSectionName(selectedShipSection);
    }

    public void resize(int width, int height)
    {
        camera = new OrthographicCamera(width,height);
        camera.position.set(0,0,0);

        bgTexture=new Texture(GraphicUtils.getPixmapRoundedRectangle(width-40,height-30,18,0xFFFFFFFF));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(!visible)
        {
            return false;
        }

        ArrayList<BaseCard> activeCards=gameState.activeDeck.getCards();
        float y=bgTexture.getHeight()/2-30;

        Vector3 v = new Vector3(screenX,screenY,0);
        camera.unproject(v);
        float x= -bgTexture.getWidth()/2+30;

        for(int i=0;i<activeCards.size();i++)
        {

            if(activeCards.get(i).shipSection==GameState.ShipSections.ALL||activeCards.get(i).shipSection==selectedShipSection)
            {
                tmpGL=new GlyphLayout();
                tmpGL.setText(font,activeCards.get(i).description,Color.WHITE,(bgTexture.getWidth()-60),-1,true);
                /*tmpGL=font.draw(
                        batch,
                        activeCards.get(i).description,
                        -bgTexture.getWidth()/2+30,
                        y,
                        (bgTexture.getWidth()-60),
                        -1,
                        true
                );*/
                /*if(i==0)
                {
                    Gdx.app.log("[SE]",""+v.x+" - "+v.y);
                    Gdx.app.log("[SE]",""+(x+tmpGL.width)+" - "+(y+tmpGL.height));
                }*/
                if(v.x>x&&v.y>y-tmpGL.height&&v.x<x+tmpGL.width&&v.y<y)
                {
                    //Click!
                    Gdx.app.log("[SE]","Click!"+activeCards.get(i).description);
                    activeCards.get(i).run();

                    visible=false;
                    activeCards.remove(i);
                    return true;
                }
                y-=(tmpGL.height*1.8);
            }
        }

        //return true;

        visible=false;
        return true;

        //return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}