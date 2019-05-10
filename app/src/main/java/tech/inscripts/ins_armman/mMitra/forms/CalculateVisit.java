package tech.inscripts.ins_armman.mMitra.forms;

/**
 * Created by lenovo on 14/11/2017.
 */
public class CalculateVisit {

    int FromWeek;
    String trimesterName;
    String visit_id;
    int ToWeek;
    String ANCVisit;

    public CalculateVisit(int fromWeek, int toWeek, String ANCVisit) {
        FromWeek = fromWeek;
        ToWeek = toWeek;
        this.ANCVisit = ANCVisit;
    }

    public CalculateVisit(int fromWeek, String visit_id, int toWeek, String ANCVisit, String trimesterName) {
        FromWeek = fromWeek;
        this.visit_id = visit_id;
        ToWeek = toWeek;
        this.ANCVisit = ANCVisit;
        this.trimesterName = trimesterName;
    }

    public String getTrimesterName() {
        return trimesterName;
    }

    public void setTrimesterName(String trimesterName) {
        this.trimesterName = trimesterName;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public int getFromWeek() {
        return FromWeek;
    }

    public void setFromWeek(int fromWeek) {
        FromWeek = fromWeek;
    }

    public int getToWeek() {
        return ToWeek;
    }

    public void setToWeek(int toWeek) {
        ToWeek = toWeek;
    }

    public String getANCVisit() {
        return ANCVisit;
    }

    public void setANCVisit(String ANCVisit) {
        this.ANCVisit = ANCVisit;
    }

    @Override
    public String toString() {
        return  "CalculateVisit{" +
                "ANCVisit='" + ANCVisit + '\'' +
                ", FromWeek=" + FromWeek +
                ", trimesterName='" + trimesterName + '\'' +
                ", visit_id='" + visit_id + '\'' +
                ", ToWeek=" + ToWeek +
                '}';
    }

}
