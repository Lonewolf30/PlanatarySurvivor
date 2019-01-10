package com.lonewolf.PS.Engine.Core;

import com.lonewolf.PS.Engine.Render.RenderingEngine;

import java.util.ArrayList;

public class Game
{
    private RenderingEngine renderingEngine;
    private static ArrayList<GameObject> objects;
    public CoreEngine engine;

    public Game()
    {
        objects = new ArrayList<>();
    }

    public void init()
    {
    }

    public void addObject(GameObject object)
    {
        object.setEngine(engine);
        objects.add(object);
    }

    public void Render()
    {
        renderingEngine.Render(objects);
    }

    public void Update()
    {
        for (GameObject object : objects)
            object.Update();
    }

    public void Input()
    {
        for (GameObject object : objects)
            object.Input();
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public void setRenderingEngine(RenderingEngine renderingEngine)
    {
        this.renderingEngine = renderingEngine;
    }

    public static ArrayList<GameObject> getAllObjects()
    {
        return objects;
    }
}
