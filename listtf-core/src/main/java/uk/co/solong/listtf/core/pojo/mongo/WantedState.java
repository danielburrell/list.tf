package uk.co.solong.listtf.core.pojo.mongo;

public enum WantedState {

    wanted(0), completed(1), unwanted(2), unknown(3);
    
    private final int state;

    private WantedState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
    
}
