package com.lonewolf.PS.Engine.Render.MeshLoading;

import com.lonewolf.PS.Engine.Core.Util;
import com.lonewolf.PS.Engine.Core.Vector2f;
import com.lonewolf.PS.Engine.Core.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class OBJModel
{
	private ArrayList<Vector3f> m_positions;
	private ArrayList<Vector2f> m_texCoords;
	private ArrayList<Vector3f> m_normals;
	private ArrayList<OBJIndex> m_indices;
	private boolean             m_hasTexCoords;
	private boolean             m_hasNormals;

	public OBJModel(String fileName)
	{
		m_positions = new ArrayList<Vector3f>();
		m_texCoords = new ArrayList<Vector2f>();
		m_normals = new ArrayList<Vector3f>();
		m_indices = new ArrayList<OBJIndex>();
		m_hasTexCoords = false;
		m_hasNormals = false;

		BufferedReader meshReader = null;

		try
		{
			meshReader = new BufferedReader(new FileReader(fileName));
			String line;

			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Util.RemoveEmptyStrings(tokens);

				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
				{
					m_positions.add(new Vector3f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("vt"))
				{
					m_texCoords.add(new Vector2f(Float.valueOf(tokens[1]),
							1.0f - Float.valueOf(tokens[2])));
				}
				else if(tokens[0].equals("vn"))
				{
					m_normals.add(new Vector3f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("f"))
				{
					for(int i = 0; i < tokens.length - 3; i++)
					{
						m_indices.add(ParseOBJIndex(tokens[1]));
						m_indices.add(ParseOBJIndex(tokens[2 + i]));
						m_indices.add(ParseOBJIndex(tokens[3 + i]));
					}
				}
			}

			meshReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IndexedModel ToIndexedModel()
	{
		IndexedModel result = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
		HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

		for(int i = 0; i < m_indices.size(); i++)
		{
			OBJIndex currentIndex = m_indices.get(i);

			Vector3f currentPosition = m_positions.get(currentIndex.GetVertexIndex());
			Vector2f currentTexCoord;
			Vector3f currentNormal;

			if(m_hasTexCoords)
				currentTexCoord = m_texCoords.get(currentIndex.GetTexCoordIndex());
			else
				currentTexCoord = new Vector2f(0,0);

			if(m_hasNormals)
				currentNormal = m_normals.get(currentIndex.GetNormalIndex());
			else
				currentNormal = new Vector3f(0,0,0);

			Integer modelVertexIndex = resultIndexMap.get(currentIndex);

			if(modelVertexIndex == null)
			{
				modelVertexIndex = result.getPositions().size();
				resultIndexMap.put(currentIndex, modelVertexIndex);

				result.getPositions().add(currentPosition);
				result.getTexCoords().add(currentTexCoord);
				if(m_hasNormals)
					result.getNormals().add(currentNormal);
			}

			Integer normalModelIndex = normalIndexMap.get(currentIndex.GetVertexIndex());

			if(normalModelIndex == null)
			{
				normalModelIndex = normalModel.getPositions().size();
				normalIndexMap.put(currentIndex.GetVertexIndex(), normalModelIndex);

				normalModel.getPositions().add(currentPosition);
				normalModel.getTexCoords().add(currentTexCoord);
				normalModel.getNormals().add(currentNormal);
				normalModel.getTangents().add(new Vector3f(0,0,0));
			}

			result.getIndices().add(modelVertexIndex);
			normalModel.getIndices().add(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}

		if(!m_hasNormals)
		{
			normalModel.CalcNormals();

			for(int i = 0; i < result.getPositions().size(); i++)
				result.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
		}

		normalModel.CalcTangents();

		for(int i = 0; i < result.getPositions().size(); i++)
			result.getTangents().add(normalModel.getTangents().get(indexMap.get(i)));

		return result;
	}

	private OBJIndex ParseOBJIndex(String token)
	{
		String[] values = token.split("/");

		OBJIndex result = new OBJIndex();
		result.SetVertexIndex(Integer.parseInt(values[0]) - 1);

		if(values.length > 1)
		{
			if(!values[1].isEmpty())
			{
				m_hasTexCoords = true;
				result.SetTexCoordIndex(Integer.parseInt(values[1]) - 1);
			}

			if(values.length > 2)
			{
				m_hasNormals = true;
				result.SetNormalIndex(Integer.parseInt(values[2]) - 1);
			}
		}

		return result;
	}
}
