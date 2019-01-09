package com.lonewolf.PS;

import com.lonewolf.PS.Engine.Component.Gui.Addon.GUIButton;
import com.lonewolf.PS.Engine.Component.Gui.Addon.GUIText;
import com.lonewolf.PS.Engine.Component.Gui.GuiFactory;
import com.lonewolf.PS.Engine.Core.Game;
import com.lonewolf.PS.Engine.Core.GameObject;
import com.lonewolf.PS.Engine.MainGame;

public class TestGame extends Game
{
    @Override
    public void init()
    {
        GuiFactory factory = new GuiFactory( 0);
        factory.addItem(new GUIText("Pause Screen",400,300, false));
        factory.addItem(new GUIButton(400,300,400,300,"Test")
        {
            @Override
            public void run() {
                engine.setGame(new MainGame());
            }
        });

        addObject(new GameObject().addComponet(factory));

        factory.run();
    }
}