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
    private float scale;
    private BoxRender boxRender;

    public GUIText(String text, float x, float y, float scale, boolean changes)
    {
        this.textRender = new TextRender(text, changes);
        this.x = x;
        this.y = y;
        this.scale = scale;
        boxRender = new BoxRender(text.length()*8, 8, x , y);
    }

    public void setText(String text)
    {
        this.textRender.setText(text);
    }

    @Override
    public void Render2D(Shader shader, RenderingEngine renderingEngine)
    {
        shader.bind();
        shader.SetUniform("color", new Vector3f(1,0,1));
        textRender.getTextTexture().Bind();
        boxRender.render();
    }
}
