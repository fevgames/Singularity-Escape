package com.fevgames.singularityescape.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fevgames.singularityescape.map.MapTile;

/**
 * Created by Roby on 21/12/2015.
 */
public class GameScreen implements Screen {

    SpriteBatch batch;
    Game game;
    OrthographicCamera camera;

    MapTile[] mapTile;

    //Texture tile;
    //float tileW,tileH;

    public GameScreen(Game _game)
    {
        this.game=_game;
        //tile = new Texture(Gdx.files.internal("tiles/brickpavers2.png"));

        //tileH=1;
        //tileW=1*((float)tile.getWidth()/(float)tile.getHeight());

        batch = new SpriteBatch();
        mapTile=new MapTile[3];
        for(int x=0;x<3;x++)
        {
            for(int y=0;y<1;y++)
            {
                mapTile[x+((y*3))]=new MapTile("tiles/brickpavers2.png");
                mapTile[x+((y*3))].setPos(x-1,y-1);
            }
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(int i=0;i<3;i++)
        {
            mapTile[i].draw(batch);
        }

        /*batch.draw(tile, -(tileW/2), -(tileH/2), tileW, tileH, 0, 0,
                tile.getWidth(), tile.getHeight(), false, false);

        batch.draw(tile, -(tileW/2)+(tileW/2), -(tileH/2+(tileH/2)), tileW, tileH, 0, 0,
                tile.getWidth(), tile.getHeight(), false, false);*/



        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //camera = new OrthographicCamera(4,4*( h / w));
        camera = new OrthographicCamera(5*(w/h),5);
        camera.position.set(0f, 0f,0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
