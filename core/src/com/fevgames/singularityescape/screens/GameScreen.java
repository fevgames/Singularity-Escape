package com.fevgames.singularityescape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.fevgames.singularityescape.common.GraphicUtils;
import com.fevgames.singularityescape.game.ActionMenu;
import com.fevgames.singularityescape.game.ActionWheel;
import com.fevgames.singularityescape.game.GameState;
import com.fevgames.singularityescape.game.MainUI;
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
    MainUI mainUI;
    GameState gameState;

    GameState.ShipSections loadedSection;

    Texture charTextures[];



    //Texture tile;
    //float tileW,tileH;

    public GameScreen(Game _game)
    {
        this.game=_game;

        charTextures=new Texture[3];
        charTextures[0]=new Texture(Gdx.files.internal("characters/tex.png"));
        charTextures[1]=new Texture(Gdx.files.internal("characters/cindy.png"));
        charTextures[2]=new Texture(Gdx.files.internal("characters/SL71b.png"));

        //tile = new Texture(Gdx.files.internal("tiles/brickpavers2.png"));

        //tileH=1;
        //tileW=1*((float)tile.getWidth()/(float)tile.getHeight());

        batch = new SpriteBatch();
        /*tiledMap = new TmxMapLoader().load("map/iso.tmx");
        tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap);*/

        inputMultiplexer=new InputMultiplexer();

        gameState=new GameState();
        gameState.init(_game,0);
        actionWheel=new ActionWheel();
        actionWheel.setGameState(gameState);
        actionMenu=new ActionMenu();
        actionMenu.setGameState(gameState);
        actionWheel.setActionMenu(actionMenu);
        mainUI=new MainUI();
        mainUI.setGameState(gameState);

        inputMultiplexer.addProcessor(mainUI);
        inputMultiplexer.addProcessor(actionMenu);
        inputMultiplexer.addProcessor(actionWheel);
        inputMultiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(inputMultiplexer);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w,h);

        loadedSection=GameState.ShipSections.NAVIGATION;
        this.loadMap(loadedSection);

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

        gameState.update();

        //Update visible ship section if it is different from loaded one
        if(gameState.currentShipSection!=loadedSection)
        {
            this.loadMap(gameState.currentShipSection);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        /*batch.setProjectionMatrix(camera.combined);
        batch.begin();*/

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //Check tex
        //if(gameState.texStatus.shipSection==loadedSection)
        {
            batch.draw(charTextures[0], camera.position.x - (charTextures[0].getWidth() / 2), camera.position.y - (charTextures[0].getHeight() / 2));
        }

        if(gameState.cindyStatus.shipSection==loadedSection)
        {
            batch.draw(charTextures[1], camera.position.x - (charTextures[1].getWidth() / 2)-charTextures[1].getWidth()*1.6f, camera.position.y - (charTextures[1].getHeight() / 2));
        }
        if(gameState.sL71bStatus.shipSection==loadedSection)
        {
            batch.draw(charTextures[2], camera.position.x - (charTextures[2].getWidth() / 2)+charTextures[2].getWidth()*1.6f, camera.position.y - (charTextures[2].getHeight() / 2));
        }



        for(int i=0;i<tiledMap.getLayers().getCount();i++)
        {
            MapLayer layer=tiledMap.getLayers().get(i);
            MapObjects objects=layer.getObjects();
            for(int k=0;k<objects.getCount();k++)
            {
                if(objects.get(k) instanceof PolylineMapObject)
                {
                    PolylineMapObject obj=(PolylineMapObject)objects.get(k);

                }
                if(objects.get(k) instanceof RectangleMapObject)
                {
                    RectangleMapObject obj=(RectangleMapObject)objects.get(k);

                    Vector3 v= GraphicUtils.translateIsoToScreen(obj.getRectangle().x,obj.getRectangle().y);

                    batch.draw(charTextures[2], v.x - (charTextures[2].getWidth() / 2), v.y - (charTextures[2].getHeight() / 2));
                    //Gdx.app.log("SE"," 1 - "+obj.getRectangle().getX());

                }


                //Gdx.app.log("SE",obj.getClass().toString());
            }
        }

        batch.end();




        actionWheel.render();
        actionMenu.render();
        mainUI.render();

        //batch.end();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("SE","GameScreen::resize("+width+","+height+")");
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //camera = new OrthographicCamera(4,4*( h / w));
        camera = new OrthographicCamera(w,h);

        camera.zoom=1.5f;
        if((Gdx.graphics.getHeight()/480)>=2)
            camera.zoom=0.8f;
        if((Gdx.graphics.getHeight()/480)>=3)
            camera.zoom=0.6f;

        this.centerOnTiledmap();

        //Gdx.app.log("SE","Tile name:"+ layer0.getObjects().getCount());
        //layer0.toString()


        actionWheel.resize(width,height);
        actionMenu.resize(width,height);
        mainUI.resize(width,height);
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

        Gdx.app.log("SE",""+camera.position.x+" - "+camera.position.y);

        /*if(keycode == Input.Keys.RIGHT)
        {
            this.loadedSection(GameState.ShipSections.ENGINEERING);
        }*/

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

        /*for(int i=0;i<tiledMap.getLayers().getCount();i++)
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
        }*/

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




    public void loadMap(GameState.ShipSections section)
    {
        String filename="";
        switch(section)
        {
            case CARGO:
                filename="Cargo.tmx";
                break;
            case NAVIGATION:
                filename="Navigation.tmx";
                break;
            case LIVING:
                filename="Living.tmx";
                break;
            case TACTICAL:
                filename="Tactical.tmx";
                break;
            case ENGINEERING:
                filename="Engineering.tmx";
                break;
        }

        if(filename.length()>0)
        {
            if(tiledMap!=null)
                tiledMap.dispose();
            tiledMap = new TmxMapLoader().load("map/tmx/"+filename);
            tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap);

            this.centerOnTiledmap();
            loadedSection=section;
        }
    }

    public void centerOnTiledmap()
    {
        if(camera!=null)
        {
            /*TiledMapTileLayer layer0 = (TiledMapTileLayer) tiledMap.getLayers().get(0);
            Vector3 center = new Vector3(layer0.getWidth() * layer0.getTileWidth() / 2, layer0.getHeight() * layer0.getTileHeight() / 2, 0);
            camera.position.set(center);*/

            /*MapProperties prop = tiledMap.getProperties();

            int mapWidth = prop.get("width", Integer.class);
            int mapHeight = prop.get("height", Integer.class);
            int tilePixelWidth = prop.get("tilewidth", Integer.class);
            int tilePixelHeight = prop.get("tileheight", Integer.class);

            int mapPixelWidth = mapWidth * tilePixelWidth;
            int mapPixelHeight = mapHeight * tilePixelHeight;

            Gdx.app.log("SE","mapPixelWidth:"+mapPixelWidth+" mapPixelHeight:"+mapPixelHeight);
            Gdx.app.log("SE","tilePixelWidth:"+tilePixelWidth+" tilePixelHeight:"+tilePixelHeight);
            Gdx.app.log("SE","mapWidth:"+mapWidth+" mapHeight:"+mapHeight);

            float tileXPos=(mapHeight/2 * tilePixelWidth / 2) + (mapWidth/2  * tilePixelWidth / 2);
            float tileYPos=(mapWidth/2  * tilePixelHeight / 2) - (mapHeight/2 * tilePixelHeight / 2);*/

            Vector3 center = new Vector3(1600.0f , -192.0f, 0);
            camera.position.set(center);
        }

    }

}
