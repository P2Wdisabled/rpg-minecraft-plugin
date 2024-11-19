package fr.P2Wdisabled.rpgplugin.armors;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InfinityDeath extends CustomArmor implements Listener {

    public InfinityDeath(int armorClass, String rarity) {
        super("Infinity Death", Material.DIAMOND_CHESTPLATE, rarity, armorClass,
                "This armor automatically repairs itself each time it breaks, while improving by 2% on all points.",
                500, 100, 0, 0, 10000); // Résistance avant de casser : 10000 coups
    }

    @Override
    public ItemStack createItem() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + type);
        lore.add(ChatColor.DARK_GRAY + "Class: " + armorClass);
        lore.add("");
        lore.add(ChatColor.WHITE + description);
        lore.add("");
        lore.add(ChatColor.GREEN + "Defense: +" + defenseBonus);
        lore.add(ChatColor.GREEN + "Health: +" + healthBonus);
        meta.setLore(lore);

        // Gérer la durabilité
        if (durability == -1) {
            meta.setUnbreakable(true);
        } else {
            // Définir la durabilité initiale si nécessaire
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public String[] getPossibleRarities() {
        return new String[]{"Mythic"};
    }

    // Implémenter les événements spécifiques pour la réparation automatique et l'amélioration des stats
}
