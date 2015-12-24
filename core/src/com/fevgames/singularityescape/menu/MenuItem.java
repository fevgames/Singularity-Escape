package com.fevgames.singularityescape.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Roby on 21/12/2015.
 */
public class MenuItem {

    private Texture texture;
    private Texture textureSelected;
    private float width,height;
    private float x,y;
    private float opacity;
    private boolean selected;

    public MenuItem(String path)
    {
        texture = new Texture(Gdx.files.internal(path));
        textureSelected = new Texture(Gdx.files.internal(path./*replace(".","_selected.").*/replace("_Regular","_Hover")));

        /*texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        textureSelected.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);*/

        x=0;
        y=0;
        opacity=1;

        selected=false;

        height=1;
        width=height*((float)texture.getWidth()/(float)texture.getHeight());
    }

    public void setHeight(float _h)
    {
        height=_h;
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

    public void setSelected(boolean _s)
    {
        selected=_s;
    }

    public void draw(SpriteBatch batch)
    {
        batch.setColor(1,1,1,opacity);
        batch.draw((selected?textureSelected:texture), -(width/2)+x, -(height/2)+y, width, height, 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
        batch.setColor(1,1,1,1);
    }

    public void dispose()
    {
        texture.dispose();
    }
}
