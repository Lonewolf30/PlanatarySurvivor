package com.lonewolf.PS.Engine.Core;

import com.lonewolf.PS.Engine.Render.RenderingEngine;
import com.lonewolf.PS.Engine.Render.Window;
import com.lonewolf.PS.Reference;
import com.lonewolf.PS.Engine.Saving;

public class CoreEngine
{
    private Window window;
    private boolean isRunning;
    private Game game;
    private RenderingEngine renderingEngine;

    public CoreEngine(Game game)
    {
        window = new Window(Reference.configs.getValue("WindowName"), new Vector2f(Reference.configs.getValue("windowWidth"), Reference.configs.getValue("windowHeight")));
        renderingEngine = new RenderingEngine();
        this.game = game;
    }

    public void start()
    {
        if (isRunning)
            return;

        run();
    }

    public void stop()
    {
        if (!isRunning)
            return;

        isRunning = false;
    }

    private void run()
    {
        isRunning = true;

        game.setEngine(this);
        game.setRenderingEngine(renderingEngine);
        game.init();

        int frames = 0;

        float frameTime = 1.0f/60f;
        float passedTime = 0;
        float processedTime = 0;

        float pastTime = (float)System.nanoTime() / Time.second;
        while (isRunning)
        {
            float currentTime = (float)System.nanoTime() / Time.second;
            float usedTime = currentTime - pastTime;
            pastTime = currentTime;

            processedTime += usedTime;
            passedTime += usedTime;

            if (processedTime > frameTime)
            {
                processedTime -= frameTime;

                if (window.isDisplayCloseRequested())
                    stop();

                Time.setDelta(usedTime);

                Render();
                frames++;

                if (passedTime > 1)
                {
                    System.out.println(frames);
                    passedTime = 0;
                    frames = 0;
                }
            }
        }

        cleanUp();
    }

    private void Render()
    {
        game.Update();
        game.Input();
        game.Render();

        window.update();
    }

    public void setGame(Game newGame)
    {
        this.game = newGame;

        game.setEngine(this);
        game.setRenderingEngine(renderingEngine);
        game.init();
    }

    public Game getGame()
    {
        return game;
    }

    public Window getWindow()
    {
        return window;
    }

    public RenderingEngine getRenderingEngine()
    {
        return renderingEngine;
    }

    private void cleanUp()
    {
        window.destroy();

        Saving.preSave();
    }
}
