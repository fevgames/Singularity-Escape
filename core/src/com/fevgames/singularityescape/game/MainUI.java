package com.fevgames.singularityescape.game;

import com.badlogic.gdx.Gdx;
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
public class MainUI implements InputProcessor {

    SpriteBatch batch;
    OrthographicCamera camera;
    GameState gameState;
    BitmapFont font;
    GlyphLayout layout;
    public boolean visible;
    private Texture bgTexture;

    public MainUI()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout=new GlyphLayout();
        visible=true;

        font.setColor(Color.WHITE);

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

        /*batch.draw(
                bgTexture,
                0-(bgTexture.getWidth()/2),
                0+(Gdx.graphics.getHeight()/2)-bgTexture.getHeight()
        );*/

        String txt="Distance: "+gameState.distance+" - Integrity: "+((int)gameState.integrity)+"% - Time: "+((int)gameState.gameTime)+" - New card in: "+(((int)(120-gameState.gameTimeSinceLastCard)));

        layout.setText(font,txt);
        font.draw(
                batch,
                txt,
                0-(bgTexture.getWidth()/2)+10,
                +0+(Gdx.graphics.getHeight()/2)-bgTexture.getHeight()/2+8
        );

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

    }

    public void resize(int width, int height)
    {
        camera = new OrthographicCamera(width,height);
        camera.position.set(0,0,0);

        bgTexture=new Texture(GraphicUtils.getPixmapRectangle(width,40,0x000000FF));
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
