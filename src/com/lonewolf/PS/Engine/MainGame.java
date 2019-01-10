package com.lonewolf.PS.Engine;

import com.lonewolf.PS.Engine.Component.*;
import com.lonewolf.PS.Engine.Core.*;
import com.lonewolf.PS.Engine.Render.*;
import com.lonewolf.PS.Entity.Player.Player;

public class MainGame extends Game
{
    @Override
    public void init()
    {
        Player player = new Player();
        addObject(player);

        Material material = new Material(new Texture("white.jpg"), 1f, 1f);

        GameObject object1 = new GameObject().addComponet(new MeshRender(new Mesh("dragon.obj"), material));

        object1.getTransform().SetPos(new Vector3f(10,10,0));

        addObject(object1);

        GameObject lightObject = new GameObject()
                .addComponet(new Light(new Vector3f(1,1,1),1f));
        lightObject.getTransform().SetPos(new Vector3f(0,5,0));
        addObject(lightObject);
    }

    @Override
    public void setRenderingEngine(RenderingEngine renderingEngine) {
        super.setRenderingEngine(renderingEngine);
        renderingEngine.render3d(true);
    }
}
