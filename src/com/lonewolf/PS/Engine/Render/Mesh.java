package com.lonewolf.PS.Engine.Render;

import com.lonewolf.PS.Engine.Core.Util;
import com.lonewolf.PS.Engine.Core.Vector3f;
import com.lonewolf.PS.Engine.Render.MeshLoading.IndexedModel;
import com.lonewolf.PS.Engine.Render.MeshLoading.OBJModel;
import com.lonewolf.PS.Engine.Render.resourceManagement.MeshResource;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Mesh
{
	private static HashMap<String, MeshResource> s_loadedModels = new HashMap<>();
	private MeshResource resource;
	private String       fileName;
	
	public Mesh(String fileName)
	{
		this.fileName = fileName;
		MeshResource oldResource = s_loadedModels.get(fileName);
		if(oldResource != null)
		{
			resource = oldResource;
			resource.AddReference();
		}
		else
		{
			LoadMesh(fileName);

			s_loadedModels.put(fileName, resource);
		}
	}
	
	public Mesh(Vertex[] vertices, int[] indices)
	{
		this(vertices, indices, false);
	}
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		fileName = "";
		AddVertices(vertices, indices, calcNormals);
	}

	@Override
	protected void finalize()
	{
		if(resource.RemoveReference() && !fileName.isEmpty())
		{
			s_loadedModels.remove(fileName);
		}
	}
	
	private void AddVertices(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		if (calcNormals)
		{
			CalcNormals(vertices, indices);
		}

		resource = new MeshResource(indices.length);
		glBindBuffer(GL_ARRAY_BUFFER, resource.GetVbo());
		glBufferData(GL_ARRAY_BUFFER, Util.CreateFlippedBuffer(vertices), GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.GetIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.CreateFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);

		glBindBuffer(GL_ARRAY_BUFFER, resource.GetVbo());

		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.GetIbo());

		glDrawElements(GL_TRIANGLES, resource.GetSize(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
	}
	
	private void CalcNormals(Vertex[] vertices, int[] indices)
	{
		for(int i = 0; i < indices.length; i += 3)
		{
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			Vector3f v1 = vertices[i1].GetPos().Sub(vertices[i0].GetPos());
			Vector3f v2 = vertices[i2].GetPos().Sub(vertices[i0].GetPos());
			
			Vector3f normal = v1.Cross(v2).Normalized();
			
			vertices[i0].SetNormal(vertices[i0].GetNormal().Add(normal));
			vertices[i1].SetNormal(vertices[i1].GetNormal().Add(normal));
			vertices[i2].SetNormal(vertices[i2].GetNormal().Add(normal));
		}

		for(int i = 0; i < vertices.length; i++)
			vertices[i].SetNormal(vertices[i].GetNormal().Normalized());
	}

	private Mesh LoadMesh(String fileName)
	{
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];

		if(!ext.equals("obj"))
		{
			System.err.println("Error: '" + ext + "' file format not supported for mesh data.");
			new Exception().printStackTrace();
			System.exit(1);
		}

		OBJModel test = new OBJModel("./res/models/" + fileName);
		IndexedModel model = test.ToIndexedModel();

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for(int i = 0; i < model.getPositions().size(); i++)
		{
			vertices.add(new Vertex(model.getPositions().get(i),
					model.getTexCoords().get(i),
					model.getNormals().get(i),
					model.getTangents().get(i)));
		}

		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);

		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);

		AddVertices(vertexData, Util.ToIntArray(indexData), false);

		return this;
	}

	public Vertex[] getVertices()
	{
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];

		OBJModel test = new OBJModel("./res/models/" + fileName);
		IndexedModel model = test.ToIndexedModel();

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for(int i = 0; i < model.getPositions().size(); i++)
		{
			vertices.add(new Vertex(model.getPositions().get(i),
					model.getTexCoords().get(i),
					model.getNormals().get(i),
					model.getTangents().get(i)));
		}

		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);

		return vertexData;
	}
}
