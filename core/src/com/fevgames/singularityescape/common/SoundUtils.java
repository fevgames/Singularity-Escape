package com.fevgames.singularityescape.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Roby on 15/01/2016.
 */
public class SoundUtils {

    public static Sound alert,cancel,click;

    public static void init()
    {
        alert = Gdx.audio.newSound(Gdx.files.internal("audio/alert.mp3"));
        cancel = Gdx.audio.newSound(Gdx.files.internal("audio/cancel.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("audio/click.mp3"));
    }
}
