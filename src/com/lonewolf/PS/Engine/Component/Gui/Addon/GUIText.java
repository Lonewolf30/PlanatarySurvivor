package com.lonewolf.PS.Engine.Component.Gui.Addon;

import com.lonewolf.PS.Engine.Component.Gui.GUIAddon;
import com.lonewolf.PS.Engine.Core.Vector3f;
import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Shader;
import com.lonewolf.PS.Engine.Render.TextRender;

public class GUIText extends GUIAddon
{
    private String text;
    private float x;
    private float y;
    private float width;
    private float scale;

    public GUIText(String text, float x, float y, float width, float scale) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.scale = scale;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public void Render2D(Shader shader, RenderingEngine renderingEngine)
    {
        shader.bind();
        shader.SetUniform("color", new Vector3f(1,0,1));
        TextRender.drawString(text, x, y, scale, width);
    }
}
