package fr.P2Wdisabled.rpgplugin.armors;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class CustomArmor {

    protected String name;
    protected Material material;
    protected String type; // Rareté actuelle
    protected int armorClass;
    protected String description;
    protected int defenseBonus;
    protected int healthBonus;
    protected int agilityBonus;
    protected int strengthBonus;
    protected int durability; // -1 pour incassable

    public CustomArmor(String name, Material material, String type, int armorClass, String description,
                       int defenseBonus, int healthBonus, int agilityBonus, int strengthBonus, int durability) {
        this.name = name;
        this.material = material;
        this.type = type;
        this.armorClass = armorClass;
        this.description = description;
        this.defenseBonus = defenseBonus;
        this.healthBonus = healthBonus;
        this.agilityBonus = agilityBonus;
        this.strengthBonus = strengthBonus;
        this.durability = durability;
    }

    // Méthode pour créer l'ItemStack de l'armure
    public abstract ItemStack createItem();

    // Méthode abstraite pour obtenir les raretés possibles
    public abstract String[] getPossibleRarities();

    // Getters pour les propriétés
    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public String getType() {
        return type;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public String getDescription() {
        return description;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public int getHealthBonus() {
        return healthBonus;
    }

    public int getAgilityBonus() {
        return agilityBonus;
    }

    public int getStrengthBonus() {
        return strengthBonus;
    }

    public int getDurability() {
        return durability;
    }
}
