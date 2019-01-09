package com.lonewolf.PS.Engine.Component.Gui;

import com.lonewolf.PS.Engine.Component.GameComponent;
import com.lonewolf.PS.Engine.Core.CoreEngine;
import com.lonewolf.PS.Engine.Core.Input;
import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Shader;
import com.lonewolf.PS.Engine.Render.Window;

import java.util.ArrayList;

public class GuiFactory extends GameComponent
{
    private ArrayList<GUIAddon> items;
    private int keyBind;
    private boolean render;
    private int spamLimit;

    public GuiFactory(int keyBind)
    {
        this.keyBind = keyBind;
        items = new ArrayList<>();
        render = false;
        spamLimit = 0;
    }

    public void addItem(GUIAddon item)
    {
        items.add(item);
    }

    public void run()
    {
        render = true;
        Input.SetCursor(true);
        spamLimit += 10;
    }

    @Override
    public void Input()
    {
        if (Input.GetKeyDown(keyBind) && !render && Input.isCursorGrabbed() && spamLimit == 0)
        {
            render = true;
            Input.SetCursor(true);
            spamLimit += 10;
        }
        else if (Input.GetKeyDown(Input.KEY_ESCAPE) && render && !Input.isCursorGrabbed() && spamLimit == 0)
        {
            render = false;
            Input.SetMousePosition(Window.getDisplaySize().Div(2));
            Input.SetCursor(false);
            spamLimit+=10;
        }

        if (spamLimit > 0)
            spamLimit--;

        if (render)
            for(GUIAddon addon : items)
                addon.Input();
    }

    @Override
    public void Update()
    {
        for(GUIAddon addon : items)
            addon.Update();
    }

    @Override
    public void Render2D(Shader shader, RenderingEngine renderingEngine)
    {
        if (render)
            for(GUIAddon addon : items)
                addon.Render2D(shader, renderingEngine);
    }

    @Override
    public void setEngine(CoreEngine engine)
    {
        super.setEngine(engine);
        for(GUIAddon addon : items)
            addon.setEngine(engine);
    }
}
