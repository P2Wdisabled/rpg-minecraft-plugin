package fr.P2Wdisabled.rpgplugin.stats;

public class TierStats {
    public int healthInc;
    public int defenseInc;
    public int manaInc;
    public int tierDamage;
    public int tierPoints;

    public TierStats(int healthInc, int defenseInc, int manaInc, int tierDamage, int tierPoints) {
        this.healthInc = healthInc;
        this.defenseInc = defenseInc;
        this.manaInc = manaInc;
        this.tierDamage = tierDamage;
        this.tierPoints = tierPoints;
    }
}
