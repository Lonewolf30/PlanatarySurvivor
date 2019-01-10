package com.lonewolf.PS.Engine.Render.resourceManagement;

import static org.lwjgl.opengl.GL15.*;

public class MeshResource
{
	private int vbo;
	private int ibo;

	private int size;
	private int refCount;

	public MeshResource(int size)
	{
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
		this.refCount = 1;
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
	}

	public void AddReference()
	{
		refCount++;
	}

	public boolean RemoveReference()
	{
		refCount--;
		return refCount == 0;
	}

	public int GetVbo()  { return vbo; }
	public int GetIbo()  { return ibo; }
	public int GetSize() { return size; }
}
