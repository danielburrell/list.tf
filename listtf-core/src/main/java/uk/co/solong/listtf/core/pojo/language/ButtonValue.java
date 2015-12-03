package uk.co.solong.listtf.core.pojo.language;

public class ButtonValue {
    private int id;
    private String text;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public ButtonValue() {
        // TODO Auto-generated constructor stub
    }
    public ButtonValue(int id, String text) {
        this.id = id;
        this.text = text;
    }
}
