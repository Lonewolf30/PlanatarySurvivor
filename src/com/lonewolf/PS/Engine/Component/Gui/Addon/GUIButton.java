package com.lonewolf.PS.Engine.Component.Gui.Addon;

import com.lonewolf.PS.Engine.Component.Gui.GUIAddon;
import com.lonewolf.PS.Engine.Core.Input;
import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Core.Vector3f;
import com.lonewolf.PS.Engine.Render.RenderHelper.BoxRender;
import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Shader;
import com.lonewolf.PS.Engine.Render.Texture;
import com.lonewolf.PS.Engine.Render.Window;

import static org.lwjgl.opengl.GL11.*;

public abstract class GUIButton extends GUIAddon
{
    private float x;
    private float y;
    private float width;
    private float height;
    private String Text;
    private BoxRender boxRender;

    private Texture texture;

    public GUIButton(float x, float y, float width, float height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Text = text;

        boxRender = new BoxRender(width, height, x, y);
        texture = new Texture("white.jpg");
    }

    @Override
    public void Render2D(Shader shader, RenderingEngine renderingEngine)
    {
        shader.bind();
        texture.Bind();
        boxRender.render(shader);
    }

    @Override
    public void Input()
    {
        if (Input.GetMouseDown(0))
        {
            Vector2f pos = Input.GetMousePosition().Sub(Window.getDisplaySize().Div(2)).Div(Window.getDisplaySize().Div(2));

            if (pos.GetX() > x && pos.GetX() < x + width)
                if (pos.GetY() > y && pos.GetY() < y + height)
                    run();
        }
    }

    public abstract void run();
}
