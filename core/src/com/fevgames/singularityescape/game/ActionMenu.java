package com.fevgames.singularityescape.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fevgames.singularityescape.common.GraphicUtils;

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

    private Texture bgTexture;
    private String [] textLines;

    public GameState.ShipSections selectedShipSection;

    public ActionMenu()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout=new GlyphLayout();
        visible=false;

        textLines=new String[4];
        textLines[0]="######";
        textLines[1]=" - Entry #1";
        textLines[2]=" - Entry #2";
        textLines[3]=" - Entry #3";

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

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(
                bgTexture,
                0-(bgTexture.getWidth()/2),
                0-(bgTexture.getHeight()/2)
        );

        for(int i=0;i<textLines.length;i++)
        {
            layout.setText(font,textLines[i]);
            font.draw(
                    batch,
                    textLines[i],
                    -bgTexture.getWidth()/2+30,
                    +bgTexture.getHeight()/2-30-(20*i)
            );
        }



        batch.end();
    }

    public void update()
    {
        textLines[0]=GameState.getShipSectionName(selectedShipSection);
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
