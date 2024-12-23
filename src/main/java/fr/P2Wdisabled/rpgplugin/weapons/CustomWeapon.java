package fr.P2Wdisabled.rpgplugin.weapons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.P2Wdisabled.rpgplugin.skills.CustomSkill;

public abstract class CustomWeapon {

    protected String name;
    protected Material material;
    protected String type; // Rareté actuelle
    protected int weaponClass;
    protected String description;
    protected int damageBonus;
    protected int defenseBonus;
    protected int manaBonus;
    protected int durability; // -1 pour incassable
    protected Map<String, CustomSkill> skills = new HashMap<>();
    protected int maxSkills = 3; // Limite par défaut du nombre de compétences

    public CustomWeapon(String name, Material material, String type, int weaponClass, String description,
                        int damageBonus, int defenseBonus, int manaBonus, int durability) {
        this.name = name;
        this.material = material;
        this.type = type;
        this.weaponClass = weaponClass;
        this.description = description;
        this.damageBonus = damageBonus;
        this.defenseBonus = defenseBonus;
        this.manaBonus = manaBonus;
        this.durability = durability;
    }

    // Méthode pour créer l'ItemStack de l'arme
    public abstract ItemStack createItem();

    // Nouvelle méthode abstraite pour obtenir les raretés possibles
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

    public int getWeaponClass() {
        return weaponClass;
    }

    public String getDescription() {
        return description;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public int getManaBonus() {
        return manaBonus;
    }

    public int getDurability() {
        return durability;
    }
    public boolean addSkill(CustomSkill skill) {
        if (skills.size() >= maxSkills) {
            return false; // Impossible d'ajouter plus de compétences
        }
        skills.put(skill.getName().toLowerCase(), skill);
        return true;
    }

    // Méthode pour retirer une compétence de l'arme
    public boolean removeSkill(String skillName) {
        return skills.remove(skillName.toLowerCase()) != null;
    }

    // Méthode pour récupérer les compétences de l'arme
    public Map<String, CustomSkill> getSkills() {
        return skills;
    }
}
