package tech.inscripts.ins_armman.mMitra.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 28/11/17.
 */

public class GraphPoint {
    @SerializedName("Year:Month")
    private String yearMonth;

    @SerializedName("Month")
    private Integer month;

    @SerializedName("-3 SD")
    private Float _3SD;

    @SerializedName("-2SD")
    private Float _2SD;

    @SerializedName("Median")
    private Float median;


    public String getYearMonth() {
        return yearMonth;
    }

    public Integer getMonth() {
        return month;
    }

    public Float get_3SD() {
        return _3SD;
    }

    public Float get_2SD() {
        return _2SD;
    }

    public Float getMedian() {
        return median;
    }
}
