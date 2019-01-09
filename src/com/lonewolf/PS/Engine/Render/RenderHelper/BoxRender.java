package com.lonewolf.PS.Engine.Render.RenderHelper;

import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Render.Shader;
import com.lonewolf.PS.Engine.Render.Window;

import static org.lwjgl.opengl.GL11.*;

public class BoxRender
{
    private float width;
    private float height;
    private float x;
    private float y;

    public BoxRender(float width, float height, float x, float y)
    {
        this.width = width/2;
        this.height = height/2;
        this.x = x - Window.getDisplaySize().GetX()/2;
        this.y = y - Window.getDisplaySize().GetY()/2;
    }

    public void render(Shader shader)
    {
        shader.SetUniform("offset", new Vector2f(x,y).Div(Window.getDisplaySize().Sub(Window.getDisplaySize().Div(2))));
        shader.SetUniform("scale", new Vector2f(width, height).Div(Window.getDisplaySize().Sub(Window.getDisplaySize().Div(2))));

        glBegin(GL_TRIANGLE_STRIP);

        glVertex2f(0,0);
        glVertex2f(0,1);
        glVertex2f(1,0);
        glVertex2f(1,1);

        glEnd();
    }
}
