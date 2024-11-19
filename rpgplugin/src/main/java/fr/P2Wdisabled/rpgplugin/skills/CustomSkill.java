package fr.P2Wdisabled.rpgplugin.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class CustomSkill implements Listener {

    protected String name;
    protected int skillClass;
    protected String type; // Type de compétence (mythic skill, rare skill, etc.)
    protected String description;
    protected int cooldown; // Temps de recharge en secondes
    protected int duration; // Durée de l'effet en secondes (si applicable)
    protected String[] applicableTo; // Parties du corps, armes ou armures applicables

    public CustomSkill(String name, int skillClass, String type, String description,
                       int cooldown, int duration, String[] applicableTo) {
        this.name = name;
        this.skillClass = skillClass;
        this.type = type;
        this.description = description;
        this.cooldown = cooldown;
        this.duration = duration;
        this.applicableTo = applicableTo;
    }

    // Méthode abstraite pour obtenir les raretés possibles (si nécessaire)
    public abstract String[] getPossibleRarities();

    // Méthode abstraite pour activer la compétence (si nécessaire)
    public abstract void activateSkill(Player player);

    // Getters pour les propriétés
    public String getName() {
        return name;
    }

    public int getSkillClass() {
        return skillClass;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getDuration() {
        return duration;
    }

    public String[] getApplicableTo() {
        return applicableTo;
    }
}
