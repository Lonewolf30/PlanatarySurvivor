package com.lonewolf.PS.Engine.Core;

public class Transform
{
	private Transform parent;
	private Matrix4f parentMatrix;

	private Vector3f pos;
	private Quaternion rot;
	private Vector3f scale;

	public Transform()
	{
		pos = new Vector3f(0,0,0);
		rot = new Quaternion(0,0,0,1);
		scale = new Vector3f(1,1,1);

		parentMatrix = new Matrix4f().InitIdentity();

	}

	public void translate(Vector3f mve)
	{
		pos = pos.Add(mve);
	}

	public void Rotate(Vector3f axis, float angle)
	{
		rot = new Quaternion(axis, angle).Mul(rot).Normalized();
	}

	public void LookAt(Vector3f point, Vector3f up)
	{
		rot = GetLookAtRotation(point, up);
	}

	public Quaternion GetLookAtRotation(Vector3f point, Vector3f up)
	{
		return new Quaternion(new Matrix4f().InitRotation(point.Sub(pos).Normalized(), up));
	}

	public boolean HasChanged()
	{
		return parent != null && parent.HasChanged();
	}

	public Matrix4f GetTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().InitTranslation(pos.GetX(), pos.GetY(), pos.GetZ());
		Matrix4f rotationMatrix = rot.ToRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().InitScale(scale.GetX(), scale.GetY(), scale.GetZ());

		return GetParentMatrix().Mul(translationMatrix.Mul(rotationMatrix.Mul(scaleMatrix)));
	}

	private Matrix4f GetParentMatrix()
	{
		if(parent != null && parent.HasChanged())
			parentMatrix = parent.GetTransformation();

		return parentMatrix;
	}

	public void SetParent(Transform parent)
	{
		this.parent = parent;
	}

	public Vector3f GetTransformedPos()
	{
		return GetParentMatrix().Transform(pos);
	}

	public Quaternion GetTransformedRot()
	{
		Quaternion parentRotation = new Quaternion(0,0,0,1);

		if(parent != null)
			parentRotation = parent.GetTransformedRot();

		return parentRotation.Mul(rot);
	}

	public Vector3f GetPos()
	{
		return pos;
	}
	
	public void SetPos(Vector3f pos)
	{
		this.pos = pos;
	}

	public Quaternion GetRot()
	{
		return rot;
	}

	public void SetRot(Quaternion rotation)
	{
		this.rot = rotation;
	}

	public Vector3f GetScale()
	{
		return scale;
	}

	public void SetScale(Vector3f scale)
	{
		this.scale = scale;
	}
}
