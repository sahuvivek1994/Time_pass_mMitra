package tech.inscripts.ins_armman.mMitra.data.model.syncing;

public class completeFiledForm {

    String name;
    String unique_id;

    public completeFiledForm(String name,String unique_id) {
        this.name = name;
        this.unique_id = unique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

}
