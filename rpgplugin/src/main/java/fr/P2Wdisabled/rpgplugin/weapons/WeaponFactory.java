package fr.P2Wdisabled.rpgplugin.weapons;

import java.util.Random;

public class WeaponFactory {

    // Méthode pour créer une arme spécifique
    public CustomWeapon createWeapon(String weaponName, int weaponClass, String rarity) {
        switch (weaponName.toLowerCase()) {
            case "doran's shield":
                return new DoranShield(weaponClass, rarity);
            //case "poisoned dagger":
            //    return new PoisonedDagger(weaponClass, rarity);
            //case "death scythe":
            //    return new DeathScythe(weaponClass, rarity);
            //case "dimensional sword":
            //    return new DimensionalSword(weaponClass, rarity);
            //case "broken sword":
            //    return new BrokenSword(weaponClass, rarity);
            //case "assassin's dagger":
            //    return new AssassinsDagger(weaponClass, rarity);
            //case "basic shield":
            //    return new BasicShield(weaponClass, rarity);
            //case "training bow":
            //    return new TrainingBow(weaponClass, rarity);
            // Ajouter d'autres armes si nécessaire
            default:
                return null;
        }
    }

    // Méthode pour générer une arme aléatoire lors d'un drop, basée sur une arme spécifique
    public CustomWeapon generateRandomWeapon(CustomWeapon baseWeapon) {
        String[] rarities = baseWeapon.getPossibleRarities();
        Random random = new Random();
        int weaponClass = random.nextInt(10) + 1; // Classe entre 1 et 10
        String rarity = rarities[random.nextInt(rarities.length)];

        return createWeapon(baseWeapon.getName(), weaponClass, rarity);
    }
}
