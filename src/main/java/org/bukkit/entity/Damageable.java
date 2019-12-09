package org.bukkit.entity;

public interface Damageable extends Entity {
    void damage(double var1);

    /**
     * @deprecated
     */
    @Deprecated
    void damage(int var1);

    void damage(double var1, Entity var3);

    /**
     * @deprecated
     */
    @Deprecated
    void damage(int var1, Entity var2);

    double getHealth();

    void setHealth(double var1);

    void setHealth(int var1);

    double getMaxHealth();

    void setMaxHealth(double var1);

    void setMaxHealth(int var1);

    void resetMaxHealth();
}
