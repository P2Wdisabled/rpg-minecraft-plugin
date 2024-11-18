package fr.P2Wdisabled.rpgplugin.stats;

import org.bukkit.entity.Entity;

public class Stats {

    private Entity entity;

    private int defense;
    private double strength; 
    private int agility;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int dexterity;

    // Points et expérience
    private int level;  
    private int xp;     
    private double statPercentageIncrease; 
    private int tier;       
    private int specialXp;  
    private int totalPoints;       // Total des points obtenus grâce au niveau et au tier
    private int availablePoints;   // Points encore disponibles à attribuer

    // Points attribués à chaque statistique
    private int healthPoints;
    private int defensePoints;
    private int strengthPoints;
    private int agilityPoints;
    private int manaPoints;
    private int dexterityPoints;

    // Incréments liés aux tiers et niveaux
    private static final int[] TIER_SPECIAL_XP_THRESHOLDS = {
        100000,   // Tier 1
        500000,   // Tier 2
        1000000,  // Tier 3
        1500000,  // Tier 4
        2000000,  // Tier 5
        3000000,  // Tier 6
        4500000,  // Tier 7
        6000000,  // Tier 8
        8000000,  // Tier 9
        15000000  // Tier 10
    };

    // Incréments des stats pour chaque Tier (TierStats)
    private static final TierStats[] TIER_STATS = {
        //          healthInc, defenseInc, manaInc, tierDamage, tierPoints
        new TierStats(1000,   500,     3000,   2000,   150),   // Tier 1
        new TierStats(2000,   1500,    5000,   5000,   200),   // Tier 2
        new TierStats(4000,   3000,    6000,   8000,   300),   // Tier 3
        new TierStats(6000,   5000,    8000,   15000,  500),   // Tier 4
        new TierStats(8000,   8000,    10000,  18000,  500),   // Tier 5
        new TierStats(10000,  10000,   10000,  20000,  500),   // Tier 6
        new TierStats(10000,  10000,   10000,  25000,  150),   // Tier 7
        new TierStats(10000,  10000,   10000,  30000,  500),   // Tier 8
        new TierStats(10000,  10000,   10000,  35000,  500),   // Tier 9
        new TierStats(100000, 50000,   20000,  100000, 1000)   // Tier 10
    };

    public Stats(Entity entity) {
        this.entity = entity;
        // Valeurs de base
        this.defense = 100;
        this.strength = 10;
        this.agility = 10;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.maxMana = 100;
        this.mana = maxMana;
        this.dexterity = 0;
        this.level = 0;
        this.xp = 0;
        this.statPercentageIncrease = 0.0;
        this.tier = 0;
        this.specialXp = 0;
        this.totalPoints = 0;
        this.availablePoints = 0;
        this.healthPoints = 0;
        this.defensePoints = 0;
        this.strengthPoints = 0;
        this.agilityPoints = 0;
        this.manaPoints = 0;
        this.dexterityPoints = 0;
        recalcStats();
    }

    public Entity getEntity() {
        return entity;
    }

    // Récupérateur pour le nombre total de points
    public int getTotalPoints() {
        return totalPoints;
    }

    // Définit le nombre total de points
    public void setTotalPoints(int totalPoints) {
        // Le nombre de points disponibles augmente de la différence
        int difference = totalPoints - this.totalPoints;
        this.totalPoints = totalPoints;
        this.availablePoints += difference; 
        recalcStats();
    }

    // Récupérateur pour les points disponibles
    public int getAvailablePoints() {
        return availablePoints;
    }

