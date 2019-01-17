package com.lonewolf.PS.Engine.Component.Gui.Addon;

import com.lonewolf.PS.Engine.Component.Gui.GUIAddon;
import com.lonewolf.PS.Engine.Core.Input;
import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Core.Vector3f;
import com.lonewolf.PS.Engine.Render.RenderHelper.BoxRender;
import com.lonewolf.PS.Engine.Render.RenderHelper.TextRender;
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
    private GUIText text;
    private BoxRender boxRender;

    private Texture texture;
    private Texture selected = new Texture("buttonSelected.jpg");
    private Texture unselected = new Texture("buttonUnselected.jpg");

    public GUIButton(float x, float y, float width, float height, String text) {
        this.x = x;
        this.y = y;
        this.width = width/2;
        this.height = height/2;
        this.text = new GUIText(text, x+width/32,y+height/8, false);
        this.text.center(x,y, width,height);

        boxRender = new BoxRender(width, height, x, y);
        texture = unselected;
    }

    @Override
    public void Render2D(Shader shader, RenderingEngine renderingEngine)
    {
        shader.bind();
        texture.Bind();
        boxRender.render(shader);
        text.Render2D(shader, renderingEngine);
    }

    @Override
    public void Input()
    {
        Vector2f pos = Input.GetMousePosition();

        if (pos.GetX()>x && pos.GetX() < x + width)
        {
            if (pos.GetY() > y && pos.GetY() < y + height)
                texture = selected;
            else
                texture = unselected;
        }
        else
            texture = unselected;

        if (Input.GetMouseDown(0))
        {
            if (pos.GetX() > x && pos.GetX() < x + width)
            {
                if (pos.GetY() > y && pos.GetY() < y + height)
                {
                    run();
                }
            }
        }
    }

    public abstract void run();
}
