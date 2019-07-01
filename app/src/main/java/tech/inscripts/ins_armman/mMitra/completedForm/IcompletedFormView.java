package tech.inscripts.ins_armman.mMitra.completedForm;

import android.content.Context;

import com.inscripts.ins_armman.npdsf.data.model.completeFiledForm;

import java.util.List;

public interface IcompletedFormView {

    void setAdapter(List<completeFiledForm> mWomenList);

    Context getContext();
}
