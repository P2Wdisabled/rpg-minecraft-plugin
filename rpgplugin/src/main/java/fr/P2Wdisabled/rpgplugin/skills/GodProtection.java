package fr.P2Wdisabled.rpgplugin.skills;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.P2Wdisabled.rpgplugin.RPGPlugin;
import fr.P2Wdisabled.rpgplugin.body.BodyPartType;
import fr.P2Wdisabled.rpgplugin.body.BodyParts;

public class GodProtection extends CustomSkill {

    private Map<UUID, Long> lastUsed = new HashMap<>();

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

    @Override
    public void activateSkill(Player player) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        long lastActivation = lastUsed.getOrDefault(playerId, 0L);

        if ((currentTime - lastActivation) / 1000 >= cooldown) {
            lastUsed.put(playerId, currentTime);

            player.setInvulnerable(true);
            player.sendMessage(ChatColor.GREEN + "God Protection activated! You are invincible for 3 seconds.");

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setInvulnerable(false);
                    player.sendMessage(ChatColor.RED + "God Protection has ended.");
                }
            }.runTaskLater(RPGPlugin.getInstance(), duration * 20L); // Convertir les secondes en ticks (1 seconde = 20 ticks)
        } else {
            long remainingTime = cooldown - ((currentTime - lastActivation) / 1000);
            player.sendMessage(ChatColor.RED + "God Protection is on cooldown for " + remainingTime + " seconds.");
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            // Vérifier si le joueur a cette compétence appliquée au corps
            BodyParts bodyParts = RPGPlugin.getInstance().getBodyManager().getBodyParts(player.getUniqueId());
            CustomSkill skill = bodyParts.getSkill(BodyPartType.BODY);
            if (skill != null && skill.getName().equalsIgnoreCase(this.name)) {
                if (player.getHealth() - event.getFinalDamage() <= 0) {
                    activateSkill(player);
                    event.setCancelled(true);
                }
            }
        }
    }
}
