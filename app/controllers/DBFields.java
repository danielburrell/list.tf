package controllers;

public enum DBFields {
    STEAMID("steamId"),
    DETAILID("detailId"),
    WANTEDID("wantedId"),
    QUALITY("quality"),
    LEVELNUMBER("levelNumber"),
    ISTRADABLE("isTradable"),
    ISCRAFTABLE("isCraftable"),
    CRAFTNUMBER("craftNumber"),
    ISGIFTWRAPPED("isGiftWrapped"),
    ISOBTAINED("isObtained"),
    PRIORITY("priority"),
    PRICE("price");
    private final String name;

    DBFields(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
