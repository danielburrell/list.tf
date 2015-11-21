package uk.co.solong.listtf.core.pojo.mongo;

public class WantedDocumentDetail {
    private String wantedId;
    private Integer level;
    private Integer quality;
    private Boolean isTradable;
    private Boolean isCraftable;
    private Long craftNumber;
    private Boolean isNumbered;
    private Boolean isGifted;
    private String price;
    public String getWantedId() {
        return wantedId;
    }
    public void setWantedId(String wantedId) {
        this.wantedId = wantedId;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getQuality() {
        return quality;
    }
    public void setQuality(int quality) {
        this.quality = quality;
    }
    public Boolean isTradable() {
        return isTradable;
    }
    public void setTradable(Boolean isTradable) {
        this.isTradable = isTradable;
    }
    public Boolean isCraftable() {
        return isCraftable;
    }
    public void setCraftable(Boolean isCraftable) {
        this.isCraftable = isCraftable;
    }
    public Long getCraftNumber() {
        return craftNumber;
    }
    public void setCraftNumber(Long craftNumber) {
        this.craftNumber = craftNumber;
    }
    public Boolean isGifted() {
        return isGifted;
    }
    public void setGifted(Boolean isGifted) {
        this.isGifted = isGifted;
    }
    public Boolean isNumbered() {
        return isNumbered;
    }
    public void setNumbered(Boolean isNumbered) {
        this.isNumbered = isNumbered;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    
}
