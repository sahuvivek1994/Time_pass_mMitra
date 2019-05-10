package tech.inscripts.ins_armman.mMitra.forms;

/**
 * Created by lenovo on 7/11/17.
 */

public class SpinnerItems {
    String id, value;

    public SpinnerItems(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
