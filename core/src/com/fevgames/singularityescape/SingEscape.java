package com.fevgames.singularityescape;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fevgames.singularityescape.common.GraphicUtils;
import com.fevgames.singularityescape.screens.GameScreen;
import com.fevgames.singularityescape.screens.MainMenuScreen;

public class SingEscape extends Game {
	//SpriteBatch batch;
	//Texture img;

	MainMenuScreen mainMenuScreen;
	GameScreen gameScreen;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		GraphicUtils.init();

		mainMenuScreen=new MainMenuScreen(this);
		setScreen(mainMenuScreen);
		/*gameScreen=new GameScreen(this);
		setScreen(gameScreen);*/
	}

	@Override
	public void render () {
		super.render();
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
	}
}
