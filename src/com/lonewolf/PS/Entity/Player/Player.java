package com.lonewolf.PS.Entity.Player;

import com.lonewolf.PS.Engine.Component.*;
import com.lonewolf.PS.Engine.Component.Gui.Addon.GUIButton;
import com.lonewolf.PS.Engine.Component.Gui.Addon.GUIText;
import com.lonewolf.PS.Engine.Component.Gui.GuiFactory;
import com.lonewolf.PS.Engine.Core.Input;
import com.lonewolf.PS.Engine.Core.Matrix4f;
import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Core.Vector3f;
import com.lonewolf.PS.Engine.Render.Window;
import com.lonewolf.PS.Entity.BaseEntity;
import com.lonewolf.PS.TestGame;

public class Player extends BaseEntity
{

    private GuiFactory guiFactory;

    public Player()
    {
        initPlayer();
    }

    public void setGuiFactory(GuiFactory factory)
    {
        guiFactory = factory;
    }

    private void initPlayer()
    {
        Input.SetMousePosition(Window.getDisplaySize().Div(2));
        Input.SetCursor(false);

        guiFactory = new GuiFactory(Input.KEY_ESCAPE);

        guiFactory.addItem(new GUIText("Pause Screen",400,300, false));

        guiFactory.addItem(new GUIButton(300,150,400,200,"Exit") {
            @Override
            public void run() {
                engine.setGame(new TestGame());
            }
        });

        addComponet(new FreeMove(12f));
        addComponet(new FreeLook(0.5f));
        addComponet(new Camera(new Matrix4f().InitPerspective((float)Math.toRadians(60), Window.aspectRatio(), 0.1f, 500)));
        addComponet(guiFactory);

        maxHealth = 20;
        health = 20;
    }
}
