package com.lonewolf.PS.Entity;

import com.lonewolf.PS.Engine.Core.GameObject;

public abstract class BaseEntity extends GameObject
{
    public float health;
    public float maxHealth;
    public boolean isInvincible;

    public void damage(int damageAmount)
    {
        this.health -= damageAmount;
    }

    public void heal(int healAmount)
    {
        this.health += (healAmount+health < maxHealth ? healAmount : maxHealth - health);
    }

    public void setInvincibility(boolean isInvincible)
    {
        this.isInvincible = isInvincible;
    }
}
