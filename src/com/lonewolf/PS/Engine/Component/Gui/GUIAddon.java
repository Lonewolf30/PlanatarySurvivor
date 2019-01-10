package com.lonewolf.PS.Engine.Component.Gui;

import com.lonewolf.PS.Engine.Component.GameComponent;
import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Shader;

public abstract class GUIAddon extends GameComponent
{
    @Override
    public void Update() {}

    @Override
    public void Render2D(Shader shader, RenderingEngine renderingEngine) {}

    @Override
    public void Input() {}
}
