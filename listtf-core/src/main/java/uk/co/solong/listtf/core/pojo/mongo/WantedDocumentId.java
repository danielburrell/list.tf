package uk.co.solong.listtf.core.pojo.mongo;

import java.io.Serializable;

public class WantedDocumentId implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2576092416513388613L;
    private String userId;
    private int defIndex;

    public WantedDocumentId(String userId, int defIndex) {
        this.userId = userId;
        this.defIndex = defIndex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDefIndex() {
        return defIndex;
    }

    public void setDefIndex(int defIndex) {
        this.defIndex = defIndex;
    }
}
