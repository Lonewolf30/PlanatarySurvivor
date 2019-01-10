package com.lonewolf.PS.Engine.Component;

import com.lonewolf.PS.Engine.Core.CoreEngine;
import com.lonewolf.PS.Engine.Core.Matrix4f;
import com.lonewolf.PS.Engine.Core.Vector3f;

public class Camera extends GameComponent
{
    private Matrix4f viewPort;

    public Camera(Matrix4f viewPort) {
        this.viewPort = viewPort;
    }

    public Matrix4f getViewPort()
    {
        Matrix4f cameraRotation = GetTransform().GetTransformedRot().Conjugate().ToRotationMatrix();
        Vector3f camPos = GetTransform().GetTransformedPos().Mul(-1);

        Matrix4f cameraTrans = new Matrix4f().InitTranslation(camPos);

        return viewPort.Mul(cameraRotation.Mul(cameraTrans));
    }

    @Override
    public void setEngine(CoreEngine engine)
    {
        engine.getRenderingEngine().setCamera(this);
    }
}
