package com.lonewolf.PS.Engine.Component;

import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Core.Vector3f;
import com.lonewolf.PS.Engine.Render.*;

public class GuiRender extends GameComponent
{
    private Mesh mesh;
    private Texture texture;

    public GuiRender()
    {
        Vertex[] vertices = {
                new Vertex(new Vector3f(-1f,1f,0), new Vector2f(0,0)),
                new Vertex(new Vector3f(-1f,-1f,0), new Vector2f(1,0)),
                new Vertex(new Vector3f(1f,-1f,0), new Vector2f(1,1)),
                new Vertex(new Vector3f(1f,1f,0), new Vector2f(0,1))
        };

        int[] indices = {0,1,3,3,1,2};

        texture = new Texture("test.jpg");

        mesh = new Mesh(vertices, indices, true);
    }

    @Override
    public void Render3D(Shader shader, RenderingEngine renderingEngine)
    {
//        shader.UpdateUniforms(texture, parent.getTransform(), renderingEngine);
        shader.bind();
        mesh.draw();
    }
}
