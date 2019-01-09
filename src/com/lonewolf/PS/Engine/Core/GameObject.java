package com.lonewolf.PS.Engine.Core;

import com.lonewolf.PS.Engine.Component.GameComponent;
import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Shader;

import java.util.ArrayList;

public class GameObject
{
    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    private CoreEngine engine;
    private GameObject parent;
    private Transform transform;

    public GameObject()
    {
        children = new ArrayList<>();
        components = new ArrayList<>();
        transform = new Transform();
    }

    public GameObject addComponet(GameComponent component)
    {
        component.setParent(this);
        components.add(component);
        return this;
    }

    public GameObject addChild(GameObject child)
    {
        children.add(child);
        return this;
    }

    public void Input()
    {
        for (GameObject object : children)
            object.Input();

        for (GameComponent component : components)
            component.Input();
    }

    public void Update()
    {
        for (GameObject object : children)
            object.Update();

        for (GameComponent component : components)
            component.Update();
    }

    public void Render2D(Shader shader, RenderingEngine renderingEngine)
    {
        for (GameObject object : children)
            object.Render2D(shader, renderingEngine);

        for (GameComponent component : components)
            component.Render2D(shader, renderingEngine);
    }

    public void Render3D(Shader shader, RenderingEngine renderingEngine)
    {
        for (GameObject object : children)
            object.Render3D(shader, renderingEngine);

        for (GameComponent component : components)
            component.Render3D(shader, renderingEngine);
    }

    public ArrayList<GameObject> getAllAttached()
    {
        ArrayList<GameObject> results = new ArrayList<>();

        results.add(this);

        if (children.size() > 0)
        {
            results.addAll(children);
            children.forEach(gameObject -> results.addAll(gameObject.getAllAttached()));
        }

        return results;
    }

    public GameComponent getComponent(Class componentClass)
    {
        for (GameComponent component : components)
            if (component.getClass() == componentClass)
                return component;

        return null;
    }

    public void setEngine(CoreEngine engine)
    {
        this.engine = engine;
        components.forEach(gameComponent -> gameComponent.setEngine(this.engine));
        children.forEach(gameObject -> gameObject.setEngine(this.engine));
    }

    public Transform getTransform() {
        return transform;
    }


    public GameObject getParent()
    {
        return parent == null ? parent : this;
    }

    public void setParent(GameObject parent)
    {
        this.parent = parent;
    }
}
