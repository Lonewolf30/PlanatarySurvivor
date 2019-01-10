package com.lonewolf.PS.Entity.Hostile.SemiHostile;

import com.lonewolf.PS.Entity.BaseEntity;
import com.lonewolf.PS.Entity.Hostile.BaseHostile;

public class BaseSemiHostile extends BaseHostile
{
    public void damage(int damageAmount, BaseEntity attacker)
    {
        this.target = attacker;
        this.damage(damageAmount);
    }
}
