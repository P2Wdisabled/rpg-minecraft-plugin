package fr.P2Wdisabled.rpgplugin.mobs;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public abstract class CustomMob {

    protected String name;
    protected int xp;
    protected double health;
    protected double defense;
    protected double agility;
    protected double baseDamage;

    public CustomMob(String name, int xp, double health, double defense, double agility, double baseDamage) {
        this.name = name;
        this.xp = xp;
        this.health = health;
        this.defense = defense;
        this.agility = agility;
        this.baseDamage = baseDamage;
    }

    // Méthode pour faire spawn le mob
    public abstract LivingEntity spawn(Location location);

    // Getters pour les propriétés
    public String getName() {
        return name;
    }

    public int getXp() {
        return xp;
    }

    public double getHealth() {
        return health;
    }

    public double getDefense() {
        return defense;
    }

    public double getAgility() {
        return agility;
    }

    public double getBaseDamage() {
        return baseDamage;
    }
}
