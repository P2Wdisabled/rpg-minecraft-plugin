package fr.P2Wdisabled.rpgplugin.weapons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class WeaponManager {

    private Map<String, CustomWeapon> registeredWeapons = new HashMap<>();

    public void registerWeapon(CustomWeapon weapon) {
        registeredWeapons.put(weapon.getName().toLowerCase(), weapon);
    }

    public CustomWeapon getWeaponByName(String name) {
        return registeredWeapons.get(name.toLowerCase());
    }

    public CustomWeapon getWeaponFromItem(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return null;
        String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName()).toLowerCase();
        return registeredWeapons.get(itemName);
    }
}
