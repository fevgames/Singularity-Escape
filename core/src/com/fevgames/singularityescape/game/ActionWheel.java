package com.fevgames.singularityescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.fevgames.singularityescape.common.GraphicUtils;
import com.fevgames.singularityescape.common.SoundUtils;
import com.fevgames.singularityescape.game.cards.BaseCard;
import com.fevgames.singularityescape.screens.GameScreen;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Roby on 28/12/2015.
 */
public class ActionWheel implements InputProcessor {

    SpriteBatch batch;
    OrthographicCamera camera;

    BitmapFont font;

    GlyphLayout layout;
    public boolean visible;
    private Texture []textTextures;
    private int []textColors;

    GameState gameState;
    ActionMenu actionMenu;

    public ActionWheel()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout=new GlyphLayout();
        visible=false;
        textTextures=new Texture[7];
        textColors=new int[7];

    }

    public void setGameState(GameState _g)
    {
        gameState=_g;
    }
    public void setActionMenu(ActionMenu _a)
    {
        actionMenu=_a;
    }

    public void render()
    {
        camera.update();

        if(!visible)
            return;

        font.getData().setScale(Gdx.graphics.getHeight()/480,Gdx.graphics.getHeight()/480);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        GameState.ShipSections[] sections=GameState.getShipSections();
        float circlePart=360/sections.length;

        float centerx=0;
        float centery=0;
        float radius=Gdx.graphics.getHeight()/2-40;

        float realCenterX=0;
        float realCenterY=0;

        ArrayList<BaseCard> tmp=gameState.activeDeck.getCards();

        for(int i=0;i<sections.length;i++)
        {
            layout.setText(font,GameState.getShipSectionName(sections[i]));
            realCenterX=(float)(centerx + Math.sin(Math.toRadians(circlePart*(float)i))*(radius));
            realCenterY=(float)(centery + Math.cos(Math.toRadians(circlePart*(float)i))*(radius));

            if(textTextures[i]==null)
            {
                textTextures[i]=new Texture(GraphicUtils.getPixmapRoundedRectangle((int)layout.width+40,(int)layout.height+24,14, 0xFFFFFFFF));
            }

            batch.setColor(Color.GRAY);
            for(int k=0;k<tmp.size();k++)
            {
                if((tmp.get(k).shipSection==sections[i]||tmp.get(k).shipSection==GameState.ShipSections.ALL)&&!tmp.get(k).description.substring(0,5).equals("Go to"))
                {
                    batch.setColor(Color.BLUE);
                    break;
                }
            }

            batch.draw(
                    textTextures[i],
                    realCenterX-(textTextures[i].getWidth()/2),
                    realCenterY-(textTextures[i].getHeight()/2)
            );

            font.draw(
                    batch,
                    GameState.getShipSectionName(sections[i]),
                    realCenterX - (layout.width/2),
                    realCenterY + (layout.height/2)
            );
        }

        layout.setText(font,"Cancel");
        if(textTextures[6]==null)
        {
            textTextures[6]=new Texture(GraphicUtils.getPixmapRoundedRectangle(70*(Gdx.graphics.getHeight()/480),70*(Gdx.graphics.getHeight()/480),35, 0xFF0000FF));
        }
        batch.setColor(Color.WHITE);
        batch.draw(
                textTextures[6],
                centerx-(textTextures[6].getWidth()/2),
                centery-(textTextures[6].getHeight()/2)
        );

        font.draw(
                batch,
                "Cancel",
                centerx - (layout.width/2),
                centery + (layout.height/2)
        );

        batch.end();
    }


    public void resize(int width, int height)
    {
        camera = new OrthographicCamera(width,height);
        camera.position.set(0,0,0);
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

        if(!visible||gameState.disableActionWheel==true)
        {
            visible=false;
            return false;
        }

        Vector3 tmp=new Vector3(screenX,screenY,0);
        camera.unproject(tmp);

        float centerx=0;
        float centery=0;
        float radius=Gdx.graphics.getHeight()/2-40;

        GameState.ShipSections[] sections=GameState.getShipSections();
        float circlePart=360/sections.length;



        for(int i=0;i<sections.length;i++)
        {

            float realCenterX=(float)(centerx + Math.sin(Math.toRadians(circlePart*(float)i))*(radius));
            float realCenterY=(float)(centery + Math.cos(Math.toRadians(circlePart*(float)i))*(radius));

            Rectangle textureBounds=new Rectangle((int)realCenterX-(textTextures[i].getWidth()/2),(int)realCenterY-(textTextures[i].getHeight()/2),textTextures[i].getWidth(),textTextures[i].getHeight());
            if(textureBounds.contains(tmp.x,tmp.y))
            {
                Gdx.app.log("SE","Click on "+GameState.getShipSectionName(sections[i]));

                actionMenu.selectedShipSection=sections[i];
                actionMenu.update();
                actionMenu.visible=true;
                this.visible=false;

                SoundUtils.click.play();

                return true;
            }

        }

        if(!(tmp.x>70||tmp.x<-70||tmp.y>70||tmp.y<-70))
        {
            SoundUtils.cancel.play();
            Gdx.app.log("SE","Click on Exit");
            visible=false;
            return true;
        }

        return false;
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
