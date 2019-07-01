package tech.inscripts.ins_armman.mMitra.data.model.syncing;

public class CompleteFormQnA {
String questionKeyword;
    String answerKeyword;

    String question;
    String answer;

    int form_id;
    String formName;
    String unique_id;
    String childNAme;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    String childId;

    public CompleteFormQnA() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public int getForm_id() {
        return form_id;
    }

    public void setForm_id(int form_id) {
        this.form_id = form_id;
    }

    public String getChildNAme() {
        return childNAme;
    }

    public void setChildNAme(String childNAme) {
        this.childNAme = childNAme;
    }


    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }


}
