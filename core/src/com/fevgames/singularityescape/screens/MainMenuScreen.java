package com.fevgames.singularityescape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fevgames.singularityescape.menu.MenuItem;

/**
 * Created by Roby on 21/12/2015.
 */
public class MainMenuScreen implements Screen,InputProcessor {

    SpriteBatch batch;

    Game game;
    OrthographicCamera camera;

    Texture bg;
    float bgW,bgH;

    Texture logo;
    float logoW,logoH;
    boolean bgGoingDown;
    float bgOffset;

    Texture vortex;
    float vortexW,vortexH,vortexAngle;

    //MenuItem menuItemEasy,menuItemMedium,menuItemHard;
    MenuItem menuItemNewGame,menuItemLoadGame,menuItemAboutUs;

    int currentItem;
    float elapsedTime;

    public MainMenuScreen(Game _game)
    {
        this.game=_game;

        elapsedTime=0;
        currentItem=0;
        bgGoingDown=true;
        bgOffset=0;

        vortexAngle=0f;

        bg = new Texture(Gdx.files.internal("menu/SingularityEscape_Background.png"));
        bgH=1.5f;
        bgW=bgH*((float)bg.getWidth()/(float)bg.getHeight());

        logo = new Texture(Gdx.files.internal("menu/SingularityEscape_Text.png"));
        logoH=0.15f;
        logoW=logoH*((float)logo.getWidth()/(float)logo.getHeight());

        vortex = new Texture(Gdx.files.internal("menu/SingularityEscape_Vortex_2.png"));
        vortexH=0.35f;
        vortexW=vortexH*((float)vortex.getWidth()/(float)vortex.getHeight());

        /*menuItemEasy=new MenuItem("menu_easy.png");
        menuItemMedium=new MenuItem("menu_medium.png");
        menuItemHard=new MenuItem("menu_hard.png");*/
        menuItemNewGame=new MenuItem("menu/NewGame_Regular.png");
        menuItemLoadGame=new MenuItem("menu/LoadGame_Regular.png");
        menuItemAboutUs=new MenuItem("menu/AboutUs_Regular.png");

        menuItemNewGame.setHeight(0.1f);
        menuItemLoadGame.setHeight(0.1f);
        menuItemAboutUs.setHeight(0.1f);

        menuItemNewGame.setPos(-0.4f,-0.30f);
        menuItemLoadGame.setPos(-0f,-0.30f);
        menuItemAboutUs.setPos(0.4f,-0.30f);

        menuItemNewGame.setOpacity(0);
        menuItemLoadGame.setOpacity(0);
        menuItemAboutUs.setOpacity(0);

        /*menuItemEasy.setHeight(0.08f);
        menuItemMedium.setHeight(0.08f);
        menuItemHard.setHeight(0.08f);

        menuItemEasy.setPos(0f,-0.20f);
        menuItemMedium.setPos(0f,-0.30f);
        menuItemHard.setPos(0f,-0.40f);

        menuItemEasy.setOpacity(0);
        menuItemMedium.setOpacity(0);
        menuItemHard.setOpacity(0);*/

        menuItemNewGame.setSelected(true);

        Gdx.app.log("SE","logoW:"+logoW);

        Gdx.input.setInputProcessor(this);

        /*float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(4,4*( h / w));*/

        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        /*
        * World height is 1
        * */

        if(elapsedTime>=3&&elapsedTime<=4)
        {
            menuItemNewGame.setOpacity(elapsedTime-3);
        }
        if(elapsedTime>=4&&elapsedTime<=5)
        {
            menuItemLoadGame.setOpacity(elapsedTime-4);
        }
        if(elapsedTime>=5&&elapsedTime<=6)
        {
            menuItemAboutUs.setOpacity(elapsedTime-5);
        }

        elapsedTime+=delta;
        vortexAngle+=delta*10;

        if(bgGoingDown)
        {
            bgOffset+=(delta*0.25)/30;
            if(bgOffset>=0.25)
                bgGoingDown=false;
        }
        else
        {
            bgOffset-=(delta*0.25)/30;
            if(bgOffset<=-0.25)
                bgGoingDown=true;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(bg, -(bgW/2), -(bgH/2)+bgOffset, bgW, bgH, 0, 0,
                bg.getWidth(), bg.getHeight(), false, false);

        batch.draw(vortex,
                (-(vortexW/2))+(logoW*0.13f),
                (-(vortexH/2))+0.25f,
                (vortexW/2),
                (vortexH/2),
                vortexW,
                vortexH,
                1,
                1,
                vortexAngle,
                0,
                0,
                vortex.getWidth(),
                vortex.getHeight(),
                false,
                false);

        batch.draw(logo, -(logoW/2), -(logoH/2)+0.25f, logoW, logoH, 0, 0,
                logo.getWidth(), logo.getHeight(), false, false);





        menuItemNewGame.draw(batch);
        menuItemLoadGame.draw(batch);
        menuItemAboutUs.draw(batch);



        /*menuItemEasy.draw(batch);
        menuItemMedium.draw(batch);
        menuItemHard.draw(batch);*/

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //camera = new OrthographicCamera(4,4*( h / w));
        camera = new OrthographicCamera(1*(w/h),1);
        camera.position.set(0f, 0f,0);

        Gdx.app.log("SE","W:"+(1*(w/h))+" H:1");
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
        logo.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode==Input.Keys.RIGHT)
        {
            currentItem++;
        }
        if(keycode==Input.Keys.LEFT)
        {
            currentItem--;
        }
        if(currentItem<0)
            currentItem=0;
        if(currentItem>2)
            currentItem=2;

        menuItemNewGame.setSelected(false);
        menuItemLoadGame.setSelected(false);
        menuItemAboutUs.setSelected(false);
        if(currentItem==0)
            menuItemNewGame.setSelected(true);
        if(currentItem==1)
            menuItemLoadGame.setSelected(true);
        if(currentItem==2)
            menuItemAboutUs.setSelected(true);

        if(keycode==Input.Keys.ENTER)
        {
            game.setScreen(new GameScreen(game));
        }


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
