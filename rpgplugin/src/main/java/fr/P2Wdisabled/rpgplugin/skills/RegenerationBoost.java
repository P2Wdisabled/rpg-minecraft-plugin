package fr.P2Wdisabled.rpgplugin.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import fr.P2Wdisabled.rpgplugin.RPGPlugin;
import fr.P2Wdisabled.rpgplugin.body.BodyPartType;
import fr.P2Wdisabled.rpgplugin.body.BodyParts;

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

    @Override
    public void activateSkill(Player player) {
        // Cette compétence est passive, l'activation se fait via l'événement
    }

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            // Vérifier si le joueur a cette compétence appliquée à l'armure (nous considérons le corps pour simplifier)
            BodyParts bodyParts = RPGPlugin.getInstance().getBodyManager().getBodyParts(player.getUniqueId());
            CustomSkill skill = bodyParts.getSkill(BodyPartType.BODY); // Ou l'endroit où l'armure est équipée
            if (skill != null && skill.getName().equalsIgnoreCase(this.name)) {
                double extraHealth = event.getAmount() * 0.10; // 10% d'augmentation
                event.setAmount(event.getAmount() + extraHealth);
            }
        }
    }
}
