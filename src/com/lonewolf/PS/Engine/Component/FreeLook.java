package com.lonewolf.PS.Engine.Component;

import com.lonewolf.PS.Engine.Core.*;
import com.lonewolf.PS.Engine.Render.Window;

public class FreeLook extends GameComponent
{
    private static final Vector3f Y_AXIS = new Vector3f(0,1,0);

    private float   m_sensitivity;

    public FreeLook(float sensitivity)
    {
        this.m_sensitivity = sensitivity;
    }

    @Override
    public void Input()
    {
        Vector2f centerPosition = Window.getDisplaySize().Div(2);

        if(Input.isCursorGrabbed())
        {
            Vector2f deltaPos = Input.GetMousePosition().Sub(centerPosition);

            float sensativity = m_sensitivity;

            boolean rotY = deltaPos.GetX() != 0;
            boolean rotX = deltaPos.GetY() != 0;

            if(rotY)
                GetTransform().Rotate(Y_AXIS, (float) Math.toRadians(deltaPos.GetX() * sensativity));

            if(rotX && GetTransform().GetRot().GetForward().GetY() > -0.999 && deltaPos.GetY() < 0)
                GetTransform().Rotate(GetTransform().GetRot().GetRight(), (float) Math.toRadians(-deltaPos.GetY() * sensativity));

            if(rotX && GetTransform().GetRot().GetForward().GetY() < 0.999 && deltaPos.GetY() > 0)
                GetTransform().Rotate(GetTransform().GetRot().GetRight(), (float) Math.toRadians(-deltaPos.GetY() * sensativity));

            if(rotY || rotX)
                Input.SetMousePosition(centerPosition);
        }
    }
}
