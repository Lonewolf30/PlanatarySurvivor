package com.lonewolf.PS.Engine.Render.MeshLoading;

import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Core.Vector3f;

import java.util.ArrayList;

public class IndexedModel
{
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<Vector3f> tangents;
	private ArrayList<Integer> indices;

	public IndexedModel()
	{
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		tangents = new ArrayList<Vector3f>();
		indices = new ArrayList<Integer>();
	}

	public void CalcNormals()
	{
		for(int i = 0; i < indices.size(); i += 3)
		{
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);

			Vector3f v1 = positions.get(i1).Sub(positions.get(i0));
			Vector3f v2 = positions.get(i2).Sub(positions.get(i0));

			Vector3f normal = v1.Cross(v2).Normalized();

			normals.get(i0).Set(normals.get(i0).Add(normal));
			normals.get(i1).Set(normals.get(i1).Add(normal));
			normals.get(i2).Set(normals.get(i2).Add(normal));
		}

		for(int i = 0; i < normals.size(); i++)
			normals.get(i).Set(normals.get(i).Normalized());
	}

	public void CalcTangents()
	{
		for(int i = 0; i < indices.size(); i += 3)
		{
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);

			Vector3f edge1 = positions.get(i1).Sub(positions.get(i0));
			Vector3f edge2 = positions.get(i2).Sub(positions.get(i0));

			float deltaU1 = texCoords.get(i1).GetX() - texCoords.get(i0).GetX();
			float deltaV1 = texCoords.get(i1).GetY() - texCoords.get(i0).GetY();
			float deltaU2 = texCoords.get(i2).GetX() - texCoords.get(i0).GetX();
			float deltaV2 = texCoords.get(i2).GetY() - texCoords.get(i0).GetY();

			float dividend = (deltaU1*deltaV2 - deltaU2*deltaV1);
			float f = dividend == 0 ? 0.0f : 1.0f/dividend;

			Vector3f tangent = new Vector3f(0,0,0);
			tangent.SetX(f * (deltaV2 * edge1.GetX() - deltaV1 * edge2.GetX()));
			tangent.SetY(f * (deltaV2 * edge1.GetY() - deltaV1 * edge2.GetY()));
			tangent.SetZ(f * (deltaV2 * edge1.GetZ() - deltaV1 * edge2.GetZ()));

			tangents.get(i0).Set(tangents.get(i0).Add(tangent));
			tangents.get(i1).Set(tangents.get(i1).Add(tangent));
			tangents.get(i2).Set(tangents.get(i2).Add(tangent));
		}

		for(int i = 0; i < tangents.size(); i++)
			tangents.get(i).Set(tangents.get(i).Normalized());
	}

	public ArrayList<Vector3f> getPositions() { return positions; }
	public ArrayList<Vector2f> getTexCoords() { return texCoords; }
	public ArrayList<Vector3f> getNormals() { return normals; }
	public ArrayList<Vector3f> getTangents() { return tangents; }
	public ArrayList<Integer>  getIndices() { return indices; }
}
