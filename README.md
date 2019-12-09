```java
/*
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getDamager().getWorld().getName())) {
            if (!(event.getEntity() instanceof LivingEntity)) {
                return;
            }

            if (ItemLoreStats.plugin.util_WorldGuard != null && event.getEntity() instanceof Player && this.util_WorldGuard.playerInInvincibleRegion((Player) event.getEntity())) {
                event.setCancelled(true);
                return;
            }

            Entity getAttacker = null;
            boolean isTool = false;
            if (!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Projectile)) {
                if (!(event.getDamager() instanceof LivingEntity)) {
                    if (event.getCause().equals(DamageCause.LIGHTNING)) {
                        return;
                    }

                    event.setCancelled(true);
                    return;
                }

                getAttacker = event.getDamager();
            } else if (event.getDamager() instanceof Projectile) {
                Entity getDefender = null;
                Projectile eventDamage = (Projectile) event.getDamager();
                if (!(eventDamage.getShooter() instanceof Entity)) {
                    return;
                }

                getDefender = (Entity) eventDamage.getShooter();
                if (eventDamage.hasMetadata("ILS_Snowball") || eventDamage.hasMetadata("ILS_SmallFireball") || eventDamage.hasMetadata("ILS_Fireball") || eventDamage.hasMetadata("ILS_Arrow") || eventDamage.hasMetadata("ILS_Egg") || eventDamage.hasMetadata("ILS_TnT")) {
                    double DirectHealAmount;
                    double AOEHealAmount;
                    double AOEHealRange;
                    Effect projectileHitEffect;
                    Iterator<Entity> reflectVal;
                    Entity entity;
                    if (eventDamage.hasMetadata("Damage=")) {
                        DirectHealAmount = eventDamage.getMetadata("DDA=").get(0).asDouble();
                        AOEHealAmount = eventDamage.getMetadata("ADA=").get(0).asDouble();
                        AOEHealRange = eventDamage.getMetadata("ADR=").get(0).asDouble();
                        projectileHitEffect = this.spellCreator.getProjectileHitEffect(this.gearStats.getSpellName(this.util_EntityManager.returnItemStackInHand(getDefender)));
                        event.getEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), projectileHitEffect, 3);
                        if (event.getEntity() instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                            ((Player) event.getEntity()).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Damage", getDefender, event.getEntity(), String.valueOf((int) DirectHealAmount), String.valueOf((int) DirectHealAmount)));
                        }

                        if (event.getEntity() instanceof LivingEntity) {
                            ((LivingEntity) event.getEntity()).damage(DirectHealAmount);
                        }

                        if (AOEHealRange > 0.0D) {
                            reflectVal = event.getEntity().getNearbyEntities(AOEHealRange, 256.0D, AOEHealRange).iterator();

                            while (reflectVal.hasNext()) {
                                entity = reflectVal.next();
                                if (entity.equals(event.getDamager())) {
                                    event.getEntity().getLocation().getWorld().playEffect(entity.getLocation(), projectileHitEffect, 3);
                                    if (entity instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                                        ((Player) entity).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Damage", getDefender, entity, String.valueOf((int) AOEHealAmount), String.valueOf((int) AOEHealAmount)));
                                    }

                                    if (entity instanceof LivingEntity) {
                                        ((LivingEntity) entity).damage(AOEHealAmount);
                                    }
                                }
                            }
                        }
                    } else {
                        event.setCancelled(true);
                    }

                    if (eventDamage.hasMetadata("Heal=")) {
                        DirectHealAmount = eventDamage.getMetadata("DHA=").get(0).asDouble();
                        AOEHealAmount = eventDamage.getMetadata("AHA=").get(0).asDouble();
                        AOEHealRange = eventDamage.getMetadata("AHR=").get(0).asDouble();
                        projectileHitEffect = this.spellCreator.getProjectileHitEffect(this.gearStats.getSpellName(this.util_EntityManager.returnItemStackInHand(getDefender)));
                        event.getEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), projectileHitEffect, 3);
                        if (event.getEntity() instanceof Player) {
                            ((Player) event.getEntity()).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Heal", getDefender, event.getEntity(), String.valueOf((int) DirectHealAmount), String.valueOf((int) DirectHealAmount)));
                        }

                        this.util_EntityManager.setEntityCurrentHealth(event.getEntity(), Math.min(this.util_EntityManager.returnEntityCurrentHealth(event.getEntity()) + DirectHealAmount, this.util_EntityManager.returnEntityMaxHealth(event.getEntity())));

                        if (AOEHealRange > 0.0D) {
                            reflectVal = event.getEntity().getNearbyEntities(AOEHealRange, 256.0D, AOEHealRange).iterator();

                            while (reflectVal.hasNext()) {
                                entity = reflectVal.next();
                                event.getEntity().getLocation().getWorld().playEffect(entity.getLocation(), projectileHitEffect, 3);
                                if (entity instanceof Player) {
                                    ((Player) entity).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Heal", getDefender, entity, String.valueOf((int) AOEHealAmount), String.valueOf((int) AOEHealAmount)));
                                }

                                this.util_EntityManager.setEntityCurrentHealth(entity, Math.min(this.util_EntityManager.returnEntityCurrentHealth(entity) + AOEHealAmount, this.util_EntityManager.returnEntityMaxHealth(entity)));
                            }
                        }
                    }

                    return;
                }

                if (!(getDefender instanceof Player)) {
                    if (event.getEntity() instanceof Player && event.getEntity() instanceof LivingEntity) {
                        getAttacker = getDefender;
                    }
                } else {
                    getAttacker = getDefender;
                }
            } else if (event.getDamager() instanceof Player) {
                if (event.getEntity() instanceof Player && ItemLoreStats.plugin.getWorldGuard() != null && (ItemLoreStats.plugin.util_WorldGuard.playerInPVPRegion((Player) event.getEntity()) || ItemLoreStats.plugin.util_WorldGuard.playerInInvincibleRegion((Player) event.getEntity()))) {
                    return;
                }

                if (((Player) event.getDamager()).getItemInHand().getType().equals(Material.BOW)) {
                    if (event.getEntity() instanceof Player) {
                        event.setDamage(1.0D);
                        this.damageDealtMessage(event.getDamager(), event.getEntity(), event.getDamage());
                        return;
                    }

                    event.setDamage(1.0D);
                    this.damageDealtMessage(event.getDamager(), event.getEntity(), event.getDamage());
                    return;
                }

                getAttacker = event.getDamager();
            }

            if (!(event.getEntity() instanceof LivingEntity)) {
                event.setCancelled(true);
                return;
            }

            LivingEntity getDefender1;
            if (event.getDamager() instanceof LivingEntity) {
                getDefender1 = (LivingEntity) event.getEntity();
                if (getDefender1.getNoDamageTicks() > 10) {
                    event.setCancelled(true);
                }
            }

            getDefender1 = (LivingEntity) event.getEntity();
            if (getAttacker instanceof Player && getDefender1 instanceof Player) {
                if (ItemLoreStats.plugin.getWorldGuard() != null && (ItemLoreStats.plugin.util_WorldGuard.playerInPVPRegion((Player) getDefender1) || ItemLoreStats.plugin.util_WorldGuard.playerInInvincibleRegion((Player) getDefender1))) {
                    return;
                }

                if (((Player) getAttacker).getName().equals(((Player) getDefender1).getName())) {
                    event.setCancelled(true);
                    return;
                }
            }

            if (getAttacker instanceof Player) {
                if (!this.xpLevel.checkXPLevel((Player) getAttacker, ((Player) getAttacker).getItemInHand())) {
                    event.setDamage(1.0D);
                    this.damageDealtMessage(getAttacker, getDefender1, 1.0D);
                    return;
                }

                if (!this.soulbound.checkSoulbound((Player) getAttacker, ((Player) getAttacker).getItemInHand())) {
                    event.setDamage(1.0D);
                    this.damageDealtMessage(getAttacker, getDefender1, 1.0D);
                    return;
                }

                if (!this.classes.checkClasses((Player) getAttacker, ((Player) getAttacker).getItemInHand())) {
                    event.setDamage(1.0D);
                    this.damageDealtMessage(getAttacker, getDefender1, 1.0D);
                    return;
                }
            }

            if (ItemLoreStats.plugin.isTool(this.util_EntityManager.returnItemStackInHand(getAttacker).getType())) {
                isTool = true;
            }

            if (getDefender1.hasMetadata("NPC") && !this.util_Citizens.checkVulnerability(getDefender1)) {
                event.setCancelled(true);
                return;
            }

            if (getAttacker instanceof Player && !getAttacker.hasMetadata("NPC")) {
                this.checkWeaponSpeed((Player) getAttacker, isTool);
            }

            if (this.dodge.dodgeChanceOnHit(getDefender1, isTool)) {
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyDodgedAttack")) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyDodgeSuccess", getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                }

                if (getDefender1 instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.dodgeAttack")) {
                    ((Player) getDefender1).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DodgeSuccess", getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                }

                event.setCancelled(true);
                return;
            }

            if (this.block.blockChanceOnHit(getDefender1, isTool)) {
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyBlockedAttack")) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyBlockSuccess", getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                }

                if (getDefender1 instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.blockAttack")) {
                    ((Player) getDefender1).sendMessage(this.util_GetResponse.getResponse("DamageMessages.BlockSuccess", getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                    getDefender1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 1));
                }

                event.setCancelled(true);
                return;
            }

            double eventDamage1 = event.getDamage();
            double getDefenderArmour;
            double getAttackerDamage;
            if (ItemLoreStats.plugin.getConfig().getString("npcModifier." + event.getDamager().getWorld().getName()) != null && !(getAttacker instanceof Player)) {
                getDefenderArmour = this.util_EntityManager.returnEntityMaxHealth(getAttacker) / ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + event.getDamager().getWorld().getName() + ".healthMultiplier");
                getAttackerDamage = (double) Math.round(this.attackerDamage((LivingEntity) getAttacker, getDefender1, getDefender1.getType(), event.getDamage(), this.util_Material.materialToDamage(this.util_EntityManager.returnItemStackInHand(getAttacker).getType()), 0.0D, false, isTool) + getDefenderArmour * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + event.getDamager().getWorld().getName() + ".damageMultiplier"));
                eventDamage1 = getAttackerDamage;
            }

            getDefenderArmour = this.armour.armourChanceOnHit(getDefender1);
            if (this.util_EntityManager.returnItemStackInHand(getAttacker).getType() == Material.BOW) {
                getAttackerDamage = this.attackerDamage((LivingEntity) getAttacker, getDefender1, getDefender1.getType(), eventDamage1, eventDamage1, getDefenderArmour, true, isTool);
            } else {
                getAttackerDamage = this.attackerDamage((LivingEntity) getAttacker, getDefender1, getDefender1.getType(), eventDamage1, this.util_Material.materialToDamage(this.util_EntityManager.returnItemStackInHand(getAttacker).getType()), getDefenderArmour, true, isTool);
            }

            double reducedDamage = getAttackerDamage / 100.0D * getDefenderArmour;
            if (getDefender1 instanceof Player && !ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
                this.durability.durabilityCalcForArmour(getDefender1, 1, "damage");
                if (((Player) getDefender1).isBlocking()) {
                    this.durability.durabilityCalcForItemInHand((Player) getDefender1, 1, "damage");
                }
            }

            if (getAttacker instanceof Player && !ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
                this.durability.durabilityCalcForItemInHand((Player) getAttacker, 1, "damage");
            }

            double reflectVal1 = this.reflect.reflectChanceOnHit(getDefender1, isTool);
            if (reflectVal1 > 0.0D && (double) this.util_Random.random(100) <= reflectVal1) {
                double damage;
                if (getAttacker instanceof Player) {
                    damage = getAttackerDamage - reducedDamage;
                    ((Player) getAttacker).damage(damage);
                    if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyReflectedAttack")) {
                        if (getDefender1 instanceof Player) {
                            ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyReflectSuccess", getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyReflectSuccess", getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                        }
                    }

                    if (getDefender1 instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.reflectAttack")) {
                        ((Player) getDefender1).sendMessage(this.util_GetResponse.getResponse("DamageMessages.ReflectSuccess", getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                    }

                    event.setCancelled(true);
                    return;
                }

                if (getAttacker instanceof LivingEntity) {
                    damage = getAttackerDamage - reducedDamage;
                    ((LivingEntity) getAttacker).damage(damage);
                    if (getDefender1 instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.reflectAttack")) {
                        ((Player) getDefender1).sendMessage(this.util_GetResponse.getResponse("DamageMessages.ReflectSuccess", getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                    }

                    event.setCancelled(true);
                    return;
                }
            }

            this.lifeSteal.lifeStealChanceOnHit(getDefender1, (LivingEntity) getAttacker, getAttackerDamage - reducedDamage, isTool);
            this.fire.fireChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.ice.iceChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.poison.poisonChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.wither.witherChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.harming.harmingChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.blind.blindChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            getAttackerDamage -= reducedDamage;
            event.setDamage(getAttackerDamage);
            if (getDefender1 instanceof Player) {
                ItemLoreStats.plugin.updateBarAPI((Player) getDefender1);
            }
        }
    }
*/
```