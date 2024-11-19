package fr.P2Wdisabled.rpgplugin.skills;

import java.util.HashMap;
import java.util.Map;

import fr.P2Wdisabled.rpgplugin.weapons.CustomWeapon;
import fr.P2Wdisabled.rpgplugin.armors.CustomArmor;

public class SkillManager {

    private Map<String, CustomSkill> registeredSkills = new HashMap<>();

    public void registerSkill(CustomSkill skill) {
        registeredSkills.put(skill.getName().toLowerCase(), skill);
    }

    public CustomSkill getSkillByName(String name) {
        return registeredSkills.get(name.toLowerCase());
    }

    // Méthodes pour appliquer des compétences aux armes et armures
    public void applySkillToWeapon(CustomWeapon weapon, CustomSkill skill) {
        weapon.addSkill(skill);
    }

    public void applySkillToArmor(CustomArmor armor, CustomSkill skill) {
        armor.addSkill(skill);
    }
}
