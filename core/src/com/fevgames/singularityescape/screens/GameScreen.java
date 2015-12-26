package com.fevgames.singularityescape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.fevgames.singularityescape.map.MapTile;

/**
 * Created by Roby on 21/12/2015.
 */
public class GameScreen implements Screen, InputProcessor {

    SpriteBatch batch;
    Game game;
    OrthographicCamera camera;

    TiledMapRenderer tiledMapRenderer;
    TiledMap tiledMap;



    //Texture tile;
    //float tileW,tileH;

    public GameScreen(Game _game)
    {
        this.game=_game;
        //tile = new Texture(Gdx.files.internal("tiles/brickpavers2.png"));

        //tileH=1;
        //tileW=1*((float)tile.getWidth()/(float)tile.getHeight());

        batch = new SpriteBatch();
        tiledMap = new TmxMapLoader().load("map/iso.tmx");
        tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);

        Gdx.app.log("SE","GameScreen::GameScreen()");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS))
        {
            camera.zoom-=0.2;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS))
        {
            camera.zoom+=0.2;
            //Gdx.app.log("SE","Pos("+camera.position.x+","+camera.position.y+")");
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        /*batch.setProjectionMatrix(camera.combined);
        batch.begin();*/

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();




        //batch.end();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("SE","GameScreen::resize("+width+","+height+")");
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //camera = new OrthographicCamera(4,4*( h / w));
        camera = new OrthographicCamera(w,h);
        camera.position.set(1508.0f,932.0f,0);
        camera.zoom=2;
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(32,0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,-32);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0,32);

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
