package fr.P2Wdisabled.rpgplugin.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.P2Wdisabled.rpgplugin.armors.ArmorFactory;
import fr.P2Wdisabled.rpgplugin.armors.ArmorManager;
import fr.P2Wdisabled.rpgplugin.armors.CustomArmor;
import fr.P2Wdisabled.rpgplugin.skills.CustomSkill;
import fr.P2Wdisabled.rpgplugin.skills.SkillFactory;
import fr.P2Wdisabled.rpgplugin.skills.SkillManager;
import fr.P2Wdisabled.rpgplugin.weapons.CustomWeapon;
import fr.P2Wdisabled.rpgplugin.weapons.WeaponFactory;
import fr.P2Wdisabled.rpgplugin.weapons.WeaponManager;
import net.md_5.bungee.api.ChatColor;

public class MobManager {

    private Map<String, CustomMob> registeredMobs = new HashMap<>();

    private ArmorManager armorManager;
    private WeaponManager weaponManager;
    private WeaponFactory weaponFactory;
    private ArmorFactory armorFactory;
    private SkillFactory skillFactory;
    private SkillManager skillManager;

    // Constructeur pour injecter les gestionnaires
    public MobManager(ArmorManager armorManager, WeaponManager weaponManager, SkillManager skillManager, WeaponFactory weaponFactory, ArmorFactory armorFactory, SkillFactory skillFactory) {
        this.armorManager = armorManager;
        this.armorFactory = armorFactory;
        this.skillFactory = skillFactory;
        this.weaponManager = weaponManager;
        this.weaponFactory = weaponFactory;
        this.skillManager = skillManager;
    }

    public void registerMob(CustomMob mob) {
        registeredMobs.put(mob.getName().toLowerCase(), mob);
    }

    public CustomMob getMobByName(String name) {
        return registeredMobs.get(name.toLowerCase());
    }

    public ItemStack giveLoot(String lootName) {
        if (lootName == null || lootName.isEmpty()) {
            return null; // Ou vous pouvez lancer une exception selon votre logique
        }

        String key = lootName.toLowerCase();

        // Tenter de récupérer une arme
        CustomWeapon baseWeapon = weaponManager.getWeaponByName(key);
        if (baseWeapon != null) {
            CustomWeapon generatedWeapon = weaponFactory.generateRandomWeapon(baseWeapon);
            // Conversion du CustomWeapon en ItemStack
            
            return generateWeaponItemStack(generatedWeapon);
        }

        // Tenter de récupérer une armure
        CustomArmor baseArmor = armorManager.getArmorByName(key);
        if (baseArmor != null) {
            CustomArmor generatedArmor = armorFactory.generateRandomArmor(baseArmor);
            // Conversion du CustomArmor en ItemStack
            return generateArmorItemStack(generatedArmor);
        }

        // Tenter de récupérer une compétence
        CustomSkill baseSkill = skillManager.getSkillByName(key);
        if (baseSkill != null) {
            CustomSkill generatedSkill = skillFactory.generateRandomSkill(baseSkill);
            // Conversion du CustomSkill en ItemStack
            return generateSkillItemStack(generatedSkill);
        }

        // Si aucun objet correspondant n'est trouvé
        return null;
    }
    private ItemStack generateSkillItemStack(CustomSkill generatedSkill) {

    	// Création de l'ItemStack à partir du CustomSkill généré
    	ItemStack item = new ItemStack(Material.BOOK); // Ou tout autre matériel approprié
    	ItemMeta meta = item.getItemMeta();

    	if (meta != null) {
    	    meta.setDisplayName(ChatColor.RESET + generatedSkill.getName());

    	    List<String> lore = new ArrayList<>();
    	    lore.add(ChatColor.GRAY + "Type: " + generatedSkill.getType());
    	    lore.add(ChatColor.GRAY + "Classe: " + generatedSkill.getSkillClass());
    	    lore.add(ChatColor.GRAY + "Description: " + generatedSkill.getDescription());
    	    lore.add(ChatColor.GRAY + "Cooldown: " + generatedSkill.getCooldown() + "s");
    	    lore.add(ChatColor.GRAY + "Durée: " + generatedSkill.getDuration() + "s");
    	    lore.add(ChatColor.GRAY + "Applicable à: " + String.join(", ", generatedSkill.getApplicableTo()));

    	    meta.setLore(lore);

    	    // Ajouter un enchantement invisible pour l'effet visuel
    	    meta.addEnchant(Enchantment.LUCK, 1, true);
    	    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

    	    item.setItemMeta(meta);
    	}

    	return item;
    }
 // Méthode utilitaire pour déterminer le slot d'équipement
    private EquipmentSlot getEquipmentSlot(Material material) {
        if (material.name().endsWith("_HELMET")) {
            return EquipmentSlot.HEAD;
        } else if (material.name().endsWith("_CHESTPLATE")) {
            return EquipmentSlot.CHEST;
        } else if (material.name().endsWith("_LEGGINGS")) {
            return EquipmentSlot.LEGS;
        } else if (material.name().endsWith("_BOOTS")) {
            return EquipmentSlot.FEET;
        } else {
            return EquipmentSlot.HAND; // Par défaut
        }
    }

