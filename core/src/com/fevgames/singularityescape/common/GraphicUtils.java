package com.fevgames.singularityescape.common;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Roby on 29/12/2015.
 */
public class GraphicUtils {

    public static Pixmap getPixmapRoundedRectangle(int width, int height, int radius, int color) {

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        // Pink rectangle
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight()-2*radius);

// Green rectangle
        pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2*radius, pixmap.getHeight());


// Bottom-left circle
        pixmap.fillCircle(radius, radius, radius);

// Top-left circle
        pixmap.fillCircle(radius, pixmap.getHeight()-radius, radius);

// Bottom-right circle
        pixmap.fillCircle(pixmap.getWidth()-radius, radius, radius);

// Top-right circle
        pixmap.fillCircle(pixmap.getWidth()-radius, pixmap.getHeight()-radius, radius);
        return pixmap;
    }

    public static Pixmap getPixmapRectangle(int width, int height, int color) {

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }







    private static Matrix4 isoTransform;
    private static Matrix4 invIsotransform;
    private static Matrix4 invIsotransformNotInv;

    public static void init () {


        // create the isometric transform
        isoTransform = new Matrix4();
        isoTransform.idt();

        // isoTransform.translate(0, 32, 0);
        isoTransform.scale((float)(Math.sqrt(2.0) / 1.0), (float)(Math.sqrt(2.0) / 2.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        // ... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransformNotInv = new Matrix4(isoTransform);
        invIsotransform.inv();
    }

    public static Vector3 translateScreenToIso (float x, float y) {
        Vector3 screenPos = new Vector3();

        screenPos.set(x, y, 0);
        screenPos.mul(invIsotransform);

        return screenPos;
    }
    public static Vector3 translateIsoToScreen (float x, float y) {
        Vector3 screenPos = new Vector3();

        screenPos.set(x, y, 0);
        screenPos.mul(invIsotransformNotInv);

        return screenPos;
    }

}
