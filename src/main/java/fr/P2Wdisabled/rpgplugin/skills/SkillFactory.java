package fr.P2Wdisabled.rpgplugin.skills;

import java.util.Random;

public class SkillFactory {

    // Méthode pour créer une compétence spécifique
    public CustomSkill createSkill(String skillName, int skillClass, String type) {
        switch (skillName.toLowerCase()) {
            case "god protection":
                return new GodProtection(skillClass, type);
            case "regeneration boost":
                return new RegenerationBoost(skillClass, type);
            // Ajouter les autres compétences ici
            default:
                return null;
        }
    }
    
    // Méthode pour générer une arme aléatoire lors d'un drop, basée sur une arme spécifique
    public CustomSkill generateRandomSkill(CustomSkill baseSkill) {
        String[] rarities = baseSkill.getPossibleRarities();
        Random random = new Random();
        int weaponClass = random.nextInt(10) + 1; // Classe entre 1 et 10
        String rarity = rarities[random.nextInt(rarities.length)];

        return createSkill(baseSkill.getName(), weaponClass, rarity);
    }
}
