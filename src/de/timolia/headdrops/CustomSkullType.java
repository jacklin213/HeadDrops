/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops;

public enum CustomSkullType {

    BLAZE("MHF_Blaze", "Blaze Head"),
    CAVE_SPIDER("MHF_CaveSpider", "Cave Spider Head"),
    CHICKEN("MHF_Chicken", "Chicken Head"),
    COW("MHF_Cow", "Cow Head"),
    ENDERMAN("MHF_Enderman", "Enderman Head"),
    GHAST("MHF_Ghast", "Ghast Head"),
    IRON_GOLEM("MHF_Golem", "Iron Golem Head"),
    MAGMA_CUBE("MHF_LavaSlime", "Magma Cube Head"),
    MUSHROOM_COW("MHF_MushroomCow", "Mushroom Cow Head"),
    OCELOT("MHF_Ocelot", "Ocelot Head"),
    PIG("MHF_Pig", "Pig Head"),
    PIG_ZOMBIE("MHF_PigZombie", "Pig Zombie Head"),
    SHEEP("MHF_Sheep", "Sheep Head"),
    SLIME("MHF_Slime", "Slime Head"),
    SPIDER("MHF_Spider", "Spider Head"),
    SQUID("MHF_Squid", "Squid Head"),
    VILLAGER("MHF_Villager", "Villager Head"),
    WITHER("MHF_Wither", "Wither Head");

    private String skin;
    private String displayName;

    private CustomSkullType(String skin, String displayName) {
        this.skin = skin;
        this.displayName = displayName;
    }

    public String getSkinName() {
        return this.skin;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public static CustomSkullType forSkinName(String skinName) {
        for (CustomSkullType t : values())
            if (t.getSkinName().equalsIgnoreCase(skinName))
                return t;

        return null;
    }

}