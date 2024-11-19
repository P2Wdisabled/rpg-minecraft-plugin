package fr.P2Wdisabled.rpgplugin.armors;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class ArmorManager {

    private Map<String, CustomArmor> registeredArmors = new HashMap<>();

    public void registerArmor(CustomArmor armor) {
        registeredArmors.put(armor.getName().toLowerCase(), armor);
    }

    public CustomArmor getArmorByName(String name) {
        return registeredArmors.get(name.toLowerCase());
    }

    public CustomArmor getArmorFromItem(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return null;
        String itemName = ChatColor.stripColor(item.getItemMeta().displayName().toString()).toLowerCase();
        return registeredArmors.get(itemName);
    }
}
