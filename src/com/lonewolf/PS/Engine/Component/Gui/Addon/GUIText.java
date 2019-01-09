package com.lonewolf.PS.Engine.Component.Gui.Addon;

import com.lonewolf.PS.Engine.Component.Gui.GUIAddon;
import com.lonewolf.PS.Engine.Core.Vector3f;
import com.lonewolf.PS.Engine.Render.RenderHelper.BoxRender;
import com.lonewolf.PS.Engine.Render.RenderHelper.TextRender;
import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Shader;

public class GUIText extends GUIAddon
{
    private TextRender textRender;
    private float x;
    private float y;
    private BoxRender boxRender;

    public GUIText(String text, float x, float y, boolean changes)
    {
        this.textRender = new TextRender(text, changes);
        this.x = x;
        this.y = y;
        boxRender = new BoxRender(0.25f, 0.25f, x , y);
    }

    public void setText(String text)
    {
        this.textRender.setText(text);
    }

    @Override
    public void Render2D(Shader shader, RenderingEngine renderingEngine)
    {
        shader.bind();
        shader.SetUniformi("img", textRender.getTextTexture().GetID());
        boxRender.render(shader);
    }
}
