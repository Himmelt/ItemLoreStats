package org.bukkit.entity;

import org.bukkit.projectiles.ProjectileSource;

public interface Projectile extends Entity {
    ProjectileSource getShooter();

    void setShooter(ProjectileSource var1);

    boolean doesBounce();

    void setBounce(boolean var1);
}
