package com.lonewolf.PS.Engine.Component.Gui.Addon;

import com.lonewolf.PS.Engine.Component.Gui.GUIAddon;
import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Core.Vector3f;
import com.lonewolf.PS.Engine.Render.RenderHelper.BoxRender;
import com.lonewolf.PS.Engine.Render.RenderHelper.TextRender;
import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Shader;
import com.lonewolf.PS.Engine.Render.Window;

public class GUIText extends GUIAddon
{
    private TextRender textRender;
    private float x;
    private float y;
    private BoxRender boxRender;
    private String text;

    public GUIText(String text, float x, float y, boolean changes)
    {
        this.textRender = new TextRender(text, changes);
        this.x = x;
        this.y = y;
        this.text = text;
        boxRender = new BoxRender(40 * text.length(), 100, x , y);
    }

    public void center(float x, float y, float width, float height)
    {
        boxRender = new BoxRender(40*text.length(), 100, x + (width/2 - 20*text.length())/2, y + (height/2 - 50)/2);
    }

    public void setText(String text)
    {
        this.textRender.setText(text);
    }

    @Override
    public void Render2D(Shader shader, RenderingEngine renderingEngine)
    {
        shader.bind();
        textRender.getTextTexture().Bind();
        boxRender.render(shader);
    }
}
