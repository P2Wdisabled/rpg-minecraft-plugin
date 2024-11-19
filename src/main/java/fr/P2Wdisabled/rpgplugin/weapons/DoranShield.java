package fr.P2Wdisabled.rpgplugin.weapons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DoranShield extends CustomWeapon implements Listener {

    public DoranShield(int weaponClass, String rarity) {
        super("Doran's Shield", Material.SHIELD, rarity, weaponClass,
                "When you hold this shield in your off hand, you increase your defense to physical damage by 10% and ranged damage (excluding spells) does not work on you",
                2, 10000, 0, 10000);
    }

    @Override
    public ItemStack createItem() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + type + " Weapon");
        lore.add(ChatColor.DARK_GRAY + "Class: " + weaponClass);
        lore.add("");
        lore.add(ChatColor.WHITE + description);
        lore.add("");
        lore.add(ChatColor.GREEN + "Damage Bonus: +" + damageBonus);
        lore.add(ChatColor.GREEN + "Defense Bonus: +" + defenseBonus);
        meta.setLore(lore);
        // Gérer la durabilité
        if (durability == -1) {
            meta.setUnbreakable(true);
        } else if (meta instanceof Damageable) {
            ((Damageable) meta).setDamage(durability);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void getDamaged(PlayerItemDamageEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInOffHand();
        if (itemInHand != null && itemInHand.getType() == Material.SHIELD && itemInHand.getItemMeta().getDisplayName().equals("Doran's Shield")) {
            if (event.getDamage() > 0) {
                if ((int) event.getItem().getDurability() < 1) {
                    event.setCancelled(true);
                    ItemMeta meta = itemInHand.getItemMeta();
                    if (meta instanceof Damageable) {
                        durability = (int) (durability * 1.25);
                        ((Damageable) meta).setDamage(durability);
                    }
                    // Augmenter la défense de 10%
                    defenseBonus = (int) (defenseBonus + defenseBonus * 1.1);
                    player.sendMessage(ChatColor.GREEN + "Your Doran's Shield has regenerated and your defense increased by 10%!");
                }
            }
        };
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void playerDamaged(EntityDamageEvent event) {
    	if(event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
    		Player player = (Player) event.getEntity();
    		ItemStack itemInHand = player.getInventory().getItemInOffHand();
    		if (itemInHand!= null && itemInHand.getType() == Material.SHIELD && itemInHand.getItemMeta().getDisplayName().equals("Doran's Shield")) {
				event.setCancelled(true);
				itemInHand.setDurability((short) (itemInHand.getDurability() - 10));
			}
    	}
    }
    @Override
    public String[] getPossibleRarities() {
        return new String[]{"Mythic"};
    }
}
