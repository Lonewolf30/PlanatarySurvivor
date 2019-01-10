package com.lonewolf.PS.Engine.Render;


import com.lonewolf.PS.Engine.Render.resourceManagement.MappedValues;

import java.util.HashMap;

public class Material extends MappedValues
{
	private HashMap<String, Texture> m_textureHashMap;

	public Material(Texture diffuse, float shineDamp, float reflect)
	{
		super();
		m_textureHashMap = new HashMap<>();
		AddTexture("diffuse", diffuse);
		AddFloat("shineDamp", shineDamp);
		AddFloat("reflect", reflect);
	}

	public void AddTexture(String name, Texture texture) { m_textureHashMap.put(name, texture); }

	public Texture GetTexture(String name)
	{
		Texture result = m_textureHashMap.get(name);
		if(result != null)
			return result;

		return new Texture("test.png");
	}
}
