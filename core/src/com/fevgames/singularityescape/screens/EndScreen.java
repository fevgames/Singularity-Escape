package com.fevgames.singularityescape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fevgames.singularityescape.menu.MenuItem;

/**
 * Created by Roby on 21/12/2015.
 */
public class EndScreen implements Screen,InputProcessor {

    SpriteBatch batch;

    Game game;
    OrthographicCamera camera;

    Texture logo;
    float logoW,logoH;

    int currentItem;
    float elapsedTime;

    public EndScreen(Game _game,boolean win)
    {
        this.game=_game;

        elapsedTime=0;
        currentItem=0;


        if(win)
            logo = new Texture(Gdx.files.internal("screens/win.png"));
        else
            logo = new Texture(Gdx.files.internal("screens/lose.png"));


        logoH=0.5f;
        logoW=logoH*((float)logo.getWidth()/(float)logo.getHeight());



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

        elapsedTime+=delta;


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        batch.draw(logo, -(logoW/2), -(logoH/2), logoW, logoH, 0, 0,
                logo.getWidth(), logo.getHeight(), false, false);



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

        if(keycode==Input.Keys.ENTER)
        {
            game.setScreen(new MainMenuScreen(game));
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

        game.setScreen(new MainMenuScreen(game));

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
