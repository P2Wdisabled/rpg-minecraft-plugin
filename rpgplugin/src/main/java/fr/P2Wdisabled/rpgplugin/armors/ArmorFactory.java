package fr.P2Wdisabled.rpgplugin.armors;

import java.util.Random;

public class ArmorFactory {

    // Méthode pour créer une armure spécifique
    public CustomArmor createArmor(String armorName, int armorClass, String rarity) {
        switch (armorName.toLowerCase()) {
            case "infinity death":
                return new InfinityDeath(armorClass, rarity);
            //case "angel armor":
            //    return new AngelArmor(armorClass, rarity);
            //case "demon's transformer":
            //    return new DemonsTransformer(armorClass, rarity);
            //case "used chestplate":
            //    return new UsedChestplate(armorClass, rarity);
            //case "soldier armor":
            //    return new SoldierArmor(armorClass, rarity);
            //case "dark cloak":
            //    return new DarkCloak(armorClass, rarity);
            default:
                return null;
        }
    }

    // Méthode pour générer une armure aléatoire basée sur une armure de base
    public CustomArmor generateRandomArmor(CustomArmor baseArmor) {
        String[] rarities = baseArmor.getPossibleRarities();
        Random random = new Random();
        int armorClass = random.nextInt(10) + 1; // Classe entre 1 et 10
        String rarity = rarities[random.nextInt(rarities.length)];

        return createArmor(baseArmor.getName(), armorClass, rarity);
    }
}
