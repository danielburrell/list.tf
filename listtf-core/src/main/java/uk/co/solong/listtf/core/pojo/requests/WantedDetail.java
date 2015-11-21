package uk.co.solong.listtf.core.pojo.requests;

public class WantedDetail {
    private String id;
    private int defIndex;
    private Integer level;
    private Integer quality;
    private Boolean isTradable;
    private Boolean isCraftable;
    private Long craftNumber;
    private Boolean isNumbered;
    private Boolean isGifted;
    private String price;
    
   
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Boolean getIsTradable() {
        return isTradable;
    }

    public void setIsTradable(Boolean isTradable) {
        this.isTradable = isTradable;
    }

    public Boolean getIsCraftable() {
        return isCraftable;
    }

    public void setIsCraftable(Boolean isCraftable) {
        this.isCraftable = isCraftable;
    }

    public Long getCraftNumber() {
        return craftNumber;
    }

    public void setCraftNumber(Long craftNumber) {
        this.craftNumber = craftNumber;
    }

    public Boolean getIsNumbered() {
        return isNumbered;
    }

    public void setIsNumbered(Boolean isNumbered) {
        this.isNumbered = isNumbered;
    }

    public Boolean getIsGifted() {
        return isGifted;
    }

    public void setIsGifted(Boolean isGifted) {
        this.isGifted = isGifted;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDefIndex() {
        return defIndex;
    }

    public void setDefIndex(int defIndex) {
        this.defIndex = defIndex;
    }
}
