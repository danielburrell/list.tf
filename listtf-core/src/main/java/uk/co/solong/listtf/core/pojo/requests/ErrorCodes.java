package uk.co.solong.listtf.core.pojo.requests;

public enum ErrorCodes {
    SEARCH_FAILED(1);

    private int id;

    private ErrorCodes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
