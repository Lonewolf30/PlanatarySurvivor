package com.lonewolf.PS.Engine.Component;

import com.lonewolf.PS.Engine.Core.CoreEngine;
import com.lonewolf.PS.Engine.Core.Vector3f;

public class Light extends GameComponent
{
    private Vector3f color;
    private Vector3f pos;
    private float intensity;

    public Light(Vector3f color, float intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    @Override
    public void setEngine(CoreEngine engine)
    {
        pos = GetTransform().GetPos();
        engine.getRenderingEngine().addLight(this);
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getPos() {
        return pos;
    }

    public float getIntensity() {
        return intensity;
    }
}
