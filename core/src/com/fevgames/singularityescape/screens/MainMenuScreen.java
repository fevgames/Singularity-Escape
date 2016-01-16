package com.fevgames.singularityescape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
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
    MenuItem menuItemNewGame,menuCredits;
    MenuItem menuEasy,menuMedium,menuHard;

    int screenID;

    int currentItem;
    int currentItem2;
    float elapsedTime;

    Music music;

    public MainMenuScreen(Game _game)
    {
        this.game=_game;

        screenID=0;

        elapsedTime=0;
        currentItem=0;
        currentItem2=0;
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
        menuCredits=new MenuItem("menu/credits_Regular.png");
        menuEasy=new MenuItem("menu/easy_Regular.png");
        menuMedium=new MenuItem("menu/medium_Regular.png");
        menuHard=new MenuItem("menu/hard_Regular.png");

        menuItemNewGame.setHeight(0.1f);
        menuCredits.setHeight(0.1f);
        menuEasy.setHeight(0.1f);
        menuMedium.setHeight(0.1f);
        menuHard.setHeight(0.1f);

        menuItemNewGame.setPos(-0.2f,-0.30f);
        menuCredits.setPos(0.2f,-0.30f);
        menuEasy.setPos(-0.4f,-0.30f);
        menuMedium.setPos(-0f,-0.30f);
        menuHard.setPos(0.4f,-0.30f);


        menuItemNewGame.setOpacity(0);
        menuCredits.setOpacity(0);

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
        menuEasy.setSelected(true);

        Gdx.app.log("SE","logoW:"+logoW);

        Gdx.input.setInputProcessor(this);

        /*float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(4,4*( h / w));*/

        batch = new SpriteBatch();

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/menu.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        /*
        * World height is 1
        * */

        if(elapsedTime>=1&&elapsedTime<=2)
        {
            menuItemNewGame.setOpacity(elapsedTime-1);
        }
        if(elapsedTime>=2&&elapsedTime<=3)
        {
            menuCredits.setOpacity(elapsedTime-2);
        }

        if(elapsedTime>3)
        {
            menuItemNewGame.setOpacity(1);
            menuCredits.setOpacity(1);
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



        if(screenID==0)
        {
            menuItemNewGame.draw(batch);
            menuCredits.draw(batch);
        }
        else if(screenID==1)
        {
            menuEasy.draw(batch);
            menuMedium.draw(batch);
            menuHard.draw(batch);
        }





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

        music.stop();
        music.dispose();

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
            if(screenID==0)
                currentItem++;
            if(screenID==1)
                currentItem2++;
        }
        if(keycode==Input.Keys.LEFT)
        {
            if(screenID==0)
                currentItem--;
            if(screenID==1)
                currentItem2--;
        }

        this.updateItems();

        if((keycode==Input.Keys.ESCAPE||keycode==Input.Keys.BACKSPACE)&&screenID==1)
        {
            screenID=0;
            return true;
        }

        if(keycode==Input.Keys.ENTER&&currentItem==0&&screenID==0)
        {
            screenID=1;
            return true;
        }
        if(keycode==Input.Keys.ENTER&&screenID==1&&currentItem2==0)
        {
            this.dispose();
            game.setScreen(new GameScreen(game,0));
            return true;
        }
        if(keycode==Input.Keys.ENTER&&screenID==1&&currentItem2==1)
        {
            this.dispose();
            game.setScreen(new GameScreen(game,1));
            return true;
        }
        if(keycode==Input.Keys.ENTER&&screenID==1&&currentItem2==2)
        {
            this.dispose();
            game.setScreen(new GameScreen(game,2));
            return true;
        }

        if(keycode==Input.Keys.ENTER&&currentItem==1)
        {
            Gdx.net.openURI("https://github.com/fevgames/Singularity-Escape/blob/master/README.md");
            return true;
            //game.setScreen(new GameScreen(game));
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

        Vector3 touchPoint=new Vector3(screenX,screenY,0);
        camera.unproject(touchPoint);

        if(screenID==0)
        {
            if(menuItemNewGame.checkClick(touchPoint.x,touchPoint.y))
            {
                screenID=1;
                currentItem=0;
            }
            if(menuCredits.checkClick(touchPoint.x,touchPoint.y))
            {
                Gdx.net.openURI("https://github.com/fevgames/Singularity-Escape/blob/master/README.md");
                /*screenID=1;
                currentItem=0;*/
            }
        }
        else if(screenID==1)
        {
            if(menuEasy.checkClick(touchPoint.x,touchPoint.y))
            {
                this.dispose();
                game.setScreen(new GameScreen(game,0));
            }
            if(menuMedium.checkClick(touchPoint.x,touchPoint.y))
            {
                this.dispose();
                game.setScreen(new GameScreen(game,1));
            }
            if(menuHard.checkClick(touchPoint.x,touchPoint.y))
            {
                this.dispose();
                game.setScreen(new GameScreen(game,2));
            }
        }

        this.updateItems();

        //game.setScreen(new GameScreen(game));
        return true;
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


    private void updateItems()
    {
        if(currentItem<0)
            currentItem=0;
        if(currentItem2<0)
            currentItem2=0;
        if(currentItem>1)
            currentItem=1;
        if(currentItem2>2)
            currentItem2=2;

        menuItemNewGame.setSelected(false);
        menuCredits.setSelected(false);
        menuEasy.setSelected(false);
        menuMedium.setSelected(false);
        menuHard.setSelected(false);

        if(currentItem==0)
            menuItemNewGame.setSelected(true);
        if(currentItem==1)
            menuCredits.setSelected(true);

        if(currentItem2==0)
            menuEasy.setSelected(true);
        if(currentItem2==1)
            menuMedium.setSelected(true);
        if(currentItem2==2)
            menuHard.setSelected(true);
    }
}
