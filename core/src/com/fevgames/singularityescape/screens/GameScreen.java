package com.fevgames.singularityescape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.fevgames.singularityescape.game.ActionMenu;
import com.fevgames.singularityescape.game.ActionWheel;
import com.fevgames.singularityescape.game.GameState;
import com.fevgames.singularityescape.map.MapTile;

/**
 * Created by Roby on 21/12/2015.
 */
public class GameScreen implements Screen, InputProcessor {

    SpriteBatch batch;
    Game game;
    OrthographicCamera camera;
    InputMultiplexer inputMultiplexer;

    TiledMapRenderer tiledMapRenderer;
    TiledMap tiledMap;

    ActionWheel actionWheel;
    ActionMenu actionMenu;
    GameState gameState;



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

        inputMultiplexer=new InputMultiplexer();

        gameState=new GameState();
        gameState.init(0);
        actionWheel=new ActionWheel();
        actionWheel.setGameState(gameState);
        actionMenu=new ActionMenu();
        actionMenu.setGameState(gameState);
        actionWheel.setActionMenu(actionMenu);

        inputMultiplexer.addProcessor(actionMenu);
        inputMultiplexer.addProcessor(actionWheel);
        inputMultiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(inputMultiplexer);

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.W))
        {
            actionWheel.visible=!actionWheel.visible;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        /*batch.setProjectionMatrix(camera.combined);
        batch.begin();*/

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        actionWheel.render();
        actionMenu.render();

        //batch.end();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("SE","GameScreen::resize("+width+","+height+")");
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //camera = new OrthographicCamera(4,4*( h / w));
        camera = new OrthographicCamera(w,h);
        camera.zoom=2;

        TiledMapTileLayer layer0 = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        Vector3 center = new Vector3(layer0.getWidth() * layer0.getTileWidth() / 2, layer0.getHeight() * layer0.getTileHeight() / 2, 0);
        camera.position.set(center);

        //Gdx.app.log("SE","Tile name:"+ layer0.getObjects().getCount());
        //layer0.toString()


        actionWheel.resize(width,height);
        actionMenu.resize(width,height);
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

        Vector3 touchPoint=new Vector3(screenX,screenY,0);
        camera.unproject(touchPoint);

        for(int i=0;i<tiledMap.getLayers().getCount();i++)
        {
            MapLayer layer=tiledMap.getLayers().get(i);
            MapObjects objects=layer.getObjects();
            for(int k=0;k<objects.getCount();k++)
            {
                if(objects.get(k) instanceof PolylineMapObject)
                {
                    PolylineMapObject obj=(PolylineMapObject)objects.get(k);
                    if(obj.getPolyline().contains(touchPoint.x,touchPoint.y))
                    {
                        Gdx.app.log("SE","CLICK!!");
                        return true;
                    }
                }
                if(objects.get(k) instanceof RectangleMapObject)
                {
                    RectangleMapObject obj=(RectangleMapObject)objects.get(k);
                    //Gdx.app.log("SE"," 1 - "+obj.getRectangle().getX()+" - "+touchPoint.x);
                    if(obj.getRectangle().contains(touchPoint.x,touchPoint.y))
                    {
                        Gdx.app.log("SE","CLICK!!");
                        return true;
                    }
                }


                //Gdx.app.log("SE",obj.getClass().toString());
            }
        }

        if(actionWheel.visible==false)
            actionWheel.visible=true;

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
