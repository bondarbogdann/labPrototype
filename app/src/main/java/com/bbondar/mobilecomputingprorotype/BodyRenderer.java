package com.bbondar.mobilecomputingprorotype;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.Loader3DSMax;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.loader.fbx.LoaderFBX;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.Renderer;

/**
 * Created by bbondar on 01-Jun-16.
 */
public class BodyRenderer extends Renderer {
    public Context context;

    private DirectionalLight directionalLight;
    private Object3D bodyObject;
    private double xOffset;
    private double yOffset;
    private double zOffset;

    @Override
    protected void initScene() {
        directionalLight = new DirectionalLight(1f, .2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

        LoaderOBJ objParser = new LoaderOBJ(this, R.raw.shirt_obj);

        try {
            objParser.parse();
            bodyObject = objParser.getParsedObject();
            bodyObject.setPosition(0,0,0);
            getCurrentScene().addChild(bodyObject);
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        getCurrentCamera().setZ(40);
        getCurrentCamera().setY(2);
        getCurrentCamera().setX(1.5);
    }

    @Override
    public void onRender(final long elapsedTime, final double deltaTime) {
        super.onRender(elapsedTime, deltaTime);
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {
    }

    public BodyRenderer(Context context) {
        super(context);
        this.context = context;
        xOffset = yOffset = zOffset = 0;
        setFrameRate(60);
    }

    public void setRotation(double x, double y, double z) {
        bodyObject.setRotation(x - xOffset, y - yOffset, z - zOffset);
    }

    public void setOffset(double xOff, double yOff, double zOff){
        xOffset = xOff;
        yOffset = yOff;
        zOffset = zOff;
    }
}
