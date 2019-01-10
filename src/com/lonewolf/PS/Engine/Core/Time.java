package com.lonewolf.PS.Engine.Core;

public class Time
{
    public static long second = 1000000000L;
    private static float Delta;

    public static void setDelta(float delta)
    {
        Time.Delta = delta;
    }

    public static float getDelta()
    {
        return  Delta;
    }
}
