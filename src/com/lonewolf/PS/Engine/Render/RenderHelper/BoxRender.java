package com.lonewolf.PS.Engine.Render.RenderHelper;

import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Render.Shader;

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

    public void render(Shader shader)
    {
        shader.SetUniform("offset", new Vector2f(x,y));
        shader.SetUniform("scale", new Vector2f(width, height));

        glBegin(GL_TRIANGLE_STRIP);

        glVertex2f(-1,-1);
        glVertex2f(-1,1);
        glVertex2f(1,-1);
        glVertex2f(1,1);

        glEnd();
    }
}
