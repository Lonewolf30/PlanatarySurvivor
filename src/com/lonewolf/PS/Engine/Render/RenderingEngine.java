package com.lonewolf.PS.Engine.Render;

import com.lonewolf.PS.Engine.Component.Camera;
import com.lonewolf.PS.Engine.Component.Light;
import com.lonewolf.PS.Engine.Core.GameObject;
import com.lonewolf.PS.Engine.Render.resourceManagement.MappedValues;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class RenderingEngine extends MappedValues
{
	private Shader Shader3D;
	private Shader Shader2D;

	private Camera camera;
	private Light ActiveLight;

	private ArrayList<Light> lights;

	private boolean render3d;

	public RenderingEngine()
	{
		glClearColor(0,0,0,0);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);

		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);

		glEnable(GL_ALPHA_TEST);

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		Shader3D = new Shader("main");
		Shader2D = new Shader("2d");
		lights = new ArrayList<>();
		render3d = false;
	}

	private void Render2D(GameObject object)
	{
		object.Render2D(Shader2D, this);
	}

	private void Render3D(GameObject object)
	{
		object.Render3D(Shader3D, this);
	}

	public void Render(ArrayList<GameObject> object)
	{
		if (camera == null && render3d)
			System.err.println("Camera Not Created");
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		if (render3d)
		lights.forEach(light -> {
			ActiveLight = light;
			object.forEach(this::Render3D);
		});

		object.forEach(this::Render2D);
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Camera getCamera() {
		return camera;
	}

    public void addLight(Light light) {
		lights.add(light);
    }

	public Light getActiveLight() {
		return ActiveLight;
	}

    public void render3d(boolean runGame)
	{
		render3d = runGame;
    }
}
