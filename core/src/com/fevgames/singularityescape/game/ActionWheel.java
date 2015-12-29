package com.fevgames.singularityescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fevgames.singularityescape.screens.GameScreen;

/**
 * Created by Roby on 28/12/2015.
 */
public class ActionWheel {

    SpriteBatch batch;
    OrthographicCamera camera;

    BitmapFont font;
    GameState gameState;
    GlyphLayout layout;
    public boolean visible;

    public ActionWheel()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout=new GlyphLayout();
        visible=false;
    }

    public void setGameState(GameState _g)
    {
        gameState=_g;
    }

    public void render()
    {
        camera.update();

        if(!visible)
            return;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        GameState.ShipSections[] sections=GameState.getShipSections();
        float circlePart=360/sections.length;

        float centerx=0;
        float centery=0;
        float radius=Gdx.graphics.getHeight()/2-40;

        for(int i=0;i<sections.length;i++)
        {
            layout.setText(font,GameState.getShipSectionName(sections[i]));
            font.draw(
                    batch,
                    GameState.getShipSectionName(sections[i]),
                    (float)(centerx + Math.sin(Math.toRadians(circlePart*(float)i))*(radius) - layout.width/2),
                    (float)(centery + Math.cos(Math.toRadians(circlePart*(float)i))*(radius) - layout.height/2)
            );
        }




        batch.end();
    }

    public void resize(int width, int height)
    {
        camera = new OrthographicCamera(width,height);
        camera.position.set(0,0,0);
    }
}
