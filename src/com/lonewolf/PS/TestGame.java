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

        factory.addItem(new GUIButton(300,350,400,200,"Run Game")
        {
            @Override
            public void run() {
                engine.setGame(new MainGame());
            }
        });

        factory.addItem(new GUIButton(300,150,400,200,"Exit") {
            @Override
            public void run() {
                engine.stop();
            }
        });

        addObject(new GameObject().addComponet(factory));

        factory.run();
    }
}