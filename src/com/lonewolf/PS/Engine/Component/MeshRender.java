package com.lonewolf.PS.Engine.Component;

import com.lonewolf.PS.Engine.Render.*;

public class MeshRender extends GameComponent
{
    private Mesh mesh;
    private Material material;

    public MeshRender(Mesh mesh, Material material)
    {
        this.mesh = mesh;
        this.material = material;
    }

    public Mesh getMesh()
    {
        return mesh;
    }

    @Override
    public void Render3D(Shader shader, RenderingEngine renderingEngine)
    {
        shader.UpdateUniforms(GetTransform(), material,  renderingEngine);
        shader.bind();
        mesh.draw();
    }
}