    private ItemStack generateArmorItemStack(CustomArmor generatedArmor) {

    	// Création de l'ItemStack à partir du CustomArmor généré
    	Material material = generatedArmor.getMaterial();
    	ItemStack item = new ItemStack(material);
    	ItemMeta meta = item.getItemMeta();

    	if (meta != null) {
    	    meta.setDisplayName(ChatColor.RESET + generatedArmor.getName());

    	    List<String> lore = new ArrayList<>();
    	    lore.add(ChatColor.GRAY + "Type: " + generatedArmor.getType());
    	    lore.add(ChatColor.GRAY + "Classe: " + generatedArmor.getArmorClass());
    	    lore.add(ChatColor.GRAY + "Description: " + generatedArmor.getDescription());
    	    lore.add(ChatColor.GRAY + "Défense Bonus: " + generatedArmor.getDefenseBonus());
    	    lore.add(ChatColor.GRAY + "Vie Bonus: " + generatedArmor.getHealthBonus());
    	    lore.add(ChatColor.GRAY + "Agilité Bonus: " + generatedArmor.getAgilityBonus());
    	    lore.add(ChatColor.GRAY + "Force Bonus: " + generatedArmor.getStrengthBonus());
    	    lore.add(ChatColor.GRAY + "Durabilité: " + (generatedArmor.getDurability() == -1 ? "Incassable" : generatedArmor.getDurability()));

    	    // Ajouter les compétences associées
    	    Map<String, CustomSkill> skills = generatedArmor.getSkills();
    	    if (!skills.isEmpty()) {
    	        lore.add(ChatColor.GOLD + "Compétences:");
    	        for (CustomSkill skill : skills.values()) {
    	            lore.add(ChatColor.AQUA + "- " + skill.getName());
    	        }
    	    }

    	    meta.setLore(lore);

    	    // Définir l'item comme incassable si nécessaire
    	    if (generatedArmor.getDurability() == -1) {
    	        meta.setUnbreakable(true);
    	    }

    	    // Appliquer les attributs supplémentaires
    	    // Défense
    	    if (generatedArmor.getDefenseBonus() != 0) {
    	        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", generatedArmor.getDefenseBonus(), AttributeModifier.Operation.ADD_NUMBER, getEquipmentSlot(material));
    	        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
    	    }

    	    // Vie
    	    if (generatedArmor.getHealthBonus() != 0) {
    	        AttributeModifier healthModifier = new AttributeModifier(UUID.randomUUID(), "generic.maxHealth", generatedArmor.getHealthBonus(), AttributeModifier.Operation.ADD_NUMBER, getEquipmentSlot(material));
    	        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, healthModifier);
    	    }

    	    // Agilité (vitesse de déplacement)
    	    if (generatedArmor.getAgilityBonus() != 0) {
    	        AttributeModifier speedModifier = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", generatedArmor.getAgilityBonus() * 0.01, AttributeModifier.Operation.ADD_SCALAR, getEquipmentSlot(material));
    	        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, speedModifier);
    	    }

    	    // Force (dégâts d'attaque)
    	    if (generatedArmor.getStrengthBonus() != 0) {
    	        AttributeModifier attackModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", generatedArmor.getStrengthBonus(), AttributeModifier.Operation.ADD_NUMBER, getEquipmentSlot(material));
    	        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackModifier);
    	    }

    	    // Cacher les attributs si souhaité
    	    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

    	    item.setItemMeta(meta);
    	}

return item;
    }
    
    private ItemStack generateWeaponItemStack(CustomWeapon weapon) {

    	// Création de l'ItemStack à partir du CustomWeapon généré
    	Material material = weapon.getMaterial();
    	ItemStack item = new ItemStack(material);
    	ItemMeta meta = item.getItemMeta();

    	if (meta != null) {
    	    meta.setDisplayName(ChatColor.RESET + weapon.getName());

    	    List<String> lore = new ArrayList<>();
    	    lore.add(ChatColor.GRAY + "Type: " + weapon.getType());
    	    lore.add(ChatColor.GRAY + "Classe: " + weapon.getWeaponClass());
    	    lore.add(ChatColor.GRAY + "Description: " + weapon.getDescription());
    	    lore.add(ChatColor.GRAY + "Dégâts Bonus: " + weapon.getDamageBonus());
    	    lore.add(ChatColor.GRAY + "Défense Bonus: " + weapon.getDefenseBonus());
    	    lore.add(ChatColor.GRAY + "Mana Bonus: " + weapon.getManaBonus());
    	    lore.add(ChatColor.GRAY + "Durabilité: " + (weapon.getDurability() == -1 ? "Incassable" : weapon.getDurability()));

    	    // Ajouter les compétences associées
    	    Map<String, CustomSkill> skills = weapon.getSkills();
    	    if (!skills.isEmpty()) {
    	        lore.add(ChatColor.GOLD + "Compétences:");
    	        for (CustomSkill skill : skills.values()) {
    	            lore.add(ChatColor.AQUA + "- " + skill.getName());
    	        }
    	    }

    	    meta.setLore(lore);

    	    // Définir l'item comme incassable si nécessaire
    	    if (weapon.getDurability() == -1) {
    	        meta.setUnbreakable(true);
    	    }

    	    // Appliquer les attributs supplémentaires
    	    // Dégâts d'attaque
    	    if (weapon.getDamageBonus() != 0) {
    	        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", weapon.getDamageBonus(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
    	        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
    	    }

    	    // Défense
    	    if (weapon.getDefenseBonus() != 0) {
    	        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", weapon.getDefenseBonus(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
    	        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
    	    }

    	    // Vous pouvez ajouter d'autres attributs si nécessaire

    	    // Cacher les attributs si souhaité
    	    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

    	    item.setItemMeta(meta);
    	}

    	return item;

    }

}
