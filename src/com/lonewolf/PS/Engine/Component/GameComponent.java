package com.lonewolf.PS.Engine.Component;

import com.lonewolf.PS.Engine.Core.CoreEngine;
import com.lonewolf.PS.Engine.Core.GameObject;
import com.lonewolf.PS.Engine.Core.Transform;
import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Shader;

public class GameComponent
{
    public GameObject parent;
    public CoreEngine engine;

    public void Update() {}

    public void Input() {}

    public void Render2D(Shader shader, RenderingEngine renderingEngine) {}

    public void Render3D(Shader shader, RenderingEngine renderingEngine) {}

    public void setEngine(CoreEngine engine) { this.engine = engine;}

    public void  setParent(GameObject parent)
    {
        this.parent = parent;
    }

    public Transform GetTransform() {return parent.getTransform();}
}
