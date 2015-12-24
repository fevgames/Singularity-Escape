package com.fevgames.singularityescape.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Roby on 21/12/2015.
 */
public class MapTile {

    private Texture texture;
    private float width,height;
    private float x,y;
    private float opacity;

    public MapTile(String path)
    {
        texture = new Texture(Gdx.files.internal(path));

        x=0;
        y=0;
        opacity=1;

        height=1;
        width=height*((float)texture.getWidth()/(float)texture.getHeight());
    }

    public void setPos(float _x,float _y)
    {
        x=_x;
        y=_y;
    }

    public void setOpacity(float _o)
    {
        opacity=_o;
    }

    public void draw(SpriteBatch batch)
    {
        batch.setColor(1,1,1,opacity);
        batch.draw((texture), -(width/2)+(width/2*x), -(height/2)+(height/2*y), width, height, 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
        batch.setColor(1,1,1,1);
    }

    public void dispose()
    {
        texture.dispose();
    }

}
