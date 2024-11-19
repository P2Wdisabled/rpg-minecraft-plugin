package fr.P2Wdisabled.rpgplugin.skills;

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
}