    // Points attribués à chaque statistique
    public int getHealthPoints() {
        return healthPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public int getStrengthPoints() {
        return strengthPoints;
    }

    public int getAgilityPoints() {
        return agilityPoints;
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public int getDexterityPoints() {
        return dexterityPoints;
    }

    // Méthode pour attribuer des points à une statistique spécifique
    public boolean allocatePoints(String stat, int pointsToAllocate) {
        if (availablePoints >= pointsToAllocate && pointsToAllocate > 0) {
            switch (stat.toLowerCase()) {
                case "health":
                    healthPoints += pointsToAllocate;
                    break;
                case "defense":
                    defensePoints += pointsToAllocate;
                    break;
                case "strength":
                    strengthPoints += pointsToAllocate;
                    break;
                case "agility":
                    agilityPoints += pointsToAllocate;
                    break;
                case "mana":
                    manaPoints += pointsToAllocate;
                    break;
                case "dexterity":
                    dexterityPoints += pointsToAllocate;
                    break;
                default:
                    return false; // Statistique invalide
            }
            availablePoints -= pointsToAllocate;
            recalcStats();
            return true;
        }
        return false;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        recalculateStatPercentageIncrease();
        recalcStats();
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
        recalculateLevel();
        recalculatePoints();
        recalculateStatPercentageIncrease();
        recalcStats();
    }

    public double getStatPercentageIncrease() {
        return statPercentageIncrease;
    }

    public void setStatPercentageIncrease(double statPercentageIncrease) {
        this.statPercentageIncrease = statPercentageIncrease;
        recalcStats();
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
        recalcStats(); 
    }

    public int getSpecialXp() {
        return specialXp;
    }

    public void setSpecialXp(int specialXp) {
        this.specialXp = specialXp;
        recalculateTier();
        recalcStats();
    }

    // Méthode pour consommer du mana
    public boolean consumeMana(int amount) {
        if (mana >= amount) {
            mana -= amount;
            return true;
        }
        return false;
    }

    // Méthode pour calculer les dégâts de base
    public double calculateBasicDamage() {
        double damage = (strength * mana) / Math.max(1, (double) defense);

        // Ajout du bonus de dégâts de base en fonction du Tier
        if (tier > 0 && tier <= TIER_STATS.length) {
            damage += TIER_STATS[tier - 1].tierDamage; 
        }

        return damage;
    }

    // Méthode pour recalculer les statistiques après tout changement
    public void recalcStats() {
        // Vérifie si tous les points sont alloués à une seule statistique
        boolean allPointsInOneStat = false;
        String statWithAllPoints = "";

        int[] allocatedPoints = {
            healthPoints, defensePoints, strengthPoints, agilityPoints, manaPoints, dexterityPoints
        };

        int nonZeroStats = 0;
        for (int points : allocatedPoints) {
            if (points > 0) {
                nonZeroStats++;
            }
        }

        if (nonZeroStats == 1 && totalPoints > 0) {
            allPointsInOneStat = true;
            // Trouver quelle stat a tous les points
            if (healthPoints > 0) statWithAllPoints = "health";
            else if (defensePoints > 0) statWithAllPoints = "defense";
            else if (strengthPoints > 0) statWithAllPoints = "strength";
            else if (agilityPoints > 0) statWithAllPoints = "agility";
            else if (manaPoints > 0) statWithAllPoints = "mana";
            else if (dexterityPoints > 0) statWithAllPoints = "dexterity";
        }

        // Calcul des statistiques de base en fonction des points attribués
        int baseMaxHealth = 100 + healthPoints * 5;
        int baseDefense = 100 + defensePoints * 5;
        double baseStrength = 10 + strengthPoints * 2.5;
        int baseAgility = 10 + agilityPoints * 2;
        int baseMaxMana = 100 + manaPoints * 10;
        int baseDexterity = dexterityPoints * 2;

        // Applique l'augmentation en pourcentage liée au niveau
        double levelMultiplier = 1 + (statPercentageIncrease / 100.0);
        baseMaxHealth = (int) (baseMaxHealth * levelMultiplier);
        baseDefense = (int) (baseDefense * levelMultiplier);
        baseMaxMana = (int) (baseMaxMana * levelMultiplier);
        baseDexterity = (int) (baseDexterity * levelMultiplier);
        baseStrength *= levelMultiplier;
        baseAgility = (int) (baseAgility * levelMultiplier);

        // Ajoute les bonus du Tier actuel (si le joueur a un Tier supérieur à 0)
        if (tier > 0 && tier <= TIER_STATS.length) {
            TierStats currentTierStats = TIER_STATS[tier - 1];
            baseMaxHealth += currentTierStats.healthInc;
            baseDefense += currentTierStats.defenseInc;
            baseMaxMana += currentTierStats.manaInc;
            // Les points de statistiques du Tier sont ajoutés au totalPoints
            int totalPointsFromTiers = currentTierStats.tierPoints;
            setTotalPoints(totalPoints + totalPointsFromTiers);
        }

        // Applique le bonus de spécialisation si tous les points sont dans une seule statistique
        if (allPointsInOneStat) {
            switch (statWithAllPoints) {
                case "health":
                    baseMaxHealth *= 2;
                    break;
                case "defense":
                    baseDefense *= 2;
                    break;
                case "strength":
                    baseStrength *= 2;
                    break;
                case "agility":
                    baseAgility *= 2;
                    break;
                case "mana":
                    baseMaxMana *= 2;
                    break;
                case "dexterity":
                    baseDexterity *= 2;
                    break;
            }
        }

        setMaxHealth(baseMaxHealth);
        setDefense(baseDefense);
        setMaxMana(baseMaxMana);
        setDexterity(baseDexterity);
        setStrength(baseStrength);
        setAgility(baseAgility);

        // Assure que la santé et le mana actuels ne dépassent pas leurs nouveaux maximums
        if (health > maxHealth) {
            health = maxHealth;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    // Méthode pour recalculer le niveau du joueur en fonction de son XP (système de niveaux)
    public void recalculateLevel() {
        int newLevel = 0;
        int requiredXpForNextLevel = 150; // XP requis pour le niveau 1
        int xpRemaining = xp;

        while (xpRemaining >= requiredXpForNextLevel) {
            xpRemaining -= requiredXpForNextLevel;
            newLevel++;
            requiredXpForNextLevel += (int) (requiredXpForNextLevel * 0.25);
        }

        if (newLevel != level) {
            setLevel(newLevel);
        }
    }

    // Méthode pour recalculer les points de statistiques du joueur en fonction de son niveau
    public void recalculatePoints() {
        int newTotalPoints = 0;
        for (int i = 1; i <= level; i++) {
            newTotalPoints += i;
        }
        // Total des points inclut désormais les points de tiers
        setTotalPoints(newTotalPoints); 
    }

    // Méthode pour recalculer l'augmentation en pourcentage des statistiques du joueur en fonction de son niveau
    public void recalculateStatPercentageIncrease() {
        double totalPercentage = 0.0;
        for (int i = 1; i <= level; i++) {
            if (i <= 9) {
                totalPercentage += 1;
            } else if (i <= 19) {
                totalPercentage += 2;
            } else if (i <= 29) {
                totalPercentage += 3;
            } else if (i <= 39) {
                totalPercentage += 4;
            } else if (i <= 49) {
                totalPercentage += 5;
            } else if (i <= 59) {
                totalPercentage += 6;
            } else if (i <= 69) {
                totalPercentage += 7;
            } else if (i <= 79) {
                totalPercentage += 8;
            } else if (i <= 89) {
                totalPercentage += 9;
            } else if (i <= 99) {
                totalPercentage += 10;
            } else if (i == 100) {
                totalPercentage += 12;
            }
        }
        setStatPercentageIncrease(totalPercentage);
    }

    // Méthode pour recalculer le Tier du joueur en fonction de son XP spéciale
    public void recalculateTier() {
        int currentTier = tier;
        while (currentTier < TIER_SPECIAL_XP_THRESHOLDS.length && specialXp >= TIER_SPECIAL_XP_THRESHOLDS[currentTier]) {
            specialXp -= TIER_SPECIAL_XP_THRESHOLDS[currentTier];
            currentTier++;
        }

        setTier(currentTier);
    }
}
