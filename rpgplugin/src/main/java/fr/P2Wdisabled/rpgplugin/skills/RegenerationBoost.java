package fr.P2Wdisabled.rpgplugin.skills;

public class RegenerationBoost extends CustomSkill {

    public RegenerationBoost(int skillClass, String type) {
        super("Regeneration Boost", skillClass, type,
                "You have a 10% increase in life regeneration.",
                0,  // Pas de cooldown
                0,  // Pas de durée spécifique (effet passif)
                new String[]{"armor"}); // Applicable à l'armure
    }

    @Override
    public String[] getPossibleRarities() {
        return new String[]{"Common Skill"};
    }
}
