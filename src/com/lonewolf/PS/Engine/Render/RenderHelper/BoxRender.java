package com.lonewolf.PS.Engine.Render.RenderHelper;

import static org.lwjgl.opengl.GL11.*;

public class BoxRender
{
    private float width;
    private float height;
    private float x;
    private float y;

    public BoxRender(float width, float height, float x, float y)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void render()
    {
        glBegin(GL_TRIANGLE_STRIP);

        glVertex2f(   x      ,   y);
        glVertex2f(   x      ,y + height);
        glVertex2f(x+width,   y);
        glVertex2f(x+width,y + height);

        glEnd();
    }
}
