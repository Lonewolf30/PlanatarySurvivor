package com.lonewolf.PS.Engine;

import com.lonewolf.PS.Engine.Core.CoreEngine;
import com.lonewolf.PS.Reference;
import com.lonewolf.PS.TestGame;

public class Initialisation
{
    public static void preInit()
    {
        Reference.configs.loadConfigration();

        Init();
    }

    public static void Init()
    {


        postInit();
    }

    public static void postInit()
    {


        CoreEngine engine = new CoreEngine(new TestGame());
        engine.start();
    }
}
