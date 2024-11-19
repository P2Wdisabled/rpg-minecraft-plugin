package fr.P2Wdisabled.rpgplugin.skills;

public class GodProtection extends CustomSkill {

    public GodProtection(int skillClass, String type) {
        super("God Protection", skillClass, type,
                "You become invincible for 3 seconds in the event of imminent death.",
                180, // Cooldown de 3 minutes
                3,   // Durée de l'effet en secondes
                new String[]{"body"}); // Applicable au corps
    }

    @Override
    public String[] getPossibleRarities() {
        return new String[]{"Mythic Skill"};
    }
}
