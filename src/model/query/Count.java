package model.query;

public class Count {
    private String str;
    private int count;

    public Count (String str, int count){
        this.str = str;
        this.count = count;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
