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
        if(Gdx.graphics.getHeight()<=480)
            font = new BitmapFont(Gdx.files.internal("fonts/opensans_15.fnt"));
        else if(Gdx.graphics.getHeight()>=1080)
            font = new BitmapFont(Gdx.files.internal("fonts/opensans_40.fnt"));
        else if(Gdx.graphics.getHeight()>=720)
            font = new BitmapFont(Gdx.files.internal("fonts/opensans_24.fnt"));
        layout=new GlyphLayout();
        visible=true;

        font.setColor(Color.WHITE);

        //font.getData().setScale(Gdx.graphics.getHeight()/1080f,Gdx.graphics.getHeight()/1080f);

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

        String txt="Distance: "+(int)gameState.distance+"/600 - Integrity: "+((int)gameState.integrity)+"% - Time: "+((int)gameState.gameTime)+" - New card in: "+(((int)(30-gameState.gameTimeSinceLastCard)))+" - Crates: "+gameState.cratesNumber;

        layout.setText(font,txt);
        font.draw(
                batch,
                txt,
                0-(bgTexture.getWidth()/2)+10,
                +0+(Gdx.graphics.getHeight()/2)-bgTexture.getHeight()/2+8
        );


        txt="[Tex] HP:"+gameState.texStatus.health+"% O2:"+gameState.texStatus.health+"%";
        txt+=" - [Cindy] HP:"+gameState.cindyStatus.health+"% O2:"+gameState.cindyStatus.health+"%";
        txt+=" - [SL71b] HP:"+gameState.sL71bStatus.health+"% O2:"+gameState.sL71bStatus.health+"%";

        layout.setText(font,txt);
        font.draw(
                batch,
                txt,
                0-(bgTexture.getWidth()/2)+10,
                +0-(Gdx.graphics.getHeight()/2)+bgTexture.getHeight()/2+8
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
