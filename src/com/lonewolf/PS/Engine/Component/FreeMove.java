package com.lonewolf.PS.Engine.Component;

import com.lonewolf.PS.Engine.Core.*;

public class FreeMove extends GameComponent
{
    private float speed;
    private Vector3f predictedMove;
    private Vector3f predictedMoveLine;

    public FreeMove(float speed)
    {
        this.speed = speed;
        predictedMove = new Vector3f(0,0,0);
        predictedMoveLine = new Vector3f(0,0,0);
    }

    @Override
    public void Input()
    {
        float moveAmt = speed * Time.getDelta();

        if (Input.GetKey(Input.KEY_W))
            move(parent.getTransform().GetRot().GetForward(), moveAmt);
        if (Input.GetKey(Input.KEY_A))
            move(parent.getTransform().GetRot().GetLeft(), moveAmt);
        if (Input.GetKey(Input.KEY_S))
            move(parent.getTransform().GetRot().GetForward(), -moveAmt);
        if (Input.GetKey(Input.KEY_D))
            move(parent.getTransform().GetRot().GetRight(), moveAmt);
        if (Input.GetKey(Input.KEY_LSHIFT))
            flight(-moveAmt);
        if (Input.GetKey(Input.KEY_SPACE))
            flight( moveAmt);
    }

    @Override
    public void Update()
    {
        GetTransform().translate(predictedMoveLine);
        GetTransform().translate(predictedMove);
        predictedMoveLine.set0();
        predictedMove.set0();
    }

    private void flight(float amt)
    {
        predictedMove = new Vector3f(0,1,0).Mul(amt);
    }

    private void move(Vector3f dir, float amt)
    {
        predictedMoveLine = predictedMoveLine.Add(dir.Mul(amt*1.5f).setFlat());
    }
}
