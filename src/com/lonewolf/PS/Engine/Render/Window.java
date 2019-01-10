package com.lonewolf.PS.Engine.Render;

import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Reference;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window
{
    public Window (String displayName, Vector2f size)
    {
        try
        {
            System.out.println("Window Name: " + displayName);
            System.out.println("Window Size: " + size.toString());
            Display.setDisplayMode(new DisplayMode((int)size.GetX(), (int)size.GetY()));
            Display.setTitle(displayName);
            Display.create();
            Keyboard.create();
            Mouse.create();

            if (Boolean.parseBoolean(Reference.configs.getValue("fullscreen")))
                setFullsreen();

        }
        catch (Exception e)
        {
            System.err.println("Display Creation Failed");
        }
    }

    private void setFullsreen() throws Exception
    {
        Display.destroy();
        Display.setDisplayMode(Display.getDesktopDisplayMode());
        Display.setFullscreen(true);
        Display.create();
    }


    public boolean isDisplayCloseRequested()
    {
        return Display.isCloseRequested();
    }

    public void update()
    {
        Display.update();
    }

    public static Vector2f getDisplaySize()
    {
        return new Vector2f(Display.getWidth(), Display.getHeight());
    }

    public static float aspectRatio()
    {
        return (float)Display.getWidth()/Display.getHeight();
    }

    public void destroy()
    {
        Display.destroy();
    }
}
