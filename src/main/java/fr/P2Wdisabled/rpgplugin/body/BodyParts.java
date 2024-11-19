package fr.P2Wdisabled.rpgplugin.body;

import java.util.HashMap;
import java.util.Map;

import fr.P2Wdisabled.rpgplugin.skills.CustomSkill;

public class BodyParts {

    private Map<BodyPartType, Map<String, CustomSkill>> skills = new HashMap<>();
    private int maxSkillsPerPart = 3; // Limite par défaut du nombre de compétences par partie du corps

    public boolean addSkill(BodyPartType part, CustomSkill skill) {
        skills.computeIfAbsent(part, k -> new HashMap<>());
        Map<String, CustomSkill> partSkills = skills.get(part);

        if (partSkills.size() >= maxSkillsPerPart) {
            return false; // Impossible d'ajouter plus de compétences à cette partie
        }
        partSkills.put(skill.getName().toLowerCase(), skill);
        return true;
    }

    public boolean removeSkill(BodyPartType part, String skillName) {
        Map<String, CustomSkill> partSkills = skills.get(part);
        if (partSkills != null) {
            return partSkills.remove(skillName.toLowerCase()) != null;
        }
        return false;
    }

    public Map<String, CustomSkill> getSkills(BodyPartType part) {
        return skills.getOrDefault(part, new HashMap<>());
    }

    public Map<BodyPartType, Map<String, CustomSkill>> getAllSkills() {
        return skills;
    }
}
